package pl.pawelantonik.metronome.feature.main.domain

import kotlinx.coroutines.flow.Flow

interface TickSettingsRepository {
  fun get(): TickSettings?
  fun observe(): Flow<TickSettings?>
  fun save(tickSettings: TickSettings?)
}

data class TickSettings(
  val bits: Int,
) {
  override fun toString(): String {
    return "$bits/4"
  }
}
