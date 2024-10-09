package pl.pawelantonik.metronome.feature.service

import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import pl.pawelantonik.metronome.feature.main.MetronomeSoundPlayer
import pl.pawelantonik.metronome.feature.settings.domain.IsVibrationEnabledRepository
import javax.inject.Inject

class Ticker @Inject constructor(
  private val pulseGenerator: PulseGenerator,
  private val soundPlayer: MetronomeSoundPlayer,
  private val isVibrationEnabledRepository: IsVibrationEnabledRepository,
  private val appVibrator: AppVibrator,
) {

  private val coroutineScope = MainScope()
  private var job: Job? = null

  fun start() {
    job?.cancel()

    job = coroutineScope.launch {
      pulseGenerator.start().filterNotNull().collectLatest { pulse ->
        soundPlayer.play(pulse.isAccent)

        if (isVibrationEnabledRepository.get()) {
          appVibrator.vibrate()
        }
      }
    }
  }

  fun stop() {
    job?.cancel()
    pulseGenerator.stop()
  }
}