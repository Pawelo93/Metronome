package pl.pawelantonik.metronome.feature.main.presentation

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import pl.pawelantonik.metronome.ui.theme.AppTheme
import kotlin.math.roundToInt

@Composable
fun SwipeValueSelector(
  initialValue: Int,
  hasAcceleration: Boolean,
  minValue: Int = 50,
  maxValue: Int = 200,
  sensitivity: Float = 0.2f,
  onValueChange: (Int) -> Unit,
) {
  var isSwiping by remember { mutableStateOf(false) }
  var currentValue by remember { mutableStateOf(initialValue) }
  if (isSwiping.not()) {
    currentValue = initialValue
  }

  Box(
    modifier = Modifier
      .pointerInput(Unit) {
        detectVerticalDragGestures(
          onVerticalDrag = { change, dragAmount ->
            isSwiping = true
            val valueChange = -(dragAmount) * sensitivity
            currentValue = (currentValue + valueChange.roundToInt())
              .coerceIn(minValue, maxValue)

            onValueChange(currentValue)
          },
          onDragEnd = {
            isSwiping = false
          }
        )
      },
    contentAlignment = Alignment.Center
  ) {
    Text(
      style = AppTheme.typography.headingH1.copy(
        fontSize = TextUnit(
          80f,
          TextUnitType.Sp
        ),
        fontWeight = FontWeight.Bold,
        color = when (hasAcceleration) {
          true -> AppTheme.colors.accentGreen
          false -> AppTheme.colors.onPrimary
        },
      ),
      text = currentValue.toString(),
    )
  }
}