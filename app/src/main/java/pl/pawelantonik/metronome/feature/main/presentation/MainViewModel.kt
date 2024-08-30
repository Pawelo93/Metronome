package pl.pawelantonik.metronome.feature.main.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.pawelantonik.metronome.common.BaseViewModel
import pl.pawelantonik.metronome.feature.main.domain.BpmRepository
import pl.pawelantonik.metronome.feature.main.domain.TickSettings
import pl.pawelantonik.metronome.feature.main.domain.TickSettingsRepository
import pl.pawelantonik.metronome.feature.main.presentation.counter.BpmDeltaValue
import pl.pawelantonik.metronome.feature.service.domain.IsMetronomeRunningRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val bpmRepository: BpmRepository,
  private val tickSettingsRepository: TickSettingsRepository,
  private val isMetronomeRunningRepository: IsMetronomeRunningRepository,
) : BaseViewModel() {

  private val _uiState = MutableStateFlow(UiState.initial())
  val uiState = _uiState.asStateFlow()

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

    viewModelScope.launch {
      isMetronomeRunningRepository.observe().collectLatest { isRunning ->
        _uiState.update { it.copy(isRunning = isRunning) }
      }
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
  }

  fun onSelectBpmDeltaValue(bpmDeltaValue: BpmDeltaValue) {
    bpmRepository.saveDelta(bpmDeltaValue.value)
    _uiState.update { it.copy(selectedBpmDeltaValue = bpmDeltaValue) }
  }

  fun onUpdateTickSettings(tickSettings: TickSettings?) {
    tickSettingsRepository.save(tickSettings)
  }

  data class UiState(
    val isRunning: Boolean,
    val bpm: Int,
    val selectedBpmDeltaValue: BpmDeltaValue,
    val bpmDeltaValues: List<BpmDeltaValue>,
//    val tickSettings: TickSettings?,
  ) {
    companion object {
      fun initial() = UiState(
        isRunning = false,
        bpm = 60,
        selectedBpmDeltaValue = BpmDeltaValue.TWO,
        bpmDeltaValues = BpmDeltaValue.entries,
//        tickSettings = null,
      )
    }

    val selectedBpmDelta: Int
      get() = this.selectedBpmDeltaValue.value

    // doubles
    val intervalMs: Long
      get() = (60000.0 / bpm).toLong()
  }
}
