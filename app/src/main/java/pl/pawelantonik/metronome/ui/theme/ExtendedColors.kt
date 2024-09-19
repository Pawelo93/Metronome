package pl.pawelantonik.metronome.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White

val PrimaryColor = Color(0xFF13171c)
val SecondaryColor = Color(0xFF2b2d34)
val AccentGreenColor = Color(0xFF92f2cb)
val AccentPurpleColor = Color(0xFF6361e2)
val AccentYellowColor = Color(0xFFfadc86)

val Black600 = Color(0xFF2D2926)
val Grey900 = Color(0xFFe5dfda)

val Background = PrimaryColor
val Red500 = Color(0xFFff4d4d)

val darkExtendedColors = ExtendedColors(
  primary = PrimaryColor,
  secondary = SecondaryColor,
  onPrimary = White,
  transparent = Transparent,
  defaultText = Black600,
  secondaryText = Grey900,
  background = Background,
  error = Red500,
  accentGreen = AccentGreenColor,
  accentPurple = AccentPurpleColor,
)

@Immutable
data class ExtendedColors(
  val primary: Color,
  val secondary: Color,
  val onPrimary: Color,
  val transparent: Color,
  val defaultText: Color,
  val secondaryText: Color,
  val background: Color,
  val error: Color,
  val accentGreen: Color,
  val accentPurple: Color,
)

val LocalExtendedColors = staticCompositionLocalOf { darkExtendedColors }
