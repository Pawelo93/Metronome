package pl.pawelantonik.metronome.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import pl.pawelantonik.metronome.R

val MainAppFont = FontFamily(
  Font(R.font.lato_light, FontWeight.Light),
  Font(R.font.lato_regular),
  Font(R.font.lato_bold, FontWeight.Bold)
)

val AppDefaultTextStyle = TextStyle(
  fontFamily = MainAppFont
)

val defaultTypography = Typography()
