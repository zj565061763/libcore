package com.fanwe.library.model;

/**
 * Created by zhengjun on 2018/3/6.
 */
public class FResult<T>
{
    private final boolean mIsSuccessful;

    private T mData;

    private int mCode;
    private String mDescription;

    public FResult(boolean isSuccessful)
    {
        mIsSuccessful = isSuccessful;
    }

    public FResult(boolean isSuccessful, T data)
    {
        mIsSuccessful = isSuccessful;
        mData = data;
    }

    public FResult(boolean isSuccessful, int code, String description)
    {
        mIsSuccessful = isSuccessful;
        mCode = code;
        mDescription = description;
    }

    /**
     * 设置结果数据
     *
     * @param data
     * @return
     */
    public FResult<T> setData(T data)
    {
        mData = data;
        return this;
    }

    /**
     * 设置结果码
     *
     * @param code
     * @return
     */
    public FResult<T> setCode(int code)
    {
        mCode = code;
        return this;
    }

    /**
     * 设置结果描述
     *
     * @param description
     * @return
     */
    public FResult<T> setDescription(String description)
    {
        mDescription = description;
        return this;
    }

    /**
     * 返回当前结果对象是否是成功的结果
     *
     * @return
     */
    public boolean isSuccessful()
    {
        return mIsSuccessful;
    }

    /**
     * 返回结果数据
     *
     * @return
     */
    public T getData()
    {
        return mData;
    }

    /**
     * 返回结果码
     *
     * @return
     */
    public int getCode()
    {
        return mCode;
    }

    /**
     * 返回结果描述
     *
     * @return
     */
    public String getDescription()
    {
        return mDescription;
    }
}
