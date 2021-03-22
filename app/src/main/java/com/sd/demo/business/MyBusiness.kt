package com.sd.demo.business

import android.util.Log
import com.sd.libcore.business.FBusiness

class MyBusiness : FBusiness() {
    val TAG = MyBusiness::class.java.simpleName

    override fun init() {
        super.init()
        Log.i(TAG, "init tag:${tag} ${this}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy tag:${tag} ${this}")
    }
}