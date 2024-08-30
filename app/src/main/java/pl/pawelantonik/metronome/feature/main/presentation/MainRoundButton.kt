package pl.pawelantonik.metronome.feature.main.presentation

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.pawelantonik.metronome.ui.extensions.clickableWithoutRipple
import pl.pawelantonik.metronome.ui.theme.AppTheme

@Composable
fun MainRoundButton(
  isRunning: Boolean,
  durationMillis: Long,
  onClick: (isRunning: Boolean) -> Unit,
) {
  val minPulseSize = 150f
  val maxPulseSize = 300f

  var lastDuration by remember { mutableLongStateOf(-1L) }

  Box(
    modifier = Modifier
      .size(300.dp)
      .clickableWithoutRipple { onClick(isRunning.not()) }
  ) {
    if (isRunning) {
      if (durationMillis != lastDuration) {
        LaunchedEffect(key1 = durationMillis) {
          lastDuration = durationMillis
        }
      } else {
        PulseLoadingWithState(
          durationMillis = durationMillis.toInt(),
          minPulseSize = minPulseSize,
          maxPulseSize = maxPulseSize,
          centreColor = Color(0.129f, 0.588f, 0.953f, 1.0f),
          pulseColor = Color(0.702f, 0.78f, 0.839f, 1.0f),
        )
      }
    } else {
      Card(
        modifier = Modifier
          .size(minPulseSize.dp)
          .align(Alignment.Center),
        shape = CircleShape,
        colors = CardDefaults.cardColors()
          .copy(containerColor = Color(0.549f, 0.616f, 0.667f, 1.0f)),
        content = {},
      )
    }

    Text(
      style = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
      modifier = Modifier.align(Alignment.Center),
      text = when (isRunning) {
        true -> "Stop"
        false -> "Start"
      },
    )

  }
}

@Composable
fun PulseLoadingWithState(
  durationMillis: Int = 1000,
  maxPulseSize: Float = 300f,
  minPulseSize: Float = 50f,
  pulseColor: Color = Color(234, 240, 246),
  centreColor: Color = Color(66, 133, 244),
) {
  val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")
  val size by infiniteTransition.animateFloat(
    initialValue = minPulseSize,
    targetValue = maxPulseSize,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "size",
  )
  val alpha by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = 0f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "alpha",
  )

  PulseLoading(
    size = size,
    alpha = alpha,
    minPulseSize = minPulseSize,
    pulseColor = pulseColor,
    centreColor = centreColor,
  )
}

@Composable
fun PulseLoading(
  size: Float,
  alpha: Float,
  minPulseSize: Float = 50f,
  pulseColor: Color = Color(234, 240, 246),
  centreColor: Color = Color(66, 133, 244),
) {
  Box(modifier = Modifier.fillMaxSize()) {
    Card(
      shape = CircleShape,
      modifier = Modifier
        .size(size.dp)
        .align(Alignment.Center)
        .alpha(alpha),
      colors = CardDefaults.cardColors().copy(containerColor = pulseColor),
      elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
      content = {},
    )
    Card(
      modifier = Modifier
        .size(minPulseSize.dp)
        .align(Alignment.Center),
      shape = CircleShape,
      colors = CardDefaults.cardColors().copy(containerColor = centreColor),
      content = {},
    )
  }
}

@Preview
@Composable
fun MainRoundButtonEnabledPreview() {
  AppTheme {
    MainRoundButton(
      isRunning = true,
      durationMillis = 0,
      onClick = {},
    )
  }
}

@Preview
@Composable
fun MainRoundButtonDisabledPreview() {
  AppTheme {
    MainRoundButton(
      isRunning = false,
      durationMillis = 0,
      onClick = {},
    )
  }
}