package pl.pawelantonik.metronome.feature.accelerateBpm.domain

import pl.pawelantonik.metronome.feature.main.domain.AccentSettings
import pl.pawelantonik.metronome.feature.main.domain.AccentSettingsRepository
import javax.inject.Inject

class AccelerationSwitcher @Inject constructor(
  private val accentSettingsRepository: AccentSettingsRepository,
  private val accelerateSettingsRepository: AccelerateSettingsRepository,
) {

  fun switch(accelerateSettings: AccelerateSettings?) {
    if (accelerateSettings != null) {
      enableAccent()
    }

    accelerateSettingsRepository.save(accelerateSettings)
  }

  private fun enableAccent() {
    val accent = accentSettingsRepository.get()?.bits
    if (accent == null) {
      val defaultAccent = AccentSettings.default()
      accentSettingsRepository.save(defaultAccent)
    }
  }
}