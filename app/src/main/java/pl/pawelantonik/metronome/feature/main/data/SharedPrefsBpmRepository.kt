package pl.pawelantonik.metronome.feature.main.data

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.pawelantonik.metronome.feature.main.domain.BpmRepository
import javax.inject.Inject

class SharedPrefsBpmRepository @Inject constructor(
  private val sharedPreferences: SharedPreferences,
) : BpmRepository {

  private companion object {
    const val BPM = "bpm_key"
    const val DELTA_BPM = "delta_bpm_key"
    const val DEFAULT_BPM_VALUE = 60
    const val DEFAULT_DELTA_VALUE = 5
  }

  private val _bpmFlow = MutableStateFlow(getBpm())

  override fun getBpm(): Int {
    return sharedPreferences.getInt(BPM, DEFAULT_BPM_VALUE)
  }

  override fun saveBpm(bpm: Int) {
    sharedPreferences.edit().putInt(BPM, bpm).apply()
    _bpmFlow.update { bpm }
  }

  override fun observeBpm(): Flow<Int> {
    return _bpmFlow.asStateFlow()
  }

  override fun getDelta(): Int {
    return sharedPreferences.getInt(DELTA_BPM, DEFAULT_DELTA_VALUE)
  }

  override fun saveDelta(deltaBpm: Int) {
    sharedPreferences.edit().putInt(DELTA_BPM, deltaBpm).apply()
  }
}