package pl.pawelantonik.metronome

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import pl.pawelantonik.metronome.common.BaseActivity
import pl.pawelantonik.metronome.feature.main.MetronomeSoundPlayer
import pl.pawelantonik.metronome.feature.main.presentation.MetronomeScreen
import pl.pawelantonik.metronome.ui.theme.AppTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
  @Inject
  lateinit var metronomeSoundPlayer: MetronomeSoundPlayer

  @Composable
  override fun SetContent() {
    metronomeSoundPlayer.init(this)

    AppTheme {
      MetronomeScreen(
        soundPlayer = metronomeSoundPlayer,
      )
    }
  }
}
