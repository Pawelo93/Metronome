package pl.pawelantonik.metronome.feature.accelerateBpm.domain

import pl.pawelantonik.metronome.feature.accelerateBpm.domain.AccelerationState.*
import pl.pawelantonik.metronome.feature.main.domain.AccentSettingsRepository
import javax.inject.Inject

class AccelerateDataPreparer @Inject constructor(
  private val accelerateSettingsRepository: AccelerateSettingsRepository,
  private val accentSettingsRepository: AccentSettingsRepository,
  private val accelerationRepository: AccelerationRepository,
) {
  fun execute(): AccelerationState {
    val accelerateSettings = accelerateSettingsRepository.get()
      ?: return AccelerationDisabled

    val accentSettings = accentSettingsRepository.get() ?: throw IllegalStateException("Accent settings is null")

    val currentAcceleration = accelerationRepository.get()

    return AccelerateData(
      constNextBarBpmAcceleration = accelerateSettings.bpmAcceleration,
      constBarsToAccelerationCount = accelerateSettings.barsCount,
      constBitsInEveryBar = accentSettings.bits,
      currentBit = currentAcceleration.currentBit,
      currentBpm = currentAcceleration.currentBpm,
      currentBar = currentAcceleration.currentBar,
    )
  }
}

sealed class AccelerationState {
  data class AccelerateData(
    val constNextBarBpmAcceleration: Int,
    val constBarsToAccelerationCount: Int,
    val constBitsInEveryBar: Int,
    val currentBpm: Int,
    val currentBit: Int,
    val currentBar: Int,
  ) : AccelerationState()

  data object AccelerationDisabled : AccelerationState()
}