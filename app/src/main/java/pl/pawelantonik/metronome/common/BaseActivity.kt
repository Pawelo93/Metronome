package pl.pawelantonik.metronome.common

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

abstract class BaseActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.Transparent.toArgb()))

    setContent {
      SetContent()
    }
  }

  protected fun backPressed() = onBackPressedDispatcher.onBackPressed()

  @Composable
  protected abstract fun SetContent()

  fun showError(exception: Exception) {
    Log.e("BaseActivity", "Exception $exception")
  }
}