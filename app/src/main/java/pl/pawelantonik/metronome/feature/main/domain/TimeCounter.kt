package pl.pawelantonik.metronome.feature.main.domain

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TimeCounter @Inject constructor() {
  private var currentIntervalMs: Long = 0
  private var endTime = timeNowInMs

  private val timeNowInMs: Long
    get() = System.nanoTime() / 1000000


  fun count(intervalMs: Long): Flow<Int> = flow {
    var count = 0
    currentIntervalMs = intervalMs

    while (true) {
      if (timeNowInMs >= endTime - 1) {
        Log.d("Metronome", "Beep - offset: ${timeNowInMs - endTime}")
        emit(count++)
        endTime = timeNowInMs + currentIntervalMs
      }
      delay(1)
    }
  }
}