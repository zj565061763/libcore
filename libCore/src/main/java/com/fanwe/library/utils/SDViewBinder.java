package com.fanwe.library.utils;

import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class SDViewBinder
{

    public static void setRatingBar(RatingBar ratingBar, float rating)
    {
        ratingBar.setRating(rating);
    }

    /**
     * 设置textView内容
     *
     * @param textView
     * @param content
     * @param emptyTip 当content为空的时候需要显示的内容
     * @return
     */
    public static boolean setTextView(TextView textView, CharSequence content, CharSequence emptyTip)
    {
        if (!TextUtils.isEmpty(content))
        {
            textView.setText(content);
            return true;
        } else
        {
            if (!TextUtils.isEmpty(emptyTip))
            {
                textView.setText(emptyTip);
            } else
            {
                textView.setText("");
            }
            return false;
        }
    }

    public static boolean setTextView(TextView textView, CharSequence content)
    {
        return setTextView(textView, content, null);
    }

    public static boolean setTextViewHtml(TextView textView, CharSequence contentHtml)
    {
        return setTextViewHtml(textView, contentHtml, null);
    }

    public static boolean setTextViewHtml(TextView textView, CharSequence contentHtml, String emptyTip)
    {
        if (!TextUtils.isEmpty(contentHtml))
        {
            contentHtml = Html.fromHtml(String.valueOf(contentHtml));
        }
        return setTextView(textView, contentHtml, emptyTip);
    }

    /**
     * 设置textView内容，并且如果内容不为空的话显示textView，内容为空隐藏textView
     *
     * @param textView
     * @param content
     */
    public static void setTextViewVisibleOrGone(TextView textView, CharSequence content)
    {
        if (TextUtils.isEmpty(content))
        {
            SDViewUtil.setGone(textView);
        } else
        {
            textView.setText(content);
            SDViewUtil.setVisible(textView);
        }
    }

    /**
     * 设置imageView图片id，并且如果id大于0的话显示imageView，id小于等于0隐藏imageView
     *
     * @param imageView
     * @param resId
     */
    public static void setImageViewVisibleOrGone(ImageView imageView, int resId)
    {
        if (resId <= 0)
        {
            SDViewUtil.setGone(imageView);
        } else
        {
            imageView.setImageResource(resId);
            SDViewUtil.setVisible(imageView);
        }
    }
}
