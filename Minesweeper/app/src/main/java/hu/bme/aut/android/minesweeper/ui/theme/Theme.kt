package hu.bme.aut.android.minesweeper.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = darkButton,
    secondary = darkBackground,
    tertiary = darkText
)

private val LightColorScheme = lightColorScheme(
    primary = lightButton,
    secondary = lightBackground,
    tertiary = lightText
)

var CustomColorScheme = lightColorScheme(
    primary = customButton,
    secondary = customBackground,
    tertiary = customText
)

@Composable
fun MinesweeperTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    custom: Boolean,
    change: Boolean = false,
    content: @Composable () -> Unit
) {
    if(change){
        var colorScheme = when {
            custom -> CustomColorScheme
            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }
        val view = LocalView.current
        if (!view.isInEditMode) {
            SideEffect {
                val window = (view.context as Activity).window
                window.statusBarColor = colorScheme.primary.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
            }
        }

        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
    else{
        var colorScheme = when {
            custom -> CustomColorScheme
            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }
        val view = LocalView.current
        if (!view.isInEditMode) {
            SideEffect {
                val window = (view.context as Activity).window
                window.statusBarColor = colorScheme.primary.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
            }
        }

        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}