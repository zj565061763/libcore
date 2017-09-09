package com.fanwe.library.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * 通知栏通知
 */
public class SDNotification extends Notification.Builder
{
    private Context context;
    private NotificationManager manager;

    public SDNotification(Context context)
    {
        super(context.getApplicationContext());
        this.context = context.getApplicationContext();
        this.manager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    // NotificationManager method
    public void notify(int id)
    {
        manager.notify(id, build());
    }

    public void notify(String tag, int id)
    {
        manager.notify(tag, id, build());
    }

    public void cancel(int id)
    {
        manager.cancel(id);
    }

    public void cancel(String tag, int id)
    {
        manager.cancel(tag, id);
    }

    public void cancelAll()
    {
        manager.cancelAll();
    }

    //Override method
    @Override
    public SDNotification setAutoCancel(boolean autoCancel)
    {
        super.setAutoCancel(autoCancel);
        return this;
    }

    @Override
    public SDNotification setCategory(String category)
    {
        super.setCategory(category);
        return this;
    }

    @Override
    public SDNotification setColor(int argb)
    {
        super.setColor(argb);
        return this;
    }

    @Override
    public SDNotification setContent(RemoteViews views)
    {
        super.setContent(views);
        return this;
    }

    @Override
    public SDNotification setContentInfo(CharSequence info)
    {
        super.setContentInfo(info);
        return this;
    }

    @Override
    public SDNotification setContentIntent(PendingIntent intent)
    {
        super.setContentIntent(intent);
        return this;
    }

    @Override
    public SDNotification setContentText(CharSequence text)
    {
        super.setContentText(text);
        return this;
    }

    @Override
    public SDNotification setContentTitle(CharSequence title)
    {
        super.setContentTitle(title);
        return this;
    }

    @Override
    public SDNotification setDefaults(int defaults)
    {
        super.setDefaults(defaults);
        return this;
    }

    @Override
    public SDNotification setDeleteIntent(PendingIntent intent)
    {
        super.setDeleteIntent(intent);
        return this;
    }

    @Override
    public SDNotification setExtras(Bundle extras)
    {
        super.setExtras(extras);
        return this;
    }

    @Override
    public SDNotification setFullScreenIntent(PendingIntent intent, boolean highPriority)
    {
        super.setFullScreenIntent(intent, highPriority);
        return this;
    }

    @Override
    public SDNotification setGroup(String groupKey)
    {
        super.setGroup(groupKey);
        return this;
    }

    @Override
    public SDNotification setGroupSummary(boolean isGroupSummary)
    {
        super.setGroupSummary(isGroupSummary);
        return this;
    }

    @Override
    public SDNotification setLargeIcon(Bitmap icon)
    {
        super.setLargeIcon(icon);
        return this;
    }

    @Override
    public SDNotification setLights(int argb, int onMs, int offMs)
    {
        super.setLights(argb, onMs, offMs);
        return this;
    }

    @Override
    public SDNotification setLocalOnly(boolean localOnly)
    {
        super.setLocalOnly(localOnly);
        return this;
    }

    @Override
    public SDNotification setNumber(int number)
    {
        super.setNumber(number);
        return this;
    }

    @Override
    public SDNotification setOngoing(boolean ongoing)
    {
        super.setOngoing(ongoing);
        return this;
    }

    @Override
    public SDNotification setOnlyAlertOnce(boolean onlyAlertOnce)
    {
        super.setOnlyAlertOnce(onlyAlertOnce);
        return this;
    }

    @Override
    public SDNotification setPriority(int pri)
    {
        super.setPriority(pri);
        return this;
    }

    @Override
    public SDNotification setProgress(int max, int progress, boolean indeterminate)
    {
        super.setProgress(max, progress, indeterminate);
        return this;
    }

    @Override
    public SDNotification setPublicVersion(Notification n)
    {
        super.setPublicVersion(n);
        return this;
    }

    @Override
    public SDNotification setShowWhen(boolean show)
    {
        super.setShowWhen(show);
        return this;
    }

    @Override
    public SDNotification setSmallIcon(int icon)
    {
        super.setSmallIcon(icon);
        return this;
    }

    @Override
    public SDNotification setSmallIcon(int icon, int level)
    {
        super.setSmallIcon(icon, level);
        return this;
    }

    @Override
    public SDNotification setSortKey(String sortKey)
    {
        super.setSortKey(sortKey);
        return this;
    }

    @Override
    public SDNotification setSound(Uri sound)
    {
        super.setSound(sound);
        return this;
    }

    @Override
    public SDNotification setSound(Uri sound, AudioAttributes audioAttributes)
    {
        super.setSound(sound, audioAttributes);
        return this;
    }

    @Override
    public SDNotification setStyle(Notification.Style style)
    {
        super.setStyle(style);
        return this;
    }

    @Override
    public SDNotification setSubText(CharSequence text)
    {
        super.setSubText(text);
        return this;
    }

    @Override
    public SDNotification setTicker(CharSequence tickerText)
    {
        super.setTicker(tickerText);
        return this;
    }

    @Override
    public SDNotification setUsesChronometer(boolean b)
    {
        super.setUsesChronometer(b);
        return this;
    }

    @Override
    public SDNotification setVibrate(long[] pattern)
    {
        super.setVibrate(pattern);
        return this;
    }

    @Override
    public SDNotification setVisibility(int visibility)
    {
        super.setVisibility(visibility);
        return this;
    }

    @Override
    public SDNotification setWhen(long when)
    {
        super.setWhen(when);
        return this;
    }
}
