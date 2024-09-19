package pl.pawelantonik.metronome.feature.service

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppVibrator @Inject constructor(
  @ApplicationContext context: Context,
) {

  companion object {
    const val VIBRATION_LENGTH_MS = 50L
  }

  private val vibrator: Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

  fun vibrate() {
    vibrator.vibrate(
      VibrationEffect.createOneShot(
        VIBRATION_LENGTH_MS,
        VibrationEffect.DEFAULT_AMPLITUDE,
      )
    )
  }
}