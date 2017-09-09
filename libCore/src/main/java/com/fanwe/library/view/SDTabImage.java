package com.fanwe.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.fanwe.library.R;
import com.fanwe.library.view.select.SDSelectViewAuto;
@Deprecated
public class SDTabImage extends SDSelectViewAuto
{

	public ImageView mIv_image;

	public SDTabImage(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public SDTabImage(Context context)
	{
		super(context);
		init();
	}

	protected void init()
	{
		setContentView(R.layout.view_tab_image);
		mIv_image = (ImageView) findViewById(R.id.iv_image);
		addAutoView(mIv_image);
		
		onNormal();
	}

}
