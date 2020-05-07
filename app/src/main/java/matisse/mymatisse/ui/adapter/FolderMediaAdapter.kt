package matisse.mymatisse.ui.adapter

import android.content.Context
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.leo.matisse.R
import matisse.mymatisse.entity.Album
import com.matisse.internal.entity.SelectionSpec
import com.matisse.ui.adapter.RecyclerViewCursorAdapter
import matisse.mymatisse.widget.CheckRadioView

class FolderMediaAdapter(var context: Context, var mCurrentPosition: Int) :
    RecyclerViewCursorAdapter<FolderMediaAdapter.FolderViewHolder>(null) {

    var itemClickListener: OnItemClickListener? = null
    private var placeholder: Drawable?

    init {
        val ta = context.theme.obtainStyledAttributes(intArrayOf(R.attr.item_placeholder))
        placeholder = ta.getDrawable(0)
        ta.recycle()
    }

    override fun onBindViewHolder(holder: FolderViewHolder, cursor: Cursor, position: Int) {

        val album = Album.valueOf(cursor)

        holder.tvBucketName.text = String.format(
            context.getString(R.string.folder_count),
            album.getDisplayName(holder.tvBucketName.context), album.getCount()
        )

        setRbSelectChecked(holder.rbSelected, cursor.position == mCurrentPosition)

        // do not need to load animated Gif
        val mContext = holder.ivBucketCover.context
        SelectionSpec.getInstance().imageEngine?.loadThumbnail(
            mContext, mContext.resources.getDimensionPixelSize(R.dimen.media_grid_size),
            placeholder, holder.ivBucketCover, album.getCoverPath()
        )
    }

    override fun getItemViewType(position: Int, cursor: Cursor) = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FolderViewHolder(
        parent,
        LayoutInflater.from(parent.context).inflate(R.layout.matisse_item_album_folder, parent, false)
    )

    inner class FolderViewHolder(private val mParentView: ViewGroup, itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var tvBucketName: TextView = itemView.findViewById(R.id.tv_bucket_name)
        var ivBucketCover: ImageView = itemView.findViewById(R.id.iv_bucket_cover)
        var rbSelected: CheckRadioView = itemView.findViewById(R.id.rb_selected)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if (itemClickListener != null) {
                itemClickListener!!.onItemClick(v, layoutPosition)
            }

            mCurrentPosition = layoutPosition
            setRadioDisChecked(mParentView)
            setRbSelectChecked(rbSelected, true)
        }

        /**
         * 设置未所有Item为未选中
         *
         * @param parentView
         */
        private fun setRadioDisChecked(parentView: ViewGroup?) {
            if (parentView == null || parentView.childCount < 1) return

            for (i in 0 until parentView.childCount) {
                val itemView = parentView.getChildAt(i)
                val rbSelect: CheckRadioView = itemView.findViewById(R.id.rb_selected)
                setRbSelectChecked(rbSelect, false)
            }
        }
    }

    private fun setRbSelectChecked(rbSelect: CheckRadioView?, checked: Boolean) {
        if (rbSelect == null) return

        rbSelect.scaleX = if (checked) 1f else 0f
        rbSelect.scaleY = if (checked) 1f else 0f
        rbSelect.setChecked(checked)
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}