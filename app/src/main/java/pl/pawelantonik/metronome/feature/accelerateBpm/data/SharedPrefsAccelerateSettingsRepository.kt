package pl.pawelantonik.metronome.feature.accelerateBpm.data

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import pl.pawelantonik.metronome.feature.accelerateBpm.domain.AccelerateSettings
import pl.pawelantonik.metronome.feature.accelerateBpm.domain.AccelerateSettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsAccelerateSettingsRepository @Inject constructor(
  private val sharedPreferences: SharedPreferences,
) : AccelerateSettingsRepository {

  private companion object {
    const val ACCELERATE_BPM_SETTINGS = "accelerate_bpm_settings_key"
    const val ACCELERATE_BARS_SETTINGS = "accelerate_bars_settings_key"
  }

  private val _settingsFlow = MutableStateFlow(get())

  override fun get(): AccelerateSettings? {
    val bpm = sharedPreferences.getInt(ACCELERATE_BPM_SETTINGS, -1)
    val bars = sharedPreferences.getInt(ACCELERATE_BARS_SETTINGS, -1)

    return if (bpm != -1 && bars != -1) {
      AccelerateSettings(bpm, bars)
    } else {
      null
    }
  }

  override fun observe(): Flow<AccelerateSettings?> {
    return _settingsFlow
  }

  override fun save(accelerateSettings: AccelerateSettings?) {
    if (accelerateSettings != null) {
      sharedPreferences.edit().putInt(ACCELERATE_BPM_SETTINGS, accelerateSettings.bpmAcceleration).apply()
      sharedPreferences.edit().putInt(ACCELERATE_BARS_SETTINGS, accelerateSettings.barsCount).apply()
    } else {
      sharedPreferences.edit().remove(ACCELERATE_BPM_SETTINGS).apply()
      sharedPreferences.edit().remove(ACCELERATE_BARS_SETTINGS).apply()
    }

    _settingsFlow.value = accelerateSettings
  }
}