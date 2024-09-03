package pl.pawelantonik.metronome.feature.service

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.pawelantonik.metronome.feature.main.SoundPlayer
import pl.pawelantonik.metronome.feature.main.domain.BpmRepository
import pl.pawelantonik.metronome.feature.main.domain.TickSettings
import pl.pawelantonik.metronome.feature.main.domain.TickSettingsRepository
import pl.pawelantonik.metronome.feature.main.domain.TimeCounter
import javax.inject.Inject

class Ticker @Inject constructor(
  private val timeCounter: TimeCounter,
  private val bpmRepository: BpmRepository,
  private val tickSettingsRepository: TickSettingsRepository,
  private val soundPlayer: SoundPlayer,
  @ApplicationContext private val context: Context,
) {
  private var counterJob: Job? = null
  private val coroutineScope = MainScope()

  private val _tickState = MutableStateFlow(TickState.disabled())

  init {
    soundPlayer.init(context)
  }

  fun start() {
    counterJob?.cancel()
    counterJob = coroutineScope.launch {
      val settings = tickSettingsRepository.get()
      bpmRepository.observeBpm().collectLatest { bpm ->
        _tickState.update { TickState(1, settings) }

        timeCounter.count(bpm.intervalMs).collectLatest {
          _tickState.update { it.nextBeat() }
          soundPlayer.play(_tickState.value.isAccentBeat)
          println("SOUND - isAccent ${_tickState.value.isAccentBeat}")
        }
      }
    }
  }

  fun stop() {
    counterJob?.cancel()
    _tickState.update { TickState.disabled() }
  }

  private val Int.intervalMs: Long
    get() = (60000.0 / this).toLong()

  data class TickState(
    val currentBit: Int,
    val tickSettings: TickSettings?,
  ) {
    companion object {
      fun disabled() = TickState(1, null)
    }

    fun nextBeat(): TickState {
      return TickState(
        currentBit = when (tickSettings != null) {
          true -> when (currentBit < (tickSettings.bits)) {
            true -> currentBit + 1
            false -> 1
          }
          false -> currentBit + 1
        } ,
        tickSettings = tickSettings,
      )
    }

    val isAccentBeat: Boolean
      get() = currentBit == 1
  }
}