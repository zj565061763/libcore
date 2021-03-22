package com.sd.demo.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import com.sd.demo.business.MyBusiness
import com.sd.libcore.view.FControlView

class TestAppView : FControlView {

    val TAG = TestAppView::class.java.simpleName

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        tagViewApi.prepare {
            val business = it.getItem(MyBusiness::class.java)
            Log.i(TAG, "prepare tagView:${it} business:${business}")
        }
    }
}