package com.sd.libcore.business.stream

import com.sd.lib.stream.FStream

interface StreamActivityBackPressed : FStream {
    fun onActivityBackPressed(): Boolean
}