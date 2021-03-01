package com.sd.libcore.business;

import android.text.TextUtils;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sd.lib.stream.FStream;
import com.sd.libcore.business.stream.BSProgress;
import com.sd.libcore.business.stream.BSTipsCallback;

public abstract class FBusiness
{
    private String mTag;

    public FBusiness(@Nullable String tag)
    {
        mTag = tag;
    }

    /**
     * 初始化
     */
    @CallSuper
    public void init()
    {
        FBusinessManager.getInstance().addBusiness(this);
    }

    /**
     * 返回当前业务类的标识
     *
     * @return
     */
    @Nullable
    public final String getTag()
    {
        return mTag;
    }

    /**
     * 设置业务类标识
     *
     * @param tag
     */
    public final void setTag(@Nullable String tag)
    {
        final String oldTag = mTag;
        if (!TextUtils.equals(oldTag, tag))
        {
            mTag = tag;
            onTagChanged(oldTag, tag);
        }
    }

    /**
     * 返回Http请求标识
     *
     * @return
     */
    @NonNull
    public String getHttpTag()
    {
        return toString();
    }

    /**
     * 返回一个流代理对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    @NonNull
    protected final <T extends FStream> T getStream(@NonNull Class<T> clazz)
    {
        return new FStream.ProxyBuilder().setTag(getTag()).build(clazz);
    }

    @NonNull
    public final BSProgress getProgress()
    {
        return getStream(BSProgress.class);
    }

    @NonNull
    public final BSTipsCallback getTipsCallback()
    {
        return getStream(BSTipsCallback.class);
    }

    /**
     * 标识变化回调
     *
     * @param oldTag
     * @param newTag
     */
    protected void onTagChanged(@Nullable String oldTag, @Nullable String newTag)
    {
    }

    /**
     * 销毁
     */
    @CallSuper
    public void onDestroy()
    {
        FBusinessManager.getInstance().removeBusiness(this);
    }
}
