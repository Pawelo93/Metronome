package pl.pawelantonik.metronome.feature.main

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import pl.pawelantonik.metronome.R
import javax.inject.Inject

class TimerImpl @Inject constructor() : Timer {
  lateinit var mediaPlayer: MediaPlayer

  private val timeNowInMs: Long
    get() = System.nanoTime() / 1000000

  override fun init(context: Context) {
    mediaPlayer = MediaPlayer.create(context, R.raw.metronome_sound)
  }

  var currentIntervalMs: Long = 0

  var endTime = timeNowInMs
  override suspend fun start(intervalMs: Long) {
    currentIntervalMs = intervalMs
    while (true) {
      if (timeNowInMs >= endTime - 1) {
        Log.d("Metronome", "Beep - offset: ${timeNowInMs - endTime}")
        mediaPlayer.seekTo(0)
        mediaPlayer.start()
        endTime = timeNowInMs + currentIntervalMs
      }

      delay(1)
    }
  }

  override fun update(intervalMs: Long) {
    currentIntervalMs = intervalMs
    endTime = timeNowInMs
  }

  override fun stop() {
    TODO("Not yet implemented")
  }

  override fun release() {
    mediaPlayer.release()
  }
}