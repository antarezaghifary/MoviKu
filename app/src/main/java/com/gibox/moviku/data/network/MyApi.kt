package com.gibox.moviku.data.network

import com.gibox.moviku.data.model.movie.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MyApi {
    @GET("genre/movie/list")
    suspend fun getMovie(
        @Query("api_key") api_key: String,
        @Header("language") language: String
    ): MovieResponse
}