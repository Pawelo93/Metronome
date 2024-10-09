package pl.pawelantonik.metronome.feature.main.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.pawelantonik.metronome.common.BaseViewModel
import pl.pawelantonik.metronome.feature.accelerateBpm.domain.AccelerationPulseCollector
import pl.pawelantonik.metronome.feature.accelerateBpm.domain.AccelerationRepository
import pl.pawelantonik.metronome.feature.counter.domain.AcceleratedBpm
import pl.pawelantonik.metronome.feature.counter.domain.BpmObserver
import pl.pawelantonik.metronome.feature.counter.domain.BpmSaver
import pl.pawelantonik.metronome.feature.settings.domain.BpmRepository
import pl.pawelantonik.metronome.feature.main.presentation.counter.BpmDeltaValue
import pl.pawelantonik.metronome.feature.service.domain.IsMetronomeRunningRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val bpmObserver: BpmObserver,
  private val bpmSaver: BpmSaver,
  private val bpmRepository: BpmRepository,
  private val isMetronomeRunningRepository: IsMetronomeRunningRepository,
  private val accelerationRepository: AccelerationRepository,
  private val accelerationPulseCollector: AccelerationPulseCollector,
) : BaseViewModel() {

  private val _uiState = MutableStateFlow(UiState.initial())
  val uiState = _uiState
    .onStart { load() }
    .stateIn(
      viewModelScope,
      SharingStarted.Lazily,
      UiState.initial(),
    )

  val shouldStartService: Flow<Boolean>
    get() = _uiState.map { it.shouldStartService }.filterNotNull().distinctUntilChanged()

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

  private fun load() {
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

    viewModelScope.launch {
      accelerationPulseCollector.collect()
    }
  }

  fun onToggleRunning() {
    val isRunning = !_uiState.value.isRunning

    if (!isRunning) {
      cancelAcceleration()
    }

    _uiState.update { it.copy(shouldStartService = it.shouldStartService?.not() ?: true) }
  }

  private fun cancelAcceleration() {
    accelerationRepository.clear()
  }

  data class UiState(
    val isRunning: Boolean,
    val shouldStartService: Boolean?,
    val acceleratedBpm: AcceleratedBpm,
    val selectedBpmDeltaValue: BpmDeltaValue,
    val bpmDeltaValues: List<BpmDeltaValue>,
  ) {
    companion object {
      fun initial() = UiState(
        isRunning = false,
        shouldStartService = null,
        acceleratedBpm = AcceleratedBpm(60, false),
        selectedBpmDeltaValue = BpmDeltaValue.TWO,
        bpmDeltaValues = BpmDeltaValue.entries,
      )
    }

    val selectedBpmDelta: Int
      get() = this.selectedBpmDeltaValue.value

    val intervalMs: Long
      get() = (60000.0 / acceleratedBpm.value).toLong()
  }
}
