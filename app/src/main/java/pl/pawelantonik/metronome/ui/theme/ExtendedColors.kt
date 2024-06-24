package pl.pawelantonik.metronome.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White

@Immutable
data class ExtendedColors(
  val primary: Color = Purple40,
  val onPrimary: Color = White,
  val transparent: Color = Transparent,
  val defaultText: Color = Black600,
  val secondaryText: Color = Grey900,
  val background: Color = Background,
  val error: Color = Red500,
)

val LocalExtendedColors = staticCompositionLocalOf { ExtendedColors() }

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Background = Color(0xFF0f1321)
val Black600 = Color(0xFF2D2926)
val Grey900 = Color(0xFF727272)

val Red500 = Color(0xFFff4d4d)
val Red400 = Color(0xFFff7a7a)
val Red300 = Color(0xFFFBB7B9)
val Red100 = Color(0xFFFAE5E5)