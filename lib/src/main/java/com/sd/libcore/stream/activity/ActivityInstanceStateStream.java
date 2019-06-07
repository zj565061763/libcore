package com.sd.libcore.stream.activity;

import android.app.Activity;
import android.os.Bundle;

public interface ActivityInstanceStateStream extends FActivityStream
{
    void onActivitySaveInstanceState(Activity activity, Bundle outState);

    void onActivityRestoreInstanceState(Activity activity, Bundle savedInstanceState);
}
