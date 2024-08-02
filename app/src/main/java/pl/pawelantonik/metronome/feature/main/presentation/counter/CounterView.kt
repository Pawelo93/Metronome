package pl.pawelantonik.metronome.feature.main.presentation.counter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.pawelantonik.metronome.feature.main.presentation.SwipeValueSelector
import pl.pawelantonik.metronome.ui.extensions.clickableWithoutRipple
import pl.pawelantonik.metronome.ui.theme.AppTheme


@Composable
fun CounterView(
  selectedNumber: Int,
  bpmValue: Int,
  bpmValues: List<BpmDeltaValue>,
  onBpmValueChange: (value: Int) -> Unit,
  onChangeBpmValue: (value: BpmDeltaValue) -> Unit,
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