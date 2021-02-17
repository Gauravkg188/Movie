package com.kg.movie.data

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    companion object{

        const val BASE_URL="https://api.themoviedb.org/3/"
        const val API_KEY="API_KEY"


    }

    @GET("movie/popular")
    suspend fun getMovies(

        @Query("api_key") api_key:String,
        @Query("page") page:Int


    ):ApiResponse


    @GET("search/movie?")
    suspend fun searchMovies(

        @Query("api_key")api_key: String,
        @Query("query") query:String,
        @Query("page") page: Int


    ):ApiResponse
}