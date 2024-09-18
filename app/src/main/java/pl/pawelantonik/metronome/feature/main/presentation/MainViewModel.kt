package pl.pawelantonik.metronome.feature.main.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.pawelantonik.metronome.common.BaseViewModel
import pl.pawelantonik.metronome.feature.counter.domain.AcceleratedBpm
import pl.pawelantonik.metronome.feature.counter.domain.BpmObserver
import pl.pawelantonik.metronome.feature.counter.domain.BpmSaver
import pl.pawelantonik.metronome.feature.main.domain.BpmRepository
import pl.pawelantonik.metronome.feature.main.presentation.counter.BpmDeltaValue
import pl.pawelantonik.metronome.feature.service.domain.IsMetronomeRunningRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val bpmObserver: BpmObserver,
  private val bpmSaver: BpmSaver,
  private val bpmRepository: BpmRepository,
  private val isMetronomeRunningRepository: IsMetronomeRunningRepository,
) : BaseViewModel() {

  private val _uiState = MutableStateFlow(UiState.initial())
  val uiState = _uiState.asStateFlow()

  fun load() {
    val bpmDelta = bpmRepository.getDelta()
    val selectedBpmDelta = BpmDeltaValue.entries.firstOrNull { it.value == bpmDelta }
    _uiState.update {
      it.copy(
        selectedBpmDeltaValue = selectedBpmDelta ?: BpmDeltaValue.FIVE,
      )
    }

    viewModelScope.launch {
      bpmObserver.observe().collectLatest { bpm ->
        _uiState.update { it.copy(acceleratedBpm = bpm) }
      }
    }

    viewModelScope.launch {
      isMetronomeRunningRepository.observe().collectLatest { isRunning ->
        _uiState.update { it.copy(isRunning = isRunning) }
      }
    }
  }

  fun onChangeBpmByAdding() {
    onChangeBpm(_uiState.value.acceleratedBpm.value + _uiState.value.selectedBpmDelta)
  }

  fun onChangeBpmBySubtract() {
    onChangeBpm(_uiState.value.acceleratedBpm.value - _uiState.value.selectedBpmDelta)
  }

  fun onChangeBpm(newBpm: Int) {
    bpmSaver.save(newBpm)
  }

  fun onSelectBpmDeltaValue(bpmDeltaValue: BpmDeltaValue) {
    bpmRepository.saveDelta(bpmDeltaValue.value)
    _uiState.update { it.copy(selectedBpmDeltaValue = bpmDeltaValue) }
  }

  data class UiState(
    val isRunning: Boolean,
    val acceleratedBpm: AcceleratedBpm,
    val selectedBpmDeltaValue: BpmDeltaValue,
    val bpmDeltaValues: List<BpmDeltaValue>,
  ) {
    companion object {
      fun initial() = UiState(
        isRunning = false,
        acceleratedBpm = AcceleratedBpm(60, false),
        selectedBpmDeltaValue = BpmDeltaValue.TWO,
        bpmDeltaValues = BpmDeltaValue.entries,
      )
    }

    val selectedBpmDelta: Int
      get() = this.selectedBpmDeltaValue.value

    // doubles
    val intervalMs: Long
      get() = (60000.0 / acceleratedBpm.value).toLong()
  }
}
