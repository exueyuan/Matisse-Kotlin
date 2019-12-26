package com.leo.matisse.mymatisse.adapter.holder

import android.content.Context
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leo.matisse.R
import com.leo.matisse.mymatisse.view.CheckNumView
import com.matisse.entity.Item

class AlbumMediaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    companion object {
        fun getItemView(context: Context): View {
            return View.inflate(context, R.layout.holder_my_album_media, null)
        }
    }

    fun onBindViewHolder(item: Item, clickItemCallback: ((Item) -> Unit)?, checkedCallback: ((Item, Boolean) -> Unit)?) {
        val iv_image = itemView.findViewById<ImageView>(R.id.iv_image)
        val cnv_check = itemView.findViewById<CheckNumView>(R.id.cnv_check)
        Glide.with(itemView.context)
                .asBitmap()
                .load(item.getContentUri())
                .into(iv_image)
        itemView.setOnClickListener {
            clickItemCallback?.invoke(item)
        }

        cnv_check.checkedCallback = { isChecked, num ->
            checkedCallback?.invoke(item, isChecked)
        }
    }
}