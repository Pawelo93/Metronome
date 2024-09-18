package pl.pawelantonik.metronome.feature.accelerateBpm.domain

import kotlinx.coroutines.flow.Flow

interface AccelerationRepository {
  fun set(acceleration: Acceleration)
  fun get(): Acceleration
  fun observe(): Flow<Acceleration>
  fun clear()
}

data class Acceleration(
  val currentBpm: Int,
  val currentBit: Int,
  val currentBar: Int,
)