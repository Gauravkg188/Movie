package com.kg.movie.viewModel

import android.app.DownloadManager
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kg.movie.data.MovieRepository

class SearchedMovieViewModel @ViewModelInject constructor(private val repository:MovieRepository,
                                                    @Assisted state: SavedStateHandle
):ViewModel()
{

    private val currentQuery=state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY )
    val movies=currentQuery.switchMap { query->
        repository.getSearchedMovies(query).cachedIn(viewModelScope)
    }

    fun searchMovies(query:String)
    {
        currentQuery.value=query
    }

    companion object{
        private const val CURRENT_QUERY="current_query"
        private const val DEFAULT_QUERY=""
    }

}