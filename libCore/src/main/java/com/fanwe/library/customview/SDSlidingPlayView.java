package com.fanwe.library.customview;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.R;
import com.fanwe.library.customview.SDViewPager.MeasureMode;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDTimer;
import com.fanwe.library.utils.SDTimer.SDTimerListener;
import com.fanwe.library.utils.SDViewUtil;

@Deprecated
public class SDSlidingPlayView extends LinearLayout
{
    private static final long DEFAULT_PLAY_SPAN = 1000 * 7;

    private EnumMode mode = EnumMode.IMAGE;

    public SDViewPager vpg_content;
    public TextView tv_index;
    public LinearLayout ll_bot;
    public LinearLayout ll_blur;

    private PagerAdapter adapter;

    private int currentPosition;
    private int lastSelectedPosition;

    private boolean isNeedAutoPlay = false;
    private boolean isPlaying = false;

    private SDTimer timer = new SDTimer();
    private OnTouchListener viewPagerTouchListener;
    private long playSpan = DEFAULT_PLAY_SPAN;

    private int selectedImageResId;
    private int normalImageResId;

    public void setMode(EnumMode mode)
    {
        if (mode != null)
        {
            this.mode = mode;
            changeBottomViewByData();
        }
    }

    public void setSelectedImageResId(int selectedImageResId)
    {
        this.selectedImageResId = selectedImageResId;
    }

    public void setNormalImageResId(int normalImageResId)
    {
        this.normalImageResId = normalImageResId;
    }

    public boolean isPlaying()
    {
        return isPlaying;
    }

    public SDViewPager getViewPager()
    {
        return vpg_content;
    }

    public void addOnPageChangeListener(OnPageChangeListener onPageChangeListener)
    {
        if (vpg_content != null)
        {
            vpg_content.addOnPageChangeListener(onPageChangeListener);
        }
    }

    public void setViewPagerTouchListener(OnTouchListener viewPagerTouchListener)
    {
        this.viewPagerTouchListener = viewPagerTouchListener;
    }

    public void setContentMatchParent()
    {
        vpg_content.setMeasureMode(MeasureMode.NORMAL);
        SDViewUtil.setHeightMatchParent(vpg_content);
    }

    public void setContentWrapContent()
    {
        vpg_content.setMeasureMode(MeasureMode.MAX_CHILD);
        SDViewUtil.setHeightWrapContent(vpg_content);
    }

    public SDSlidingPlayView(Context context)
    {
        this(context, null);
    }

    public SDSlidingPlayView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.view_sliding_play_view, this, true);
        vpg_content = (SDViewPager) findViewById(R.id.vpg_content);
        ll_blur = (LinearLayout) findViewById(R.id.ll_blur);
        ll_bot = (LinearLayout) findViewById(R.id.ll_bot);
        SDViewUtil.setHeight(ll_blur, SDViewUtil.dp2px(20));

        vpg_content.setMeasureMode(MeasureMode.MAX_CHILD);
        vpg_content.addOnPageChangeListener(defaultOnPageChangeListener);
        vpg_content.setOnTouchListener(defaultOnTouchListener);
    }

    private TextView createTextView()
    {
        TextView tvCount = new TextView(getContext());
        tvCount.setTextColor(SDResourcesUtil.getColor(R.color.white));
        return tvCount;
    }

    public void setAdapter(PagerAdapter adapter)
    {
        this.adapter = adapter;
        if (this.adapter != null)
        {
            this.adapter.registerDataSetObserver(new DataSetObserver()
            {
                @Override
                public void onChanged()
                {
                    changeBottomViewByData();
                    super.onChanged();
                }
            });
            vpg_content.setAdapter(this.adapter);
            changeBottomViewByData();
        }
    }

    private void changeBottomViewByData()
    {
        if (adapter == null)
        {
            return;
        }

        ll_blur.removeAllViews();
        int count = adapter.getCount();
        switch (mode)
        {
            case IMAGE:
                if (count > 1)
                {
                    for (int i = 0; i < count; i++)
                    {
                        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1);
                        if (i != count - 1)
                        {
                            params.rightMargin = getRightMargin();
                        }
                        ImageView iv = new ImageView(getContext());
                        iv.setScaleType(ScaleType.CENTER_INSIDE);
                        setImageViewState(iv, false);
                        ll_blur.addView(iv, params);
                    }
                }
                break;
            case NUMBER:
                tv_index = createTextView();
                ll_blur.setBackgroundColor(SDResourcesUtil.getColor(R.color.res_blur_m));
                ll_blur.addView(tv_index);
                break;

            default:
                break;
        }
        updateBottomIndex();
    }

    private int getRightMargin()
    {
        return 20;
    }

    private void setImageViewState(View view, boolean selected)
    {
        if (view != null && (view instanceof ImageView))
        {
            ImageView iv = (ImageView) view;
            if (selected)
            {
                iv.setImageResource(selectedImageResId);
            } else
            {
                iv.setImageResource(normalImageResId);
            }
        }
    }

    private void setImageViewState(int position, boolean selected)
    {
        View view = ll_blur.getChildAt(position);
        setImageViewState(view, selected);
    }

    /**
     * 触摸监听
     */
    private OnTouchListener defaultOnTouchListener = new OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            if (viewPagerTouchListener != null)
            {
                viewPagerTouchListener.onTouch(v, event);
            }
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    stopPlay();
                    break;
                case MotionEvent.ACTION_MOVE:
                    stopPlay();
                    break;
                case MotionEvent.ACTION_UP:
                    startPlayAuto();
                    break;

                default:
                    break;
            }
            return false;
        }
    };

    /**
     * ViewPager滚动监听
     */
    private OnPageChangeListener defaultOnPageChangeListener = new OnPageChangeListener()
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {

        }

        @Override
        public void onPageSelected(int position)
        {
            lastSelectedPosition = currentPosition;
            currentPosition = position;

            updateBottomIndex();
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {

        }
    };

    private void updateBottomIndex()
    {
        if (adapter == null)
        {
            return;
        }

        int total = adapter.getCount();

        switch (mode)
        {
            case IMAGE:
                setImageViewState(lastSelectedPosition, false);
                setImageViewState(currentPosition, true);
                break;
            case NUMBER:
                tv_index.setText((currentPosition + 1) + "/" + total);
                break;

            default:
                break;
        }
    }

    private void setCurrentItem(int position)
    {
        if (adapter == null)
        {
            return;
        }

        int count = adapter.getCount();

        if (position < 0)
        {
            position = 0;
        }

        if (position >= count)
        {
            position = 0;
        }

        if (position >= 0 && position < count)
        {
            vpg_content.setCurrentItem(position, true);
        }
    }

    public void startPlay()
    {
        startPlay(DEFAULT_PLAY_SPAN);
    }

    public void startPlay(long playSpan)
    {
        if (playSpan < 1000)
        {
            playSpan = DEFAULT_PLAY_SPAN;
        }

        this.playSpan = playSpan;

        isNeedAutoPlay = true;
        startPlayAuto();
    }

    private void startPlayAuto()
    {
        if (adapter == null)
        {
            return;
        }

        if (!isNeedAutoPlay)
        {
            return;
        }

        if (adapter.getCount() <= 1)
        {
            return;
        }

        isPlaying = true;
        timer.startWork(playSpan, playSpan, new SDTimerListener()
        {

            @Override
            public void onWorkMain()
            {
                if (adapter.getCount() <= 1)
                {
                    stopPlay();
                    return;
                }
                setCurrentItem(vpg_content.getCurrentItem() + 1);
            }

            @Override
            public void onWork()
            {

            }
        });
    }

    public void stopPlay()
    {
        isPlaying = false;
        isNeedAutoPlay = false;
        timer.stopWork();
    }

    public enum EnumMode
    {
        NUMBER, IMAGE;
    }

}
