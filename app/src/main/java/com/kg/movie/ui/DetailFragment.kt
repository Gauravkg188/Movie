package com.kg.movie.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.kg.movie.R
import com.kg.movie.databinding.DetailFragmentBinding


class DetailFragment:Fragment(R.layout.detail_fragment)
{

   private val navArgs by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = DetailFragmentBinding.bind(view)


        binding.apply {


            val movie=navArgs.Movie
            var url="https://image.tmdb.org/t/p/w500" + movie.poster_path


            Glide.with(this@DetailFragment)
                .load(url)
                .fitCenter()
                .error(R.drawable.placeholder)
                .listener(object :RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }


                })
                .into(posterImage)

            if(movie.poster_path==null)
            {
                noImage.isVisible=true
            }
            textOverView.text=movie.overview


            textCategory.text=if(movie.adult)"Adult" else "Universal"
            val rating=movie.vote_average.toString()+" Star"
            textRating.text=rating
            textRelease.text=movie.release_date






        }

    }









}
