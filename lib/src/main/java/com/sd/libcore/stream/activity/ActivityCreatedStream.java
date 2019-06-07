package com.sd.libcore.stream.activity;

import android.app.Activity;
import android.os.Bundle;

public interface ActivityCreatedStream extends FActivityStream
{
    void onActivityCreated(Activity activity, Bundle savedInstanceState);
}
