package com.gibox.moviku.data.network

import com.gibox.moviku.BuildConfig
import com.gibox.moviku.data.model.upcoming.UpcomingResponse
import org.koin.dsl.module

val repositoryModule = module {
    factory {
        MovieRepository(get())
    }
}

class MovieRepository(
    private val api: MyApi
) {
    suspend fun getDataUpcoming(
    ): UpcomingResponse {
        return api.getUpcoming(
            BuildConfig.API_KEY,
            "en-US"
        )
    }
}