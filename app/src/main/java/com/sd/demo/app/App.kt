package com.sd.demo.app

import com.sd.libcore.app.FApplication
import com.sd.libcore.utils.FActivityStack

class App : FApplication() {
    override fun onCreateMainProcess() {
        FActivityStack.getInstance().setDebug(true)
    }
}