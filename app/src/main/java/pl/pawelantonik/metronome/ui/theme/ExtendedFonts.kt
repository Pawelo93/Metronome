package pl.pawelantonik.metronome.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

@Immutable
data class ExtendedFonts(
  val headingH1: TextStyle,
  val headingH2: TextStyle,
  val headingH3: TextStyle,
  val basic: TextStyle,
  val basicBold: TextStyle,
  val medium: TextStyle,
  val micro: TextStyle,
)

internal val LocalExtendedFonts = staticCompositionLocalOf {
  ExtendedFonts(
    headingH1 = AppDefaultTextStyle,
    headingH2 = AppDefaultTextStyle,
    headingH3 = AppDefaultTextStyle,
    basic = AppDefaultTextStyle,
    basicBold = AppDefaultTextStyle,
    medium = AppDefaultTextStyle,
    micro = AppDefaultTextStyle,
  )
}
