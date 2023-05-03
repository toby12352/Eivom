package com.example.eivom.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.eivom.R
import com.example.eivom.data.TvShowInfo
import com.example.eivom.data.TvShowList

class TvShowInfoAdapter(
    private val onClick: (TvShowList) -> Unit
): RecyclerView.Adapter<TvShowInfoAdapter.TvShowInfoViewHolder>() {
    var tvShowInfoList: List<TvShowList> = listOf()

    fun updateInfo(info: List<TvShowList>){
        tvShowInfoList = info
        Log.d("TvShowInfoAdapter", "Data: $tvShowInfoList")
        notifyDataSetChanged()
    }

    override fun getItemCount() = tvShowInfoList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvShowInfoViewHolder{
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_info_list_item, parent, false)

        return TvShowInfoViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: TvShowInfoViewHolder, position: Int){
        holder.bind(tvShowInfoList[position])
    }

    class TvShowInfoViewHolder(
        itemView: View,
        val onClick: (TvShowList) -> Unit
    ): ViewHolder(itemView){
        private val posterTV: ImageView = itemView.findViewById(R.id.movie_poster)

        private lateinit var currentTvShowInfo : TvShowList

        init{
            itemView.setOnClickListener{
                currentTvShowInfo.let(onClick)
            }
        }

        fun bind(tvShowList: TvShowList){
            val ctx = itemView.context

            currentTvShowInfo = tvShowList

            Glide.with(ctx)
                .load(tvShowList.poster_path)
                .into(posterTV)
        }
    }
}