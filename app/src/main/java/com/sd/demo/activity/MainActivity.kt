package com.sd.demo.activity

import android.os.Bundle
import android.view.View
import com.sd.demo.R
import com.sd.demo.databinding.ActMainBinding
import com.sd.libcore.activity.FStreamActivity

class MainActivity : FStreamActivity() {

    private lateinit var mBinding: ActMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
        mBinding = ActMainBinding.bind(contentView)
    }

    override fun onClick(v: View) {
        super.onClick(v)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.viewTag.destroyItem()
    }
}