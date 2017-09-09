package com.fanwe.library.handler;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public abstract class OnActivityResultHandler
{

	protected FragmentActivity mActivity;
	protected Fragment mFragment;

	public OnActivityResultHandler(FragmentActivity mActivity)
	{
		super();
		this.mActivity = mActivity;
	}

	public OnActivityResultHandler(Fragment mFragment)
	{
		super();
		this.mFragment = mFragment;
		if (mFragment != null)
		{
			this.mActivity = mFragment.getActivity();
		}
	}

	protected void startActivity(Intent intent)
	{
		if (mFragment != null)
		{
			mFragment.startActivity(intent);
		} else
		{
			if (mActivity != null)
			{
				mActivity.startActivity(intent);
			}
		}
	}

	protected void startActivityForResult(Intent intent, int requestCode)
	{
		if (mFragment != null)
		{
			mFragment.startActivityForResult(intent, requestCode);
		} else
		{
			if (mActivity != null)
			{
				mActivity.startActivityForResult(intent, requestCode);
			}
		}
	}

	public abstract void onActivityResult(int requestCode, int resultCode, Intent data);

}
