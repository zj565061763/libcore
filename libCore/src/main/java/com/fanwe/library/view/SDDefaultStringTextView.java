package com.fanwe.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 显示默认字符串的TextView
 */
public abstract class SDDefaultStringTextView extends TextView
{
    public static final String BEFORE = "before";
    public static final String AFTER = "after";

    public SDDefaultStringTextView(Context context)
    {
        super(context);
        init();
    }

    public SDDefaultStringTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SDDefaultStringTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        String defaultText = getDefaultText();
        String text = getText().toString();

        String result = buildDefaultString(defaultText, text, BEFORE, AFTER);

        setText(result);
    }

    public static String buildDefaultString(String defaultText, String text, String before, String after)
    {
        String result = null;
        if (text.startsWith(before))
        {
            if (text.contains(after))
            {
                String removedBefore = text.replaceFirst(before, "");
                String start = removedBefore.substring(0, removedBefore.indexOf(after));
                String end = removedBefore.substring(removedBefore.indexOf(after) + after.length());

                result = start + defaultText + end;
            } else
            {
                result = text.replaceFirst(before, "") + defaultText;
            }
        } else if (text.startsWith(after))
        {
            result = defaultText + text.replaceFirst(after, "");
        } else
        {
            result = defaultText + text;
        }
        return result;
    }

    protected abstract String getDefaultText();

}
