package com.leo.matisse.mymatisse.holder

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leo.matisse.R
import com.leo.matisse.mymatisse.utils.CheckedManager
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
        //设置数值
        Log.i("Hahah", ""+CheckedManager.getNum(item))
        cnv_check.checkedNum = CheckedManager.getNum(item)


        itemView.setOnClickListener {
            clickItemCallback?.invoke(item)
        }

        cnv_check.checkedCallback = { isChecked ->
            checkedCallback?.invoke(item, isChecked)
        }
    }
}