package pl.pawelantonik.metronome.feature.service.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.pawelantonik.metronome.feature.service.domain.IsMetronomeRunningRepository
import javax.inject.Inject

class InMemoryIsMetronomeRunningRepository @Inject constructor() : IsMetronomeRunningRepository {
  private var isRunning: Boolean = false
  private val _isRunningFlow = MutableStateFlow(isRunning)

  override fun set(isRunning: Boolean) {
    this.isRunning = isRunning
    _isRunningFlow.value = isRunning
  }

  override fun get(): Boolean {
    return isRunning
  }

  override fun observe(): Flow<Boolean> {
    return _isRunningFlow.asStateFlow()
  }
}