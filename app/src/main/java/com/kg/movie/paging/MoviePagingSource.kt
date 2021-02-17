package com.kg.movie.paging

import androidx.paging.PagingSource
import com.kg.movie.data.MovieApi
import com.kg.movie.model.Movie
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1
class MoviePagingSource( private  val movieApi:MovieApi) :PagingSource<Int,Movie>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {

            val response=movieApi.getMovies(MovieApi.API_KEY,position)
            val movies=response.results


            LoadResult.Page(
            data = movies,
            prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
            nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }


}