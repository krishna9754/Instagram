package com.example.instagram.mode

import kotlinx.coroutines.flow.StateFlow

enum class AppTheme {
    MODE_AUTO,
    MODE_DAY,
    MODE_NIGHT;

    companion object {
        fun fromOrdinal(ordinal: Int): AppTheme {
            return values().getOrElse(ordinal) { MODE_AUTO }
        }
    }
}


interface ThemeSetting{
    val themeStream: StateFlow<AppTheme>
    var theme: AppTheme
}