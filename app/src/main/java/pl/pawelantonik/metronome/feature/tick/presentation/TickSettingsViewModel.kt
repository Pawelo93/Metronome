package pl.pawelantonik.metronome.feature.tick.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import pl.pawelantonik.metronome.common.BaseViewModel
import pl.pawelantonik.metronome.feature.main.domain.TickSettings
import pl.pawelantonik.metronome.feature.main.domain.TickSettingsRepository
import javax.inject.Inject

@HiltViewModel
class TickSettingsViewModel @Inject constructor(
  private val tickSettingsRepository: TickSettingsRepository,
) : BaseViewModel() {

  private val _uiState = MutableStateFlow(UiState.initial())
  val uiState = _uiState.asStateFlow()

  fun load() {
    _uiState.update { UiState(tickSettingsRepository.get()) }
  }

  fun onUpdateTickSettings(tickSettings: TickSettings?) {
    tickSettingsRepository.save(tickSettings)
    _uiState.update { UiState(tickSettings) }
  }

  data class UiState(
    val tickSettings: TickSettings?,
  ) {
    companion object {
      fun initial() = UiState(
        tickSettings = null,
      )
    }
  }
}
