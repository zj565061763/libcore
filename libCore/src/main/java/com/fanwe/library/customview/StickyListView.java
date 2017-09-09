package com.fanwe.library.customview;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ListView;

public class StickyListView extends ListView
{

	/** 是否sticky该view */
	public static final String STICKY_TAG = "sticky";

	/** 是否需要一直绘制stickyview，当该view被sticky的时候 */
	public static final String FLAG_NONCONSTANT = "-nonconstant";

	/** 是否需要在view被sticky的时候隐藏原来的view */
	public static final String FLAG_HASTRANSPARANCY = "-hastransparancy";

	private ArrayList<View> stickyViews;
	private View currentlyStickingView;

	/** stickyview顶部超出listview顶部的距离(小于等于0) */
	private float stickyViewTopOffset;

	private boolean redirectTouchesToStickyView;

	private boolean clippingToPadding;
	private boolean clipToPaddingHasBeenSet;

	// add
	private View mFirstHeaderView;

	private final Runnable invalidateRunnable = new Runnable()
	{

		public void run()
		{
			if (currentlyStickingView != null)
			{
				int l = getLeftToRoot(currentlyStickingView);
				int t = 0;
				int r = l + currentlyStickingView.getWidth();
				int b = (int) ((currentlyStickingView.getHeight() + stickyViewTopOffset));
				invalidate(l, t, r, b);
			}
			postDelayed(this, 16);
		}
	};

	public StickyListView(Context context)
	{
		this(context, null);
	}

	public StickyListView(Context context, AttributeSet attrs)
	{
		this(context, attrs, android.R.attr.scrollViewStyle);
	}

	public StickyListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		setup();
	}

	public void setup()
	{
		stickyViews = new ArrayList<View>();
	}

	protected int getLeftToRoot(View v)
	{
		return getViewXOnScreen(v) - getViewXOnScreen(mFirstHeaderView);
	}

	protected int getRightToRoot(View v)
	{
		return getLeftToRoot(v) + v.getWidth();
	}

	protected int getTopToRoot(View v)
	{
		return getViewYOnScreen(v) - getViewYOnScreen(mFirstHeaderView);
	}

	protected int getBottomToRoot(View v)
	{
		return getTopToRoot(v) + v.getHeight();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout(changed, l, t, r, b);
		if (!clipToPaddingHasBeenSet)
		{
			clippingToPadding = true;
		}
		// notifyHierarchyChanged();
	}

	@Override
	public void setClipToPadding(boolean clipToPadding)
	{
		super.setClipToPadding(clipToPadding);
		clippingToPadding = clipToPadding;
		clipToPaddingHasBeenSet = true;
	}

	@Override
	public void addHeaderView(View v, Object data, boolean isSelectable)
	{
		super.addHeaderView(v, data, isSelectable);
		findStickyViews(v);
	}

	@Override
	protected void dispatchDraw(Canvas canvas)
	{
		super.dispatchDraw(canvas);
		if (currentlyStickingView != null)
		{
			canvas.save();

			float dx = getPaddingLeft();
			float dy = (clippingToPadding ? stickyViewTopOffset : 0);

			float left = 0;
			float top = (clippingToPadding ? -stickyViewTopOffset : 0);
			float right = getWidth();
			float bottom = currentlyStickingView.getHeight();

			canvas.translate(dx, dy);
			canvas.clipRect(left, top, right, bottom);

			if (getStringTagForView(currentlyStickingView).contains(FLAG_HASTRANSPARANCY))
			{
				showView(currentlyStickingView);
				currentlyStickingView.draw(canvas);
				hideView(currentlyStickingView);
			} else
			{
				currentlyStickingView.draw(canvas);
			}
			canvas.restore();
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		if (ev.getAction() == MotionEvent.ACTION_DOWN)
		{
			redirectTouchesToStickyView = true;
		}

		if (redirectTouchesToStickyView)
		{
			redirectTouchesToStickyView = currentlyStickingView != null;
			if (redirectTouchesToStickyView)
			{
				redirectTouchesToStickyView = ev.getY() <= (currentlyStickingView.getHeight() + stickyViewTopOffset)
						&& ev.getX() >= getViewXOnScreen(currentlyStickingView)
						&& ev.getX() <= getViewXOnScreen(currentlyStickingView) + currentlyStickingView.getWidth();
			}
		} else if (currentlyStickingView == null)
		{
			redirectTouchesToStickyView = false;
		}
		if (redirectTouchesToStickyView)
		{
			currentlyStickingView.dispatchTouchEvent(ev);
			return true;
		}
		return super.dispatchTouchEvent(ev);
	}

	private boolean hasNotDoneActionDown = true;

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		if (redirectTouchesToStickyView)
		{
			currentlyStickingView.dispatchTouchEvent(ev);
			return true;
		}

		if (ev.getAction() == MotionEvent.ACTION_DOWN)
		{
			hasNotDoneActionDown = false;
		}

		if (hasNotDoneActionDown)
		{
			MotionEvent down = MotionEvent.obtain(ev);
			down.setAction(MotionEvent.ACTION_DOWN);
			super.onTouchEvent(down);
			hasNotDoneActionDown = false;
		}

		if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL)
		{
			hasNotDoneActionDown = true;
		}

		return super.onTouchEvent(ev);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt)
	{
		super.onScrollChanged(l, t, oldl, oldt);
		doTheStickyThing();
	}

	public static int getViewXOnScreen(View view)
	{
		int[] location = new int[2];
		if (view != null)
		{
			view.getLocationOnScreen(location);
		}
		return location[0];
	}

	public static int getViewYOnScreen(View view)
	{
		int[] location = new int[2];
		if (view != null)
		{
			view.getLocationOnScreen(location);
		}
		return location[1];
	}

	public int getScrollYFixed()
	{
		int value = 0;
		if (mFirstHeaderView != null)
		{
			value = getViewYOnScreen(this) - getViewYOnScreen(mFirstHeaderView);
		}
		return value;
	}

	public int getScrollYStickyView()
	{
		int value = 0;
		if (currentlyStickingView != null)
		{
			value = getViewYOnScreen(this) - getViewYOnScreen(currentlyStickingView);
		}
		return value;
	}

	private void doTheStickyThing()
	{
		View viewThatShouldStick = null;
		View approachingView = null;
		for (View v : stickyViews)
		{
			int viewTop = getViewYOnScreen(v) - getViewYOnScreen(this) + (clippingToPadding ? 0 : getPaddingTop());
			if (viewTop <= 0)
			{
				if (viewThatShouldStick == null)
				{
					viewThatShouldStick = v;
				} else
				{
					int viewTopSticky = getViewYOnScreen(viewThatShouldStick) - getViewYOnScreen(this) + (clippingToPadding ? 0 : getPaddingTop());
					if (viewTop > viewTopSticky)
					{
						viewThatShouldStick = v;
					}
				}
			} else
			{
				if (approachingView == null)
				{
					approachingView = v;
				} else
				{
					int viewTopApproaching = getViewYOnScreen(approachingView) - getViewYOnScreen(this) + (clippingToPadding ? 0 : getPaddingTop());
					if (viewTop < viewTopApproaching)
					{
						approachingView = v;
					}
				}
			}
		}
		if (viewThatShouldStick != null)
		{
			if (approachingView == null)
			{
				stickyViewTopOffset = 0;
			} else
			{
				stickyViewTopOffset = Math.min(0, getViewYOnScreen(approachingView) - getViewYOnScreen(this)
						+ (clippingToPadding ? 0 : getPaddingTop()) - viewThatShouldStick.getHeight());
			}
			if (viewThatShouldStick != currentlyStickingView)
			{
				if (currentlyStickingView != null)
				{
					stopStickingCurrentlyStickingView();
				}
				startStickingView(viewThatShouldStick);
			}
		} else if (currentlyStickingView != null)
		{
			stopStickingCurrentlyStickingView();
		}
	}

	private void startStickingView(View viewThatShouldStick)
	{
		currentlyStickingView = viewThatShouldStick;
		if (getStringTagForView(currentlyStickingView).contains(FLAG_HASTRANSPARANCY))
		{
			hideView(currentlyStickingView);
		}
		if (((String) currentlyStickingView.getTag()).contains(FLAG_NONCONSTANT))
		{
			post(invalidateRunnable);
		}
	}

	private void stopStickingCurrentlyStickingView()
	{
		if (getStringTagForView(currentlyStickingView).contains(FLAG_HASTRANSPARANCY))
		{
			showView(currentlyStickingView);
		}
		currentlyStickingView = null;
		removeCallbacks(invalidateRunnable);
	}

	/**
	 * Notify that the sticky attribute has been added or removed from one or
	 * more views in the View hierarchy
	 */
	public void notifyStickyAttributeChanged()
	{
		notifyHierarchyChanged();
	}

	private void notifyHierarchyChanged()
	{
		if (currentlyStickingView != null)
		{
			stopStickingCurrentlyStickingView();
		}
		mFirstHeaderView = null;
		stickyViews.clear();
		int headerViewCount = getHeaderViewsCount();
		if (headerViewCount > 0)
		{
			for (int i = 0; i < headerViewCount; i++)
			{
				findStickyViews(getChildAt(i));
			}
			doTheStickyThing();
			invalidate();
		}
	}

	private void findStickyViews(View v)
	{
		if (mFirstHeaderView == null)
		{
			mFirstHeaderView = v;
		}
		if (v instanceof ViewGroup)
		{
			ViewGroup vg = (ViewGroup) v;
			for (int i = 0; i < vg.getChildCount(); i++)
			{
				String tag = getStringTagForView(vg.getChildAt(i));
				if (tag != null && tag.contains(STICKY_TAG))
				{
					stickyViews.add(vg.getChildAt(i));
				} else if (vg.getChildAt(i) instanceof ViewGroup)
				{
					findStickyViews(vg.getChildAt(i));
				}
			}
		} else
		{
			String tag = getStringTagForView(v);
			if (tag != null && tag.contains(STICKY_TAG))
			{
				stickyViews.add(v);
			}
		}
	}

	private String getStringTagForView(View v)
	{
		Object tagObject = v.getTag();
		return String.valueOf(tagObject);
	}

	@SuppressLint("NewApi")
	private void hideView(View v)
	{
		if (Build.VERSION.SDK_INT >= 11)
		{
			v.setAlpha(0);
		} else
		{
			AlphaAnimation anim = new AlphaAnimation(1, 0);
			anim.setDuration(0);
			anim.setFillAfter(true);
			v.startAnimation(anim);
		}
	}

	@SuppressLint("NewApi")
	private void showView(View v)
	{
		if (Build.VERSION.SDK_INT >= 11)
		{
			v.setAlpha(1);
		} else
		{
			AlphaAnimation anim = new AlphaAnimation(0, 1);
			anim.setDuration(0);
			anim.setFillAfter(true);
			v.startAnimation(anim);
		}
	}
}