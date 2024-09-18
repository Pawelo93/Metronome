package pl.pawelantonik.metronome.feature.main.domain

import kotlinx.coroutines.flow.Flow

interface AccentSettingsRepository {
  fun get(): AccentSettings?
  fun observe(): Flow<AccentSettings?>
  fun save(accentSettings: AccentSettings?)
}

data class AccentSettings(
  val bits: Int,
) {
  override fun toString(): String {
    return "$bits/4"
  }

  companion object {
    fun default() = AccentSettings(bits = 4)
  }
}
