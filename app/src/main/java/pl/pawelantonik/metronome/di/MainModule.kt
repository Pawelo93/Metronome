package pl.pawelantonik.metronome.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.pawelantonik.metronome.feature.counter.data.SharedPrefsCounterSettingsRepository
import pl.pawelantonik.metronome.feature.counter.domain.CounterSettingsRepository
import pl.pawelantonik.metronome.feature.main.MetronomeSoundPlayer
import pl.pawelantonik.metronome.feature.main.SoundPlayer
import pl.pawelantonik.metronome.feature.main.data.SharedPrefsAccelerateSettingsRepository
import pl.pawelantonik.metronome.feature.main.data.SharedPrefsBpmRepository
import pl.pawelantonik.metronome.feature.main.data.SharedPrefsAccentSettingsRepository
import pl.pawelantonik.metronome.feature.main.domain.AccelerateSettingsRepository
import pl.pawelantonik.metronome.feature.main.domain.BpmRepository
import pl.pawelantonik.metronome.feature.main.domain.AccentSettingsRepository
import pl.pawelantonik.metronome.feature.service.PulseGenerator
import pl.pawelantonik.metronome.feature.service.PulseGeneratorImpl
import pl.pawelantonik.metronome.feature.service.data.InMemoryIsMetronomeRunningRepository
import pl.pawelantonik.metronome.feature.service.domain.IsMetronomeRunningRepository
import pl.pawelantonik.metronome.feature.settings.data.SharedPrefsIsVibrationEnabledRepository
import pl.pawelantonik.metronome.feature.settings.domain.IsVibrationEnabledRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {

  @Binds abstract fun bindSoundPlayer(timer: MetronomeSoundPlayer): SoundPlayer

  @Binds abstract fun bindBpmRepository(bpmRepository: SharedPrefsBpmRepository): BpmRepository

  @Binds abstract fun bindTickSettingsRepository(tickSettingsRepository: SharedPrefsAccentSettingsRepository): AccentSettingsRepository

  @Binds abstract fun bindAccelerateSettingsRepository(accelerateSettingsRepository: SharedPrefsAccelerateSettingsRepository): AccelerateSettingsRepository

  @Binds abstract fun bindCounterSettingsRepository(counterSettingsRepository: SharedPrefsCounterSettingsRepository): CounterSettingsRepository

  @Binds abstract fun bindIsVibrationEnabledRepository(isVibrationEnabledRepository: SharedPrefsIsVibrationEnabledRepository): IsVibrationEnabledRepository

  @Binds abstract fun bindIsMetronomeRunningRepository(isMetronomeRunningRepository: InMemoryIsMetronomeRunningRepository): IsMetronomeRunningRepository

  @Binds abstract fun bindPulseGenerator(pulseGenerator: PulseGeneratorImpl): PulseGenerator

}