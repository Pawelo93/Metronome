package pl.pawelantonik.metronome.feature.settings.data

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import pl.pawelantonik.metronome.feature.settings.domain.AccentSettings
import pl.pawelantonik.metronome.feature.settings.domain.AccentSettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsAccentSettingsRepository @Inject constructor(
  private val sharedPreferences: SharedPreferences,
) : AccentSettingsRepository {

  private companion object {
    const val ACCENT_SETTINGS = "accent_settings_key"
  }

  private val _settingsFlow = MutableStateFlow(get())

  override fun get(): AccentSettings? {
    val bits = sharedPreferences.getInt(ACCENT_SETTINGS, -1)
    return if (bits > 0) AccentSettings(bits) else null
  }

  override fun observe(): Flow<AccentSettings?> {
    return _settingsFlow
  }

  override fun save(accentSettings: AccentSettings?) {
    if (accentSettings != null) {
      sharedPreferences.edit().putInt(ACCENT_SETTINGS, accentSettings.bits).apply()
    } else {
      sharedPreferences.edit().remove(ACCENT_SETTINGS).apply()
    }

    _settingsFlow.value = accentSettings
  }
}