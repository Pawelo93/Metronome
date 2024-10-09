package pl.pawelantonik.metronome.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.pawelantonik.metronome.feature.accelerateBpm.data.InMemoryAccelerationRepository
import pl.pawelantonik.metronome.feature.accelerateBpm.domain.AccelerationRepository
import pl.pawelantonik.metronome.feature.settings.data.SharedPrefsCounterSettingsRepository
import pl.pawelantonik.metronome.feature.settings.domain.CounterSettingsRepository
import pl.pawelantonik.metronome.feature.settings.data.SharedPrefsAccelerateSettingsRepository
import pl.pawelantonik.metronome.feature.settings.data.SharedPrefsBpmRepository
import pl.pawelantonik.metronome.feature.settings.data.SharedPrefsAccentSettingsRepository
import pl.pawelantonik.metronome.feature.settings.domain.AccelerateSettingsRepository
import pl.pawelantonik.metronome.feature.settings.domain.BpmRepository
import pl.pawelantonik.metronome.feature.settings.domain.AccentSettingsRepository
import pl.pawelantonik.metronome.feature.service.PulseGenerator
import pl.pawelantonik.metronome.feature.service.PulseGeneratorImpl
import pl.pawelantonik.metronome.feature.service.data.InMemoryIsMetronomeRunningRepository
import pl.pawelantonik.metronome.feature.service.domain.IsMetronomeRunningRepository
import pl.pawelantonik.metronome.feature.settings.data.SharedPrefsIsVibrationEnabledRepository
import pl.pawelantonik.metronome.feature.settings.domain.IsVibrationEnabledRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {

  @Binds abstract fun bindBpmRepository(bpmRepository: SharedPrefsBpmRepository): BpmRepository

  @Binds abstract fun bindTickSettingsRepository(tickSettingsRepository: SharedPrefsAccentSettingsRepository): AccentSettingsRepository

  @Binds abstract fun bindAccelerateSettingsRepository(accelerateSettingsRepository: SharedPrefsAccelerateSettingsRepository): AccelerateSettingsRepository

  @Binds abstract fun bindCounterSettingsRepository(counterSettingsRepository: SharedPrefsCounterSettingsRepository): CounterSettingsRepository

  @Binds abstract fun bindIsVibrationEnabledRepository(isVibrationEnabledRepository: SharedPrefsIsVibrationEnabledRepository): IsVibrationEnabledRepository

  @Binds abstract fun bindIsMetronomeRunningRepository(isMetronomeRunningRepository: InMemoryIsMetronomeRunningRepository): IsMetronomeRunningRepository

  @Binds abstract fun bindAccelerateBpmRepository(accelerateBpmRepository: InMemoryAccelerationRepository): AccelerationRepository

  @Binds abstract fun bindPulseGenerator(pulseGenerator: PulseGeneratorImpl): PulseGenerator

}