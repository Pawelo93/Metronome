package pl.pawelantonik.metronome.feature.main

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import pl.pawelantonik.metronome.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MetronomeSoundPlayer @Inject constructor() {
  private lateinit var baseSound: MediaPlayer
  private lateinit var accentSound: MediaPlayer

  fun init(context: Context) {
    baseSound = MediaPlayer.create(context, R.raw.sound_1_others_mini)
    accentSound = MediaPlayer.create(context, R.raw.sound_1_first_mini)

    baseSound.seekTo(0)
    accentSound.seekTo(0)
  }

  fun play(isAccentBeat: Boolean) {
    when (isAccentBeat) {
      true -> playSound(accentSound)
      false -> playSound(baseSound)
    }
  }

  fun release() {
    if (this::baseSound.isInitialized) {
      baseSound.release()
    }
    if (this::accentSound.isInitialized) {
      accentSound.release()
    }
  }

  private fun playSound(mediaPlayer: MediaPlayer) {
    try {
      mediaPlayer.seekTo(0)
      mediaPlayer.start()
    } catch (e: Exception) {
      Log.e(MetronomeSoundPlayer::class.java.simpleName, e.message, e)
    }
  }
}