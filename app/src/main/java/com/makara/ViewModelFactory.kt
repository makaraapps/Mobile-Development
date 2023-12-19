package com.makara

import UploadImageViewModel
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makara.data.MakaraRepository
import com.makara.data.local.pref.MakaraPreference
import com.makara.di.Injection
import com.makara.ui.auth.AuthViewModel

class ViewModelFactory(private val repository: MakaraRepository, private val makaraPreference: MakaraPreference) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(repository, makaraPreference) as T
            }
            modelClass.isAssignableFrom(UploadImageViewModel::class.java) -> {
                UploadImageViewModel(repository, makaraPreference) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context), Injection.provideMakaraPreference(context))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}
