package pl.pawelantonik.metronome.feature.main.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.pawelantonik.metronome.common.BaseViewModel
import pl.pawelantonik.metronome.feature.main.presentation.MainViewModel.ChangeBpmValue.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

  private val _uiState = MutableStateFlow(UiState.initial())
  val uiState = _uiState.asStateFlow()

  fun onChangeBpm(newValue: Int) {
    _uiState.update {
      it.copy(bpm = newValue)
    }
  }

  fun onChangeBpm(changeDir: BpmChangeDir) {
    _uiState.update {
      val currentBpm = _uiState.value.bpm
      val changeValue = _uiState.value.selectedChangeBpmValue.value
      val newBpm = when (changeDir) {
        BpmChangeDir.UP -> currentBpm + changeValue
        BpmChangeDir.DOWN -> currentBpm - changeValue
      }

      it.copy(bpm = newBpm)
    }
  }

  fun onChangeBpmValue(changeBpmValue: ChangeBpmValue) {
    _uiState.update {
      it.copy(selectedChangeBpmValue = changeBpmValue)
    }
  }

  enum class BpmChangeDir {
    UP,
    DOWN,
  }

  enum class ChangeBpmValue(val value: Int) {
    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10),
  }

  data class UiState(
    val isRunning: Boolean,
    val bpm: Int,
    val selectedChangeBpmValue: ChangeBpmValue,
    val changeBpmValues: List<ChangeBpmValue>,
    val singleBar: SingleBar,
  ) {
    companion object {
      fun initial() = UiState(
        isRunning = false,
        bpm = 120,
        selectedChangeBpmValue = TWO,
        changeBpmValues = ChangeBpmValue.entries,
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