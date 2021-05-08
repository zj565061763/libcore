package com.sd.libcore.business

import android.text.TextUtils
import java.util.*
import kotlin.collections.ArrayList

/**
 * 业务类管理
 */
object FBusinessManager {
    private val _mapBusiness = WeakHashMap<FBusiness, String>()

    /**
     * 放回指定tag的业务类
     */
    fun getBusiness(tag: String?): Collection<FBusiness> {
        val listResult = mutableListOf<FBusiness>()
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
    fun <T : FBusiness> getBusiness(clazz: Class<T>): Collection<T> {
        val listResult = mutableListOf<T>()
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
    fun <T : FBusiness> getBusiness(clazz: Class<T>, tag: String?): Collection<T> {
        val listResult = mutableListOf<T>()
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
        val listCopy = ArrayList<FBusiness>(_mapBusiness.keys)
        for (item in listCopy) {
            if (callback.onBusiness(item)) {
                break
            }
        }
    }

    /**
     * 保存业务类
     */
    @Synchronized
    internal fun addBusiness(business: FBusiness) {
        _mapBusiness[business] = ""
    }

    /**
     * 移除业务类
     */
    @Synchronized
    internal fun removeBusiness(business: FBusiness) {
        _mapBusiness.remove(business)
    }

    fun interface FindBusinessCallback {
        /**
         * 业务类对象回调
         * @return true-停止查找
         */
        fun onBusiness(business: FBusiness): Boolean
    }
}