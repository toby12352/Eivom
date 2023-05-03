package com.example.eivom.ui

import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.eivom.R
import com.example.eivom.data.MovieList
import org.w3c.dom.Text

class MovieInfoAdapter(
    private val onClick: (MovieList) -> Unit
): RecyclerView.Adapter<MovieInfoAdapter.MovieInfoViewHolder>(){
    var movieInfoList: List<MovieList> = listOf()

    fun updateInfo(info: List<MovieList>?) {
        movieInfoList = info ?: listOf()
        notifyDataSetChanged()
    }

    override fun getItemCount() = movieInfoList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieInfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_info_list_item, parent, false)

        return MovieInfoViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: MovieInfoViewHolder, position: Int) {
        holder.bind(movieInfoList[position])
    }

    class MovieInfoViewHolder(itemView: View, val onClick: (MovieList) -> Unit): ViewHolder(itemView){

        private val posterTV: ImageView = itemView.findViewById(R.id.movie_poster)

        private lateinit var currentMovieInfo: MovieList

        /*
         * Set up a click listener on this individual ViewHolder.  Call the provided onClick
         * function, passing the forecast item represented by this ViewHolder as an argument.
         */
        init {
            itemView.setOnClickListener {
                currentMovieInfo.let(onClick)
            }
        }

        fun bind(movieList: MovieList){
            val ctx = itemView.context

            currentMovieInfo = movieList

            Glide.with(ctx)
                .load(movieList.poster_path)
                .into(posterTV)
        }
    }
}