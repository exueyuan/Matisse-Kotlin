package com.leo.matisse.mymatisse.adapter.holder

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.leo.matisse.R
import com.matisse.entity.Item

class AlbumMediaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    companion object {
        fun getItemView(context: Context): View {
            return View.inflate(context, R.layout.holder_my_album_media, null)
        }
    }

    fun onBindViewHolder(item: Item) {
        val text = itemView.findViewById<TextView>(R.id.text)
        text.text = item.getContentUri().toString()
    }
}