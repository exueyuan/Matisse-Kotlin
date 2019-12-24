package com.leo.matisse.mymatisse

import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.leo.matisse.R
import com.leo.matisse.mymatisse.fragment.MyMediaSelectionFragment
import com.matisse.entity.Album
import com.matisse.model.AlbumCallbacks
import com.matisse.model.SelectedItemCollection
import com.matisse.ui.activity.matisse.AlbumFolderSheetHelper
import com.matisse.ui.adapter.FolderItemMediaAdapter
import com.matisse.ui.view.FolderBottomSheet
import com.matisse.ui.view.MediaSelectionFragment
import com.matisse.utils.UIUtils
import kotlinx.android.synthetic.main.activity_my_matisse.*

class MyMatisseActivity : AppCompatActivity() {

    var instanceState: Bundle? = null
    private lateinit var albumLoadHelper: MyAlbumLoadHelper
    private var allAlbum: Album? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_matisse)
        //加载相册
        albumLoadHelper = MyAlbumLoadHelper(this, object : AlbumCallbacks {
            override fun onAlbumStart() {
                // do nothing
            }

            override fun onAlbumLoad(cursor: Cursor) {
                //在主线程中调用
                Handler(Looper.getMainLooper()).post {
                    //获取到所有的相册
                    //回到首位
                    if (cursor.moveToFirst()) {
                        allAlbum = Album.valueOf(cursor).apply {
                            onAlbumSelected(this)
                        }
                        tv_album.text = allAlbum?.displayName
                    }
                }
            }

            override fun onAlbumReset() {
            }
        })

        tv_album.setOnClickListener {

        }
    }


    //处理加载后的效果
    private fun onAlbumSelected(album: Album) {
        if (album.isAll() && album.isEmpty()) {
            UIUtils.setViewVisible(true, empty_view)
            UIUtils.setViewVisible(false, fl_fragment)
        } else {
            UIUtils.setViewVisible(false, empty_view)
            UIUtils.setViewVisible(true, fl_fragment)
            val fragment = MyMediaSelectionFragment.newInstance(album)
            supportFragmentManager.beginTransaction()
                    .replace(fl_fragment.id, fragment, MyMediaSelectionFragment::class.java.simpleName)
                    .commitAllowingStateLoss()
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        albumLoadHelper.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        albumLoadHelper.onDestroy()
    }
}