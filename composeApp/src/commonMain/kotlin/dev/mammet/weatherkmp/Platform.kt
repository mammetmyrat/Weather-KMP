package dev.mammet.weatherkmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform