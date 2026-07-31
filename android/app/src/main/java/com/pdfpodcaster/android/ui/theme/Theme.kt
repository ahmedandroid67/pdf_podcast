package com.pdfpodcaster.android.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = AppColors.Indigo600,
    onPrimary = AppColors.FrostWhite,
    primaryContainer = AppColors.Indigo100,
    onPrimaryContainer = AppColors.Indigo900,
    secondary = AppColors.Teal600,
    onSecondary = AppColors.FrostWhite,
    secondaryContainer = AppColors.Teal400.copy(alpha = 0.2f),
    onSecondaryContainer = AppColors.Teal600,
    tertiary = AppColors.Indigo400,
    background = AppColors.FrostWhite,
    onBackground = AppColors.LightOnSurface,
    surface = AppColors.LightSurface,
    onSurface = AppColors.LightOnSurface,
    surfaceVariant = AppColors.FrostBlue,
    onSurfaceVariant = AppColors.LightOnSurfaceVariant,
    outline = AppColors.LightOutline,
    error = AppColors.ErrorRed,
    onError = AppColors.FrostWhite,
    errorContainer = AppColors.ErrorRed.copy(alpha = 0.1f),
    onErrorContainer = AppColors.ErrorRed
)

private val DarkColorScheme = darkColorScheme(
    primary = AppColors.Indigo400,
    onPrimary = AppColors.OceanDark,
    primaryContainer = AppColors.Indigo800,
    onPrimaryContainer = AppColors.Indigo100,
    secondary = AppColors.Teal500,
    onSecondary = AppColors.OceanDark,
    secondaryContainer = AppColors.Teal600.copy(alpha = 0.3f),
    onSecondaryContainer = AppColors.Teal400,
    tertiary = AppColors.Indigo300,
    background = AppColors.OceanDark,
    onBackground = AppColors.DarkOnSurface,
    surface = AppColors.DarkSurface,
    onSurface = AppColors.DarkOnSurface,
    surfaceVariant = AppColors.DarkSurface2,
    onSurfaceVariant = AppColors.DarkOnSurfaceVariant,
    outline = AppColors.DarkOutline,
    error = AppColors.ErrorRed,
    onError = AppColors.FrostWhite,
    errorContainer = AppColors.ErrorRed.copy(alpha = 0.2f),
    onErrorContainer = AppColors.ErrorRed.copy(alpha = 0.8f)
)

@Composable
fun PdfPodcasterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}