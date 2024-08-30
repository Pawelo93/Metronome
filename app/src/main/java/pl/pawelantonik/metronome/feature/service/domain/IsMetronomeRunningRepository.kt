package pl.pawelantonik.metronome.feature.service.domain

import kotlinx.coroutines.flow.Flow

interface IsMetronomeRunningRepository {
    fun set(isRunning: Boolean)
    fun get(): Boolean
    fun observe(): Flow<Boolean>
}