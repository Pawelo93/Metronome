package pl.pawelantonik.metronome.feature.tick.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import pl.pawelantonik.metronome.feature.counter.presentation.CounterViewModel

@Composable
fun TickSettingsView(
  tickSettingsViewModel: TickSettingsViewModel,
  counterViewModel: CounterViewModel,
  onTickSettingsClicked: () -> Unit,
  onCounterSettingsClicked: () -> Unit,
) {
  val uiState by tickSettingsViewModel.uiState.collectAsState()
  val counterUiState by counterViewModel.uiState.collectAsState()

  BottomOptions(
   tickSettings = uiState.tickSettings,
    isCounterEnabled = counterUiState.isCounterEnabled,
    onTickSettingsClicked = onTickSettingsClicked,
    onCounterSettingsClicked = onCounterSettingsClicked,
  )
}