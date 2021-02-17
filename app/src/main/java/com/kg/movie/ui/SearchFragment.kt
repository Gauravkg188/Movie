package com.kg.movie.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kg.movie.R
import com.kg.movie.adapter.Adapter
import com.kg.movie.databinding.SearchFragmentBinding

import com.kg.movie.model.Movie
import com.kg.movie.viewModel.SearchedMovieViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment: Fragment(R.layout.search_fragment),Adapter.OnItemClickListener
{

    private val viewModel by viewModels<SearchedMovieViewModel>()
    private var bindings: SearchFragmentBinding?=null
    private val binding get() = bindings!!


    private lateinit var adapter: Adapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindings=SearchFragmentBinding.bind(view)


        adapter= Adapter(this)

        binding.apply {

            searchRecyclerView.setHasFixedSize(true)
            searchRecyclerView.layoutManager=
                GridLayoutManager(context,2,RecyclerView.VERTICAL,false)
            searchRecyclerView.adapter=adapter

            searchQuery.showSoftInputOnFocus=true

            buttonRetry.setOnClickListener{adapter.retry()}

            searchImageButton.setOnClickListener {
                if(searchQuery.text.isNotEmpty())
            {
                binding.searchRecyclerView.scrollToPosition(0)
                viewModel.searchMovies(searchQuery.text.toString().trim())
                searchQuery.clearFocus()

            }

            }



        }


        viewModel.movies.observe(viewLifecycleOwner)
        {
            adapter.submitData(viewLifecycleOwner.lifecycle,it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                searchRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error





                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    searchRecyclerView.isVisible = false
                    noResultText.isVisible=true

                }
                else
                {
                    noResultText.isVisible=false
                }
            }
        }


    }

    override fun onItemClick(movie: Movie) {

            var action=SearchFragmentDirections.actionSearchFragmentToDetailFragment(movie)
            findNavController().navigate(action)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindings=null
    }

}