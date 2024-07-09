package pl.pawelantonik.metronome

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import pl.pawelantonik.metronome.common.BaseActivity
import pl.pawelantonik.metronome.feature.main.presentation.MetronomeScreen
import pl.pawelantonik.metronome.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : BaseActivity() {
  @Composable
  override fun SetContent() {
    AppTheme {
      MetronomeScreen()
    }
  }
}
