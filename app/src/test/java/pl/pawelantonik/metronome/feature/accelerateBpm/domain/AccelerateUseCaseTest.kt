package pl.pawelantonik.metronome.feature.accelerateBpm.domain

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import pl.pawelantonik.metronome.feature.accelerateBpm.domain.AccelerationState.*

class AccelerateUseCaseTest {
  private val accelerateDataPreparer: AccelerateDataPreparer = mockk()
  private val accelerationRepository: AccelerationRepository = mockk(relaxed = true)
  private val accelerateUseCase = AccelerateUseCase(
    accelerateDataPreparer,
    accelerationRepository,
  )

  @Test
  fun `should increment current beat on execute`() {
    every { accelerateDataPreparer.execute() } returns AccelerateData(
      constNextBarBpmAcceleration = 20,
      constBarsToAccelerationCount = 2,
      constBitsInEveryBar = 4,
      currentBpm = 0,
      currentBit = 1,
      currentBar = 1,
    )

    accelerateUseCase.execute()

    verify {
      accelerationRepository.set(
        Acceleration(
          currentBpm = 0,
          currentBit = 2,
          currentBar = 1,
        )
      )
    }
  }

  @Test
  fun `should increment current bar on third execute`() {
    every { accelerateDataPreparer.execute() } returns AccelerateData(
      constNextBarBpmAcceleration = 20,
      constBarsToAccelerationCount = 2,
      constBitsInEveryBar = 4,
      currentBpm = 0,
      currentBit = 3,
      currentBar = 1,
    )

    accelerateUseCase.execute()

    verify {
      accelerationRepository.set(
        Acceleration(
          currentBpm = 0,
          currentBit = 1,
          currentBar = 2,
        )
      )
    }
  }

  @Test
  fun `should change bpm`() {
    every { accelerateDataPreparer.execute() } returns AccelerateData(
      constNextBarBpmAcceleration = 20,
      constBarsToAccelerationCount = 2,
      constBitsInEveryBar = 4,
      currentBpm = 0,
      currentBit = 4,
      currentBar = 2,
    )

    accelerateUseCase.execute()

    verify {
      accelerationRepository.set(
        Acceleration(
          currentBpm = 20,
          currentBar = 1,
          currentBit = 1,
        )
      )
    }
  }
}