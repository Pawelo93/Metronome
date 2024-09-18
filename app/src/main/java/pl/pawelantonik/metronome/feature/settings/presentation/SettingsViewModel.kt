package pl.pawelantonik.metronome.feature.settings.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.pawelantonik.metronome.common.BaseViewModel
import pl.pawelantonik.metronome.feature.accelerateBpm.domain.AccelerateSettings
import pl.pawelantonik.metronome.feature.accelerateBpm.domain.AccelerateSettingsRepository
import pl.pawelantonik.metronome.feature.accelerateBpm.domain.AccelerationSwitcher
import pl.pawelantonik.metronome.feature.main.domain.AccentSettings
import pl.pawelantonik.metronome.feature.main.domain.AccentSettingsRepository
import pl.pawelantonik.metronome.feature.settings.domain.IsVibrationEnabledRepository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
  private val accentSettingsRepository: AccentSettingsRepository,
  private val accelerateSettingsRepository: AccelerateSettingsRepository,
  private val isVibrationEnabledRepository: IsVibrationEnabledRepository,
  private val accelerationSwitch: AccelerationSwitcher,
) : BaseViewModel() {

  private val _uiState = MutableStateFlow(UiState.initial())
  val uiState = _uiState.asStateFlow()

  fun load() {
    _uiState.update {
      UiState(
        accentSettings = accentSettingsRepository.get(),
        accelerateSettings = accelerateSettingsRepository.get(),
        isVibrationEnabled = isVibrationEnabledRepository.get(),
      )
    }
  }

  fun onUpdateTickSettings(accentSettings: AccentSettings?) {
    accentSettingsRepository.save(accentSettings)
    var newState = _uiState.value
    if (accentSettings == null) {
      accelerationSwitch.switch(null)
      newState = newState.copy(accelerateSettings = null)
    }
    _uiState.update { newState.copy(accentSettings = accentSettings) }
  }

  fun onUpdateAccelerateSettings(accelerateSettings: AccelerateSettings?) {
    accelerationSwitch.switch(accelerateSettings)
    val accentSettings = accentSettingsRepository.get()
    _uiState.update {
      it.copy(
        accelerateSettings = accelerateSettings,
        accentSettings = accentSettings,
      )
    }
  }

  fun onToggleIsVibrationEnabled() {
    val newIsVibrationEnabled = _uiState.value.isVibrationEnabled.not()
    isVibrationEnabledRepository.save(newIsVibrationEnabled)
    _uiState.update { it.copy(isVibrationEnabled = newIsVibrationEnabled) }
  }

  data class UiState(
    val accentSettings: AccentSettings?,
    val accelerateSettings: AccelerateSettings?,
    val isVibrationEnabled: Boolean,
  ) {

    val accelerateSettingsOrDefault: AccelerateSettings
      get() = accelerateSettings ?: AccelerateSettings(2, 1)

    companion object {
      fun initial() = UiState(
        accentSettings = null,
        accelerateSettings = null,
        isVibrationEnabled = false,
      )
    }
  }
}
