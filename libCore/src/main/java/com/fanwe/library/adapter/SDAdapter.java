package com.fanwe.library.adapter;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fanwe.lib.selectmanager.FSelectManager;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.listener.SDItemLongClickCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;

@Deprecated
public abstract class SDAdapter<T> extends BaseAdapter implements
        ISDAdapter<T>,
        FSelectManager.Callback<T>,
        View.OnClickListener,
        SDItemClickCallback<T>
{

    private List<T> mListModel = new ArrayList<T>();
    private Activity mActivity;

    /**
     * 保存每个itemView对应的position
     */
    private Map<View, Integer> mMapViewPosition = new WeakHashMap<>();

    private boolean mAutoNotifyDataSetChanged = true;

    private FSelectManager<T> mSelectManager;

    private SDItemClickCallback<T> mItemClickCallback;
    private SDItemLongClickCallback<T> mItemLongClickCallback;

    public SDAdapter(List<T> listModel, Activity activity)
    {
        this.mActivity = activity;
        setData(listModel);
    }

    /**
     * 设置item点击回调
     *
     * @param itemClickCallback
     */
    public void setItemClickCallback(SDItemClickCallback<T> itemClickCallback)
    {
        this.mItemClickCallback = itemClickCallback;
    }

    /**
     * 设置item长按回调
     *
     * @param itemLongClickCallback
     */
    public void setItemLongClickCallback(SDItemLongClickCallback<T> itemLongClickCallback)
    {
        this.mItemLongClickCallback = itemLongClickCallback;
    }

    /**
     * 通知item点击回调
     *
     * @param position
     * @param item
     * @param view
     */
    public void notifyItemClickCallback(int position, T item, View view)
    {
        if (mItemClickCallback != null)
        {
            mItemClickCallback.onItemClick(position, item, view);
        }
    }

    /**
     * 通知item长按回调
     *
     * @param position
     * @param item
     * @param view
     */
    public void notifyItemLongClickCallback(int position, T item, View view)
    {
        if (mItemLongClickCallback != null)
        {
            mItemLongClickCallback.onItemLongClick(position, item, view);
        }
    }

    /**
     * 获得选择管理器
     *
     * @return
     */
    public FSelectManager<T> getSelectManager()
    {
        if (mSelectManager == null)
        {
            mSelectManager = new FSelectManager<>();
            mSelectManager.setMode(FSelectManager.Mode.SINGLE);
            mSelectManager.addCallback(this);
        }
        return mSelectManager;
    }

    @Override
    public void onNormal(T item)
    {
        if (item instanceof FSelectManager.Selectable)
        {
            FSelectManager.Selectable selectable = (FSelectManager.Selectable) item;
            selectable.setSelected(false);
        }

        final int position = indexOf(item);
        updateData(position);
    }

    @Override
    public void onSelected(T item)
    {
        if (item instanceof FSelectManager.Selectable)
        {
            FSelectManager.Selectable selectable = (FSelectManager.Selectable) item;
            selectable.setSelected(true);
        }

        final int position = indexOf(item);
        updateData(position);
    }

    @Override
    public void notifyDataSetChanged()
    {
        mMapViewPosition.clear();
        super.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view)
    {
        final Integer position = mMapViewPosition.get(view);
        if (position != null)
        {
            onItemClick(position, getItem(position), view);
        }
    }

    @Override
    public void onItemClick(int position, T model, View view)
    {
        notifyItemClickCallback(position, model, view);
    }

    @Override
    public int getCount()
    {
        return getDataCount();
    }

    @Override
    public T getItem(int position)
    {
        return getData(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView = onGetView(position, convertView, parent);
        mMapViewPosition.put(convertView, position);
        return convertView;
    }

    protected abstract View onGetView(int position, View convertView, ViewGroup parent);

    /**
     * 获得该position对应的itemView
     *
     * @param position
     * @return
     */
    public List<View> getItemView(int position)
    {
        if (mMapViewPosition.isEmpty())
        {
            return null;
        }

        final List<View> list = new ArrayList<>();

        final Set<Entry<View, Integer>> set = mMapViewPosition.entrySet();
        for (Entry<View, Integer> item : set)
        {
            if (Integer.valueOf(position).equals(item.getValue()))
            {
                View view = item.getKey();
                if (view != null && view.getParent() != null)
                {
                    list.add(view);
                }
            }
        }

        return list;
    }

    /**
     * 若重写此方法，则应该把需要刷新的逻辑写在重写方法中，然后不调用super的方法，此方法会在调用updateItem方法刷新某一项时候触发
     *
     * @param position
     * @param convertView
     * @param parent
     * @param model
     */
    protected void onUpdateView(int position, View convertView, ViewGroup parent, T model)
    {
        getView(position, convertView, parent);
    }

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
        } else
        {
            return false;
        }
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
    public void setData(List<T> list)
    {
        if (list != null)
        {
            mListModel = list;
        } else
        {
            mListModel.clear();
        }
        getSelectManager().setItems(mListModel);
    }

    @Override
    public List<T> getData()
    {
        return mListModel;
    }

    @Override
    public void appendData(T model)
    {
        if (model == null)
        {
            return;
        }

        mListModel.add(model);
        getSelectManager().appendItem(model);
        if (mAutoNotifyDataSetChanged)
        {
            notifyDataSetChanged();
        }
    }

    @Override
    public void appendData(List<T> list)
    {
        if (list == null || list.isEmpty())
        {
            return;
        }

        mListModel.addAll(list);
        getSelectManager().appendItems(list);
        if (mAutoNotifyDataSetChanged)
        {
            notifyDataSetChanged();
        }
    }

    @Override
    public void removeData(T model)
    {
        removeData(indexOf(model));
    }

    @Override
    public T removeData(int position)
    {
        if (!isPositionLegal(position))
        {
            return null;
        }

        final T model = mListModel.remove(position);
        getSelectManager().removeItem(model);
        if (mAutoNotifyDataSetChanged)
        {
            notifyDataSetChanged();
        }
        return model;
    }

    @Override
    public void insertData(int position, T model)
    {
        if (model == null)
        {
            return;
        }

        mListModel.add(position, model);
        getSelectManager().insertItem(position, model);
        if (mAutoNotifyDataSetChanged)
        {
            notifyDataSetChanged();
        }
    }

    @Override
    public void insertData(int position, List<T> list)
    {
        if (list == null || list.isEmpty())
        {
            return;
        }

        mListModel.addAll(position, list);
        getSelectManager().insertItem(position, list);
        if (mAutoNotifyDataSetChanged)
        {
            notifyDataSetChanged();
        }
    }

    @Override
    public void updateData(int position, T model)
    {
        if (model == null || !isPositionLegal(position))
        {
            return;
        }

        mListModel.set(position, model);
        getSelectManager().updateItem(position, model);
        if (mAutoNotifyDataSetChanged)
        {
            updateData(position);
        }
    }

    @Override
    public void updateData(int position)
    {
        List<View> list = getItemView(position);
        if (list == null || list.isEmpty())
        {
            return;
        }

        for (View item : list)
        {
            onUpdateView(position, item, (ViewGroup) item.getParent(), getItem(position));
        }
    }

    @Override
    public void clearData()
    {
        updateData(null);
    }

    //----------ISDAdapter implements end----------

    // util method
    @SuppressWarnings("unchecked")
    public static <V extends View> V get(int id, View convertView)
    {
        SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
        if (viewHolder == null)
        {
            viewHolder = new SparseArray<View>();
            convertView.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null)
        {
            childView = convertView.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (V) childView;
    }
}
