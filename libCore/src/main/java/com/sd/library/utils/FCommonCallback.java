package com.sd.library.utils;

/**
 * 通用的结果回调
 */
public interface FCommonCallback<T>
{
    void onSuccess(T result);

    void onError(int code, String desc);
}
