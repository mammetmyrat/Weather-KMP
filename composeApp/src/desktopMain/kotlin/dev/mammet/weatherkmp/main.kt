package dev.mammet.weatherkmp

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.mammet.weatherkmp.di.initKoin

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Weather-KMP",
        ) {
            val calculateWindowSize = calculateWindowSizeClass()
            App(calculateWindowSize.widthSizeClass)
        }
    }
}