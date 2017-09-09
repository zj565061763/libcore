package com.fanwe.library.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.Uri;
import android.text.TextUtils;

import com.fanwe.library.SDLibrary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

public class SDOtherUtil
{
    /**
     * 复制到剪切板
     *
     * @param content
     */
    public static void copyText(CharSequence content)
    {
        ClipboardManager clip = (ClipboardManager) SDLibrary.getInstance().getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        clip.setPrimaryClip(ClipData.newPlainText(null, content));
    }

    /**
     * 粘贴剪切板的内容
     *
     * @return
     */
    public static CharSequence pasteText()
    {
        ClipboardManager clip = (ClipboardManager) SDLibrary.getInstance().getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (clip.hasPrimaryClip())
        {
            return clip.getPrimaryClip().getItemAt(0).getText();
        }
        return null;
    }

    public static String hideMobile(String mobile)
    {
        String result = null;
        if (!TextUtils.isEmpty(mobile) && TextUtils.isDigitsOnly(mobile) && mobile.length() == 11)
        {
            result = mobile.substring(0, 3) + "****" + mobile.substring(7);
        }
        return result;
    }

    public static Type[] getType(Class<?> clazz)
    {
        Type[] types = null;
        if (clazz != null)
        {
            Type type = clazz.getGenericSuperclass();
            ParameterizedType parameterizedType = (ParameterizedType) type;
            types = parameterizedType.getActualTypeArguments();
        }
        return types;
    }

    public static Type getType(Class<?> clazz, int index)
    {
        Type type = null;
        Type[] types = getType(clazz);
        if (types != null && index >= 0 && types.length > index)
        {
            type = types[index];
        }
        return type;
    }

    public static String build(Object... content)
    {
        return build(",", content);
    }

    public static String build(String separator, Object... content)
    {
        if (separator == null)
        {
            separator = "";
        }
        StringBuilder sb = new StringBuilder();
        if (content != null && content.length > 0)
        {
            for (Object obj : content)
            {
                if (obj != null)
                {
                    sb.append(obj.toString()).append(separator);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 把文件加到相册
     *
     * @param file
     */
    public static void scanFile(Context context, File file)
    {
        if (context != null && file != null && file.exists())
        {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
        }
    }

    /**
     * 耳机是否已经插入
     *
     * @param context
     * @return
     */
    public static boolean isHeadsetPlug(Context context)
    {
        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return manager.isWiredHeadsetOn();
    }

    public static long getTotalCpuTime()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/stat")), 1000);
            String load = reader.readLine();
            reader.close();

            String[] cpuInfos = load.split(" ");

            long cpu = Long.parseLong(cpuInfos[2])
                    + Long.parseLong(cpuInfos[3]) + Long.parseLong(cpuInfos[4])
                    + Long.parseLong(cpuInfos[6]) + Long.parseLong(cpuInfos[5])
                    + Long.parseLong(cpuInfos[7]) + Long.parseLong(cpuInfos[8]);

            return cpu;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getAppCpuTime()
    {
        try
        {
            int pid = android.os.Process.myPid();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/" + pid + "/stat")), 1000);
            String load = reader.readLine();
            reader.close();

            String[] cpuInfos = load.split(" ");
            long cpu = Long.parseLong(cpuInfos[13])
                    + Long.parseLong(cpuInfos[14]) + Long.parseLong(cpuInfos[15])
                    + Long.parseLong(cpuInfos[16]);

            return cpu;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public static int[] getCpuRate()
    {
        int[] arrCpu = new int[2];
        try
        {
            String strLine;
            Process p = Runtime.getRuntime().exec("top -n 1");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((strLine = br.readLine()) != null)
            {
                if (strLine.trim().length() < 1)
                {
                    continue;
                } else
                {
                    String[] arrInfo = strLine.split("%");

                    String appCpu = arrInfo[0].replace("User ", "");
                    String systemCpu = arrInfo[1].replace(", System ", "");

                    arrCpu[0] = Integer.valueOf(appCpu);
                    arrCpu[1] = Integer.valueOf(systemCpu);
                    break;
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return arrCpu;
    }

    /**
     * 域名是否可用，只能在后台线程调用
     *
     * @param host
     * @return
     */
    public static boolean isHostAvailable(String host)
    {
        try
        {
            InetAddress inetAddress = InetAddress.getByName(host);
            String ip = inetAddress.getHostAddress();
            if (!TextUtils.isEmpty(ip))
            {
                return true;
            } else
            {
                return false;
            }
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static String getHost(String url)
    {
        try
        {
            URI uri = new URI(url);
            return uri.getHost();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 定位服务是否开启
     *
     * @param context
     * @return
     */
    public static boolean isLocationServiceEnabled(final Context context)
    {
        if (context != null)
        {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null)
            {
                boolean isGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                return (isGps || isNetwork);
            }
        }
        return false;
    }

}
