package com.fanwe.library.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.fanwe.library.adapter.SDAdapter;
@Deprecated
public class SDViewPageIndicator extends HorizontalScrollView implements OnPageChangeListener
{
    private ViewPager mViewPager;
    private LinearLayout ll_root;

    private LinearLayout ll_tabs;
    private SDAdapter mAdapter;

    private Runnable mScrollRunnable;
    private ItemSelectedCallback mItemSelectedCallback;

    public SDViewPageIndicator(Context context)
    {
        super(context);
        init();
    }

    public SDViewPageIndicator(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SDViewPageIndicator(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        setHorizontalScrollBarEnabled(false);
        addRootLayout();
        addTabLayout();
    }

    public LinearLayout getTabsLayout()
    {
        return ll_tabs;
    }

    /**
     * 添加根部局
     */
    private void addRootLayout()
    {
        ll_root = new LinearLayout(getContext());
        ll_root.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        addView(ll_root, params);
    }

    /**
     * 添加tab根部局
     */
    private void addTabLayout()
    {
        if (ll_tabs == null)
        {
            ll_tabs = new LinearLayout(getContext());
            ll_tabs.setOrientation(LinearLayout.HORIZONTAL);
            ll_tabs.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            ll_root.addView(ll_tabs, params);
        }
    }

    /**
     * 设置item选中回调
     *
     * @param itemSelectedCallback
     */
    public void setItemSelectedCallback(ItemSelectedCallback itemSelectedCallback)
    {
        this.mItemSelectedCallback = itemSelectedCallback;
    }

    /**
     * 设置adapter
     *
     * @param adapter
     */
    public void setAdapter(SDAdapter adapter)
    {
        if (mAdapter != adapter)
        {
            if (mAdapter != null)
            {
                mAdapter.unregisterDataSetObserver(mDataSetObserverInternal);
            }
            mAdapter = adapter;
            if (adapter != null)
            {
                adapter.registerDataSetObserver(mDataSetObserverInternal);
            }
            notifyDataSetChanged();
        }
    }

    private DataSetObserver mDataSetObserverInternal = new DataSetObserver()
    {
        @Override
        public void onChanged()
        {
            notifyDataSetChanged();
            super.onChanged();
        }
    };

    private void scrollToTab(final int position)
    {
        if (!isPositionLegal(position))
        {
            return;
        }
        View child = ll_tabs.getChildAt(position);
        postScrollRunnable(child);
        mAdapter.getSelectManager().setSelected(position, true);
        notifyItemSelected(position, child);
    }

    private void notifyItemSelected(int position, View child)
    {
        if (mItemSelectedCallback != null)
        {
            mItemSelectedCallback.onItemSelected(position, child);
        }
    }

    private void postScrollRunnable(final View child)
    {
        removeScrollRunnable();
        mScrollRunnable = new Runnable()
        {
            public void run()
            {
                int scrollY = child.getLeft() - (getWidth() - child.getWidth()) / 2;
                smoothScrollTo(scrollY, 0);
                mScrollRunnable = null;
            }
        };
        postScrollRunnable();
    }

    private void removeScrollRunnable()
    {
        if (mScrollRunnable != null)
        {
            removeCallbacks(mScrollRunnable);
            mScrollRunnable = null;
        }
    }

    private void postScrollRunnable()
    {
        if (mScrollRunnable != null)
        {
            post(mScrollRunnable);
        }
    }

    @Override
    public void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        postScrollRunnable();
    }

    @Override
    public void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        removeScrollRunnable();
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
    }

    @Override
    public void onPageSelected(int position)
    {
        scrollToTab(position);
    }

    /**
     * 设置ViewPager
     *
     * @param viewPager
     */
    public void setViewPager(ViewPager viewPager)
    {
        if (mViewPager != viewPager)
        {
            if (mViewPager != null)
            {
                mViewPager.removeOnPageChangeListener(this);
            }
            mViewPager = viewPager;
            if (viewPager != null)
            {
                viewPager.addOnPageChangeListener(this);
            }
        }
    }

    /**
     * 通知刷新数据
     */
    public void notifyDataSetChanged()
    {
        ll_tabs.removeAllViews();

        if (mAdapter != null && mAdapter.getCount() > 0)
        {
            int count = mAdapter.getCount();
            for (int i = 0; i < count; i++)
            {
                View view = mAdapter.getView(i, null, ll_tabs);
                if (!view.hasOnClickListeners())
                {
                    view.setOnClickListener(new DefaultItemClickListener(i));
                }
                ll_tabs.addView(view);
            }
        }
        requestLayout();
    }

    private class DefaultItemClickListener implements OnClickListener
    {
        private int itemPosition;

        public DefaultItemClickListener(int position)
        {
            this.itemPosition = position;
        }

        @Override
        public void onClick(View v)
        {
            setCurrentItem(itemPosition);
        }
    }

    private boolean isPositionLegal(int position)
    {
        if (position >= 0 && mAdapter != null && position < mAdapter.getCount())
        {
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * 设置选中的项
     *
     * @param position
     */
    public void setCurrentItem(int position)
    {
        if (!isPositionLegal(position))
        {
            return;
        }
        if (mViewPager != null && mViewPager.getCurrentItem() != position)
        {
            mViewPager.setCurrentItem(position);
        } else
        {
            scrollToTab(position);
        }
    }

    public interface ItemSelectedCallback
    {
        void onItemSelected(int position, View view);
    }

}
