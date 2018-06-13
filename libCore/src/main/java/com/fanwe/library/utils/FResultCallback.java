package com.fanwe.library.utils;

/**
 * 通用的结果回调
 */
public interface FResultCallback<T>
{
    void onSuccess(T result);

    void onError(int code, String desc);
}
