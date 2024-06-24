package pl.pawelantonik.metronome.feature.main.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import pl.pawelantonik.metronome.ui.theme.AppTheme

@Composable
fun BottomOptions() {
  Column {
    Row(horizontalArrangement = Arrangement.Center) {
      OptionItem(text = "Accent")
      OptionItem(text = "Other")
    }
  }
}

@Composable
private fun RowScope.OptionItem(text: String) {
  Box(
    modifier = Modifier
      .weight(1f)
      .padding(24.dp)
      .border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(8.dp)
      )
      .clip(RoundedCornerShape(8.dp))
      .padding(8.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(start = AppTheme.spacings.small, end = AppTheme.spacings.small),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Text(text = text)
      Icon(
        modifier = Modifier.size(16.dp),
        imageVector = Icons.Filled.Check,
        contentDescription = null,
      )
    }
  }
}