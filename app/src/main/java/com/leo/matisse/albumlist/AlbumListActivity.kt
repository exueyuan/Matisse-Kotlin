package com.leo.matisse.albumlist

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.leo.matisse.BaseMatisseActivity
import com.leo.matisse.R
import com.leo.matisse.albumlist.adapter.AlbumListAdapter
import com.leo.matisse.mymatisse.MyAlbumLoadHelper
import matisse.mymatisse.entity.Album
import com.matisse.model.AlbumCallbacks
import kotlinx.android.synthetic.main.model_matisse_activity_album_list.*

class AlbumListActivity : BaseMatisseActivity() {
    companion object {
        const val RESULT_INTENT_ALBUM = "result_intent_album"
    }
    private lateinit var albumLoadHelper: MyAlbumLoadHelper

    val albumList = arrayListOf<Album>()
    var albumListAdapter = AlbumListAdapter(albumList, clickCallback = { album ->
        //设置退出数据
        val resultData = Intent()
        resultData.putExtra(RESULT_INTENT_ALBUM, album)
        setResult(Activity.RESULT_OK, resultData)
        finish()
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.model_matisse_activity_album_list)

        rv_album_list.layoutManager = LinearLayoutManager(this)
        rv_album_list.adapter = albumListAdapter

        //加载相册
        albumLoadHelper = MyAlbumLoadHelper(this, object : AlbumCallbacks {
            override fun onAlbumStart() {
                // do nothing
            }

            override fun onAlbumLoad(cursor: Cursor) {
                albumList.addAll(readAlbumFromCursor(cursor))
                for (i in 0 until (albumList.size)){
                    val album = albumList[i]
                    Log.i("Album","${album.displayName},${album.getCount()},${album.getCoverPath()}")
                }
                albumListAdapter.notifyDataSetChanged()

                //在主线程中调用
                Handler(Looper.getMainLooper()).post {

                }
            }

            override fun onAlbumReset() {
            }
        })
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        albumLoadHelper.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        albumLoadHelper.onDestroy()
    }


    private fun readAlbumFromCursor(albumFolderCursor: Cursor): ArrayList<Album> {
        var allFolderCoverPath: Uri? = null
        var allFolderCount = 0L

        val albumFolderList = arrayListOf<Album>()

        albumFolderCursor.moveToFirst()
        while (albumFolderCursor.moveToNext()) {
            val album = Album.valueOf(albumFolderCursor!!)
            if (albumFolderList.size == 0) {
                allFolderCoverPath = album.getCoverPath()
            }
            albumFolderList.add(album)
            allFolderCount += album.getCount()
        }
        albumFolderList.add(
                0, Album(allFolderCoverPath, getString(R.string.album_name_all), allFolderCount)
        )
        return albumFolderList
    }
}