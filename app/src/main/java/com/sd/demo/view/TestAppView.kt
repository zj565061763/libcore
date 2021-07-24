package com.sd.demo.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.sd.demo.business.MyBusiness
import com.sd.libcore.view.FControlView

class TestAppView : FControlView {
    val TAG = TestAppView::class.java.simpleName

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        Log.i(TAG, "constructor")
        tagViewApi.prepare {
            val business = it.getItem(MyBusiness::class.java)
            Log.i(TAG, "prepare tagView:${it} business:${business}")
        }

        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                Log.i(TAG, "lifecycle onStateChanged ${event}")
            }
        })
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.i(TAG, "onAttachedToWindow")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.i(TAG, "onDetachedFromWindow")
    }
}