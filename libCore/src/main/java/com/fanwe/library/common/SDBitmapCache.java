package com.fanwe.library.common;

import com.fanwe.library.utils.MD5Util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

public class SDBitmapCache
{
	private static final int DEFAULT_MEMORY_CACHE_SIZE = 2 * 1024 * 1024;

	private static SDBitmapCache mInstance;
	private LruCache<String, Bitmap> mCache;

	private SDBitmapCache()
	{
		mCache = new LruCache<String, Bitmap>(DEFAULT_MEMORY_CACHE_SIZE);
	}

	public static SDBitmapCache getInstance()
	{
		if (mInstance == null)
		{
			synchronized (SDBitmapCache.class)
			{
				if (mInstance == null)
				{
					mInstance = new SDBitmapCache();
				}
			}
		}
		return mInstance;
	}

	private String createKey(String uri)
	{
		String cacheKey = null;
		if (!TextUtils.isEmpty(uri))
		{
			cacheKey = MD5Util.MD5(uri);
		}
		return cacheKey;
	}

	public void put(String uri, Bitmap bitmap)
	{
		try
		{
			mCache.put(createKey(uri), bitmap);
		} catch (Exception e)
		{
		}
	}

	public Bitmap get(String uri)
	{
		Bitmap bitmap = null;
		try
		{
			bitmap = mCache.get(createKey(uri));
		} catch (Exception e)
		{
		}
		return bitmap;
	}

	public void clear()
	{
		mCache.evictAll();
	}

}
