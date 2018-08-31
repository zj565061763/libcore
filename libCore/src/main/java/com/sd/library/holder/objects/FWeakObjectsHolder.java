package com.sd.library.holder.objects;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 弱引用对象holder
 *
 * @param <T>
 */
public class FWeakObjectsHolder<T> implements ObjectsHolder<T>
{
    private final List<WeakReference<T>> mListObject;
    private final ReferenceQueue<T> mQueue = new ReferenceQueue<>();

    public FWeakObjectsHolder(List<WeakReference<T>> list)
    {
        if (list == null)
            list = new CopyOnWriteArrayList<>();

        mListObject = list;
    }

    @Override
    public boolean add(T object)
    {
        if (object == null || contains(object))
            return false;

        mListObject.add(new WeakReference<>(object, mQueue));
        return true;
    }

    @Override
    public boolean remove(Object object)
    {
        if (object == null)
            return false;

        releaseWeakReferenceIfNeed();
        int index = -1;
        for (WeakReference<T> item : mListObject)
        {
            index++;
            if (object.equals(item.get()))
                break;
        }
        mListObject.remove(index);
        return index >= 0;
    }

    @Override
    public boolean contains(T object)
    {
        if (object == null)
            return false;

        releaseWeakReferenceIfNeed();
        for (WeakReference<T> item : mListObject)
        {
            if (object.equals(item.get()))
                return true;
        }
        return false;
    }

    @Override
    public int size()
    {
        releaseWeakReferenceIfNeed();
        return mListObject.size();
    }

    @Override
    public void clear()
    {
        releaseWeakReferenceIfNeed();
        mListObject.clear();
    }

    @Override
    public Object foreach(ForeachCallback<T> callback)
    {
        if (callback == null)
            return null;

        releaseWeakReferenceIfNeed();
        for (WeakReference<T> item : mListObject)
        {
            if (callback.next(item.get()))
                break;
        }
        return callback.getData();
    }

    @Override
    public Object foreachReverse(ForeachCallback<T> callback)
    {
        if (callback == null)
            return null;

        releaseWeakReferenceIfNeed();
        final ListIterator<WeakReference<T>> it = mListObject.listIterator(mListObject.size());
        while (it.hasPrevious())
        {
            if (callback.next(it.previous().get()))
                break;
        }
        return callback.getData();
    }

    @Override
    public String getObjectsString()
    {
        final List<T> list = new ArrayList<>();

        releaseWeakReferenceIfNeed();
        for (WeakReference<T> item : mListObject)
        {
            list.add(item.get());
        }
        return list.toString();
    }

    private void releaseWeakReferenceIfNeed()
    {
        while (true)
        {
            final Reference<? extends T> item = mQueue.poll();
            if (item == null)
                break;
            else
                mListObject.remove(item);
        }
    }
}
