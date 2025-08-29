package dev.mammet.weatherkmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.mammet.weatherkmp.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Weather-KMP",
        ) {
            App()
        }
    }
}