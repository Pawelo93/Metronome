package pl.pawelantonik.metronome.feature.main.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.pawelantonik.metronome.R
import pl.pawelantonik.metronome.feature.accelerateBpm.presentation.AccelerateViewModel
import pl.pawelantonik.metronome.feature.counter.presentation.CounterViewModel
import pl.pawelantonik.metronome.feature.main.presentation.counter.CounterView
import pl.pawelantonik.metronome.feature.service.MetronomeService
import pl.pawelantonik.metronome.feature.settings.presentation.SettingsView
import pl.pawelantonik.metronome.feature.settings.presentation.SettingsViewModel
import pl.pawelantonik.metronome.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetronomeScreen() {
  val context = LocalContext.current
  val mainViewModel: MainViewModel = hiltViewModel()
  val counterViewModel: CounterViewModel = hiltViewModel()
  val settingsViewModel: SettingsViewModel = hiltViewModel()
  val accelerateViewModel: AccelerateViewModel = hiltViewModel()
  mainViewModel.load()
  settingsViewModel.load()
  counterViewModel.load()
  accelerateViewModel.load()

  val mainUiState by mainViewModel.uiState.collectAsState()
  val counterUiState by counterViewModel.uiState.collectAsState()
  val settingsUiState by settingsViewModel.uiState.collectAsState()

  Scaffold(
    topBar = {
      CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors().copy(
          containerColor = AppTheme.colors.background,
          navigationIconContentColor = Color.White
        ),
        title = {
          Image(
            modifier = Modifier.size(46.dp),
            painter = painterResource(id = R.drawable.music_logo),
            colorFilter = ColorFilter.tint(AppTheme.colors.onPrimary),
            contentDescription = null,
          )
        },
      )
    },
    content = { paddingValues ->
      Column(
        modifier = Modifier
          .padding(paddingValues)
          .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Spacer(modifier = Modifier.height(24.dp))

        CounterView(
          selectedNumber = mainUiState.selectedBpmDeltaValue.value,
          acceleratedBpm = mainUiState.acceleratedBpm,
          onBpmValueChange = { newBpm -> mainViewModel.onChangeBpm(newBpm) },
          bpmValues = mainUiState.bpmDeltaValues,
          onAddBpm = { mainViewModel.onChangeBpmByAdding() },
          onSubtractBpm = { mainViewModel.onChangeBpmBySubtract() },
          onChangeBpmValue = { mainViewModel.onSelectBpmDeltaValue(it) }
        )

        MainRoundButton(
          isRunning = mainUiState.isRunning,
          durationMillis = mainUiState.intervalMs,
          // TODO change this
          counterText = when (counterUiState.isCounterEnabled) {
            true -> counterUiState.counterText
            false -> null
          },
          onClick = {
            if (mainUiState.isRunning) {
              accelerateViewModel.cancelAcceleration()
            }

            when (mainUiState.isRunning) {
              true -> MetronomeService.getStopIntent(context)
              false -> MetronomeService.getStartIntent(context)
            }.also {
              context.startService(it)
            }
          },
        )

        var showTickSettingsDialog by remember {
          mutableStateOf(false)
        }
        var showAccelerateSettingsDialog by remember {
          mutableStateOf(false)
        }

        if (showTickSettingsDialog) {
          TickSettingsDialog(
            onDismiss = { showTickSettingsDialog = false },
            onOptionSelected = { option ->
              settingsViewModel.onUpdateTickSettings(option)
              showTickSettingsDialog = false
            }
          )
        }

        if (showAccelerateSettingsDialog) {
          AccelerateSettingsDialog(
            currentAccelerateSettings = settingsUiState.accelerateSettingsOrDefault,
            onDismiss = { showAccelerateSettingsDialog = false },
            onOptionSelected = { option ->
              settingsViewModel.onUpdateAccelerateSettings(option)
              showAccelerateSettingsDialog = false
            }
          )
        }

        SettingsView(
          settingsViewModel = settingsViewModel,
          counterViewModel = counterViewModel,
          onTickSettingsClicked = {
            showTickSettingsDialog = true
          },
          onCounterSettingsClicked = {
            counterViewModel.toggleCounter()
          },
          onAccelerateBpmClicked = {
            showAccelerateSettingsDialog = true
          },
          onVibrationClicked = {
            settingsViewModel.onToggleIsVibrationEnabled()
          }
        )
      }
    },
  )
}

@Preview(showBackground = true)
@Composable
fun MetronomeScreenPreview() {
  AppTheme {
    MetronomeScreen()
  }
}