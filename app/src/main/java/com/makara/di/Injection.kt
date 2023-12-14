package com.makara.di

import android.content.Context
import com.makara.data.MakaraRepository
import com.makara.data.local.pref.MakaraPreference
import com.makara.data.local.pref.dataStore

object Injection {
    fun provideRepository(context: Context): MakaraRepository {
        val pref = MakaraPreference.getInstance(context.dataStore)
        return MakaraRepository.getInstance(pref)
    }
}