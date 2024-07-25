package pl.pawelantonik.metronome

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import pl.pawelantonik.metronome.common.BaseActivity
import pl.pawelantonik.metronome.feature.main.Timer
import pl.pawelantonik.metronome.feature.main.presentation.MetronomeScreen
import pl.pawelantonik.metronome.ui.theme.AppTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
  @Inject
  lateinit var timer: Timer

  @Composable
  override fun SetContent() {
    timer.init(this)

    AppTheme {
      MetronomeScreen(
        timer = timer,
      )
    }
  }
}
