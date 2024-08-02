package pl.pawelantonik.metronome.feature.main.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.pawelantonik.metronome.common.BaseViewModel
import pl.pawelantonik.metronome.feature.main.domain.BpmRepository
import pl.pawelantonik.metronome.feature.main.domain.TickSettings
import pl.pawelantonik.metronome.feature.main.domain.TickSettingsRepository
import pl.pawelantonik.metronome.feature.main.domain.TimeCounter
import pl.pawelantonik.metronome.feature.main.presentation.counter.BpmDeltaValue
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val timeCounter: TimeCounter,
  private val bpmRepository: BpmRepository,
  private val tickSettingsRepository: TickSettingsRepository,
) : BaseViewModel() {

  private val _uiState = MutableStateFlow(UiState.initial())
  val uiState = _uiState.asStateFlow()

  private val _tick = MutableStateFlow(TickState.disabled())
  val tick = _tick.asStateFlow()

  private var counterJob: Job? = null

  fun load() {
    val bpm = bpmRepository.getBpm()
    val bpmDelta = bpmRepository.getDelta()
    val selectedBpmDelta = BpmDeltaValue.entries.firstOrNull { it.value == bpmDelta }
    _uiState.update {
      it.copy(
        bpm = bpm,
        selectedBpmDeltaValue = selectedBpmDelta ?: BpmDeltaValue.FIVE,
      )
    }

    tickSettingsRepository.get()?.let { tickSettings ->
      onUpdateTickSettings(tickSettings)
    }
  }

  fun onChangeBpmByAdding() {
    onChangeBpm(_uiState.value.bpm + _uiState.value.selectedBpmDelta)
  }

  fun onChangeBpmBySubtract() {
    onChangeBpm(_uiState.value.bpm - _uiState.value.selectedBpmDelta)
  }

  fun onChangeBpm(newBpm: Int) {
    bpmRepository.saveBpm(newBpm)
    _uiState.update { it.copy(bpm = newBpm) }

    if (_uiState.value.isRunning) {
      startCounter()
    }
  }

  fun onSelectBpmDeltaValue(bpmDeltaValue: BpmDeltaValue) {
    bpmRepository.saveDelta(bpmDeltaValue.value)
    _uiState.update { it.copy(selectedBpmDeltaValue = bpmDeltaValue) }
  }

  fun onUpdateIsRunning(isRunning: Boolean) = viewModelScope.launch {
    if (isRunning) {
      startCounter()
    } else {
      stopCounter()
    }
    _uiState.update { it.copy(isRunning = isRunning) }
  }

  fun onUpdateTickSettings(tickSettings: TickSettings?) {
    tickSettingsRepository.save(tickSettings)
    _tick.update { it.copy(tickSettings = tickSettings) }
  }

  private fun startCounter() {
    counterJob?.cancel()
    counterJob = viewModelScope.launch {
      _tick.update { it.copy(currentBit = 1) }
      timeCounter.count(_uiState.value.intervalMs).collectLatest {
        _tick.update { it.nextBeat() }
      }
    }
  }

  private fun stopCounter() {
    counterJob?.cancel()
  }

  data class UiState(
    val isRunning: Boolean,
    val bpm: Int,
    val selectedBpmDeltaValue: BpmDeltaValue,
    val bpmDeltaValues: List<BpmDeltaValue>,
  ) {
    companion object {
      fun initial() = UiState(
        isRunning = false,
        bpm = 60,
        selectedBpmDeltaValue = BpmDeltaValue.TWO,
        bpmDeltaValues = BpmDeltaValue.entries,
      )
    }

    val intervalMs: Long
      get() = (60000.0 / bpm).toLong()

    val selectedBpmDelta: Int
      get() = this.selectedBpmDeltaValue.value
  }

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
