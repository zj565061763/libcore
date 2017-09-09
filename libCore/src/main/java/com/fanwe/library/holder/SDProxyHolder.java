package com.fanwe.library.holder;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理对象持有者
 *
 * @param <T>
 */
public class SDProxyHolder<T> extends SDObjectHolder<T> implements InvocationHandler
{
    private Class<T> mClass;
    private T mProxy;

    private WeakReference mChild;

    public SDProxyHolder(Class<T> clazz)
    {
        this.mClass = clazz;
    }

    /**
     * 创建代理
     *
     * @param clazz   要创建代理的接口
     * @param handler
     * @return
     */
    public static <P> P newProxyInstance(Class<P> clazz, InvocationHandler handler)
    {
        return (P) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, handler);
    }

    /**
     * 获得内部持有的代理对象
     *
     * @return
     */
    protected T getProxy()
    {
        if (mProxy == null)
        {
            mProxy = newProxyInstance(mClass, this);
        }
        return mProxy;
    }

    /**
     * 返回的是代理对象
     *
     * @return
     */
    @Override
    public T get()
    {
        return getProxy();
    }

    /**
     * 设置child对象<br>
     * 当内部代理对象方法被触发的时候会先通知内部真实对象的方法，然后再通知child对象内部持有对象的方法
     *
     * @param child
     * @param <C>
     */
    public <C extends T> void notify(SDProxyHolder<C> child)
    {
        if (this == child)
        {
            throw new IllegalArgumentException("child should not be current instance");
        }
        if (getChild() != child)
        {
            if (child != null)
            {
                mChild = new WeakReference<>(child);
            } else
            {
                mChild = null;
            }
        }
    }

    /**
     * 获得child对象
     *
     * @return
     */
    protected SDProxyHolder<T> getChild()
    {
        if (mChild == null)
        {
            return null;
        } else
        {
            return (SDProxyHolder<T>) mChild.get();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        final Object object = super.get();
        final SDProxyHolder child = getChild();

        if (object != null)
        {
            //先触发内部持有对象，再触发child中的持有对象
            Object realResult = method.invoke(object, args);
            if (child != null)
            {
                method.invoke(child.get(), args);
            }
            return realResult;
        } else
        {
            if (child != null)
            {
                return method.invoke(child.get(), args);
            } else
            {
                return null;
            }
        }
    }
}
