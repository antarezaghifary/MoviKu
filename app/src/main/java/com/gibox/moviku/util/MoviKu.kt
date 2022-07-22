package com.gibox.moviku.util

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.gibox.moviku.data.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MoviKu : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.e(" run base application")
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        startKoin {
            androidLogger()
            androidContext(this@MoviKu)
            modules(
                networkModule,
            )
        }
    }
}