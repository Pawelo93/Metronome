package pl.pawelantonik.metronome.feature.settings.domain

import kotlinx.coroutines.flow.Flow

interface IsVibrationEnabledRepository {
  fun get(): Boolean
  fun observe(): Flow<Boolean>
  fun save(isVibrationEnabled: Boolean)
}
