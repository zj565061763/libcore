package com.fanwe.library.utils;

import android.text.TextUtils;

public class SDOtherUtil
{
    public static String hideMobile(String mobile)
    {
        String result = null;
        if (!TextUtils.isEmpty(mobile) && TextUtils.isDigitsOnly(mobile) && mobile.length() == 11)
        {
            result = mobile.substring(0, 3) + "****" + mobile.substring(7);
        }
        return result;
    }
}
