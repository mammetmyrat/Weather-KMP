package dev.mammet.weatherkmp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.window.ComposeUIViewController
import dev.mammet.weatherkmp.di.initKoin

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    val calculateWindowSize = calculateWindowSizeClass()

    App(calculateWindowSize.widthSizeClass, dynamicColor = false, darkTheme = isSystemInDarkTheme())
}