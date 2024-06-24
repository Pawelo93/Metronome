package pl.pawelantonik.metronome.feature.main.presentation

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.pawelantonik.metronome.ui.theme.AppTheme

@Composable
fun MainRoundButton(
  isEnabled: Boolean,
  durationMillis: Int,
) {
  val minPulseSize = 150f
  val maxPulseSize = 300f

  Box(
    modifier = Modifier.size(300.dp),
  ) {
      if (isEnabled) {
        PulseLoading(
          durationMillis = durationMillis,
          minPulseSize = minPulseSize,
          maxPulseSize = maxPulseSize,
          centreColor = Color(0.129f, 0.588f, 0.953f, 1.0f),
          pulseColor = Color(0.702f, 0.78f, 0.839f, 1.0f),
        )
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
        text = when (isEnabled) {
          true -> "Start"
          false -> "Stop"
        },
      )

  }
}

@Composable
fun PulseLoading(
  durationMillis: Int = 1000,
  maxPulseSize: Float = 300f,
  minPulseSize: Float = 50f,
  pulseColor: Color = Color(234, 240, 246),
  centreColor: Color = Color(66, 133, 244),
) {
  val infiniteTransition = rememberInfiniteTransition()
  val size by infiniteTransition.animateFloat(
    initialValue = minPulseSize,
    targetValue = maxPulseSize,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    )
  )
  val alpha by infiniteTransition.animateFloat(
    initialValue = 1f,
    targetValue = 0f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    )
  )
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
      isEnabled = true,
      durationMillis = 0,
    )
  }
}

@Preview
@Composable
fun MainRoundButtonDisabledPreview() {
  AppTheme {
    MainRoundButton(
      isEnabled = false,
      durationMillis = 0,
    )
  }
}