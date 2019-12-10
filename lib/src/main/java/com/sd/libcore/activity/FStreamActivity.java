package com.sd.libcore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.sd.lib.stream.FStream;
import com.sd.lib.stream.FStreamManager;
import com.sd.libcore.stream.activity.ActivityCreatedStream;
import com.sd.libcore.stream.activity.ActivityDestroyedStream;
import com.sd.libcore.stream.activity.ActivityInstanceStateStream;
import com.sd.libcore.stream.activity.ActivityKeyEventStream;
import com.sd.libcore.stream.activity.ActivityPausedStream;
import com.sd.libcore.stream.activity.ActivityResultStream;
import com.sd.libcore.stream.activity.ActivityResumedStream;
import com.sd.libcore.stream.activity.ActivityStartedStream;
import com.sd.libcore.stream.activity.ActivityStoppedStream;
import com.sd.libcore.stream.activity.ActivityTouchEventStream;

import java.lang.reflect.Method;

public abstract class FStreamActivity extends FActivity implements FStream
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FStreamManager.getInstance().register(this);
        notifyOnCreate(savedInstanceState);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        notifyOnStart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        notifyOnResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        notifyOnPause();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        notifyOnStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        FStreamManager.getInstance().unregister(this);
        notifyOnDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        notifyOnSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        notifyOnRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        notifyOnActivityResult(requestCode, resultCode, data);
    }

    private ActivityTouchEventStream mTouchEventStream;

    private ActivityTouchEventStream getTouchEventStream()
    {
        if (mTouchEventStream == null)
        {
            mTouchEventStream = new FStream.ProxyBuilder()
                    .setTag(getStreamTag())
                    .setDispatchCallback(new DispatchCallback()
                    {
                        @Override
                        public boolean beforeDispatch(FStream stream, Method method, Object[] methodParams)
                        {
                            return false;
                        }

                        @Override
                        public boolean afterDispatch(FStream stream, Method method, Object[] methodParams, Object methodResult)
                        {
                            if (Boolean.TRUE.equals(methodResult))
                                return true;

                            return false;
                        }
                    })
                    .build(ActivityTouchEventStream.class);
        }
        return mTouchEventStream;
    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev)
    {
        final boolean result = getTouchEventStream().dispatchTouchEvent(FStreamActivity.this, ev);
        if (result)
            return true;

        return super.dispatchTouchEvent(ev);
    }

    private ActivityKeyEventStream mKeyEventStream;

    private ActivityKeyEventStream getKeyEventStream()
    {
        if (mKeyEventStream == null)
        {
            mKeyEventStream = new FStream.ProxyBuilder()
                    .setTag(getStreamTag())
                    .setDispatchCallback(new DispatchCallback()
                    {
                        @Override
                        public boolean beforeDispatch(FStream stream, Method method, Object[] methodParams)
                        {
                            return false;
                        }

                        @Override
                        public boolean afterDispatch(FStream stream, Method method, Object[] methodParams, Object methodResult)
                        {
                            if (Boolean.TRUE.equals(methodResult))
                                return true;

                            return false;
                        }
                    })
                    .build(ActivityKeyEventStream.class);
        }
        return mKeyEventStream;
    }

    @Override
    public boolean dispatchKeyEvent(final KeyEvent event)
    {
        final boolean result = getKeyEventStream().dispatchKeyEvent(FStreamActivity.this, event);
        if (result)
            return true;

        return super.dispatchKeyEvent(event);
    }

    @Override
    public Object getTagForStream(Class<? extends FStream> clazz)
    {
        return getStreamTag();
    }

    public final String getStreamTag()
    {
        return FStreamActivity.this.toString();
    }

    private void notifyOnCreate(final Bundle savedInstanceState)
    {
        final ActivityCreatedStream stream = new FStream.ProxyBuilder()
                .setTag(getStreamTag())
                .build(ActivityCreatedStream.class);

        stream.onActivityCreated(FStreamActivity.this, savedInstanceState);
    }

    private void notifyOnStart()
    {
        final ActivityStartedStream stream = new FStream.ProxyBuilder()
                .setTag(getStreamTag())
                .build(ActivityStartedStream.class);

        stream.onActivityStarted(FStreamActivity.this);
    }

    private void notifyOnResume()
    {
        final ActivityResumedStream stream = new FStream.ProxyBuilder()
                .setTag(getStreamTag())
                .build(ActivityResumedStream.class);

        stream.onActivityResumed(FStreamActivity.this);
    }

    private void notifyOnPause()
    {
        final ActivityPausedStream stream = new FStream.ProxyBuilder()
                .setTag(getStreamTag())
                .build(ActivityPausedStream.class);

        stream.onActivityPaused(FStreamActivity.this);
    }

    private void notifyOnStop()
    {
        final ActivityStoppedStream stream = new FStream.ProxyBuilder()
                .setTag(getStreamTag())
                .build(ActivityStoppedStream.class);

        stream.onActivityStopped(FStreamActivity.this);
    }

    private void notifyOnDestroy()
    {
        final ActivityDestroyedStream stream = new FStream.ProxyBuilder()
                .setTag(getStreamTag())
                .build(ActivityDestroyedStream.class);

        stream.onActivityDestroyed(FStreamActivity.this);
    }

    private void notifyOnSaveInstanceState(final Bundle outState)
    {
        final ActivityInstanceStateStream stream = new FStream.ProxyBuilder()
                .setTag(getStreamTag())
                .build(ActivityInstanceStateStream.class);

        stream.onActivitySaveInstanceState(FStreamActivity.this, outState);
    }

    private void notifyOnRestoreInstanceState(final Bundle savedInstanceState)
    {
        final ActivityInstanceStateStream stream = new FStream.ProxyBuilder()
                .setTag(getStreamTag())
                .build(ActivityInstanceStateStream.class);

        stream.onActivityRestoreInstanceState(FStreamActivity.this, savedInstanceState);
    }

    private void notifyOnActivityResult(final int requestCode, final int resultCode, final Intent data)
    {
        final ActivityResultStream stream = new FStream.ProxyBuilder()
                .setTag(getStreamTag())
                .build(ActivityResultStream.class);

        stream.onActivityResult(FStreamActivity.this, requestCode, resultCode, data);
    }
}
