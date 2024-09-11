package pl.pawelantonik.metronome.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.pawelantonik.metronome.feature.counter.data.SharedPrefsCounterSettingsRepository
import pl.pawelantonik.metronome.feature.counter.domain.CounterSettingsRepository
import pl.pawelantonik.metronome.feature.main.MetronomeSoundPlayer
import pl.pawelantonik.metronome.feature.main.SoundPlayer
import pl.pawelantonik.metronome.feature.main.data.SharedPrefsBpmRepository
import pl.pawelantonik.metronome.feature.main.data.SharedPrefsTickSettingsRepository
import pl.pawelantonik.metronome.feature.main.domain.BpmRepository
import pl.pawelantonik.metronome.feature.main.domain.TickSettingsRepository
import pl.pawelantonik.metronome.feature.service.PulseGenerator
import pl.pawelantonik.metronome.feature.service.PulseGeneratorImpl
import pl.pawelantonik.metronome.feature.service.data.InMemoryIsMetronomeRunningRepository
import pl.pawelantonik.metronome.feature.service.domain.IsMetronomeRunningRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {

  @Binds abstract fun bindSoundPlayer(timer: MetronomeSoundPlayer): SoundPlayer

  @Binds abstract fun bindBpmRepository(bpmRepository: SharedPrefsBpmRepository): BpmRepository

  @Binds abstract fun bindTickSettingsRepository(tickSettingsRepository: SharedPrefsTickSettingsRepository): TickSettingsRepository

  @Binds abstract fun bindCounterSettingsRepository(counterSettingsRepository: SharedPrefsCounterSettingsRepository): CounterSettingsRepository

  @Binds abstract fun bindIsMetronomeRunningRepository(isMetronomeRunningRepository: InMemoryIsMetronomeRunningRepository): IsMetronomeRunningRepository

  @Binds abstract fun bindPulseGenerator(pulseGenerator: PulseGeneratorImpl): PulseGenerator

}