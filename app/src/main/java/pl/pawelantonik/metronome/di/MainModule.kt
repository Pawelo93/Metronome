package pl.pawelantonik.metronome.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {

//  @Binds abstract fun bindMessagesActivityIntentProvider(messagesActivityIntentProvider: MessagesActivityIntentProviderImpl): MessagesActivityIntentProvider
}