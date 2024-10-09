package pl.pawelantonik.metronome.feature.counter.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import pl.pawelantonik.metronome.common.BaseViewModel
import pl.pawelantonik.metronome.feature.settings.domain.CounterSettingsRepository
import pl.pawelantonik.metronome.feature.service.PulseGenerator
import javax.inject.Inject

@HiltViewModel
class CounterViewModel @Inject constructor(
  private val counterSettingsRepository: CounterSettingsRepository,
  private val pulseGenerator: PulseGenerator,
) : BaseViewModel() {

  private val _uiState = MutableStateFlow(UiState.initial())
  val uiState = _uiState
    .onStart { load() }
    .stateIn(
      viewModelScope,
      SharingStarted.Lazily,
      UiState.initial(),
    )

  val counterText: StateFlow<String?> = _uiState
    .map {
      when (it.isCounterEnabled) {
        true -> it.pulseCounter.toString()
        false -> null
      }
    }
    .stateIn(
      viewModelScope,
      SharingStarted.Lazily,
      null,
    )

  fun toggleCounter() {
    val currentState = _uiState.value.isCounterEnabled
    val newCounterState = !currentState

    counterSettingsRepository.save(newCounterState)
    _uiState.update { it.copy(isCounterEnabled = newCounterState) }
  }

  private fun load() = launchWithDefaultErrorHandler {
    counterSettingsRepository.observe().collectLatest { isEnabled ->
      _uiState.update { it.copy(isCounterEnabled = isEnabled) }

      if (isEnabled) {
        pulseGenerator.observe().collectLatest { pulse ->
          pulse?.let {
            _uiState.update { it.copy(pulseCounter = pulse.counter) }
          }
        }
      }
    }
  }

  data class UiState(
    val isCounterEnabled: Boolean,
    val pulseCounter: Int,
  ) {
    companion object {
      fun initial() = UiState(
        isCounterEnabled = false,
        pulseCounter = 0,
      )
    }
  }
}
