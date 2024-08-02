package pl.pawelantonik.metronome.feature.main.domain

interface BpmRepository {
  fun getBpm(): Int
  fun saveBpm(bpm: Int)
  fun getDelta(): Int
  fun saveDelta(deltaBpm: Int)
}