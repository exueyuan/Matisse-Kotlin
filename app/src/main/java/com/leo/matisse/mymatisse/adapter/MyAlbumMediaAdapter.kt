package com.leo.matisse.mymatisse.adapter


import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leo.matisse.mymatisse.adapter.holder.AlbumMediaHolder
import com.matisse.entity.Item

class MyAlbumMediaAdapter(val itemList: ArrayList<Item>,
                          val clickItemCallback: ((Item) -> Unit)? = null,
                          val checkedCallback:((Item, Boolean)->Unit)? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_CAPTURE = 0X01
        const val VIEW_TYPE_MEDIA = 0X02
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_CAPTURE -> {
                AlbumMediaHolder(AlbumMediaHolder.getItemView(parent.context))
            }
            else -> {
                AlbumMediaHolder(AlbumMediaHolder.getItemView(parent.context))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]
        if (holder is AlbumMediaHolder) {
            holder.onBindViewHolder(item, clickItemCallback, checkedCallback)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = itemList[position]
        return if (item.isCapture()) VIEW_TYPE_CAPTURE else VIEW_TYPE_MEDIA
    }

    override fun getItemId(position: Int): Long {
        val item = itemList[position]
        return item.id
    }

}