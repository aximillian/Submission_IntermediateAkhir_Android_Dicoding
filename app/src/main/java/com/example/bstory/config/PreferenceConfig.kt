package com.example.bstory.config

import android.content.Context
import android.content.SharedPreferences
import com.example.bstory.domain.auth.LoginResult

class PreferenceConfig(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(AppConfig.PREFS_NAME, Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    val getToken = prefs.getString(AppConfig.KEY_TOKEN, "")
    val getName = prefs.getString(AppConfig.KEY_NAME, "")

    fun setLoginPrefs(loginResult: LoginResult) {
        editor.putString(AppConfig.KEY_NAME, loginResult.name)
        editor.putString(AppConfig.KEY_TOKEN, loginResult.token)
        editor.apply()
    }

    fun clearAllPreferences() {
        editor.remove(AppConfig.KEY_TOKEN)
        editor.remove(AppConfig.KEY_NAME)
        editor.apply()
    }
}