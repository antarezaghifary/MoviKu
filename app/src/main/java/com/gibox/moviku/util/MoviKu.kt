package com.gibox.moviku.util

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.gibox.moviku.data.network.networkModule
import com.gibox.moviku.data.network.repositoryModule
import com.gibox.moviku.ui.activity.detail.detailModule
import com.gibox.moviku.ui.activity.detail.detailViewModel
import com.gibox.moviku.ui.activity.review.reviewModule
import com.gibox.moviku.ui.activity.review.reviewViewModel
import com.gibox.moviku.ui.fragment.genre.genreModule
import com.gibox.moviku.ui.fragment.genre.genreViewModel
import com.gibox.moviku.ui.fragment.home.homeModule
import com.gibox.moviku.ui.fragment.home.homeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MoviKu : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.e(" run base application")
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MoviKu)
            modules(
                networkModule,
                repositoryModule,
                homeViewModel,
                homeModule,
                genreViewModel,
                genreModule,
                detailViewModel,
                detailModule,
                reviewViewModel,
                reviewModule
            )
        }
    }
}