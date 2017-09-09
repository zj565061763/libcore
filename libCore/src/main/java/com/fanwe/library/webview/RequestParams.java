package com.fanwe.library.webview;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import android.text.TextUtils;

public class RequestParams
{
	private LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();

	public RequestParams set(LinkedHashMap<String, String> params)
	{
		this.params = params;
		return this;
	}

	public String get(String key)
	{
		return params.get(key);
	}

	public RequestParams put(String key, String value)
	{
		params.put(key, value);
		return this;
	}

	public String build()
	{
		String data = null;
		if (!params.isEmpty())
		{
			StringBuilder sb = new StringBuilder(data);
			for (Entry<String, String> item : params.entrySet())
			{
				sb.append(item.getKey()).append("=").append(item.getValue());
				sb.append("&");
			}
			sb.setLength(sb.length() - 1);
			data = sb.toString();
		}
		return data;
	}

	public String build(String url)
	{
		String data = build();
		if (!TextUtils.isEmpty(data))
		{
			if (!TextUtils.isEmpty(url))
			{
				if (url.contains("?"))
				{
					if (url.endsWith("?"))
					{
						data = url + data;
					} else
					{
						if (url.endsWith("&"))
						{
							data = url + data;
						} else
						{
							data = url + "&" + data;
						}
					}
				} else
				{
					data = url + "?" + data;
				}
			}
		} else
		{
			data = url;
		}
		return data;
	}

}
