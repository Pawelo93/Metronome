package pl.pawelantonik.metronome.feature.service

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import pl.pawelantonik.metronome.feature.main.SoundPlayer
import javax.inject.Inject

class Ticker @Inject constructor(
  private val pulseGenerator: PulseGenerator,
  private val soundPlayer: SoundPlayer,
  @ApplicationContext private val context: Context,
) {

  private val coroutineScope = MainScope()
  private var job: Job? = null

  init {
    soundPlayer.init(context)
  }

  fun start() {
    job?.cancel()

    job = coroutineScope.launch {
      pulseGenerator.start().collectLatestNotNull { pulse ->
        soundPlayer.play(pulse.isAccent)
      }
    }
  }

  private suspend fun <T : Any> Flow<T?>.collectLatestNotNull(action: suspend (T) -> Unit) {
    this.filterNotNull().collectLatest { value ->
      action(value)
    }
  }

  fun stop() {
    job?.cancel()
    pulseGenerator.stop()
  }
}