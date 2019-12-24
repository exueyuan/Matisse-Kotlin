package com.leo.matisse.albumlist.adapter


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leo.matisse.albumlist.holder.AlbumItemHolder
import com.leo.matisse.mymatisse.adapter.holder.AlbumMediaHolder
import com.matisse.entity.Album
import com.matisse.entity.Item

class AlbumListAdapter(val albumList: ArrayList<Album>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AlbumItemHolder(AlbumItemHolder.getItemView(parent.context))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val album = albumList[position]
        if (holder is AlbumItemHolder) {
            holder.onBindViewHolder(album)
        }
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

}