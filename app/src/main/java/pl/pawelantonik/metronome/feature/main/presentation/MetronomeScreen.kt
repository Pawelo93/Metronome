package pl.pawelantonik.metronome.feature.main.presentation

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.pawelantonik.metronome.R
import pl.pawelantonik.metronome.feature.main.Timer
import pl.pawelantonik.metronome.ui.extensions.clickableWithoutRipple
import pl.pawelantonik.metronome.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetronomeScreen(
  timer: Timer,
) {
  val mainViewModel: MainViewModel = hiltViewModel()
  val mainUiState by mainViewModel.uiState.collectAsState()

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

        Counter(
          selectedNumber = mainUiState.selectedChangeBpmValue.value,
          bpmValue = mainUiState.bpm,
          onBpmValueChange = { newBpm -> mainViewModel.onChangeBpm(newBpm) },
          bpmValues = mainUiState.changeBpmValues,
          onAddBpm = { mainViewModel.onChangeBpm(MainViewModel.BpmChangeDir.UP) },
          onSubtractBpm = { mainViewModel.onChangeBpm(MainViewModel.BpmChangeDir.DOWN) },
          onChangeBpmValue = { mainViewModel.onChangeBpmValue(it) }
        )

        val scope = rememberCoroutineScope()
        var job: Job? by remember { mutableStateOf(null) }

        if (mainUiState.isRunning) {
          timer.update(mainUiState.intervalMs)
        }

        MainRoundButton(
          isRunning = mainUiState.isRunning,
          durationMillis = mainUiState.intervalMs,
          onClick = { isRunning ->
            mainViewModel.startStop(isRunning)
            if (isRunning) {
              job = scope.launch { timer.start(mainUiState.intervalMs) }
            } else {
              job?.cancel()
            }
          },
        )

        DisposableEffect(Unit) {
          onDispose {
            timer.release()
            job?.cancel()
          }
        }

        Spacer(modifier = Modifier.height(32.dp))

        BottomOptions()
      }
    },
  )
}

@Composable
private fun Counter(
  selectedNumber: Int,
  bpmValue: Int,
  bpmValues: List<MainViewModel.ChangeBpmValue>,
  onBpmValueChange: (value: Int) -> Unit,
  onChangeBpmValue: (value: MainViewModel.ChangeBpmValue) -> Unit,
  onAddBpm: () -> Unit,
  onSubtractBpm: () -> Unit,
) {

  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceAround,
    verticalAlignment = Alignment.CenterVertically
  ) {
    val textStyle =
      AppTheme.typography.headingH1.copy(color = AppTheme.colors.onPrimary, fontSize = 52.sp)
    Column {
      Text(
        modifier = Modifier.clickableWithoutRipple { onSubtractBpm() },
        style = textStyle,
        text = "-",
      )
    }

    Column {
      SwipeValueSelector(
        initialValue = bpmValue,
        onValueChange = {
          onBpmValueChange(it)
        },
      )
    }

    Column {
      Text(
        modifier = Modifier.clickableWithoutRipple { onAddBpm() },
        style = textStyle,
        text = "+",
      )
    }
  }

  Spacer(modifier = Modifier.height(16.dp))

  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceAround,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row {
      for (valueChange in bpmValues) {
        Text(
          modifier = Modifier
            .clickableWithoutRipple { onChangeBpmValue(valueChange) }
            .padding(start = AppTheme.spacings.small, end = AppTheme.spacings.small)
            .then(
              if (valueChange.value == selectedNumber) {
                Modifier.background(
                  color = AppTheme.colors.primary,
                  shape = CircleShape,
                )
              } else {
                Modifier
              }
            )
            .size(30.dp)
            .wrapContentSize(),
          text = valueChange.value.toString(),
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun MetronomeScreenPreview() {
  AppTheme {
    MetronomeScreen(
      timer = object : Timer{
        override fun init(context: Context) {
          TODO("Not yet implemented")
        }

        override suspend fun start(intervalMs: Long) {
          TODO("Not yet implemented")
        }

        override fun update(intervalMs: Long) {
          TODO("Not yet implemented")
        }

        override fun stop() {
          TODO("Not yet implemented")
        }

        override fun release() {
          TODO("Not yet implemented")
        }
      }
    )
  }
}