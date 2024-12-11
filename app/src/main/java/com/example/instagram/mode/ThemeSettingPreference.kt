package com.example.instagram.mode

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ThemeSettingPreference @Inject constructor(
    @ApplicationContext context: Context
) : ThemeSetting {

    private val preference: SharedPreferences = context.getSharedPreferences("sample_theme", Context.MODE_PRIVATE)
    override val themeStream = MutableStateFlow(AppTheme.fromOrdinal(preference.getInt("app_theme", AppTheme.MODE_AUTO.ordinal)))
    override var theme: AppTheme by AppThemePreferenceDelegate("app_theme", AppTheme.MODE_AUTO)

    inner class AppThemePreferenceDelegate(
        private val name: String,
        private val default: AppTheme
    ) : ReadWriteProperty<Any?, AppTheme> {
        override fun getValue(thisRef: Any?, property: KProperty<*>): AppTheme {
            return AppTheme.fromOrdinal(preference.getInt(name, default.ordinal))
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: AppTheme) {
            preference.edit().putInt(name, value.ordinal).apply()
            themeStream.value = value
        }
    }
}