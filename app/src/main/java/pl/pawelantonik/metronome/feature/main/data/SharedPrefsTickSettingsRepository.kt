package pl.pawelantonik.metronome.feature.main.data

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import pl.pawelantonik.metronome.feature.main.domain.TickSettings
import pl.pawelantonik.metronome.feature.main.domain.TickSettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsTickSettingsRepository @Inject constructor(
  private val sharedPreferences: SharedPreferences,
) : TickSettingsRepository {

  private companion object {
    const val TICK_SETTINGS = "tick_settings_key"
  }

  private val _settingsFlow = MutableStateFlow(get())

  override fun get(): TickSettings? {
    val bits = sharedPreferences.getInt(TICK_SETTINGS, -1)
    return if (bits > 0) TickSettings(bits) else null
  }

  override fun observe(): Flow<TickSettings?> {
    return _settingsFlow
  }

  override fun save(tickSettings: TickSettings?) {
    if (tickSettings != null) {
      sharedPreferences.edit().putInt(TICK_SETTINGS, tickSettings.bits).apply()
    } else {
      sharedPreferences.edit().remove(TICK_SETTINGS).apply()
    }

    _settingsFlow.value = tickSettings
  }
}