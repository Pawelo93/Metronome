package pl.pawelantonik.metronome.feature.main.domain

interface TickSettingsRepository {
  fun get(): TickSettings?
  fun save(tickSettings: TickSettings?)
}

data class TickSettings(
  val bits: Int,
) {
  override fun toString(): String {
    return "$bits/4"
  }
}

