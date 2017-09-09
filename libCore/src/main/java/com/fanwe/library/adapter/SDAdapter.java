package com.fanwe.library.adapter;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.listener.SDItemLongClickCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;

public abstract class SDAdapter<T> extends BaseAdapter implements
        ISDAdapter<T>,
        SDSelectManager.SelectCallback<T>,
        View.OnClickListener,
        SDItemClickCallback<T>
{

    private List<T> mListModel = new ArrayList<T>();
    private Activity mActivity;

    /**
     * 保存每个itemView对应的position
     */
    private Map<View, Integer> mMapViewPosition = new WeakHashMap<>();

    private SDSelectManager<T> mSelectManager;

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
    public SDSelectManager<T> getSelectManager()
    {
        if (mSelectManager == null)
        {
            mSelectManager = new SDSelectManager<>();
            mSelectManager.setMode(SDSelectManager.Mode.SINGLE);
            mSelectManager.addSelectCallback(this);
        }
        return mSelectManager;
    }

    @Override
    public void onNormal(int position, T item)
    {
        if (item instanceof SDSelectManager.Selectable)
        {
            SDSelectManager.Selectable selectable = (SDSelectManager.Selectable) item;
            selectable.setSelected(false);
        }
        updateData(position);
    }

    @Override
    public void onSelected(int position, T item)
    {
        if (item instanceof SDSelectManager.Selectable)
        {
            SDSelectManager.Selectable selectable = (SDSelectManager.Selectable) item;
            selectable.setSelected(true);
        }
        updateData(position);
    }

    @Override
    public void notifyDataSetChanged()
    {
        clearViews();
        super.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view)
    {
        if (mMapViewPosition.containsKey(view))
        {
            int position = mMapViewPosition.get(view);
            T model = getItem(position);
            onItemClick(position, model, view);
        }
    }

    @Override
    public void onItemClick(int position, T model, View view)
    {
        notifyItemClickCallback(position, model, view);
    }

    private void clearViews()
    {
        mMapViewPosition.clear();
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
        if (mMapViewPosition.size() <= 0)
        {
            return null;
        }

        List<View> listItem = new ArrayList<>();

        Set<Entry<View, Integer>> set = mMapViewPosition.entrySet();
        for (Entry<View, Integer> item : set)
        {
            if (Integer.valueOf(position).equals(item.getValue()))
            {
                View view = item.getKey();
                if (view != null && view.getParent() != null)
                {
                    listItem.add(view);
                }
            }
        }

        return listItem;
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
    public int indexOf(T t)
    {
        int index = -1;
        if (t != null)
        {
            index = mListModel.indexOf(t);
        }
        return index;
    }

    @Override
    public void updateData(List<T> listModel)
    {
        setData(listModel);
        notifyDataSetChanged();
    }

    @Override
    public void setData(List<T> list)
    {
        if (list != null)
        {
            this.mListModel = list;
        } else
        {
            this.mListModel.clear();
        }
        getSelectManager().setItems(mListModel);
        getSelectManager().synchronizeSelected();
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
            getSelectManager().synchronizeSelected(model);
            notifyDataSetChanged();
        }
    }

    @Override
    public void appendData(List<T> list)
    {
        if (list != null && list.size() > 0)
        {
            mListModel.addAll(list);
            getSelectManager().synchronizeSelected(list);
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
        T model = null;
        if (isPositionLegal(position))
        {
            model = mListModel.remove(position);
            getSelectManager().setSelected(position, false);
            notifyDataSetChanged();
        }
        return model;
    }

    @Override
    public void insertData(int position, T model)
    {
        if (model != null)
        {
            mListModel.add(position, model);
            getSelectManager().synchronizeSelected(model);
            notifyDataSetChanged();
        }
    }

    @Override
    public void insertData(int position, List<T> list)
    {
        if (list != null && !list.isEmpty())
        {
            mListModel.addAll(position, list);
            getSelectManager().synchronizeSelected(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public void updateData(int position, T model)
    {
        if (isPositionLegal(position))
        {
            mListModel.set(position, model);
            getSelectManager().synchronizeSelected(model);
            updateData(position);
        }
    }

    @Override
    public void updateData(int position)
    {
        List<View> listItem = getItemView(position);
        if (listItem != null && !listItem.isEmpty())
        {
            for (View itemView : listItem)
            {
                onUpdateView(position, itemView, (ViewGroup) itemView.getParent(), getItem(position));
            }
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
