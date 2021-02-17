package com.kg.movie.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kg.movie.data.MovieRepository

class MovieViewModel @ViewModelInject constructor(private val movieRepository: MovieRepository):ViewModel() {


    val movies= movieRepository.getPopularMovies().cachedIn(viewModelScope)


}