package com.sd.libcore.stream.activity;

import android.app.Activity;
import android.content.Intent;

public interface ActivityResultStream extends FActivityStream
{
    void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data);
}
