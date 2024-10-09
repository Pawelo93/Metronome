package pl.pawelantonik.metronome.feature.settings.domain

import kotlinx.coroutines.flow.Flow

interface CounterSettingsRepository {
  fun get(): Boolean
  fun observe(): Flow<Boolean>
  fun save(isEnabled: Boolean)
}
