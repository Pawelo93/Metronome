package pl.pawelantonik.metronome.feature.settings.domain

import kotlinx.coroutines.flow.Flow

interface BpmRepository {
  fun getBpm(): Int
  fun saveBpm(bpm: Int)
  fun observeBpm(): Flow<Int>
  fun getDelta(): Int
  fun saveDelta(deltaBpm: Int)
}