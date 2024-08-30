package pl.pawelantonik.metronome.feature.main

import android.content.Context

interface SoundPlayer {
  fun init(context: Context)
  fun play(isAccentBeat: Boolean)
  fun release()
}
