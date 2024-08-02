package pl.pawelantonik.metronome.feature.main.presentation

import android.content.Context
import android.content.Intent
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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
import pl.pawelantonik.metronome.MetronomeService
import pl.pawelantonik.metronome.R
import pl.pawelantonik.metronome.feature.main.SoundPlayer
import pl.pawelantonik.metronome.feature.main.presentation.counter.CounterView
import pl.pawelantonik.metronome.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetronomeScreen(
  soundPlayer: SoundPlayer,
) {
  val context = LocalContext.current
  val mainViewModel: MainViewModel = hiltViewModel()
  mainViewModel.load()

  val mainUiState by mainViewModel.uiState.collectAsState()
  val tickState by mainViewModel.tick.collectAsState()

  Scaffold(
    topBar = {
      CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors().copy(
          containerColor = AppTheme.colors.background,
          navigationIconContentColor = Color.White
        ),
        title = {
          Image(
            modifier = Modifier.size(56.dp),
            painter = painterResource(id = R.drawable.music_logo),
            colorFilter = ColorFilter.tint(AppTheme.colors.primary),
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
        Spacer(modifier = Modifier.height(32.dp))

        CounterView(
          selectedNumber = mainUiState.selectedBpmDeltaValue.value,
          bpmValue = mainUiState.bpm,
          onBpmValueChange = { newBpm -> mainViewModel.onChangeBpm(newBpm) },
          bpmValues = mainUiState.bpmDeltaValues,
          onAddBpm = { mainViewModel.onChangeBpmByAdding() },
          onSubtractBpm = { mainViewModel.onChangeBpmBySubtract() },
          onChangeBpmValue = { mainViewModel.onSelectBpmDeltaValue(it) }
        )

        LaunchedEffect(tickState.currentBit) {
          if (mainUiState.isRunning) {
            soundPlayer.play(tickState.isAccentBeat)
          }
        }

        var isServiceRunning by remember {
          mutableStateOf(false)
        }

        MainRoundButton(
          isRunning = mainUiState.isRunning,
          durationMillis = mainUiState.intervalMs,
          onClick = { isRunning ->
//            mainViewModel.onUpdateIsRunning(isRunning)

            if (isServiceRunning) {
              Intent(context, MetronomeService::class.java).also {
                it.action = MetronomeService.ServiceActions.STOP.toString()
                context.startService(it)
                isServiceRunning = false
              }
            } else {
              Intent(context, MetronomeService::class.java).also {
                it.action = MetronomeService.ServiceActions.START.toString()
                context.startService(it)
                isServiceRunning = true
              }
            }
          },
        )

        DisposableEffect(Unit) {
          onDispose {
            soundPlayer.release()
          }
        }

        Spacer(modifier = Modifier.height(32.dp))

        var showTickSettingsDialog by remember {
          mutableStateOf(false)
        }

        if (showTickSettingsDialog) {
          TickSettingsDialog(
            onDismiss = { showTickSettingsDialog = false },
            onOptionSelected = { option ->
              mainViewModel.onUpdateTickSettings(option)
              showTickSettingsDialog = false
            }
          )
        }

        BottomOptions(
          tickSettings = tickState.tickSettings,
          onTickSettingsClicked = {
            showTickSettingsDialog = true
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
    MetronomeScreen(
      soundPlayer = object : SoundPlayer {
        override fun init(context: Context) {
        }

        override fun play(isAccentBeat: Boolean) {
        }

        override fun release() {
        }
      }
    )
  }
}