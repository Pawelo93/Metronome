package pl.pawelantonik.metronome.feature.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.pawelantonik.metronome.feature.settings.domain.AccentSettings
import pl.pawelantonik.metronome.ui.theme.AppTheme

@Composable
fun TickSettingsDialog(
  onDismiss: () -> Unit,
  onOptionSelected: (AccentSettings?) -> Unit,
) {
  AlertDialog(
    containerColor = AppTheme.colors.primary,
    onDismissRequest = onDismiss,
    title = {
      Text(
        text = "Choose a time signature",
        style = AppTheme.typography.headingH3,
        color = AppTheme.colors.secondaryText,
      )
    },
    text = {
      Column {
        TickDialogTextButton(
          AccentSettings(4),
          onOptionSelected = onOptionSelected,
        )
        TickDialogTextButton(
          AccentSettings(3),
          onOptionSelected = onOptionSelected,
        )
        TickDialogTextButton(
          null,
          onOptionSelected = onOptionSelected,
        )
      }
    },
    confirmButton = {},
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text(
          "Cancel",
          style = AppTheme.typography.basic,
          color = AppTheme.colors.secondaryText,
        )
      }
    },
    modifier = Modifier.padding(vertical = 20.dp),
  )
}

@Composable
private fun TickDialogTextButton(
  accentSettings: AccentSettings?,
  onOptionSelected: (AccentSettings?) -> Unit,
) {
  TextButton(
    modifier = Modifier.fillMaxWidth(),
    onClick = {
      onOptionSelected(accentSettings)
    },
  ) {
    Text(
      text = when {
        accentSettings != null -> accentSettings.toString()
        else -> "None"
      },
      style = AppTheme.typography.basicBold,
      color = AppTheme.colors.secondaryText,
    )
  }
}

@Preview
@Composable
private fun TickSettingsDialogPreview() {
  AppTheme {
    TickSettingsDialog(
      onDismiss = {},
      onOptionSelected = {},
    )
  }
}