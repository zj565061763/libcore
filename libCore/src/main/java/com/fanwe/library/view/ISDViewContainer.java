package com.fanwe.library.view;

import android.view.View;

/**
 * Created by Administrator on 2017/12/6.
 */
public interface ISDViewContainer
{
    /**
     * 把view添加到某个容器
     *
     * @param parentId 容器id
     * @param child    要添加的view
     */
    void addView(int parentId, View child);

    /**
     * 把view替换到某个容器
     *
     * @param parentId 容器id
     * @param child    要替换到容器上的view
     */
    void replaceView(int parentId, View child);

    /**
     * 切换容器的内容为view<br>
     * 如果child没有被添加到parent，child将会被添加到parent，最终只显示child，隐藏parent的所有其他child
     *
     * @param parentId parent容器id
     * @param child
     */
    void toggleView(int parentId, View child);
}
