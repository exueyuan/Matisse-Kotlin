package com.leo.matisse.mymatisse.fragment

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leo.matisse.mymatisse.adapter.MyAlbumMediaAdapter
import com.matisse.R
import com.matisse.entity.Album
import com.matisse.entity.ConstValue
import com.matisse.entity.Item
import com.matisse.internal.entity.SelectionSpec
import com.matisse.model.AlbumCallbacks
import com.matisse.model.AlbumMediaCollection
import com.matisse.widget.MediaGridInset
import kotlinx.android.synthetic.main.fragment_my_media_selection.*

class MyMediaSelectionFragment : Fragment() {
    //加载图片器
    private val albumMediaCollection = AlbumMediaCollection()

    private val albumMediaList = arrayListOf<Item>()
    private val adapter: MyAlbumMediaAdapter = MyAlbumMediaAdapter(albumMediaList)
    private lateinit var album: Album

    companion object {
        fun newInstance(album: Album): MyMediaSelectionFragment {
            val fragment = MyMediaSelectionFragment()
            val args = Bundle()
            args.putParcelable(ConstValue.EXTRA_ALBUM, album)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_media_selection, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        album = arguments?.getParcelable(ConstValue.EXTRA_ALBUM)!!

        val selectionSpec = SelectionSpec.getInstance()
        //设置行数
        val spanCount = selectionSpec.spanCount
        recyclerview.layoutManager = GridLayoutManager(context!!, spanCount)
        val spacing = resources.getDimensionPixelSize(R.dimen.media_grid_spacing)
        recyclerview.addItemDecoration(MediaGridInset(spanCount, spacing, false))
        recyclerview.adapter = adapter

        //设置获取图片
        albumMediaCollection.onCreate(activity!!, object : AlbumCallbacks {
            override fun onAlbumStart() {

            }

            override fun onAlbumLoad(cursor: Cursor) {
                //然后cursor转化成list
                for (i in 0 until if (isDataValid(cursor)) cursor.count else 0) {
                    cursor.moveToPosition(i)
                    val item = Item.valueOf(cursor)
                    albumMediaList.add(item)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onAlbumReset() {
                albumMediaList.clear()
                adapter.notifyDataSetChanged()
            }

        })
        //右边是指是否开启拍照，默认不开启
        albumMediaCollection.load(album, selectionSpec.capture)
    }


    private fun isDataValid(cursor: Cursor?) = cursor != null && !cursor.isClosed
}