package com.fanwe.library.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util
{

	public static String MD5(String value)
	{
		String result;
		try
		{
			final MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(value.getBytes());
			byte[] bytes = digest.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++)
			{
				String hex = Integer.toHexString(0xFF & bytes[i]);
				if (hex.length() == 1)
				{
					sb.append('0');
				}
				sb.append(hex);
			}
			result = sb.toString();
		} catch (NoSuchAlgorithmException e)
		{
			result = null;
		}
		return result;
	}

}
