package com.makara.data.local.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
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
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
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