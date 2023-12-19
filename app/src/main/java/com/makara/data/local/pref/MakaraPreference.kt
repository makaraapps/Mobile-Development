package com.makara.data.local.pref

import android.util.Log
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class MakaraPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(session: MakaraModel) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = session.email
            preferences[TOKEN_KEY] = session.token
            preferences[IS_LOGIN_KEY] = session.isLogin
        }
    }

    fun getSession(): Flow<MakaraModel> {
        return dataStore.data.map { preferences ->
            MakaraModel(
                preferences[EMAIL_KEY] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }
    }

    suspend fun login() {
        dataStore.edit { preference ->
            preference[IS_LOGIN_KEY] = true
        }
        Log.i("MakaraPreference", "Succesfully login")
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun saveAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
            Log.i("MakaraPreference", "Succesfully save token, token: ${preferences[TOKEN_KEY]}")
        }
    }

    suspend fun getAuthToken(): String? {
        Log.i("MakaraPreference", "getAuthToken")
        val preferences = dataStore.data.first() // Read the current preferences
        val token = preferences[TOKEN_KEY] ?: return null
        Log.i("MakaraPreference", "getAuthToken, token: $token")
        return token
    }


    companion object {
        @Volatile
        private var INSTANCE: MakaraPreference? = null

        private val EMAIL_KEY = stringPreferencesKey("email")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): MakaraPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = MakaraPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}