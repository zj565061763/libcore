package com.fanwe.library.view;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/4/21.
 */
public interface ISDViewContainer
{
    /**
     * 把view添加到容器
     *
     * @param view 要添加的view
     */
    void addView(View view);

    /**
     * 把view添加到某个容器
     *
     * @param parentId 容器id
     * @param view     要添加的view
     */
    void addView(int parentId, View view);

    /**
     * 把view从它的容器上移除
     *
     * @param view 要移除的view
     */
    void removeView(View view);

    /**
     * 把view从它的容器上移除
     *
     * @param viewId 要移除的viewId
     * @return 移除的view
     */
    View removeView(int viewId);

    /**
     * 把view替换到某个容器
     *
     * @param parentId 容器id
     * @param child    要替换到容器上的view
     */
    void replaceView(int parentId, View child);

    /**
     * 把view替换到某个容器
     *
     * @param parent 容器
     * @param child  要替换到容器上的view
     */
    void replaceView(ViewGroup parent, View child);

    /**
     * 切换容器的内容为view<br>
     * 如果child没有被添加到parent，child将会被添加到parent，最终只显示child，隐藏parent的所有其他child
     *
     * @param parentId parent容器id
     * @param child
     */
    void toggleView(int parentId, View child);

    /**
     * 切换容器的内容为view<br>
     * 如果child没有被添加到parent，child将会被添加到parent，最终只显示child，隐藏parent的所有其他child
     *
     * @param parent
     * @param child
     */
    void toggleView(ViewGroup parent, View child);
}
