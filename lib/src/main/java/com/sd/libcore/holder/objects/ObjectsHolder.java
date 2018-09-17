package com.sd.libcore.holder.objects;

/**
 * 对象holder
 *
 * @param <T>
 */
public interface ObjectsHolder<T>
{
    /**
     * 添加对象
     *
     * @param object
     * @return true-调用此方法后对象被添加了
     */
    boolean add(T object);

    /**
     * 移除对象
     *
     * @param object
     * @return true-调用此方法后对象被移除了
     */
    boolean remove(Object object);

    /**
     * 是否包含某个对象
     *
     * @param object
     * @return true-包含
     */
    boolean contains(T object);

    /**
     * 当前保存对象的个数
     *
     * @return
     */
    int size();

    /**
     * 清空
     */
    void clear();

    /**
     * 正序遍历
     *
     * @param callback
     * @return 返回callback中的data {@link ForeachCallback#getData()}
     */
    Object foreach(ForeachCallback<T> callback);

    /**
     * 倒序遍历
     *
     * @param callback
     * @return 返回callback中的data {@link ForeachCallback#getData()}
     */
    Object foreachReverse(ForeachCallback<T> callback);

    abstract class ForeachCallback<T>
    {
        private Object mData;

        /**
         * 设置遍历的数据
         *
         * @param data
         */
        protected final void setData(Object data)
        {
            mData = data;
        }

        /**
         * 返回设置的数据
         *
         * @return
         */
        public final Object getData()
        {
            return mData;
        }

        /**
         * 遍历到每一个item的时候触发此方法
         *
         * @param item
         * @return true-停止遍历，false-继续遍历
         */
        protected abstract boolean next(T item);
    }
}
