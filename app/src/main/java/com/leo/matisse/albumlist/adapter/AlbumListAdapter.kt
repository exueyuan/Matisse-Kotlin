package com.leo.matisse.albumlist.adapter


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leo.matisse.albumlist.holder.AlbumItemHolder
import matisse.mymatisse.entity.Album

class AlbumListAdapter(val albumList: ArrayList<Album>,
                       val clickCallback:((Album)->Unit)? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AlbumItemHolder(AlbumItemHolder.getItemView(parent.context))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val album = albumList[position]
        if (holder is AlbumItemHolder) {
            holder.onBindViewHolder(album, clickCallback)
        }
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

}