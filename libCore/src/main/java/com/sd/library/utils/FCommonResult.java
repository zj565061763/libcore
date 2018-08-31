package com.sd.library.utils;

/**
 * Created by zhengjun on 2018/3/6.
 */
public class FCommonResult<T>
{
    private final boolean mIsSuccessful;

    private T mData;

    private int mCode;
    private String mDescription;

    public FCommonResult(boolean isSuccessful)
    {
        mIsSuccessful = isSuccessful;
    }

    public FCommonResult(boolean isSuccessful, T data)
    {
        mIsSuccessful = isSuccessful;
        mData = data;
    }

    public FCommonResult(boolean isSuccessful, int code, String description)
    {
        mIsSuccessful = isSuccessful;
        mCode = code;
        mDescription = description;
    }

    public FCommonResult(FCommonResult<T> result)
    {
        mIsSuccessful = result.isSuccessful();
        mData = result.getData();
        mCode = result.getCode();
        mDescription = result.getDescription();
    }

    /**
     * 设置结果数据
     *
     * @param data
     * @return
     */
    public FCommonResult<T> setData(T data)
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
    public FCommonResult<T> setCode(int code)
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
    public FCommonResult<T> setDescription(String description)
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
