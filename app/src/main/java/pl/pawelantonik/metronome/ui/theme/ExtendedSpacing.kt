package pl.pawelantonik.metronome.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class ExtendedSpacings(
  val none: Dp = 0.dp,
  val micro: Dp = 4.dp,
  val small: Dp = 8.dp,
  val base: Dp= 16.dp,
  val large: Dp= 24.dp,
  val xlarge: Dp= 36.dp,
  val xxlarge: Dp= 64.dp,
)

val LocalExtendedSpacings = compositionLocalOf { ExtendedSpacings() }

