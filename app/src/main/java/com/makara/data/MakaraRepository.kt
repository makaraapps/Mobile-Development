package com.makara.data

import com.makara.data.local.pref.MakaraModel
import com.makara.data.local.pref.MakaraPreference
import kotlinx.coroutines.flow.Flow

class MakaraRepository private constructor(
    private val userPreference: MakaraPreference
) {

    suspend fun saveSession(user: MakaraModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<MakaraModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: MakaraRepository? = null
        fun getInstance(
            userPreference: MakaraPreference
        ): MakaraRepository =
            instance ?: synchronized(this) {
                instance ?: MakaraRepository(userPreference)
            }.also { instance = it }
    }
}