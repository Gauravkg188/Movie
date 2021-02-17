package com.kg.movie.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.kg.movie.paging.MoviePagingSource
import com.kg.movie.paging.SearchedMoviePagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor (private val movieApi: MovieApi) {


    fun getPopularMovies()= Pager(

        config = PagingConfig( pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false),
        pagingSourceFactory ={MoviePagingSource(movieApi)}

    ).liveData

   fun getSearchedMovies(query:String)=Pager(

       config = PagingConfig(pageSize = 20,
               maxSize = 100,
                enablePlaceholders = false
           ),pagingSourceFactory = {SearchedMoviePagingSource(movieApi,query)}



   ).liveData




}