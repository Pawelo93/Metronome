package pl.pawelantonik.metronome.feature.settings.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import pl.pawelantonik.metronome.feature.counter.presentation.CounterViewModel

@Composable
fun SettingsView(
  settingsViewModel: SettingsViewModel,
  counterViewModel: CounterViewModel,
  onTickSettingsClicked: () -> Unit,
  onCounterSettingsClicked: () -> Unit,
  onAccelerateBpmClicked: () -> Unit,
  onVibrationClicked: () -> Unit,
) {
  val settingsUiState by settingsViewModel.uiState.collectAsState()
  val counterUiState by counterViewModel.uiState.collectAsState()

  BottomOptions(
    accentSettings = settingsUiState.accentSettings,
    isCounterEnabled = counterUiState.isCounterEnabled,
    accelerateSettings = settingsUiState.accelerateSettings,
    isVibrationEnabled = settingsUiState.isVibrationEnabled,
    onTickSettingsClicked = onTickSettingsClicked,
    onCounterSettingsClicked = onCounterSettingsClicked,
    onAccelerateBpmClicked = onAccelerateBpmClicked,
    onVibrationClicked = onVibrationClicked,
  )
}