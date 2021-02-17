package com.kg.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Switch
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kg.movie.R
import com.kg.movie.databinding.MovieItemBinding
import com.kg.movie.model.Movie

class Adapter(private val listener:OnItemClickListener): PagingDataAdapter<Movie,Adapter.MovieViewHolder>(COMPARATOR)
{

   inner class MovieViewHolder(private val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root)
    {
        init {
            binding.root.setOnClickListener{
                val position=bindingAdapterPosition
                if (position!=RecyclerView.NO_POSITION)
                {
                    val movie=getItem(position)
                    if(movie!=null)
                    listener.onItemClick(movie)
                }
            }
        }
        fun bind(movie:Movie)
        {
            binding.apply {
                var url="https://image.tmdb.org/t/p/w500" + movie.poster_path
                Glide.with(itemView)
                    .load(url)
                    .fitCenter()
                    .error(R.drawable.ic_baseline_error_24)
                    .into(imagePoster)

                textTitle.text=movie.title
                textLanguage.text=getLanguage(movie.original_language)
                val rating=movie.vote_average
                val textRating=rating.toString()+" Star"
                this.textRating.text= textRating

            }
        }

        private fun getLanguage(language:String):String{

            return when(language){

                "en"-> "English"
                "hi"-> "hindi"
                "de"-> "German"
                "no"-> "Norwegian"
                "nl"-> "Dutch"
                "zh"->"Chinese"
                "ru"->"Russian"
                "fr"->"French"
                else -> language

            }


        }

    }

    companion object{

        private val COMPARATOR=object:DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id==newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie)= oldItem==newItem



        }


    }


    interface OnItemClickListener {
        fun onItemClick(movie: Movie)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie=getItem(position)

        if(movie!=null)
        {
            holder.bind(movie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding=MovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(binding)
    }


}