package com.leo.matisse.albumlist.holder

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.leo.matisse.R
import com.matisse.entity.Album

class AlbumItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    companion object {
        fun getItemView(context: Context): View {
            return View.inflate(context, R.layout.holder_album_item, null)
        }
    }

    fun onBindViewHolder(album: Album) {
        val tv_title = itemView.findViewById<TextView>(R.id.tv_title)
        val tv_count = itemView.findViewById<TextView>(R.id.tv_count)
        val iv_image = itemView.findViewById<ImageView>(R.id.iv_image)

        tv_title.text = album.displayName
        tv_count.text = album.getCount().toString()
        Glide.with(itemView.context)
                .asBitmap()
                .load(album.getCoverPath())
                .into(iv_image)
    }
}