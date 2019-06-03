package com.sd.libcore.stream.activity;

import android.app.Activity;
import android.os.Bundle;

import com.sd.lib.stream.FStream;

public interface ActivityInstanceStateStream extends FStream
{
    void onActivitySaveInstanceState(Activity activity, Bundle outState);

    void onActivityRestoreInstanceState(Activity activity, Bundle savedInstanceState);
}
