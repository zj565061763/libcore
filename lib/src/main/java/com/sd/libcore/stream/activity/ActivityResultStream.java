package com.sd.libcore.stream.activity;

import android.app.Activity;
import android.content.Intent;

import com.sd.lib.stream.FStream;

public interface ActivityResultStream extends FStream
{
    void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data);
}
