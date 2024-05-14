package com.sdevprem.roadcastassignment.presentation.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.sdevprem.roadcastassignment.data.entity.MovieMeta
import com.sdevprem.roadcastassignment.databinding.RvItemMovieBinding
import java.text.SimpleDateFormat
import java.util.Locale

class MoviesAdapter : PagingDataAdapter<MovieMeta, MoviesAdapter.MovieItemViewHolder>(
    diffCallback = DIFFER
) {
    inner class MovieItemViewHolder(
        private val binding: RvItemMovieBinding
    ) : ViewHolder(binding.root) {

        fun bind(movieMeta: MovieMeta) = binding.run {
            tvMovieTitle.text = movieMeta.title
            tvMovieYear.text = SimpleDateFormat("yyyy", Locale.getDefault())
                .format(movieMeta.releaseDate)
            Glide.with(imgMovieImg.context)
                .load(movieMeta.posterUrl)
                .into(imgMovieImg)
            return@run
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        return MovieItemViewHolder(
            RvItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(movieMeta = getItem(position) ?: return)
    }

    companion object {
        private val DIFFER = object : DiffUtil.ItemCallback<MovieMeta>() {
            override fun areItemsTheSame(oldItem: MovieMeta, newItem: MovieMeta): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: MovieMeta, newItem: MovieMeta): Boolean {
                return oldItem == newItem
            }
        }
    }

}