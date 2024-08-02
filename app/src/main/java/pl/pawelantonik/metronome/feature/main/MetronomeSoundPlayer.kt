package pl.pawelantonik.metronome.feature.main

import android.content.Context
import android.media.MediaPlayer
import pl.pawelantonik.metronome.R
import javax.inject.Inject

interface SoundPlayer {
  fun init(context: Context)
  fun play(isAccentBeat: Boolean)
  fun release()
}

class MetronomeSoundPlayer @Inject constructor() : SoundPlayer {
  private lateinit var baseSound: MediaPlayer
  private lateinit var accentSound: MediaPlayer

  override fun init(context: Context) {
    baseSound = MediaPlayer.create(context, R.raw.stick_sound)
    accentSound = MediaPlayer.create(context, R.raw.metronome_sound)
  }

  override fun play(isAccentBeat: Boolean) {
    when (isAccentBeat) {
      true -> playSound(accentSound)
      false -> playSound(baseSound)
    }
  }

  override fun release() {
    baseSound.release()
    accentSound.release()
  }

  private fun playSound(mediaPlayer: MediaPlayer) {
    mediaPlayer.seekTo(0)
    mediaPlayer.start()
  }
}