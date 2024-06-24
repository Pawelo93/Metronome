package pl.pawelantonik.metronome

import androidx.compose.runtime.Composable
import pl.pawelantonik.metronome.common.BaseActivity
import pl.pawelantonik.metronome.feature.main.presentation.MetronomeScreen
import pl.pawelantonik.metronome.ui.theme.AppTheme

class MainActivity : BaseActivity() {
  @Composable
  override fun SetContent() {
    AppTheme {
      MetronomeScreen()
    }
  }
}
