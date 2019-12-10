package com.sd.libcore.stream.activity;

import android.app.Activity;
import android.os.Bundle;

import com.sd.lib.stream.FStream;

@Deprecated
public interface ActivityCreatedStream extends FStream
{
    void onActivityCreated(Activity activity, Bundle savedInstanceState);
}
