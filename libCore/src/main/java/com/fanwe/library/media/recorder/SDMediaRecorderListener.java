package com.fanwe.library.media.recorder;

import android.media.MediaRecorder;

import java.io.File;

/**
 * Created by Administrator on 2016/7/15.
 */
public interface SDMediaRecorderListener
{
    void onRecording();

    void onStopped(File file, long duration);

    void onReleased();

    void onInfo(MediaRecorder mr, int what, int extra);

    void onError(MediaRecorder mr, int what, int extra);

    void onException(Exception e);

}
