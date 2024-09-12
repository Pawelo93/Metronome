package pl.pawelantonik.metronome.feature.settings.data

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import pl.pawelantonik.metronome.feature.settings.domain.IsVibrationEnabledRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsIsVibrationEnabledRepository @Inject constructor(
  private val sharedPreferences: SharedPreferences,
) : IsVibrationEnabledRepository {

  private companion object {
    const val IS_VIBRATION_ENABLED_KEY = "is_vibration_enabled_key"
  }

  private val _settingsFlow = MutableStateFlow(get())

  override fun get(): Boolean {
    return sharedPreferences.getBoolean(IS_VIBRATION_ENABLED_KEY, false)
  }

  override fun observe(): Flow<Boolean> {
    return _settingsFlow
  }

  override fun save(isVibrationEnabled: Boolean) {
    sharedPreferences.edit().putBoolean(IS_VIBRATION_ENABLED_KEY, isVibrationEnabled).apply()
    _settingsFlow.update { isVibrationEnabled }
  }
}