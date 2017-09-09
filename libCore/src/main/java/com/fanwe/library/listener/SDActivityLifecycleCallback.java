package com.fanwe.library.listener;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Administrator on 2017/4/7.
 */

public interface SDActivityLifecycleCallback extends Application.ActivityLifecycleCallbacks
{
    void onActivityRestoreInstanceState(Activity activity, Bundle savedInstanceState);

    void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data);
}
