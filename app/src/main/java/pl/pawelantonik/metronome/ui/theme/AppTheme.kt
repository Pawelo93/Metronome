package pl.pawelantonik.metronome.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object AppTheme {
  val colors: ExtendedColors
    @Composable
    get() = LocalExtendedColors.current
  val typography: ExtendedFonts
    @Composable
    get() = LocalExtendedFonts.current
  val spacings: ExtendedSpacings
    @Composable
    get() = LocalExtendedSpacings.current
}

private val colors: ExtendedColors
  get() = ExtendedColors(
    primary = Purple40,
    onPrimary = White,
    transparent = Transparent,
    defaultText = Black600,
    secondaryText = Grey900,
    background = Background,
    error = Red500,
  )

private val fonts: ExtendedFonts
  @Composable
  get() = ExtendedFonts(
    headingH1 = TextStyle(
      fontFamily = MainAppFont,
      fontSize = 24.sp,
      lineHeight = 30.sp,
      fontWeight = FontWeight.Bold,
      color = AppTheme.colors.defaultText
    ),
    headingH2 = TextStyle(
      fontFamily = MainAppFont,
      fontSize = 20.sp,
      lineHeight = 24.sp,
      fontWeight = FontWeight.Bold,
      color = AppTheme.colors.defaultText
    ),
    headingH3 = TextStyle(
      fontFamily = MainAppFont,
      fontSize = 16.sp,
      lineHeight = 21.sp,
      fontWeight = FontWeight.Bold,
      color = AppTheme.colors.defaultText
    ),
    basic = TextStyle(
      fontFamily = MainAppFont,
      fontSize = 16.sp,
      lineHeight = 21.sp,
      fontWeight = FontWeight.Normal,
      color = AppTheme.colors.defaultText
    ),
    basicBold = TextStyle(
      fontFamily = MainAppFont,
      fontSize = 16.sp,
      lineHeight = 21.sp,
      fontWeight = FontWeight.Bold,
      color = AppTheme.colors.defaultText
    ),
    medium = TextStyle(
      fontFamily = MainAppFont,
      fontSize = 14.sp,
      lineHeight = 17.sp,
      fontWeight = FontWeight.Normal,
      color = AppTheme.colors.secondaryText
    ),
    micro = TextStyle(
      fontFamily = MainAppFont,
      fontSize = 10.sp,
      lineHeight = 14.sp,
      fontWeight = FontWeight.Normal,
      color = AppTheme.colors.secondaryText,
    ),
  )

private val spacings: ExtendedSpacings
  get() = ExtendedSpacings(
    none = 0.dp,
    micro = 4.dp,
    small = 8.dp,
    base = 16.dp,
    large = 24.dp,
    xlarge = 36.dp,
    xxlarge = 64.dp,
  )

private val simpleLightMaterialColorPalette = lightColorScheme(
  primary = Purple40,
  onPrimary = Black600,
  secondary = Purple80,
  onSecondary = Black600,
  background = Background,
  onBackground = White,
  surface = White,
  onSurface = Black600,
  error = Red500,
  onError = White,
)

val simpleShapes = Shapes(
  small = RoundedCornerShape(4.dp),
  medium = RoundedCornerShape(4.dp),
  large = RoundedCornerShape(0.dp)
)

val simpleFonts = Typography(
  displayLarge = defaultTypography.displayLarge.copy(fontFamily = MainAppFont),
  displayMedium = defaultTypography.displayMedium.copy(fontFamily = MainAppFont),
  displaySmall = defaultTypography.displaySmall.copy(fontFamily = MainAppFont),
  headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = MainAppFont),
  headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = MainAppFont),
  headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = MainAppFont),
  titleLarge = defaultTypography.titleLarge.copy(fontFamily = MainAppFont),
  titleMedium = defaultTypography.titleMedium.copy(fontFamily = MainAppFont),
  titleSmall = defaultTypography.titleSmall.copy(fontFamily = MainAppFont),
  bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = MainAppFont),
  bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = MainAppFont),
  bodySmall = defaultTypography.bodySmall.copy(fontFamily = MainAppFont),
  labelLarge = defaultTypography.labelLarge.copy(fontFamily = MainAppFont),
  labelMedium = defaultTypography.labelMedium.copy(fontFamily = MainAppFont),
  labelSmall = defaultTypography.labelSmall.copy(fontFamily = MainAppFont),
)

@Composable
fun AppTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Dynamic color is available on Android 12+
  dynamicColor: Boolean = true,
  content: @Composable () -> Unit,
) {
//  val colorScheme = when {
//    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//      val context = LocalContext.current
//      if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//    }
//
//    darkTheme -> DarkColorScheme
//    else -> LightColorScheme
//  }

  CompositionLocalProvider(
    LocalExtendedColors provides colors,
    LocalExtendedSpacings provides spacings,
    LocalExtendedFonts provides fonts,
  ) {
    MaterialTheme(
      colorScheme = simpleLightMaterialColorPalette,
      shapes = simpleShapes,
      typography = simpleFonts,
      content = content,
    )
  }

}