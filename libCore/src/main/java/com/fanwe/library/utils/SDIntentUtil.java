package com.fanwe.library.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;

import com.fanwe.library.SDLibrary;

import java.io.File;
import java.util.List;

public class SDIntentUtil
{

    public static Intent getIntentAppSetting(Context context)
    {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        return intent;
    }

    public static Intent getIntentQQChat(String qqNumber)
    {
        Intent intent = null;
        if (!TextUtils.isEmpty(qqNumber))
        {
            String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qqNumber;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        }
        return intent;
    }

    public static Intent getIntentGetContent()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        return intent;
    }

    public static Intent getIntentChooser(String title, Intent... intents)
    {
        // 显示一个供用户选择的应用列表
        Intent intent = new Intent(Intent.ACTION_CHOOSER);
        intent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
        if (!TextUtils.isEmpty(title))
        {
            intent.putExtra(Intent.EXTRA_TITLE, title);
        }
        return intent;
    }

    public static Intent getIntentOpenBrowser(String url)
    {
        if (TextUtils.isEmpty(url))
        {
            return null;
        }

        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        return intent;
    }

    /**
     * 获得打开本地图库的intent
     *
     * @return
     */
    public static Intent getIntentSelectLocalImage()
    {
        // Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // intent.setType("image/*");
        // intent.putExtra("crop", true);
        // intent.putExtra("return-data", true);
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return intent;
    }

    public static Intent getIntentSelectLocalImage2()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        return intent;
    }

    /**
     * 获调用拍照的intent
     *
     * @return
     */
    public static Intent getIntentTakePhoto(File saveFile)
    {
        if (saveFile == null)
        {
            return null;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(saveFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
    }

    /**
     * 获调发送邮件的intent
     *
     * @return
     */
    public static Intent getIntentSendEmail(String email, String subject)
    {
        if (TextUtils.isEmpty(email))
        {
            return null;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        return intent;
    }

    public static Intent getIntentCallPhone(String phoneNumber)
    {
        return new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
    }

    public static boolean isIntentAvailable(Intent intent)
    {
        if (intent != null)
        {
            List<ResolveInfo> activities = SDLibrary.getInstance().getContext().getPackageManager()
                    .queryIntentActivities(intent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
            return activities.size() != 0;
        }
        return false;
    }

    public static Intent getIntentLocalMap(String latitude, String longitude, String name)
    {
        Intent intent = null;
        if (!TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(longitude))
        {
            double lat = SDTypeParseUtil.getDouble(latitude);
            double lon = SDTypeParseUtil.getDouble(longitude);
            intent = getIntentLocalMap(lat, lon, name);
        }
        return intent;
    }

    public static Intent getIntentLocalMap(double latitude, double longitude, String name)
    {
        String uriString = "geo:" + latitude + "," + longitude;
        if (!TextUtils.isEmpty(name))
        {
            uriString = uriString + "?q=" + name;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriString));
        if (!isIntentAvailable(intent)) // 没有自带地图
        {
            String uriPre = "http://ditu.google.cn/maps?hl=zh&mrt=loc&q=";
            uriString = uriPre + latitude + "," + longitude;
            if (!TextUtils.isEmpty(name))
            {
                uriString = uriString + "(" + name + ")";
            }
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriString));
        }
        return intent;
    }

}
