package pl.pawelantonik.metronome.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White

//val PrimaryColor = Color(0xFF2b3b4e)
val PrimaryColor = Color(0xFF13171c)
//val SecondaryColor = Color(0xFFadc2bb)
val SecondaryColor = Color(0xFF2b2d34)
//val AccentGreenColor = Color(0xFF79c39c)
val AccentGreenColor = Color(0xFF92f2cb)
val AccentPurpleColor = Color(0xFF6361e2)
val AccentYellowColor = Color(0xFFfadc86)

val Black600 = Color(0xFF2D2926)
//val Grey900 = Color(0xFF727272)
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



//val Purple80 = Color(0xFFD0BCFF)
//val PurpleGrey80 = Color(0xFFCCC2DC)
//val Pink80 = Color(0xFFEFB8C8)
//
//val Purple40 = Color(0xFF6650a4)
//val PurpleGrey40 = Color(0xFF625b71)
//val Pink40 = Color(0xFF7D5260)
//
//
//val Red400 = Color(0xFFff7a7a)
//val Red300 = Color(0xFFFBB7B9)
//val Red100 = Color(0xFFFAE5E5)