package com.riki.apptheme

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val currentTheme = prefs.getString("theme", "system") ?: "system"
        ThemeHelper.applyTheme(currentTheme)

        findViewById<Button>(R.id.btnSelectTheme).setOnClickListener {
            showThemeSelectionDialog()
        }
    }

    private fun showThemeSelectionDialog() {
        val currentTheme = prefs.getString("theme", "system") ?: "system"
        ThemeSelectionDialog.show(this, currentTheme) { selectedTheme ->
            prefs.edit().putString("theme", selectedTheme).apply()
            ThemeHelper.applyTheme(selectedTheme)
            recreate() // Tái tạo activity để áp dụng theme mới
        }
    }
}

object ThemeSelectionDialog {
    fun show(context: Context, currentTheme: String, onThemeSelected: (String) -> Unit) {
        val themes = arrayOf("Light", "Dark", "System default")
        val themeValues = arrayOf("light", "dark", "system")
        val currentIndex = themeValues.indexOf(currentTheme)

        AlertDialog.Builder(context)
            .setTitle("Select Theme")
            .setSingleChoiceItems(themes, currentIndex) { dialog, which ->
                val selectedTheme = themeValues[which]
                onThemeSelected(selectedTheme)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}

