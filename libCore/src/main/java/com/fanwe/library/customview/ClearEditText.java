package com.fanwe.library.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.fanwe.library.R;

public class ClearEditText extends EditText implements OnFocusChangeListener, TextWatcher
{
	private Drawable mDrawable;
	private boolean mHasFocus;
	private EnumMode mMode = EnumMode.CLEAR;
	private OnClickDrawableRightListener mListenerOnClickDrawableRight;
	private boolean mShowPassword = false;
	private int mInputTypeOriginal;

	private int mDrawableClearResId = R.drawable.selector_edt_delete;
	private int mDrawablePasswordOnResId = R.drawable.ic_eye_show_content_on;
	private int mDrawablePasswordOffResId = R.drawable.ic_eye_show_content_off;

	public void setmDrawableClearResId(int drawableClearResId)
	{
		this.mDrawableClearResId = drawableClearResId;
		updateDrawable();
	}

	public void setmDrawablePasswordOffResId(int drawablePasswordOffResId)
	{
		this.mDrawablePasswordOffResId = drawablePasswordOffResId;
		updateDrawable();
	}

	public void setmDrawablePasswordOnResId(int drawablePasswordOnResId)
	{
		this.mDrawablePasswordOnResId = drawablePasswordOnResId;
		updateDrawable();
	}

	public void setmMode(EnumMode mode)
	{
		if (mode != null)
		{
			this.mMode = mode;
			updateDrawable();
		}
	}

	public void setmListenerOnClickDrawableRight(OnClickDrawableRightListener listenerOnClickDrawableRight)
	{
		this.mListenerOnClickDrawableRight = listenerOnClickDrawableRight;
	}

	public ClearEditText(Context context)
	{
		this(context, null);
	}

	public ClearEditText(Context context, AttributeSet attrs)
	{
		this(context, attrs, android.R.attr.editTextStyle);
	}

	public ClearEditText(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}

	public void updateDrawable()
	{
		switch (mMode)
		{
		case NORMAL:

			break;
		case CLEAR:
			mDrawable = getResources().getDrawable(mDrawableClearResId);
			break;
		case PASSWORD:
			if (mShowPassword)
			{
				mDrawable = getResources().getDrawable(mDrawablePasswordOnResId);
			} else
			{
				mDrawable = getResources().getDrawable(mDrawablePasswordOffResId);
			}
			break;

		default:
			break;
		}

		if (mDrawable != null)
		{
			mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
		}

		whetherDrawableRightVisible();
	}

	private void init()
	{
		mInputTypeOriginal = getInputType();
		updateDrawable();
		setOnFocusChangeListener(this);
		addTextChangedListener(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_UP)
		{
			if (getCompoundDrawables()[2] != null)
			{

				boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight()) && (event.getX() < ((getWidth() - getPaddingRight())));

				if (touchable)
				{
					notifyListener();
				}
			}
		}

		return super.onTouchEvent(event);
	}

	private void notifyListener()
	{
		if (mListenerOnClickDrawableRight != null)
		{
			if (mListenerOnClickDrawableRight.onClick(this))
			{
				return;
			}
		}
		switch (mMode)
		{
		case NORMAL:

			break;
		case CLEAR:
			this.setText("");
			break;
		case PASSWORD:
			clickModePassword();
			break;

		default:
			break;
		}
	}

	private void clickModePassword()
	{
		int inputType = getInputType();
		if (inputType == InputType.TYPE_CLASS_TEXT)
		{
			inputType = mInputTypeOriginal;
			mShowPassword = false;
		} else
		{
			inputType = InputType.TYPE_CLASS_TEXT;
			mShowPassword = true;
		}
		setInputType(inputType);
		setSelection(getText().toString().length());
		updateDrawable();
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus)
	{
		this.mHasFocus = hasFocus;
		whetherDrawableRightVisible();
	}

	protected void whetherDrawableRightVisible()
	{
		if (mMode != EnumMode.NORMAL)
		{
			if (mHasFocus)
			{
				setDrawableRightVisible(getText().length() > 0);
			} else
			{
				setDrawableRightVisible(false);
			}
		}
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		whetherDrawableRightVisible();
	}

	protected void setDrawableRightVisible(boolean visible)
	{
		Drawable right = null;
		if (isEnabled() && visible)
		{
			right = mDrawable;
		} else
		{
			right = null;
		}
		setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int count, int after)
	{
		whetherDrawableRightVisible();
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after)
	{

	}

	@Override
	public void afterTextChanged(Editable s)
	{

	}

	public void setShakeAnimation()
	{
		this.setAnimation(shakeAnimation(5));
	}

	public static Animation shakeAnimation(int counts)
	{
		Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
		translateAnimation.setInterpolator(new CycleInterpolator(counts));
		translateAnimation.setDuration(1000);
		return translateAnimation;
	}

	public enum EnumMode
	{
		NORMAL, CLEAR, PASSWORD;
	}

	public interface OnClickDrawableRightListener
	{
		public boolean onClick(View v);
	}

}
