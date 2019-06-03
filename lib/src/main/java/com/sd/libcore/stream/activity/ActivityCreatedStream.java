package com.sd.libcore.stream.activity;

import android.app.Activity;
import android.os.Bundle;

import com.sd.lib.stream.FStream;

public interface ActivityCreatedStream extends FStream
{
    void onActivityCreated(Activity activity, Bundle savedInstanceState);
}
