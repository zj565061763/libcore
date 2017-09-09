package com.fanwe.library.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.fanwe.library.fragment.SDBaseFragment;

public abstract class SDFragmentStatePagerAdapter<T> extends FragmentStatePagerAdapter
{

	protected List<T> mListModel = new ArrayList<T>();
	protected LayoutInflater mInflater;
	protected Activity mActivity;
	protected FragmentManager mFragmentManager;

	public SDFragmentStatePagerAdapter(List<T> listModel, Activity activity, FragmentManager fm)
	{
		super(fm);
		setData(listModel);
		this.mActivity = activity;
		this.mFragmentManager = fm;
		this.mInflater = mActivity.getLayoutInflater();
	}

	public void updateData(List<T> listModel)
	{
		setData(listModel);
		this.notifyDataSetChanged();
	}

	public List<T> getData()
	{
		return mListModel;
	}

	public void setData(List<T> listModel)
	{
		if (listModel != null)
		{
			this.mListModel = listModel;
		} else
		{
			this.mListModel = new ArrayList<T>();
		}
	}

	public T getItemModel(int position)
	{
		if (mListModel != null && position >= 0 && mListModel.size() > position)
		{
			return mListModel.get(position);
		} else
		{
			return null;
		}
	}

	@Override
	public int getItemPosition(Object object)
	{
		return POSITION_NONE;
	}

	@Override
	public Fragment getItem(int position)
	{
		return getItemFragment(position, getItemModel(position));
	}

	public abstract Fragment getItemFragment(int position, T model);

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		if (object instanceof SDBaseFragment)
		{
			SDBaseFragment fragment = (SDBaseFragment) object;
			fragment.setIsRemovedFromViewPager(true);
		}
		super.destroyItem(container, position, object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		return super.instantiateItem(container, position);
	}

	@Override
	public int getCount()
	{
		if (mListModel != null)
		{
			return mListModel.size();
		} else
		{
			return 0;
		}
	}

}
