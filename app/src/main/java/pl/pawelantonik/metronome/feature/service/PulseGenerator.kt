package pl.pawelantonik.metronome.feature.service

import android.annotation.SuppressLint
import android.util.Log
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.pawelantonik.metronome.feature.counter.domain.BpmObserver
import pl.pawelantonik.metronome.feature.main.domain.AccentSettings
import pl.pawelantonik.metronome.feature.main.domain.AccentSettingsRepository
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
  private val bpmObserver: BpmObserver,
  private val accentSettingsRepository: AccentSettingsRepository,
) : PulseGenerator {
  private val coroutineScope = MainScope()
  private var job: Job? = null

  private val Int.intervalMs: Long
    get() = (60000.0 / this).toLong()

  private val pulseFlow = MutableStateFlow<Pulse?>(null)

  private var runnerBackgroundThread: Thread? = null
  private var isRunning = true

  override fun start(): Flow<Pulse?> {
    run()
    return pulseFlow
  }

  private fun run() {
    job?.cancel()

    job = coroutineScope.launch {
      accentSettingsRepository.observe()
        .collectLatest { settings ->
          runnerBlock(settings)
        }
    }
  }

  override fun observe(): Flow<Pulse?> {
    return pulseFlow
  }

  override fun stop() {
    isRunning = false

    runnerBackgroundThread?.join()
    runnerBackgroundThread = null

    pulseFlow.value = null
  }

  @SuppressLint("DiscouragedApi")
  private fun runnerBlock(settings: AccentSettings?) {
    stop()
    var counterValue = 0

    isRunning = true
    runnerBackgroundThread = Thread {
      var currentTime = System.currentTimeMillis()
      while (isRunning) {
        try {
          val bpm = bpmObserver.get()

          if (currentTime + bpm.intervalMs <= System.currentTimeMillis() || counterValue == 0) {
            if (counterValue > 0) {
              currentTime += bpm.intervalMs
            }
            counterValue++

            if (settings != null && counterValue > settings.bits) {
              counterValue = 1
            }

            if (counterValue == 1) {
              pulseFlow.update { Pulse(true, counterValue) }
            } else {
              pulseFlow.update { Pulse(false, counterValue) }
            }
          }

          Thread.sleep(0, 100)
        } catch (e: InterruptedException) {
          Log.e("ThreadExample", "Thread interrupted", e)
        }
      }
    }.apply { start() }
  }
}