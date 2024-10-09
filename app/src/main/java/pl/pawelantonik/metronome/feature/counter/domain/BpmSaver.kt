package pl.pawelantonik.metronome.feature.counter.domain

import pl.pawelantonik.metronome.feature.accelerateBpm.domain.AccelerationRepository
import pl.pawelantonik.metronome.feature.settings.domain.BpmRepository
import javax.inject.Inject

class BpmSaver @Inject constructor(
  private val bpmRepository: BpmRepository,
  private val accelerationRepository: AccelerationRepository,
) {
  fun save(bpm: Int) {
    val currentBpm = bpmRepository.getBpm()
    val additionalBpm = accelerationRepository.get().currentBpm
    if (currentBpm + additionalBpm != bpm) {
      bpmRepository.saveBpm(bpm - additionalBpm)
    }
  }
}