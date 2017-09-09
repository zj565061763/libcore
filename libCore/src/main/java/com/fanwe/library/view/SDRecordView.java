package com.fanwe.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.fanwe.library.utils.SDViewUtil;

public class SDRecordView extends LinearLayout
{

    private View recordView;
    private View cancelView;

    private boolean isTouchCancelView = false;
    private boolean isCancel = false;

    private RecordViewListener listener;

    public void setListener(RecordViewListener listener)
    {
        this.listener = listener;
    }

    public void setCancelView(View cancelView)
    {
        this.cancelView = cancelView;
    }

    public void setRecordView(View recordView)
    {
        this.recordView = recordView;
    }

    public SDRecordView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public SDRecordView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SDRecordView(Context context)
    {
        super(context);
        init();
    }

    private void init()
    {
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        if (ev.getAction() == MotionEvent.ACTION_DOWN)
        {
            if (SDViewUtil.isViewUnder(recordView, ev))
            {
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (SDViewUtil.isViewUnder(recordView, ev))
                {
                    boolean down = false;
                    if (listener != null)
                    {
                        down = listener.onDownRecordView();
                    }
                    super.onTouchEvent(ev);
                    return down;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isCancel)
                {
                    reset();
                    if (listener != null)
                    {
                        listener.onCancel();
                    }
                    return super.onTouchEvent(ev);
                } else
                {
                    if (SDViewUtil.isViewUnder(cancelView, ev))
                    {
                        if (!isTouchCancelView)
                        {
                            isTouchCancelView = true;
                            if (listener != null)
                            {
                                listener.onEnterCancelView();
                            }
                        }
                    } else
                    {
                        if (isTouchCancelView)
                        {
                            isTouchCancelView = false;
                            if (listener != null)
                            {
                                listener.onLeaveCancelView();
                            }
                        }
                    }
                    return true;
                }
            case MotionEvent.ACTION_UP:
                reset();
                if (SDViewUtil.isViewUnder(cancelView, ev))
                {
                    if (listener != null)
                    {
                        listener.onUpCancelView();
                    }
                } else
                {
                    if (listener != null)
                    {
                        listener.onUp();
                    }
                }
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    protected void reset()
    {
        isCancel = false;
        isTouchCancelView = false;
    }

    public void cancel()
    {
        isCancel = true;
        if (listener != null)
        {
            listener.onCancel();
        }
    }

    public interface RecordViewListener
    {
        boolean onDownRecordView();

        void onEnterCancelView();

        void onLeaveCancelView();

        void onUp();

        void onUpCancelView();

        void onCancel();
    }

}
