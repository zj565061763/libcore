package com.sd.libcore.business.stream

import com.sd.lib.stream.FStream

interface BSTipsCallback : FStream {
    fun bsShowTips(tips: String?)
}