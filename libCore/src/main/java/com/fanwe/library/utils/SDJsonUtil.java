package com.fanwe.library.utils;

import com.fanwe.lib.utils.json.FJson;
import com.google.gson.Gson;

import java.util.Map;

/**
 * 用{@link FJson}替代
 */
@Deprecated
public class SDJsonUtil
{
    private static final Gson GSON = new Gson();

    private SDJsonUtil()
    {
    }

    public static <T> T json2Object(String json, Class<T> clazz)
    {
        return GSON.fromJson(json, clazz);
    }

    public static String object2Json(Object obj)
    {
        return GSON.toJson(obj);
    }

    public static <T> T map2Object(Map map, Class<T> clazz)
    {
        T t = null;
        if (map != null)
        {
            String json = object2Json(map);
            t = json2Object(json, clazz);
        }
        return t;
    }
}
