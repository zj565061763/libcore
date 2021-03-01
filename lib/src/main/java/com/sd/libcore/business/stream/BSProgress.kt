package com.sd.libcore.business.stream

import com.sd.lib.stream.FStream

interface BSProgress : FStream {
    fun bsShowProgress(msg: String?)
    fun bsHideProgress()
}