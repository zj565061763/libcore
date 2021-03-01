package com.sd.libcore.business

import android.text.TextUtils
import androidx.annotation.CallSuper
import com.sd.lib.stream.FStream
import com.sd.lib.stream.FStream.ProxyBuilder
import com.sd.libcore.business.stream.BSProgress
import com.sd.libcore.business.stream.BSTipsCallback

abstract class FBusiness {

    constructor(businessTag: String?) {
        tag = businessTag
    }

    /**
     * 业务类标识
     */
    var tag: String?
        set(value) {
            val oldTag = field
            if (!TextUtils.equals(oldTag, value)) {
                field = value
                onTagChanged(oldTag, value)
            }
        }

    /**
     * 返回Http请求标识
     *
     * @return
     */
    open val httpTag: String
        get() = toString()

    /**
     * [BSProgress]
     */
    val progress: BSProgress
        get() = getStream(BSProgress::class.java)

    /**
     * [BSTipsCallback]
     */
    val tipsCallback: BSTipsCallback
        get() = getStream(BSTipsCallback::class.java)

    /**
     * 初始化
     */
    @CallSuper
    open fun init() {
        FBusinessManager.instance.addBusiness(this)
    }

    /**
     * 返回一个流代理对象
     *
     * @param clazz
     * @param <T>
     * @return
    </T> */
    protected fun <T : FStream> getStream(clazz: Class<T>): T {
        return ProxyBuilder().setTag(tag).build(clazz)
    }

    /**
     * 标识变化回调
     *
     * @param oldTag
     * @param newTag
     */
    protected open fun onTagChanged(oldTag: String?, newTag: String?) {}

    /**
     * 销毁
     */
    @CallSuper
    open fun onDestroy() {
        FBusinessManager.instance.removeBusiness(this)
    }
}