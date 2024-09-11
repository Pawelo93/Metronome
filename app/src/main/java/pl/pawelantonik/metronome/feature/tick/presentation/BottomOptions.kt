package pl.pawelantonik.metronome.feature.tick.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.pawelantonik.metronome.R
import pl.pawelantonik.metronome.feature.main.domain.TickSettings
import pl.pawelantonik.metronome.ui.theme.AppTheme

@Composable
fun BottomOptions(
  tickSettings: TickSettings?,
  isCounterEnabled: Boolean,
  onTickSettingsClicked: () -> Unit,
  onCounterSettingsClicked: () -> Unit,
) {
  Column {
    Row(horizontalArrangement = Arrangement.Center) {
      AnimatedOptionItem(
        modifier = Modifier.padding(start = 24.dp, end = 8.dp),
        optionItemState = OptionItemState(
          name = "Accent",
          text = tickSettings?.toString() ?: "Off",
          isEnabledIconVisible = tickSettings != null,
        ),
        onClick = onTickSettingsClicked,
      )

      AnimatedOptionItem(
        modifier = Modifier.padding(start = 8.dp, end = 24.dp),
        optionItemState = OptionItemState(
          name = "Counter",
          text = when (isCounterEnabled) {
            true -> "On"
            false -> "Off"
          },
          isEnabledIconVisible = isCounterEnabled,
        ),
        onClick = { onCounterSettingsClicked() },
      )
    }

    // TODO add logic
    Row(horizontalArrangement = Arrangement.Center) {
      AnimatedOptionItem(
        modifier = Modifier.padding(start = 24.dp, end = 8.dp),
        optionItemState = OptionItemState(
          name = "Accelerate",
          text = tickSettings?.toString() ?: "Off",
          isEnabledIconVisible = tickSettings != null,
        ),
        onClick = onTickSettingsClicked,
      )

      AnimatedOptionItem(
        modifier = Modifier.padding(start = 8.dp, end = 24.dp),
        optionItemState = OptionItemState(
          name = "Vibration",
          text = when (isCounterEnabled) {
            true -> "On"
            false -> "Off"
          },
          isEnabledIconVisible = isCounterEnabled,
        ),
        onClick = { onCounterSettingsClicked() },
      )
    }
  }
}

data class OptionItemState(
  val name: String,
  val text: String,
  val isEnabledIconVisible: Boolean,
)

@Composable
private fun RowScope.AnimatedOptionItem(
  modifier: Modifier,
  optionItemState: OptionItemState,
  onClick: () -> Unit,
) {
  AnimatedContent(
    modifier = Modifier
      .weight(1f)
      .padding(top = 24.dp)
      .then(modifier),
    targetState = optionItemState,
    label = optionItemState.name,
  ) { state ->
    OptionItem(
      name = state.name,
      text = state.text,
      onClick = onClick,
      isEnabledIconVisible = state.isEnabledIconVisible,
    )
  }
}

@Composable
private fun OptionItem(
  name: String,
  text: String,
  onClick: () -> Unit,
  isEnabledIconVisible: Boolean,
) {
  Card(
    modifier = Modifier
      .clickable { onClick() }
      .fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
  ) {

    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(AppTheme.colors.secondary)
        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
    ) {

      Text(
        modifier = Modifier.padding(bottom = 16.dp),
        style = AppTheme.typography.medium.copy(color = AppTheme.colors.secondaryText),
        text = name,
      )

      Row(
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Icon(
          modifier = Modifier
            .size(24.dp),
          painter = painterResource(
            id = when (isEnabledIconVisible) {
              true -> R.drawable.check
              false -> R.drawable.empty_check
            },
          ),
          contentDescription = "Selected Icon",
          tint = when (isEnabledIconVisible) {
            true -> AppTheme.colors.accentGreen
            false -> AppTheme.colors.background
          },
        )

        Text(
          modifier = Modifier.padding(start = 20.dp),
          style = AppTheme.typography.medium.copy(color = AppTheme.colors.secondaryText),
          text = text,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
        )
      }
    }
  }
}

@Preview(heightDp = 200)
@Composable
fun BottomOptionsPreview() {
  AppTheme {
    BottomOptions(
      TickSettings(2),
      false,
      {},
      {},
    )
  }
}