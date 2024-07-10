package com.riki.apptheme

/**
 * Created by Sonpx on 07/10/2024
 * Copyright(©)Cloudxanh. All rights reserved.
 */

import android.app.Application
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate

object ThemeHelper {
    private const val LIGHT_MODE = "light"
    private const val DARK_MODE = "dark"
    private const val DEFAULT_MODE = "system"

    fun applyTheme(themePreference: String) {
        when (themePreference) {
            LIGHT_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DARK_MODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            DEFAULT_MODE -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }
            }
        }
    }

    fun init(application: Application) {
        val prefs = application.getSharedPreferences("app_prefs", Application.MODE_PRIVATE)
        val savedTheme = prefs.getString("theme", DEFAULT_MODE) ?: DEFAULT_MODE
        applyTheme(savedTheme)
    }
}