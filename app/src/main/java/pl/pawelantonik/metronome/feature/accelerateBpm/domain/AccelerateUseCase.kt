package pl.pawelantonik.metronome.feature.accelerateBpm.domain

import javax.inject.Inject

class AccelerateUseCase @Inject constructor(
  private val accelerateDataPreparer: AccelerateDataPreparer,
  private val accelerationRepository: AccelerationRepository,
) {

  fun execute() {
    val state = accelerateDataPreparer.execute()

    if (state is AccelerationState.AccelerateData) {
      with(state) {

        var newCurrentBit = currentBit + 1
        var newBpm = currentBpm
        var newBar = currentBar

        if (newCurrentBit > state.constBitsInEveryBar) {
          newCurrentBit = 1
          newBar += 1

          if (newBar > state.constBarsToAccelerationCount) {
            newBpm += state.constNextBarBpmAcceleration
            newBar = 1
          }
        }

        val acceleration = Acceleration(
          currentBpm = newBpm,
          currentBar = newBar,
          currentBit = newCurrentBit,
        )
        accelerationRepository.set(acceleration)
      }
    }
  }
}