package pl.pawelantonik.metronome.ui.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.clickableWithoutRipple(onClick: () -> Unit) = composed {
  this.then(
    clickable(
      indication = null,
      interactionSource = remember { MutableInteractionSource() },
    ) {
      onClick()
    }
  )
}