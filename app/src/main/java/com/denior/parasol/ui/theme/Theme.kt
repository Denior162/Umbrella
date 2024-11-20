package com.denior.parasol.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Immutable
data class RiskExtendedColorScheme(
    val lowRisk: ColorFamily,
    val moderateRisk: ColorFamily,
    val highRisk: ColorFamily,
    val veryHighRisk: ColorFamily,
    val extremeRisk: ColorFamily,
)

@Immutable
data class SkinTypeExtendedColorScheme(
    val skinType1: ColorFamily,
    val skinType2: ColorFamily,
    val skinType3: ColorFamily,
    val skinType4: ColorFamily,
    val skinType5: ColorFamily,
    val skinType6: ColorFamily,
)

val skinTypeExtendedLight = SkinTypeExtendedColorScheme(
    skinType1 = ColorFamily(
        skinType1Light,
        onSkinType1Light,
        skinType1ContainerLight,
        onSkinType1ContainerLight,
    ),
    skinType2 = ColorFamily(
        skinType2Light,
        onSkinType2Light,
        skinType2ContainerLight,
        onSkinType2ContainerLight,
    ),
    skinType3 = ColorFamily(
        skinType3Light,
        onSkinType3Light,
        skinType3ContainerLight,
        onSkinType3ContainerLight,
    ),
    skinType4 = ColorFamily(
        skinType4Light,
        onSkinType4Light,
        skinType4ContainerLight,
        onSkinType4ContainerLight,
    ),
    skinType5 = ColorFamily(
        skinType5Light,
        onSkinType5Light,
        skinType5ContainerLight,
        onSkinType5ContainerLight,
    ),
    skinType6 = ColorFamily(
        skinType6Light,
        onSkinType6Light,
        skinType6ContainerLight,
        onSkinType6ContainerLight,
    ),
)

val skinTypeExtendedDark = SkinTypeExtendedColorScheme(
    skinType1 = ColorFamily(
        skinType1Dark,
        onSkinType1Dark,
        skinType1ContainerDark,
        onSkinType1ContainerDark,
    ),
    skinType2 = ColorFamily(
        skinType2Dark,
        onSkinType2Dark,
        skinType2ContainerDark,
        onSkinType2ContainerDark,
    ),
    skinType3 = ColorFamily(
        skinType3Dark,
        onSkinType3Dark,
        skinType3ContainerDark,
        onSkinType3ContainerDark,
    ),
    skinType4 = ColorFamily(
        skinType4Dark,
        onSkinType4Dark,
        skinType4ContainerDark,
        onSkinType4ContainerDark,
    ),
    skinType5 = ColorFamily(
        skinType5Dark,
        onSkinType5Dark,
        skinType5ContainerDark,
        onSkinType5ContainerDark,
    ),
    skinType6 = ColorFamily(
        skinType6Dark,
        onSkinType6Dark,
        skinType6ContainerDark,
        onSkinType6ContainerDark,
    ),
)

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

val riskExtendedLight = RiskExtendedColorScheme(
    lowRisk = ColorFamily(
        lowRiskLight,
        onLowRiskLight,
        lowRiskContainerLight,
        onLowRiskContainerLight,
    ),
    moderateRisk = ColorFamily(
        moderateRiskLight,
        onModerateRiskLight,
        moderateRiskContainerLight,
        onModerateRiskContainerLight,
    ),
    highRisk = ColorFamily(
        highRiskLight,
        onHighRiskLight,
        highRiskContainerLight,
        onHighRiskContainerLight,
    ),
    veryHighRisk = ColorFamily(
        veryHighRiskLight,
        onVeryHighRiskLight,
        veryHighRiskContainerLight,
        onVeryHighRiskContainerLight,
    ),
    extremeRisk = ColorFamily(
        extremeRiskLight,
        onExtremeRiskLight,
        extremeRiskContainerLight,
        onExtremeRiskContainerLight,
    ),
)

val riskExtendedDark = RiskExtendedColorScheme(
    lowRisk = ColorFamily(
        lowRiskDark,
        onLowRiskDark,
        lowRiskContainerDark,
        onLowRiskContainerDark,
    ),
    moderateRisk = ColorFamily(
        moderateRiskDark,
        onModerateRiskDark,
        moderateRiskContainerDark,
        onModerateRiskContainerDark,
    ),
    highRisk = ColorFamily(
        highRiskDark,
        onHighRiskDark,
        highRiskContainerDark,
        onHighRiskContainerDark,
    ),
    veryHighRisk = ColorFamily(
        veryHighRiskDark,
        onVeryHighRiskDark,
        veryHighRiskContainerDark,
        onVeryHighRiskContainerDark,
    ),
    extremeRisk = ColorFamily(
        extremeRiskDark,
        onExtremeRiskDark,
        extremeRiskContainerDark,
        onExtremeRiskContainerDark,
    ),
)


@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val LocalSkinTypeExtendedColorScheme = staticCompositionLocalOf { skinTypeExtendedLight }
val LocalRiskExtendedColorScheme = staticCompositionLocalOf { riskExtendedLight }

@Composable
fun ParasolTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkScheme
        else -> lightScheme
    }

    val skinTypeScheme = if (darkTheme) skinTypeExtendedDark else skinTypeExtendedLight
    val riskScheme = if (darkTheme) riskExtendedDark else riskExtendedLight

    CompositionLocalProvider(
        LocalSkinTypeExtendedColorScheme provides skinTypeScheme,
        LocalRiskExtendedColorScheme provides riskScheme
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}


fun getUVCardColors(uvi: Double, riskExtendedColorScheme: RiskExtendedColorScheme): CardColors {
    return when {
        uvi <= 2 -> CardColors(
            containerColor = riskExtendedColorScheme.lowRisk.colorContainer,
            contentColor = riskExtendedColorScheme.lowRisk.onColorContainer,
            disabledContainerColor = riskExtendedColorScheme.lowRisk.colorContainer,
            disabledContentColor = riskExtendedColorScheme.lowRisk.onColorContainer
        )

        uvi <= 5 -> CardColors(
            containerColor = riskExtendedColorScheme.moderateRisk.colorContainer,
            contentColor = riskExtendedColorScheme.moderateRisk.onColorContainer,
            disabledContainerColor = riskExtendedColorScheme.moderateRisk.colorContainer,
            disabledContentColor = riskExtendedColorScheme.moderateRisk.onColorContainer
        )

        uvi <= 7 -> CardColors(
            containerColor = riskExtendedColorScheme.highRisk.colorContainer,
            contentColor = riskExtendedColorScheme.highRisk.onColorContainer,
            disabledContainerColor = riskExtendedColorScheme.highRisk.colorContainer,
            disabledContentColor = riskExtendedColorScheme.highRisk.onColorContainer
        )

        uvi <= 10 -> CardColors(
            containerColor = riskExtendedColorScheme.veryHighRisk.colorContainer,
            contentColor = riskExtendedColorScheme.veryHighRisk.onColorContainer,
            disabledContainerColor = riskExtendedColorScheme.veryHighRisk.colorContainer,
            disabledContentColor = riskExtendedColorScheme.veryHighRisk.onColorContainer
        )

        else -> CardColors(
            containerColor = riskExtendedColorScheme.extremeRisk.colorContainer,
            contentColor = riskExtendedColorScheme.extremeRisk.onColorContainer,
            disabledContainerColor = riskExtendedColorScheme.extremeRisk.colorContainer,
            disabledContentColor = riskExtendedColorScheme.extremeRisk.onColorContainer
        )
    }
}