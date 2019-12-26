package com.leo.matisse.mymatisse

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.leo.matisse.BaseMatisseActivity
import com.leo.matisse.R
import com.leo.matisse.albumlist.AlbumListActivity
import com.leo.matisse.mymatisse.fragment.MyMediaSelectionFragment
import com.matisse.entity.Album
import com.matisse.entity.IncapableCause
import com.matisse.model.AlbumCallbacks
import com.matisse.utils.UIUtils
import kotlinx.android.synthetic.main.activity_my_matisse.*

class MyMatisseActivity : BaseMatisseActivity() {
    companion object {
        const val REQUEST_CODE_ALBUM = 0
    }

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
                //cursor获取相册

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
            if (allAlbum?.isAll() == true && allAlbum?.isEmpty() == true) {
                UIUtils.handleCause(this, IncapableCause(getString(com.matisse.R.string.empty_album)))
                return@setOnClickListener
            }


            val intent = Intent(this, AlbumListActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ALBUM)
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
            tv_album.text = album.displayName
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ALBUM && resultCode == Activity.RESULT_OK) {
            val album = data?.getParcelableExtra<Album>(AlbumListActivity.RESULT_INTENT_ALBUM)
            if (album != null) {
                onAlbumSelected(album)
            }
        }
    }
}