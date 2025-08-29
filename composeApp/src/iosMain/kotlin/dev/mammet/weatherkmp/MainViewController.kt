package dev.mammet.weatherkmp

import androidx.compose.ui.window.ComposeUIViewController
import dev.mammet.weatherkmp.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }