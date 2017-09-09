package com.fanwe.library.customview;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * 用com.fanwe.library.view.SDFullRecyclerView替代
 */
@Deprecated
public class SDGridLinearLayout extends LinearLayout
{
    private BaseAdapter adapter;
    private int colNumber = 1;
    private int totalWidth;
    private int avalibleWidth;
    private int rowNumber;
    /**
     * 是否以正方形布局
     */
    private boolean square = false;
    private ItemClickListener itemClickListener;

    private int verticalStrokeWidth;
    private int horizontalStrokeWidth;

    private int verticalStrokeColor;
    private int horizontalStrokeColor;

    public void setHorizontalStrokeColor(int horizontalStrokeColor)
    {
        this.horizontalStrokeColor = horizontalStrokeColor;
    }

    public void setVerticalStrokeColor(int verticalStrokeColor)
    {
        this.verticalStrokeColor = verticalStrokeColor;
    }

    public void setHorizontalStrokeWidth(int horizontalStrokeWidth)
    {
        this.horizontalStrokeWidth = horizontalStrokeWidth;
    }

    public void setVerticalStrokeWidth(int verticalStrokeWidth)
    {
        this.verticalStrokeWidth = verticalStrokeWidth;
    }

    public SDGridLinearLayout(Context context)
    {
        this(context, null);
    }

    public SDGridLinearLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private void init()
    {
        setOrientation(VERTICAL);
    }

    /**
     * 是否以正方形布局
     *
     * @param square
     */
    public void setSquare(boolean square)
    {
        this.square = square;
    }

    public boolean isSquare()
    {
        return square;
    }

    public void setItemClickListener(ItemClickListener listenerOnItemClick)
    {
        this.itemClickListener = listenerOnItemClick;
    }

    public int getColWidth()
    {
        return getAvalibleWidth() / colNumber;
    }

    private ItemParamsCreater itemParamsCreater = new ItemParamsCreater()
    {

        @Override
        public LayoutParams create(View itemView, int index)
        {
            int width = getColWidth();
            int height = LayoutParams.MATCH_PARENT;
            if (square)
            {
                height = width;
            }
            LayoutParams params = new LayoutParams(width, height);
            return params;
        }
    };

    private StrokeCreater strokeCreater = new StrokeCreater()
    {

        @Override
        public View createVertical()
        {
            View view = null;
            if (verticalStrokeWidth > 0)
            {
                view = new View(getContext());
                LayoutParams params = new LayoutParams(verticalStrokeWidth, LayoutParams.MATCH_PARENT);
                view.setLayoutParams(params);
                if (verticalStrokeColor != 0)
                {
                    view.setBackgroundColor(verticalStrokeColor);
                }
            }
            return view;
        }

        @Override
        public View createHorizontal()
        {
            View view = null;
            if (horizontalStrokeWidth > 0)
            {
                view = new View(getContext());
                LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, horizontalStrokeWidth);
                view.setLayoutParams(params);
                if (horizontalStrokeColor != 0)
                {
                    view.setBackgroundColor(horizontalStrokeColor);
                }
            }
            return view;
        }
    };

    public void setItemParamsCreater(ItemParamsCreater itemParamsCreater)
    {
        if (itemParamsCreater != null)
        {
            this.itemParamsCreater = itemParamsCreater;
        }
    }

    public void setStrokeCreater(StrokeCreater strokeCreater)
    {
        if (strokeCreater != null)
        {
            this.strokeCreater = strokeCreater;
        }
    }

    public void setTotalWidth(int totalWidth)
    {
        if (totalWidth > 0)
        {
            this.totalWidth = totalWidth;
        }
    }

    public int getTotalWidth()
    {
        return totalWidth;
    }

    public int getAvalibleWidth()
    {
        return avalibleWidth;
    }

    public void setColNumber(int colNumber)
    {
        if (colNumber >= 1)
        {
            this.colNumber = colNumber;
        }
    }

    public int getColNumber()
    {
        return colNumber;
    }

    public int getRowNumber()
    {
        return rowNumber;
    }

    public void setAdapter(BaseAdapter adapter)
    {
        this.adapter = adapter;
        this.adapter.registerDataSetObserver(new DataSetObserver()
        {
            @Override
            public void onChanged()
            {
                notifyDataSetChanged();
            }
        });
        notifyDataSetChanged();
    }

    public BaseAdapter getAdapter()
    {
        return adapter;
    }

    private boolean needCreate;

    private void setNeedCreate(boolean needCreate)
    {
        if (needCreate)
        {
            if (adapter != null)
            {
                this.needCreate = true;
            }
        } else
        {
            this.needCreate = false;
        }
    }

    public void notifyDataSetChanged()
    {
        setNeedCreate(true);
        post(new Runnable()
        {
            @Override
            public void run()
            {
                create();
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
        if (needCreate)
        {
            create();
        }
    }

    private void initParams()
    {
        totalWidth = getWidth();

        int totalVerticalStrokeWidth = 0;
        if (totalWidth > 0 && adapter != null && strokeCreater != null && colNumber > 1)
        {
            // 计算竖分割线总宽度
            totalVerticalStrokeWidth = (colNumber - 1) * verticalStrokeWidth;
        }

        avalibleWidth = totalWidth - totalVerticalStrokeWidth;
    }

    protected void create()
    {
        if (adapter == null)
        {
            return;
        }

        initParams();

        if (totalWidth <= 0)
        {
            return;
        }

        this.removeAllViews();

        int count = adapter.getCount();
        if (count % colNumber == 0)
        {
            rowNumber = count / colNumber;
        } else
        {
            rowNumber = count / colNumber + 1;
        }

        if (count > 0)
        {
            LinearLayout llRow = null;
            int column = -1;
            int row = -1;
            for (int i = 0; i < count; i++)
            {
                if (i % colNumber == 0)
                {
                    column = 0;
                    row++;

                    // 添加横分割线
                    if (row > 0 && row < rowNumber)
                    {
                        View strokeHor = strokeCreater.createHorizontal();
                        if (strokeHor != null)
                        {
                            this.addView(strokeHor);
                        }
                    }

                    // 添加新的一行
                    llRow = createRowLinearLayout();
                    this.addView(llRow);
                }

                // 添加竖直分割线
                if (column > 0 && column < colNumber)
                {
                    View strokeVer = strokeCreater.createVertical();
                    if (strokeVer != null)
                    {
                        llRow.addView(strokeVer);
                    }
                }

                View itemView = adapter.getView(i, null, this);
                LayoutParams params = itemParamsCreater.create(itemView, i);
                itemView.setLayoutParams(params);
                whetherRegisterClick(i, itemView);
                wrapperItemView(i, itemView);
                llRow.addView(itemView);
                column++;
            }
        }

        setNeedCreate(false);
    }

    protected void wrapperItemView(int i, View itemView)
    {

    }

    private void whetherRegisterClick(final int i, final View itemView)
    {
        if (itemClickListener != null)
        {
            itemView.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    itemClickListener.onItemClick(i, itemView, SDGridLinearLayout.this);
                }
            });
        }
    }

    private LinearLayout createRowLinearLayout()
    {
        LinearLayout llRow = new LinearLayout(getContext());
        llRow.setOrientation(LinearLayout.HORIZONTAL);
        llRow.setGravity(Gravity.CENTER_VERTICAL);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        llRow.setLayoutParams(params);

        return llRow;
    }

    public interface ItemParamsCreater
    {
        LayoutParams create(View itemView, int index);
    }

    public interface StrokeCreater
    {
        View createHorizontal();

        View createVertical();
    }

    public interface ItemClickListener
    {
        void onItemClick(int position, View view, ViewGroup parent);
    }

}
