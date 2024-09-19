package pl.pawelantonik.metronome.feature.accelerateBpm.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.pawelantonik.metronome.feature.accelerateBpm.domain.Acceleration
import pl.pawelantonik.metronome.feature.accelerateBpm.domain.AccelerationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InMemoryAccelerationRepository @Inject constructor() : AccelerationRepository {
  private val defaultAcceleration = Acceleration(0, 1, 1)
  private var acceleration: Acceleration = defaultAcceleration
  private val _accelerationFlow = MutableStateFlow(acceleration)

  override fun set(acceleration: Acceleration) {
    println("HERE ### set $acceleration")
    this.acceleration = acceleration
    _accelerationFlow.value = acceleration
  }

  override fun get(): Acceleration {
    return acceleration
  }

  override fun observe(): Flow<Acceleration> {
    return _accelerationFlow.asStateFlow()
  }

  override fun clear() {
    set(defaultAcceleration)
  }
}
