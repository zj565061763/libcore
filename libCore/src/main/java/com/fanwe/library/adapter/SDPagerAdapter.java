package com.fanwe.library.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.library.listener.SDItemClickCallback;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public abstract class SDPagerAdapter<T> extends PagerAdapter implements ISDAdapter<T>
{
    private List<T> mListModel = new ArrayList<T>();
    private Activity mActivity;

    private boolean mAutoNotifyDataSetChanged = true;

    private SparseArray<View> mArrCacheView = new SparseArray<>();
    private boolean mAutoCacheView = false;

    private SDItemClickCallback<T> mItemClickCallback;

    public SDPagerAdapter(List<T> listModel, Activity activity)
    {
        setData(listModel);
        this.mActivity = activity;
    }

    /**
     * 设置是否自动缓存view
     *
     * @param autoSaveView
     */
    public void setAutoCacheView(boolean autoSaveView)
    {
        if (mAutoCacheView != autoSaveView)
        {
            mAutoCacheView = autoSaveView;
            if (!autoSaveView)
            {
                mArrCacheView.clear();
            }
        }
    }

    private void saveCacheViewIfNeed(int position, View view)
    {
        if (view == null || !mAutoCacheView)
        {
            return;
        }
        mArrCacheView.put(position, view);
    }

    /**
     * 移除缓存的view
     *
     * @param position
     * @return
     */
    public View removeCacheView(int position)
    {
        View view = mArrCacheView.get(position);
        if (view != null)
        {
            mArrCacheView.remove(position);
        }
        return view;
    }

    /**
     * 清空缓存的view
     */
    public void clearCacheView()
    {
        mArrCacheView.clear();
    }

    public void setItemClickCallback(SDItemClickCallback<T> itemClickCallback)
    {
        this.mItemClickCallback = itemClickCallback;
    }

    public void notifyItemClickCallback(int position, T item, View view)
    {
        if (mItemClickCallback != null)
        {
            mItemClickCallback.onItemClick(position, item, view);
        }
    }

    @Override
    public int getCount()
    {
        return getDataCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        View view = mArrCacheView.get(position);
        if (view == null)
        {
            view = getView(container, position);
            saveCacheViewIfNeed(position, view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getItemPosition(Object object)
    {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }

    public abstract View getView(ViewGroup container, int position);

    //----------ISDAdapter implements start----------

    @Override
    public Activity getActivity()
    {
        return mActivity;
    }

    @Override
    public View inflate(int resource, ViewGroup root)
    {
        return getActivity().getLayoutInflater().inflate(resource, root, false);
    }

    @Override
    public boolean isPositionLegal(int position)
    {
        if (position >= 0 && position < mListModel.size())
        {
            return true;
        }
        return false;
    }

    @Override
    public void setAutoNotifyDataSetChanged(boolean auto)
    {
        mAutoNotifyDataSetChanged = auto;
    }

    @Override
    public T getData(int position)
    {
        if (isPositionLegal(position))
        {
            return mListModel.get(position);
        } else
        {
            return null;
        }
    }

    @Override
    public int getDataCount()
    {
        if (mListModel != null)
        {
            return mListModel.size();
        } else
        {
            return 0;
        }
    }

    @Override
    public int indexOf(T model)
    {
        return mListModel.indexOf(model);
    }

    @Override
    public void updateData(List<T> listModel)
    {
        setData(listModel);
        if (mAutoNotifyDataSetChanged)
        {
            notifyDataSetChanged();
        }
    }

    @Override
    public void setData(List<T> listModel)
    {
        if (listModel != null)
        {
            this.mListModel = listModel;
        } else
        {
            this.mListModel.clear();
        }
    }

    @Override
    public List<T> getData()
    {
        return mListModel;
    }

    @Override
    public void appendData(T model)
    {
        if (model != null)
        {
            mListModel.add(model);
            if (mAutoNotifyDataSetChanged)
            {
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public void appendData(List<T> list)
    {
        if (list != null && !list.isEmpty())
        {
            mListModel.addAll(list);
            if (mAutoNotifyDataSetChanged)
            {
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public void removeData(T model)
    {
        if (model != null)
        {
            int position = mListModel.indexOf(model);
            removeData(position);
        }
    }

    @Override
    public T removeData(int position)
    {
        T model = null;
        if (isPositionLegal(position))
        {
            model = mListModel.remove(position);
            if (mAutoNotifyDataSetChanged)
            {
                notifyDataSetChanged();
            }
        }
        return model;
    }

    @Override
    public void insertData(int position, T model)
    {
        if (model != null)
        {
            mListModel.add(position, model);
            if (mAutoNotifyDataSetChanged)
            {
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public void insertData(int position, List<T> list)
    {
        if (list != null && !list.isEmpty())
        {
            mListModel.addAll(position, list);
            if (mAutoNotifyDataSetChanged)
            {
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public void updateData(int position, T model)
    {
        if (model != null && isPositionLegal(position))
        {
            mListModel.set(position, model);
            updateData(position);
        }
    }

    @Override
    public void updateData(int position)
    {
        if (isPositionLegal(position))
        {
            if (mAutoNotifyDataSetChanged)
            {
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public void clearData()
    {
        updateData(null);
    }

    //----------ISDAdapter implements end----------

}
