package pl.pawelantonik.metronome.feature.counter.data

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import pl.pawelantonik.metronome.feature.counter.domain.CounterSettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsCounterSettingsRepository @Inject constructor(
  private val sharedPreferences: SharedPreferences,
) : CounterSettingsRepository {

  private companion object {
    const val COUNTER_SETTINGS = "counter_settings_key"
  }

  private val _settingsFlow = MutableStateFlow(get())

  override fun get(): Boolean {
    return sharedPreferences.getBoolean(COUNTER_SETTINGS, false)
  }

  override fun observe(): Flow<Boolean> {
    return _settingsFlow
  }

  override fun save(isEnabled: Boolean) {
    sharedPreferences.edit().putBoolean(COUNTER_SETTINGS, isEnabled).apply()
    _settingsFlow.value = isEnabled
  }
}
