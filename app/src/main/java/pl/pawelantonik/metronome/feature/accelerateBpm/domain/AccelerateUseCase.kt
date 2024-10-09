package pl.pawelantonik.metronome.feature.accelerateBpm.domain

import javax.inject.Inject

class AccelerateUseCase @Inject constructor(
  private val accelerateDataPreparer: AccelerateDataPreparer,
  private val accelerationRepository: AccelerationRepository,
) {

  fun execute() {
    val accelerateState = accelerateDataPreparer.execute()

    if (accelerateState is AccelerationState.AccelerateData) {
      with(accelerateState) {

        var newCurrentBit = currentBit + 1
        var newBpm = currentBpm
        var newBar = currentBar

        if (newCurrentBit > constBitsInEveryBar) {
          newCurrentBit = 1
          newBar += 1

          if (newBar > constBarsToAccelerationCount) {
            newBpm += constNextBarBpmAcceleration
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