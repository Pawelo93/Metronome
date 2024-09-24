package pl.pawelantonik.metronome.feature.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.pawelantonik.metronome.feature.accelerateBpm.domain.AccelerateSettings
import pl.pawelantonik.metronome.ui.theme.AppTheme

@Composable
fun AccelerateSettingsDialog(
  currentAccelerateSettings: AccelerateSettings,
  onDismiss: () -> Unit,
  onOptionSelected: (AccelerateSettings?) -> Unit
) {
  var selectedBpm by remember { mutableIntStateOf(currentAccelerateSettings.bpmAcceleration) }
  var selectedBars by remember { mutableIntStateOf(currentAccelerateSettings.barsCount) }

  AlertDialog(
    containerColor = AppTheme.colors.primary,
    onDismissRequest = onDismiss,
    title = {
      Text(
        text = "Choose an acceleration",
        style = AppTheme.typography.headingH3,
        color = AppTheme.colors.secondaryText,
      )
    },
    text = {
      BpmSelector(
        selectedBpm = selectedBpm,
        selectedBars = selectedBars,
        onBpmSelected = { selectedBpm = it },
        onBarsSelected = { selectedBars = it },
      )
    },
    confirmButton = {
      TextButton(
        onClick = {
          onOptionSelected(
            AccelerateSettings(
              bpmAcceleration = selectedBpm,
              barsCount = selectedBars,
            )
          )
        },
      ) {
        Text(
          style = AppTheme.typography.basic,
          color = AppTheme.colors.secondaryText,
          text = "Confirm",
        )
      }
    },
    dismissButton = {
      TextButton(
        onClick = {
          onOptionSelected(null)
        },
      ) {
        Text(
          style = AppTheme.typography.basic,
          color = AppTheme.colors.secondaryText,
          text = "Remove",
        )
      }
    },
    modifier = Modifier.padding(vertical = 20.dp)
  )
}

@Composable
fun BpmSelector(
  selectedBpm: Int,
  selectedBars: Int,
  onBpmSelected: (bpm: Int) -> Unit,
  onBarsSelected: (bars: Int) -> Unit,
) {
  // todo needs default values
  var bpmExpanded by remember { mutableStateOf(false) }
  var barsExpanded by remember { mutableStateOf(false) }

  val bpmOptions = (1..10).toList()
  val barsOptions = (1..8).toList()

  Row(modifier = Modifier.padding(16.dp)) {
    SimpleText("Add ")

    DropdownText(
      text = "$selectedBpm bpm",
      onClick = { bpmExpanded = true }
    )

    SimpleDropDownMenu(
      isExpanded = bpmExpanded,
      name = "bpm",
      options = bpmOptions,
      onIsExpandedChange = { bpmExpanded = it },
      onItemSelected = { onBpmSelected(it) }
    )

    SimpleText(" every ")

    DropdownText(
      text = "$selectedBars bars",
      onClick = { barsExpanded = true }
    )

    SimpleDropDownMenu(
      isExpanded = barsExpanded,
      name = "bars",
      options = barsOptions,
      onIsExpandedChange = { barsExpanded = it },
      onItemSelected = { onBarsSelected(it) }
    )
  }
}

@Composable
private fun SimpleText(text: String) {
  Text(
    modifier = Modifier.padding(AppTheme.spacings.small),
    style = AppTheme.typography.basicBold,
    color = AppTheme.colors.secondaryText,
    text = text,
  )
}

@Composable
private fun DropdownText(
  text: String, onClick: () -> Unit,
) {
  Surface(
    color = AppTheme.colors.accentGreen,
    shape = RoundedCornerShape(8.dp),
  ) {
    Text(
      style = AppTheme.typography.medium.copy(color = AppTheme.colors.defaultText),
      text = text,
      modifier = Modifier
        .clickable { onClick() }
        .padding(AppTheme.spacings.small)
    )
  }
}

@Composable
private fun SimpleDropDownMenu(
  isExpanded: Boolean,
  options: List<Int>,
  name: String,
  onIsExpandedChange: (isExpanded: Boolean) -> Unit,
  onItemSelected: (item: Int) -> Unit,
) {
  DropdownMenu(
    modifier = Modifier.background(AppTheme.colors.secondary),
    expanded = isExpanded,
    onDismissRequest = { onIsExpandedChange(false) }
  ) {
    options.forEach { item ->
      DropdownMenuItem(
        text = {
          Text(
            style = AppTheme.typography.medium,
            text = "$item $name"
          )
        },
        onClick = {
          onItemSelected(item)
          onIsExpandedChange(false)
        }
      )
    }
  }
}

@Preview
@Composable
private fun AccelerateSettingsDialogPreview() {
  AppTheme {
    AccelerateSettingsDialog(
      currentAccelerateSettings = AccelerateSettings(2, 3),
      onDismiss = {},
      onOptionSelected = {},
    )
  }
}
