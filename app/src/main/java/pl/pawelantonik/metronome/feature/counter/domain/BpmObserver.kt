package pl.pawelantonik.metronome.feature.counter.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import pl.pawelantonik.metronome.feature.accelerateBpm.domain.AccelerationRepository
import pl.pawelantonik.metronome.feature.settings.domain.BpmRepository
import javax.inject.Inject

data class AcceleratedBpm(
  val value: Int,
  val hasAcceleration: Boolean,
)

class BpmObserver @Inject constructor(
  private val bpmRepository: BpmRepository,
  private val accelerationRepository: AccelerationRepository,
) {
  fun observe(): Flow<AcceleratedBpm> {
    return bpmRepository.observeBpm()
      .combine(observeCurrentBpm()) { bpm, currentBpm ->
        AcceleratedBpm(
          value = bpm + currentBpm,
          hasAcceleration = currentBpm > 0,
        )
      }
  }

  fun get(): Int {
    return bpmRepository.getBpm() + accelerationRepository.get().currentBpm
  }

  private fun observeCurrentBpm(): Flow<Int> =
    accelerationRepository.observe()
      .map { it.currentBpm }
      .distinctUntilChanged()
}