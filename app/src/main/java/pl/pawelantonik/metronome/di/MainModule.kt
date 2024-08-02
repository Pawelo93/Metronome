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

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {

  @Binds abstract fun bindSoundPlayer(timer: MetronomeSoundPlayer): SoundPlayer

  @Binds abstract fun bindBpmRepository(bpmRepository: SharedPrefsBpmRepository): BpmRepository

  @Binds abstract fun bindTickSettingsRepository(tickSettingsRepository: SharedPrefsTickSettingsRepository): TickSettingsRepository

}