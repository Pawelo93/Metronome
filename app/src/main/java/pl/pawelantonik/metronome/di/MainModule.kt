package pl.pawelantonik.metronome.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.pawelantonik.metronome.feature.main.MetronomeSoundPlayer
import pl.pawelantonik.metronome.feature.main.SoundPlayer
import pl.pawelantonik.metronome.feature.main.data.SharedPrefsBpmRepository
import pl.pawelantonik.metronome.feature.main.data.SharedPrefsTickSettingsRepository
import pl.pawelantonik.metronome.feature.main.domain.BpmRepository
import pl.pawelantonik.metronome.feature.main.domain.TickSettingsRepository
import pl.pawelantonik.metronome.feature.service.data.InMemoryIsMetronomeRunningRepository
import pl.pawelantonik.metronome.feature.service.domain.IsMetronomeRunningRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {

  @Binds @Singleton abstract fun bindSoundPlayer(timer: MetronomeSoundPlayer): SoundPlayer

  @Binds @Singleton abstract fun bindBpmRepository(bpmRepository: SharedPrefsBpmRepository): BpmRepository

  @Binds @Singleton abstract fun bindTickSettingsRepository(tickSettingsRepository: SharedPrefsTickSettingsRepository): TickSettingsRepository

  @Binds @Singleton abstract fun bindIsMetronomeRunningRepository(isMetronomeRunningRepository: InMemoryIsMetronomeRunningRepository): IsMetronomeRunningRepository

}