package com.example.pawpal.app.app_preference

import android.content.Context
import androidx.core.content.edit

class AppPreference(context: Context) {

    private val appPreference =
        context.getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        appPreference.edit { putString(TOKEN_KEY, token) }
    }

    fun getToken(): String? = appPreference.getString(TOKEN_KEY, null)

    fun clear() {
        appPreference.edit {
            remove(TOKEN_KEY)
            remove(USER_ID_KEY)
        }
    }

    fun saveUserId(id: String) {
        appPreference.edit { putString(USER_ID_KEY, id) }
    }

    fun getUserId(): String? = appPreference.getString(USER_ID_KEY, null)

    companion object {
        private const val APP_PREFERENCES_NAME = "app.preferences"
        private const val TOKEN_KEY = "token_key"
        private const val USER_ID_KEY = "user_id"
    }
}