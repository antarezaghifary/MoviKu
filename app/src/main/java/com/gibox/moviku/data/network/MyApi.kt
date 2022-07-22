package com.gibox.moviku.data.network

import com.gibox.moviku.data.model.genre.GenreResponse
import com.gibox.moviku.data.model.movie.MovieResponse
import com.gibox.moviku.data.model.popular.PopularResponse
import com.gibox.moviku.data.model.review.ReviewResponse
import com.gibox.moviku.data.model.top_rated.TopRatedResponse
import com.gibox.moviku.data.model.trailer.TrailerResponse
import com.gibox.moviku.data.model.upcoming.UpcomingResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MyApi {
    @GET("genre/movie/list")
    suspend fun getGenre(
        @Query("api_key") api_key: String,
        @Header("language") language: String
    ): GenreResponse

    @GET("discover/movie")
    suspend fun getMovie(
        @Query("page") page: Int,
        @Query("api_key") api_key: String,
        @Query("with_genres") with_genres: Int,
        @Header("language") language: String
    ): MovieResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getReview(
        @Path("movie_id") movie_id: Int,
        @Query("page") page: Int,
        @Query("api_key") api_key: String,
        @Header("language") language: String
    ): ReviewResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getTrailer(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String
    ): TrailerResponse

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): UpcomingResponse

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): PopularResponse

    @GET("movie/top_rated")
    suspend fun getTop(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): TopRatedResponse
}