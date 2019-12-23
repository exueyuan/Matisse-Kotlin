package com.leo.matisse.mymatisse

import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.leo.matisse.R
import com.matisse.entity.Album
import com.matisse.model.AlbumCallbacks
import com.matisse.model.SelectedItemCollection
import com.matisse.ui.activity.matisse.AlbumFolderSheetHelper
import com.matisse.ui.adapter.FolderItemMediaAdapter
import com.matisse.ui.view.FolderBottomSheet
import com.matisse.ui.view.MediaSelectionFragment
import com.matisse.utils.UIUtils
import kotlinx.android.synthetic.main.activity_my_matisse.*

class MyMatisseActivity : AppCompatActivity(),
        MediaSelectionFragment.SelectionProvider {

    override fun provideSelectedItemCollection(): SelectedItemCollection {
        return selectedCollection
    }

    var instanceState: Bundle? = null
    private lateinit var selectedCollection: SelectedItemCollection
    private lateinit var albumFolderSheetHelper: AlbumFolderSheetHelper
    private lateinit var albumLoadHelper: MyAlbumLoadHelper
    private var allAlbum: Album? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_matisse)
        selectedCollection = SelectedItemCollection(this).apply { onCreate(instanceState) }
        //加载相册
        albumLoadHelper = MyAlbumLoadHelper(this, albumCallback)

        albumFolderSheetHelper = AlbumFolderSheetHelper(this, albumSheetCallback)
    }

    //加载相册返回的callback
    private var albumCallback = object : AlbumCallbacks {
        override fun onAlbumStart() {
            // do nothing
        }

        override fun onAlbumLoad(cursor: Cursor) {
            albumFolderSheetHelper.setAlbumFolderCursor(cursor)

            Handler(Looper.getMainLooper()).post {
                if (cursor.moveToFirst()) {
                    allAlbum = Album.valueOf(cursor).apply {
                        onAlbumSelected(this)
                    }
                }
            }
        }

        override fun onAlbumReset() {
            albumFolderSheetHelper.clearFolderSheetDialog()
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
            val fragment = MediaSelectionFragment.newInstance(album)
            supportFragmentManager.beginTransaction()
                    .replace(fl_fragment.id, fragment, MediaSelectionFragment::class.java.simpleName)
                    .commitAllowingStateLoss()
        }
    }

    private var albumSheetCallback = object : FolderBottomSheet.BottomSheetCallback {
        override fun initData(adapter: FolderItemMediaAdapter) {
            adapter.setListData(albumFolderSheetHelper.readAlbumFromCursor())
        }

        override fun onItemClick(album: Album, position: Int) {
            /*if (!albumFolderSheetHelper.setLastFolderCheckedPosition(position)) return
            albumLoadHelper.setStateCurrentSelection(position)

            button_apply.text = album.getDisplayName(activity)
            onAlbumSelected(album)*/
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        selectedCollection.onSaveInstanceState(outState)
        albumLoadHelper.onSaveInstanceState(outState)
    }
}