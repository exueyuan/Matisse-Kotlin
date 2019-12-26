package com.leo.matisse.mymatisse.adapter.holder

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leo.matisse.R
import com.matisse.entity.Item

class AlbumMediaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    companion object {
        fun getItemView(context: Context): View {
            return View.inflate(context, R.layout.holder_my_album_media, null)
        }
    }

    fun onBindViewHolder(item: Item) {
        val iv_image = itemView.findViewById<ImageView>(R.id.iv_image)
        Glide.with(itemView.context)
                .asBitmap()
                .load(item.getContentUri())
                .into(iv_image)
    }
}