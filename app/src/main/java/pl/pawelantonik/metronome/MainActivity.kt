package pl.pawelantonik.metronome

import android.os.Build
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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

  override fun onCreate(savedInstanceState: Bundle?) {
    installSplashScreen()
    super.onCreate(savedInstanceState)

    metronomeSoundPlayer.init(this)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      ActivityCompat.requestPermissions(
        this,
        arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
        0,
      )
    }
  }

  @Composable
  override fun SetContent() {
    AppTheme {
      MetronomeScreen()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    metronomeSoundPlayer.release()
  }
}
