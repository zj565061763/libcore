package com.sd.libcore.activity;

import android.os.Bundle;

import com.sd.lib.stream.FStream;
import com.sd.lib.stream.FStreamManager;
import com.sd.libcore.business.holder.FActivityBusinessHolder;
import com.sd.libcore.business.holder.FBusinessHolder;
import com.sd.libcore.business.stream.StreamActivityBackPressed;

import java.lang.reflect.Method;

public abstract class FStreamActivity extends FActivity implements FStream {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FStreamManager.getInstance().register(this);
    }

    @Override
    public Object getTagForStream(Class<? extends FStream> clazz) {
        return getStreamTag();
    }

    public final String getStreamTag() {
        return FStreamActivity.this.toString();
    }

    @Deprecated
    public final FBusinessHolder getBusinessHolder() {
        return FActivityBusinessHolder.with(this);
    }

    @Override
    public void onBackPressed() {
        final StreamActivityBackPressed stream = new ProxyBuilder()
                .setDispatchCallback(new DispatchCallback() {
                    @Override
                    public boolean beforeDispatch(FStream stream, Method method, Object[] methodParams) {
                        return false;
                    }

                    @Override
                    public boolean afterDispatch(FStream stream, Method method, Object[] methodParams, Object methodResult) {
                        return Boolean.TRUE.equals(methodResult);
                    }
                })
                .setTag(getStreamTag())
                .build(StreamActivityBackPressed.class);

        if (stream.onActivityBackPressed()) {
            return;
        }

        if (onActivityBackPressed()) {
            return;
        }

        super.onBackPressed();
    }

    /**
     * 返回键回调
     *
     * @return true-拦截掉事件
     */
    protected boolean onActivityBackPressed() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FStreamManager.getInstance().unregister(this);
    }
}