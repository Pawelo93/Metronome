package pl.pawelantonik.metronome.feature.main

import android.content.Context

interface Timer {
  fun init(context: Context)

  suspend fun start(intervalMs: Long)

  fun update(intervalMs: Long)

  fun stop()

  fun release()
}