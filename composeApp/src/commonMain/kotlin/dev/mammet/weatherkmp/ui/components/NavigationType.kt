package dev.mammet.weatherkmp.ui.components

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

enum class NavigationType{
    BOTTOM_NAVIGATION,
    RAIL_NAVIGATION,
    PERMANENT_NAVIGATION_DRAWER
}

fun getNavigationType(windowSize: WindowWidthSizeClass): NavigationType{
    return when(windowSize){
        WindowWidthSizeClass.Compact -> NavigationType.BOTTOM_NAVIGATION
        WindowWidthSizeClass.Medium -> NavigationType.RAIL_NAVIGATION
        WindowWidthSizeClass.Expanded -> NavigationType.PERMANENT_NAVIGATION_DRAWER
        else -> NavigationType.BOTTOM_NAVIGATION
    }
}