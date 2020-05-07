package com.leo.matisse.mymatisse.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.leo.matisse.R

class CheckNumView : FrameLayout {
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    lateinit var tv_num: TextView
    //-1代表没有选中
    var checkedNum = -1
        set(value) {
            field = value
            if (field <= 0) {
                tv_num.visibility = View.GONE
            } else {
                tv_num.visibility = View.VISIBLE
                tv_num.text = field.toString()
            }
        }
    var checkedCallback: ((isChecked: Boolean) -> Unit)? = null


    private fun init() {
        val rootView = View.inflate(context, R.layout.model_matisse_view_check_num, this)
        tv_num = rootView.findViewById(R.id.tv_num)
        tv_num.visibility = View.GONE
        rootView.setOnClickListener {
            if (checkedNum <= 0) {
                checkedCallback?.invoke(true)
            } else {
                checkedCallback?.invoke(false)
            }
        }
    }


}