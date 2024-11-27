package com.example.instagram.mode

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ThemeSettingPreference @Inject constructor(
    @ApplicationContext context: Context
): ThemeSetting{

    override val themeStream: MutableStateFlow<AppTheme>
    override var theme: AppTheme by AppThemePreferenceDelegate("app_theme", AppTheme.MODE_AUTO)

    init {
        themeStream = MutableStateFlow(theme)
    }

    private val preference: SharedPreferences = context.getSharedPreferences("sample_theme", Context.MODE_PRIVATE)

    inner class AppThemePreferenceDelegate(
        private val name: String,
        private val default: AppTheme
    ): ReadWriteProperty<Any?, AppTheme>{
        override fun getValue(thisRef: Any?, property: KProperty<*>): AppTheme {
            return AppTheme.fromOrdinal(preference.getInt(name, default.ordinal))
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: AppTheme) {
            themeStream.value = value
            preference.edit{
                putInt(name, value.ordinal)
            }
        }

    }
}

