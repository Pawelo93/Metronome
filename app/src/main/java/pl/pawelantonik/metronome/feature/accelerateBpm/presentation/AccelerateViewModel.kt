package pl.pawelantonik.metronome.feature.accelerateBpm.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import pl.pawelantonik.metronome.common.BaseViewModel
import pl.pawelantonik.metronome.feature.accelerateBpm.domain.AccelerateSettingsRepository
import pl.pawelantonik.metronome.feature.accelerateBpm.domain.AccelerateUseCase
import pl.pawelantonik.metronome.feature.accelerateBpm.domain.AccelerationRepository
import pl.pawelantonik.metronome.feature.service.PulseGenerator
import javax.inject.Inject

@HiltViewModel
class AccelerateViewModel @Inject constructor(
  private val pulseGenerator: PulseGenerator,
  private val accelerateSettingsRepository: AccelerateSettingsRepository,
  private val accelerateUseCase: AccelerateUseCase,
  private val accelerationRepository: AccelerationRepository,
) : BaseViewModel() {

  private val _uiState = MutableStateFlow(UiState.initial())
  val uiState = _uiState.asStateFlow()

  fun load() = launchWithDefaultErrorHandler {
    accelerateSettingsRepository.observe().collectLatest { accelerateSettings ->
      _uiState.update { it.copy(isAccelerationEnabled = accelerateSettings != null) }

      if (accelerateSettings != null) {
        pulseGenerator.observe().collectLatest { pulse ->
          pulse?.let {
            accelerateUseCase.execute()
          }
        }
      }
    }
  }

  fun cancelAcceleration() {
    accelerationRepository.clear()
  }

  data class UiState(
    val isAccelerationEnabled: Boolean,
  ) {
    companion object {
      fun initial() = UiState(
        isAccelerationEnabled = false,
      )
    }
  }
}
