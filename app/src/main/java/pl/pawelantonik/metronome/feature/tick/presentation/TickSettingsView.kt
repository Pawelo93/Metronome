package pl.pawelantonik.metronome.feature.tick.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun TickSettingsView(
  tickSettingsViewModel: TickSettingsViewModel,
  onTickSettingsClicked: () -> Unit,
) {
  val uiState by tickSettingsViewModel.uiState.collectAsState()

  BottomOptions(
   tickSettings = uiState.tickSettings,
    onTickSettingsClicked = onTickSettingsClicked,
  )
}