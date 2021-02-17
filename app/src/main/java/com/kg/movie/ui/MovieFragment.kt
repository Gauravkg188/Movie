package com.kg.movie.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.kg.movie.R
import com.kg.movie.adapter.Adapter
import com.kg.movie.databinding.MovieFragmentBinding
import com.kg.movie.model.Movie

import com.kg.movie.viewModel.MovieViewModel
import com.kg.movie.viewModel.SearchedMovieViewModel

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment:Fragment(R.layout.movie_fragment),Adapter.OnItemClickListener
{

    private val viewModel by viewModels<MovieViewModel>()

    private lateinit var adapter:Adapter
    private var movieFragmentBinding:MovieFragmentBinding?=null
    private val binding get() = movieFragmentBinding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieFragmentBinding=MovieFragmentBinding.bind(view)

        adapter=Adapter(this)

        binding.apply {

            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            recyclerView.adapter=adapter

            buttonRetry.setOnClickListener { adapter.retry() }
        }


        viewModel.movies.observe(viewLifecycleOwner)
        {
            adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error




                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false

                }
            }
        }


        setHasOptionsMenu(true)

    }



    override fun onDestroyView() {
        super.onDestroyView()
        movieFragmentBinding=null
    }

    override fun onItemClick(movie: Movie) {
        var action=MovieFragmentDirections.actionMovieFragmentToDetailFragment(movie)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.main_movie_fragment,menu)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

       when(item.itemId)
       {
           R.id.action_search->{var action =MovieFragmentDirections.actionMovieFragmentToSearchFragment()
           findNavController().navigate(action)
               return true
           }
       }

        return super.onOptionsItemSelected(item)
    }




}