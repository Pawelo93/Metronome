package pl.pawelantonik.metronome.feature.service

import android.annotation.SuppressLint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.pawelantonik.metronome.feature.main.domain.BpmRepository
import pl.pawelantonik.metronome.feature.main.domain.TickSettingsRepository
import java.util.TimerTask
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

data class Pulse(
  val isAccent: Boolean,
  val counter: Int,
)

interface PulseGenerator {
  fun start(): Flow<Pulse?>
  fun observe(): Flow<Pulse?>
  fun stop()
}

@Singleton
class PulseGeneratorImpl @Inject constructor(
  private val bpmRepository: BpmRepository,
  private val tickSettingsRepository: TickSettingsRepository,
) : PulseGenerator {

  private var scheduler: ScheduledExecutorService? = null
  private var scheduledFuture: ScheduledFuture<*>? = null
  private val coroutineScope = MainScope()
  private var job: Job? = null

  private val Int.intervalMs: Long
    get() = (60000.0 / this).toLong()

  private val pulseFlow = MutableStateFlow<Pulse?>(null)

  override fun start(): Flow<Pulse?> {
    run()
    return pulseFlow
  }

  @SuppressLint("DiscouragedApi")
  private fun run() {
    job?.cancel()

    job = coroutineScope.launch {
      tickSettingsRepository.observe()
        .combine(bpmRepository.observeBpm()) { settings, bpm -> Pair(settings, bpm) }
        .collectLatest { (settings, bpm) ->
          stop()
          var counterValue = 0
          scheduler = Executors.newScheduledThreadPool(1)
          scheduledFuture = scheduler?.scheduleAtFixedRate(
            object : TimerTask() {
              override fun run() {
                counterValue++
                if (settings != null && counterValue > settings.bits) {
                  counterValue = 1
                }

                // generate pulse
                if (counterValue == 1) {
                  pulseFlow.update { Pulse(true, counterValue) }
                } else {
                  pulseFlow.update { Pulse(false, counterValue) }
                }
              }
            },
            0, bpm.intervalMs, TimeUnit.MILLISECONDS,
          )
        }
    }
  }

  override fun observe(): Flow<Pulse?> {
    return pulseFlow
  }

  override fun stop() {
    if (scheduledFuture?.isCancelled == false) {
      scheduledFuture?.cancel(true)
    }
    if (scheduler?.isShutdown == false) {
      scheduler?.shutdown()
    }

    pulseFlow.value = null
  }
}