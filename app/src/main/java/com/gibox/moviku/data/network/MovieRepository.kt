package com.gibox.moviku.data.network

import com.gibox.moviku.BuildConfig
import com.gibox.moviku.data.model.movie.MovieResponse
import com.gibox.moviku.data.model.popular.PopularResponse
import com.gibox.moviku.data.model.top_rated.TopRatedResponse
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

    suspend fun getDataPopuar(
    ): PopularResponse {
        return api.getPopular(
            BuildConfig.API_KEY,
            "en-US"
        )
    }

    suspend fun getDataTop(
    ): TopRatedResponse {
        return api.getTop(
            BuildConfig.API_KEY,
            "en-US"
        )
    }

    suspend fun getMovie(
        page: Int,
        withGenre: Int
    ): MovieResponse {
        return api.getMovie(
            page,
            BuildConfig.API_KEY,
            withGenre,
            "en-US"
        )
    }
}