package dev.mammet.weatherkmp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import dev.mammet.weatherkmp.ui.components.getNavigationType
import dev.mammet.weatherkmp.ui.home.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(windowWidthSizeClass: WindowWidthSizeClass) {
    MaterialTheme {
        HomeScreen(
            navigationType = getNavigationType(windowWidthSizeClass)
        )
    }
}