package com.example.eivom.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.eivom.R
import com.example.eivom.data.MovieList
import com.example.eivom.data.VideoInfo
import com.example.eivom.data.VideoList
import kotlin.reflect.KFunction1

class VideoInfoAdapter(private val onClick: KFunction1<VideoList, Unit>): RecyclerView.Adapter<VideoInfoAdapter.VideoInfoViewHolder>() {
    var videoInfoList: List<VideoList> = listOf()

    fun updateInfo(info: VideoInfo?) {
        videoInfoList = info?.results ?: listOf()
        Log.d("VideoInfoAdapter", "Data: $videoInfoList")
        notifyDataSetChanged()
    }

    override fun getItemCount() = videoInfoList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoInfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_info_list_item, parent, false)

        return VideoInfoViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: VideoInfoViewHolder, position: Int) {
        holder.bind(videoInfoList[position])
    }

    class VideoInfoViewHolder(itemView: View, val onClick: (VideoList) -> Unit): ViewHolder(itemView) {
        private val key: TextView = itemView.findViewById(R.id.overview)

        private lateinit var currentVideoInfo: VideoList

        init {
            itemView.setOnClickListener {
                currentVideoInfo.let(onClick)
            }
        }

        fun bind(videoList: VideoList) {
            val ctx = itemView.context

            currentVideoInfo = videoList

            key.text = videoList.key
        }
    }
}