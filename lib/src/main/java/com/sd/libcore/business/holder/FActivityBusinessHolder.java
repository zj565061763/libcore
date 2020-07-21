package com.sd.libcore.business.holder;

import android.app.Activity;

import java.util.Map;
import java.util.WeakHashMap;

public class FActivityBusinessHolder extends FBusinessHolder
{
    private FActivityBusinessHolder()
    {
    }

    //---------- static ----------

    private static final Map<Activity, FBusinessHolder> MAP_HOLDER = new WeakHashMap<>();

    public static synchronized FBusinessHolder with(Activity activity)
    {
        if (activity == null)
            return new FActivityBusinessHolder();

        FBusinessHolder holder = MAP_HOLDER.get(activity);
        if (holder == null)
        {
            holder = new FActivityBusinessHolder();
            if (!activity.isFinishing())
                MAP_HOLDER.put(activity, holder);
        }
        return holder;
    }
}
