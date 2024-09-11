package pl.pawelantonik.metronome.feature.counter.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import pl.pawelantonik.metronome.common.BaseViewModel
import pl.pawelantonik.metronome.feature.counter.domain.CounterSettingsRepository
import pl.pawelantonik.metronome.feature.service.PulseGenerator
import javax.inject.Inject

@HiltViewModel
class CounterViewModel @Inject constructor(
  private val counterSettingsRepository: CounterSettingsRepository,
  private val pulseGenerator: PulseGenerator,
) : BaseViewModel() {

  private val _uiState = MutableStateFlow(UiState.initial())
  val uiState = _uiState.asStateFlow()

  fun load() = launchWithDefaultErrorHandler {
    counterSettingsRepository.observe().collectLatest { isEnabled ->
      _uiState.update { it.copy(isCounterEnabled = isEnabled) }

      if (isEnabled) {
        pulseGenerator.observe().collectLatest { pulse ->
          pulse?.let {
            _uiState.update { it.copy(counterText = pulse.counter.toString()) }
          }
        }
      }
    }
  }

  fun toggleCounter() {
    val currentState = _uiState.value.isCounterEnabled
    val newCounterState = !currentState

    counterSettingsRepository.save(newCounterState)
    _uiState.update { it.copy(isCounterEnabled = newCounterState) }
  }

  data class UiState(
    val isCounterEnabled: Boolean,
    val counterText: String,
  ) {
    companion object {
      fun initial() = UiState(
        isCounterEnabled = false,
        counterText = "",
      )
    }
  }
}
