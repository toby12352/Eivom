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
import com.example.eivom.data.PersonInfo
import com.example.eivom.data.PersonList

class PersonInfoAdapter(
    private val onClick: (PersonList) -> Unit
): RecyclerView.Adapter<PersonInfoAdapter.PersonInfoViewHolder>() {
    var personInfoList: List<PersonList> = listOf()

    fun updateInfo(info: PersonInfo?){
        personInfoList = info?.results ?: listOf()
        notifyDataSetChanged()
        Log.d("PersonInfoAdapter", "Data: $personInfoList")
    }

    override fun getItemCount() = personInfoList.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PersonInfoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_info_list_item, parent, false)

        return PersonInfoViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: PersonInfoViewHolder, position: Int){
        holder.bind(personInfoList[position])
    }

    class PersonInfoViewHolder(
        itemView: View,
        val onClick: (PersonList) -> Unit
    ): ViewHolder(itemView){
        private val profileTV: ImageView = itemView.findViewById(R.id.movie_poster)

        private lateinit var currentPersonInfo: PersonList

        init{
            itemView.setOnClickListener{
                currentPersonInfo.let(onClick)
            }
        }

        fun bind(personList: PersonList){
            val ctx = itemView.context

            currentPersonInfo = personList

            Glide.with(ctx)
                .load(personList.profile_path)
                .into(profileTV)
        }
    }
}