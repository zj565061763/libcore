package com.sd.libcore.business

import android.text.TextUtils
import java.util.*

/**
 * 业务类管理
 */
class FBusinessManager private constructor() {
    private val mMapBusiness: MutableMap<FBusiness, String> = WeakHashMap()

    /**
     * 放回指定tag的业务类
     */
    fun getBusiness(tag: String?): List<FBusiness> {
        val listResult: MutableList<FBusiness> = ArrayList()
        findBusiness {
            if (TextUtils.equals(it.tag, tag)) {
                listResult.add(it)
            }
            false
        }
        return listResult
    }

    /**
     * 返回指定类型的业务类
     */
    fun <T : FBusiness> getBusiness(clazz: Class<T>): List<T> {
        requireNotNull(clazz) { "null argument" }
        val listResult: MutableList<T> = ArrayList()
        findBusiness {
            if (it.javaClass == clazz) {
                listResult.add(it as T)
            }
            false
        }
        return listResult
    }

    /**
     * 返回指定类型和tag的业务类
     */
    fun <T : FBusiness> getBusiness(clazz: Class<T>, tag: String?): List<T> {
        val listResult: MutableList<T> = ArrayList()
        findBusiness {
            if (it.javaClass == clazz && TextUtils.equals(it.tag, tag)) {
                listResult.add(it as T)
            }
            false
        }
        return listResult
    }

    /**
     * 查找业务类
     */
    fun findBusiness(callback: FindBusinessCallback) {
        val listCopy = ArrayList(mMapBusiness.keys)
        for (item in listCopy) {
            if (callback.onBusiness(item!!)) {
                break
            }
        }
    }

    /**
     * 保存业务类
     */
    @Synchronized
    fun addBusiness(business: FBusiness) {
        requireNotNull(business) { "null argument" }
        mMapBusiness[business] = ""
    }

    /**
     * 移除业务类
     */
    @Synchronized
    fun removeBusiness(business: FBusiness) {
        requireNotNull(business) { "null argument" }
        mMapBusiness.remove(business)
    }

    fun interface FindBusinessCallback {
        /**
         * 业务类对象回调
         *
         * @return true-停止查找
         */
        fun onBusiness(business: FBusiness): Boolean
    }

    companion object {
        @JvmStatic
        val instance: FBusinessManager by lazy {
            FBusinessManager()
        }
    }
}