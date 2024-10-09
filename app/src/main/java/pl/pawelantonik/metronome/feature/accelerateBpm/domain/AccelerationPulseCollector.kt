package pl.pawelantonik.metronome.feature.accelerateBpm.domain

import kotlinx.coroutines.flow.collectLatest
import pl.pawelantonik.metronome.feature.service.PulseGenerator
import pl.pawelantonik.metronome.feature.settings.domain.AccelerateSettingsRepository
import javax.inject.Inject

class AccelerationPulseCollector @Inject constructor(
  private val pulseGenerator: PulseGenerator,
  private val accelerateSettingsRepository: AccelerateSettingsRepository,
  private val accelerateUseCase: AccelerateUseCase,
) {

  suspend fun collect() {
    accelerateSettingsRepository.observe().collectLatest { accelerateSettings ->
      if (accelerateSettings != null) {
        pulseGenerator.observe().collectLatest { pulse ->
          pulse?.let {
            accelerateUseCase.execute()
          }
        }
      }
    }
  }
}