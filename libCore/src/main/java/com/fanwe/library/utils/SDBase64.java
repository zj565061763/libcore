package com.fanwe.library.utils;

import android.util.Base64;

public class SDBase64
{

	public static byte[] encodeToByte(String content)
	{
		byte[] result = null;
		try
		{
			result = Base64.encode(content.getBytes("UTF-8"), Base64.DEFAULT);
		} catch (Exception e)
		{
			result = null;
		}
		return result;
	}

	public static byte[] encodeToByte(byte[] content)
	{
		byte[] result = null;
		try
		{
			result = Base64.encode(content, Base64.DEFAULT);
		} catch (Exception e)
		{
			result = null;
		}
		return result;
	}

	public static String encodeToString(byte[] content)
	{
		String result = null;
		try
		{
			result = Base64.encodeToString(content, Base64.DEFAULT);
		} catch (Exception e)
		{
			result = null;
		}
		return result;
	}

	public static String encodeToString(String content)
	{
		String result = null;
		try
		{
			result = Base64.encodeToString(content.getBytes("UTF-8"), Base64.DEFAULT);
		} catch (Exception e)
		{
			result = null;
		}
		return result;
	}

	public static String decodeToString(String content)
	{
		String result = null;
		try
		{
			byte[] byteResult = Base64.decode(content, Base64.DEFAULT);
			result = new String(byteResult, "UTF-8");
		} catch (Exception e)
		{
			result = null;
		}
		return result;
	}

	public static String decodeToString(byte[] content)
	{
		String result = null;
		try
		{
			byte[] byteResult = Base64.decode(content, Base64.DEFAULT);
			result = new String(byteResult, "UTF-8");
		} catch (Exception e)
		{
			result = null;
		}
		return result;
	}

	public static byte[] decodeToByte(String content)
	{
		byte[] result = null;
		try
		{
			result = Base64.decode(content, Base64.DEFAULT);
		} catch (Exception e)
		{
			result = null;
		}
		return result;
	}

	public static byte[] decodeToByte(byte[] content)
	{
		byte[] result = null;
		try
		{
			result = Base64.decode(content, Base64.DEFAULT);
		} catch (Exception e)
		{
			result = null;
		}
		return result;
	}

}
