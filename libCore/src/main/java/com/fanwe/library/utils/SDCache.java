package com.fanwe.library.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 本地缓存工具类
 * <p>
 * 如果已经缓存的对象的类的属性发生了变化并且serialVersionUID也变化了，则获取不到原先缓存的对象。
 * <p>
 * 如果仍然想获取到原先缓存的对象，则应该保持serialVersionUID不变(private static final long serialVersionUID = 0L);
 */
@Deprecated
public class SDCache
{
    private static final String TAG = "SDCache";

    private static final String DEFAULT_CACHE_DIR = "sdcache";

    private static final String INT = "int_";
    private static final String DOUBLE = "double_";
    private static final String BOOLEAN = "boolean_";
    private static final String STRING = "string_";
    private static final String SERIALIZABLE = "serializable_";
    private static final String OBJECT = "object_";
    private static final String OBJECT_JSON = "object_json_";

    private static Object lock = new Object();
    private static File cacheDir;

    public static void init(Context context)
    {
        synchronized (lock)
        {
            initCacheDir(context);
        }
    }

    // int
    public static boolean hasInt(String key)
    {
        synchronized (lock)
        {
            return hasString(INT + key);
        }
    }

    public static boolean setInt(String key, int value)
    {
        synchronized (lock)
        {
            return setString(INT + key, String.valueOf(value));
        }
    }

    public static int getInt(String key, int defaultValue)
    {
        synchronized (lock)
        {
            try
            {
                String result = getString(INT + key, String.valueOf(defaultValue));
                return Integer.valueOf(result);
            } catch (Exception e)
            {
                Log.e(TAG, "getInt " + key + ":" + e.toString());
            }
            return defaultValue;
        }
    }

    public static boolean removeInt(String key)
    {
        synchronized (lock)
        {
            return removeString(INT + key);
        }
    }

    // double
    public static boolean hasDouble(String key)
    {
        synchronized (lock)
        {
            return hasString(DOUBLE + key);
        }
    }

    public static boolean setDouble(String key, double value)
    {
        synchronized (lock)
        {
            return setString(DOUBLE + key, String.valueOf(value));
        }
    }

    public static double getDouble(String key, double defaultValue)
    {
        synchronized (lock)
        {
            try
            {
                String result = getString(DOUBLE + key, String.valueOf(defaultValue));
                return Double.valueOf(result);
            } catch (Exception e)
            {
                Log.e(TAG, "getDouble " + key + ":" + e.toString());
            }
            return defaultValue;
        }
    }

    public static boolean removeDouble(String key)
    {
        synchronized (lock)
        {
            return removeString(DOUBLE + key);
        }
    }

    // boolean
    public static boolean hasBoolean(String key)
    {
        synchronized (lock)
        {
            return hasString(BOOLEAN + key);
        }
    }

    public static boolean setBoolean(String key, boolean value)
    {
        synchronized (lock)
        {
            return setString(BOOLEAN + key, String.valueOf(value));
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue)
    {
        synchronized (lock)
        {
            try
            {
                String result = getString(BOOLEAN + key, String.valueOf(defaultValue));
                return Boolean.valueOf(result);
            } catch (Exception e)
            {
                Log.e(TAG, "getBoolean " + key + ":" + e.toString());
            }
            return defaultValue;
        }
    }

    public static boolean removeBoolean(String key)
    {
        synchronized (lock)
        {
            return removeString(BOOLEAN + key);
        }
    }

    // string
    public static boolean hasString(String key)
    {
        synchronized (lock)
        {
            return hasSerializable(STRING + key);
        }
    }

    public static boolean setString(String key, String value)
    {
        return setString(key, value, false);
    }

    public static boolean setString(String key, String value, boolean encrypt)
    {
        synchronized (lock)
        {
            try
            {
                if (value == null)
                {
                    return removeString(key);
                } else
                {
                    StringInfo stringInfo = new StringInfo();
                    stringInfo.setData(value);
                    stringInfo.setEncrypt(encrypt);
                    stringInfo.encryptIfNeed();

                    return setSerializable(STRING + key, stringInfo);
                }
            } catch (Exception e)
            {
                Log.e(TAG, "setString " + key + ":" + e.toString());
                return false;
            }
        }
    }

    public static String getString(String key, String defaultValue)
    {
        synchronized (lock)
        {
            try
            {
                StringInfo stringInfo = getSerializable(STRING + key);
                if (stringInfo != null)
                {
                    stringInfo.decryptIfNeed();
                    return stringInfo.getData();
                } else
                {
                    return defaultValue;
                }
            } catch (Exception e)
            {
                Log.e(TAG, "getString " + key + ":" + e.toString());
                return defaultValue;
            }
        }
    }

    public static boolean removeString(String key)
    {
        synchronized (lock)
        {
            return removeSerializable(STRING + key);
        }
    }

    // serializable
    private static boolean hasSerializable(String key)
    {
        synchronized (lock)
        {
            return hasCache(SERIALIZABLE + key);
        }
    }

    private static <T extends Serializable> boolean setSerializable(String key, T model)
    {
        synchronized (lock)
        {
            ObjectOutputStream out = null;
            try
            {
                File file = createCacheFile(SERIALIZABLE + key);
                out = getOutputStream(file);
                out.writeObject(model);
                return true;
            } catch (Exception e)
            {
                Log.e(TAG, "setObject " + e.toString());
                return false;
            } finally
            {
                closeQuietly(out);
            }
        }
    }

    private static <T extends Serializable> T getSerializable(String key)
    {
        synchronized (lock)
        {
            ObjectInputStream in = null;
            try
            {
                File file = getCacheFile(SERIALIZABLE + key);
                in = getInputStream(file);
                return (T) in.readObject();
            } catch (Exception e)
            {
                Log.e(TAG, "getObject " + e.toString());
                return null;
            } finally
            {
                closeQuietly(in);
            }
        }
    }

    private static boolean removeSerializable(String key)
    {
        synchronized (lock)
        {
            return remove(SERIALIZABLE + key);
        }
    }

    // object
    public static <T extends Serializable> boolean hasObject(Class<T> clazz)
    {
        if (clazz == null)
        {
            return false;
        }
        synchronized (lock)
        {
            return hasSerializable(OBJECT + clazz.getName());
        }
    }

    public static <T extends Serializable> boolean setObject(T model)
    {
        if (model == null)
        {
            return false;
        }
        return setSerializable(OBJECT + model.getClass().getName(), model);
    }

    public static <T extends Serializable> T getObject(Class<T> clazz)
    {
        if (clazz == null)
        {
            return null;
        }
        return getSerializable(OBJECT + clazz.getName());
    }

    public static <T extends Serializable> boolean removeObject(Class<T> clazz)
    {
        if (clazz == null)
        {
            return false;
        }
        return removeSerializable(OBJECT + clazz.getName());
    }

    // object_json
    public static <T extends Serializable> boolean hasObjectJson(Class<T> clazz)
    {
        if (clazz == null)
        {
            return false;
        }
        synchronized (lock)
        {
            return hasString(OBJECT_JSON + clazz.getName());
        }
    }

    public static <T> boolean setObjectJson(T model)
    {
        return setObjectJson(model, false);
    }

    public static <T> boolean setObjectJson(T model, boolean encrypt)
    {
        synchronized (lock)
        {
            try
            {
                String json = object2Json(model);

                return setString(OBJECT_JSON + model.getClass().getName(), json, encrypt);
            } catch (Exception e)
            {
                Log.e(TAG, "setObjectJson " + e.toString());
                return false;
            }
        }
    }

    public static <T> T getObjectJson(Class<T> clazz)
    {
        synchronized (lock)
        {
            try
            {
                String json = getString(OBJECT_JSON + clazz.getName(), "");
                return json2Object(json, clazz);
            } catch (Exception e)
            {
                Log.e(TAG, "getObjectJson " + e.toString());
                return null;
            }
        }
    }

    public static <T> boolean removeObjectJson(Class<T> clazz)
    {
        synchronized (lock)
        {
            if (clazz == null)
            {
                return false;
            }
            return removeString(OBJECT_JSON + clazz.getName());
        }
    }

    private static <T> T json2Object(String json, Class<T> clazz)
    {
        return SDJsonUtil.json2Object(json, clazz);
    }

    private static String object2Json(Object obj)
    {
        return SDJsonUtil.object2Json(obj);
    }

    /**
     * 移除缓存
     *
     * @param key
     * @return
     */
    private static boolean remove(String key)
    {
        synchronized (lock)
        {
            try
            {
                File file = getCacheFile(key);
                return file.delete();
            } catch (Exception e)
            {
                Log.e(TAG, "remove " + key + ":" + e.toString());
                return false;
            }
        }
    }

    /**
     * 清除所有缓存
     */
    public static void clear()
    {
        synchronized (lock)
        {
            if (cacheDir == null)
            {
                return;
            }
            File[] arrFile = cacheDir.listFiles();
            if (arrFile == null)
            {
                return;
            }

            for (File file : arrFile)
            {
                deleteFileOrDir(file);
            }
        }
    }

    //-------------------------------------------------------------------------

    /**
     * 初始化缓存目录
     *
     * @param context
     */
    private static void initCacheDir(Context context)
    {
        if (cacheDir == null)
        {
            File dir = context.getExternalCacheDir();
            if (dir == null)
            {
                dir = context.getCacheDir();
            }
            cacheDir = new File(dir, DEFAULT_CACHE_DIR);
        }

        makesureDirExist();
    }

    private static void makesureDirExist()
    {
        if (!cacheDir.exists())
        {
            cacheDir.mkdirs();
        }
    }

    /**
     * 获得真实的key
     *
     * @param key
     * @return
     */
    private static String createRealKey(String key)
    {
        return MD5Util.MD5(key);
    }

    /**
     * 根据key获得缓存文件
     *
     * @param key
     * @return
     */
    private static File getCacheFile(String key)
    {
        if (cacheDir == null)
        {
            Log.e(TAG, "please invoke init(context) before set and get");
            return null;
        }
        makesureDirExist();
        File file = new File(cacheDir, createRealKey(key));
        return file;
    }

    /**
     * 缓存是否存在
     *
     * @param key
     * @return
     */
    private static boolean hasCache(String key)
    {
        File file = getCacheFile(key);
        if (file != null && file.exists())
        {
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * 创建缓存文件
     *
     * @param key
     * @return
     */
    private static File createCacheFile(String key) throws Exception
    {
        File file = getCacheFile(key);
        if (!file.exists())
        {
            file.createNewFile();
        }
        return file;
    }

    private static ObjectOutputStream getOutputStream(File file) throws Exception
    {
        return new ObjectOutputStream(new FileOutputStream(file));
    }

    private static ObjectInputStream getInputStream(File file) throws Exception
    {
        return new ObjectInputStream(new FileInputStream(file));
    }

    private static boolean deleteFileOrDir(File path)
    {
        if (path == null || !path.exists())
        {
            return true;
        }
        if (path.isFile())
        {
            return path.delete();
        }
        File[] files = path.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                deleteFileOrDir(file);
            }
        }
        return path.delete();
    }

    private static void closeQuietly(Closeable closeable)
    {
        if (closeable != null)
        {
            try
            {
                closeable.close();
            } catch (Throwable ignored)
            {
            }
        }
    }

    private static class StringInfo implements Serializable
    {
        private static final long serialVersionUID = 0L;

        private String data;
        private boolean encrypt;

        public String getData()
        {
            return data;
        }

        public void setData(String data)
        {
            this.data = data;
        }

        public boolean isEncrypt()
        {
            return encrypt;
        }

        public void setEncrypt(boolean encrypt)
        {
            this.encrypt = encrypt;
        }

        public void encryptIfNeed()
        {
            if (encrypt)
            {
                if (!TextUtils.isEmpty(data))
                {
                    data = AESUtil.encrypt(data);
                }
            }
        }

        public void decryptIfNeed()
        {
            if (encrypt)
            {
                if (!TextUtils.isEmpty(data))
                {
                    data = AESUtil.decrypt(data);
                }
            }
        }
    }

}
