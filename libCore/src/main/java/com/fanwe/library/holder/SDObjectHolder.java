package com.fanwe.library.holder;

/**
 * 强引用对象持有者
 *
 * @param <T>
 */
class SDObjectHolder<T> implements ISDObjectHolder<T>
{
    private T mObject;

    @Override
    public T get()
    {
        return mObject;
    }

    @Override
    public void set(T object)
    {
        this.mObject = object;
    }

    @Override
    public boolean isEmpty()
    {
        return get() == null;
    }
}
