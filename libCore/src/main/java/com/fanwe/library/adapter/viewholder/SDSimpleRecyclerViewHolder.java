package com.fanwe.library.adapter.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2017/4/13.
 */

public abstract class SDSimpleRecyclerViewHolder<T> extends SDRecyclerViewHolder<T>
{
    public SDSimpleRecyclerViewHolder(Context context)
    {
        super(new RecyclerItemFrameLayout(context));
        RecyclerItemFrameLayout frameLayout = (RecyclerItemFrameLayout) itemView;
        frameLayout.setContentView(getLayoutId());
    }

    /**
     * 返回布局id
     *
     * @return
     */
    public abstract int getLayoutId();


    public static class RecyclerItemFrameLayout extends FrameLayout
    {
        private View realView;

        public RecyclerItemFrameLayout(@NonNull Context context)
        {
            super(context);
        }

        public View setContentView(int layoutId)
        {
            LayoutInflater.from(getContext()).inflate(layoutId, this, true);
            realView = getChildAt(0);

            ViewGroup.LayoutParams childParams = realView.getLayoutParams();
            if (childParams != null)
            {
                ViewGroup.LayoutParams newParams = new ViewGroup.LayoutParams(childParams);
                setLayoutParams(newParams);
            }
            return realView;
        }

        public View getRealView()
        {
            return realView;
        }
    }

}
