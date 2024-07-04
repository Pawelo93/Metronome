package pl.pawelantonik.metronome.feature.main.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.pawelantonik.metronome.common.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

  private val _uiState = MutableStateFlow(UiState.initial())
  val uiState = _uiState.asStateFlow()

  data class UiState(
    val isRunning: Boolean,
    val bpm: Int,
    val singleBar: SingleBar,
  ) {
    companion object {
      fun initial() = UiState(
        isRunning = false,
        bpm = 120,
        singleBar = SingleBar(1, 4),
      )
    }

    val isAccentBeat: Boolean
      get() = singleBar.currentBit == 1
  }
}

data class SingleBar(
  val currentBit: Int = 1,
  val allBits: Int = 4,
) {
  fun onBeat(): SingleBar {
    return SingleBar(
      currentBit = when (currentBit < allBits) {
        true -> currentBit + 1
        false -> 1
      },
      allBits = allBits,
    )
  }
}