package com.fanwe.library.media.recorder;

import android.content.Context;
import android.media.MediaRecorder;
import android.text.TextUtils;

import com.fanwe.library.looper.SDCountDownTimer;
import com.fanwe.library.utils.SDFileUtil;

import java.io.File;

/**
 * Created by Administrator on 2016/7/15.
 */
public class SDMediaRecorder
{
    private static final long MIN_RECORD_TIME = 1000;
    private static final String DIR_NAME = "record";

    private static SDMediaRecorder instance;
    private Context mContext;
    private MediaRecorder mRecorder;
    private State mState = State.Idle;
    private File mDirFile;
    private boolean mIsInit;

    private File mRecordFile;
    private long mStartTime;

    private SDCountDownTimer mCountDownTimer = new SDCountDownTimer();
    private long mMaxRecordTime;

    private SDMediaRecorderListener mListener;

    private SDMediaRecorder()
    {
    }

    public static SDMediaRecorder getInstance()
    {
        if (instance == null)
        {
            instance = new SDMediaRecorder();
        }
        return instance;
    }

    public MediaRecorder getRecorder()
    {
        return mRecorder;
    }

    /**
     * 初始化录音器
     *
     * @param context
     */
    public void init(Context context)
    {
        try
        {
            if (!mIsInit)
            {
                if (mRecorder != null)
                {
                    release();
                }

                this.mContext = context;
                mDirFile = SDFileUtil.getCacheDir(context, DIR_NAME);

                mRecorder = new MediaRecorder();
                mRecorder.setOnErrorListener(onErrorListener);
                mRecorder.setOnInfoListener(onInfoListener);
                mState = State.Idle;
                mIsInit = true;
            }
        } catch (Exception e)
        {
            if (mListener != null)
            {
                mListener.onException(e);
            }
        }
    }

    public void setCountDownCallback(SDCountDownTimer.Callback countDownCallback)
    {
        mCountDownTimer.setCallback(countDownCallback);
    }

    public void setMaxRecordTime(long maxRecordTime)
    {
        this.mMaxRecordTime = maxRecordTime;
    }

    public void deleteAllFile()
    {
        SDFileUtil.deleteFileOrDir(mDirFile);
    }

    public Context getContext()
    {
        return mContext;
    }

    public State getState()
    {
        return mState;
    }

    public File getRecordFile()
    {
        return mRecordFile;
    }

    public File getDirFile()
    {
        return mDirFile;
    }

    public File getFile(String fileName)
    {
        File file = new File(mDirFile, fileName);
        return file;
    }

    private MediaRecorder.OnInfoListener onInfoListener = new MediaRecorder.OnInfoListener()
    {

        @Override
        public void onInfo(MediaRecorder mr, int what, int extra)
        {
            notifyInfo(mr, what, extra);
        }
    };

    private MediaRecorder.OnErrorListener onErrorListener = new MediaRecorder.OnErrorListener()
    {
        @Override
        public void onError(MediaRecorder mr, int what, int extra)
        {
            resetData();
            stopRecorder();
            notifyError(mr, what, extra);
        }
    };

    public void registerListener(SDMediaRecorderListener listener)
    {
        this.mListener = listener;
    }

    public void unregisterListener(SDMediaRecorderListener listener)
    {
        if (listener != null)
        {
            if (this.mListener == listener)
            {
                this.mListener = null;
            }
        }
    }

    private void setState(State state)
    {
        this.mState = state;
    }

    /**
     * 开始录音
     *
     * @param path 录音文件保存路径，如果为空的话，会用录音器内部的路径规则生成录音文件
     */
    public void start(String path)
    {
        switch (mState)
        {
            case Idle:
                startRecorder(path);
                break;
            case Recording:

                break;
            case Stopped:
                startRecorder(path);
                break;
            case Released:

                break;
            default:
                break;
        }
    }

    /**
     * 停止录音
     */
    public void stop()
    {
        switch (mState)
        {
            case Idle:

                break;
            case Recording:
                stopRecorder();
                break;
            case Stopped:

                break;
            case Released:

                break;
            default:
                break;
        }
    }

    /**
     * 释放资源，一般在录音界面关闭的时候调用，调用后如果想系继续使用的话需要手动调用init(context)方法初始化
     */
    public void release()
    {
        switch (mState)
        {
            case Idle:

                break;
            case Recording:
                stop();
                release();
                break;
            case Stopped:
                releaseRecorder();
                break;
            case Released:

                break;
            default:
                break;
        }
    }

    private void startRecorder(String path)
    {
        try
        {
            setState(State.Recording);

            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

            if (TextUtils.isEmpty(path))
            {
                mRecordFile = SDFileUtil.createDefaultFileUnderDir(mDirFile);
                path = mRecordFile.getAbsolutePath();
            } else
            {
                mRecordFile = new File(path);
            }

            mRecorder.setOutputFile(path);
            mRecorder.prepare();
            mRecorder.start();
            mStartTime = System.currentTimeMillis();

            startTimer();
            notifyRecording();
        } catch (Exception e)
        {
            notifyException(e);
        }
    }

    private void startTimer()
    {
        if (mMaxRecordTime > MIN_RECORD_TIME)
        {
            mCountDownTimer.start(mMaxRecordTime, 1000);
        }
    }

    private void stopTimer()
    {
        mCountDownTimer.stop();
    }

    private void stopRecorder()
    {
        try
        {
            setState(State.Stopped);
            mRecorder.stop();
            mRecorder.reset();

            stopTimer();
            notifyStopped();

            resetData();
        } catch (Exception e)
        {
            notifyException(e);
        }
    }

    private void resetData()
    {
        mRecordFile = null;
        mStartTime = 0;
    }

    private void releaseRecorder()
    {
        setState(State.Released);
        mRecorder.release();

        stopTimer();
        notifyReleased();
        mIsInit = false;
    }

    private void notifyRecording()
    {
        if (mListener != null)
        {
            mListener.onRecording();
        }
    }

    protected void notifyStopped()
    {
        if (mListener != null)
        {
            long duration = 0;
            if (mStartTime > 0)
            {
                duration = System.currentTimeMillis() - mStartTime;
            }
            mListener.onStopped(mRecordFile, duration);
        }
    }

    private void notifyReleased()
    {
        if (mListener != null)
        {
            mListener.onReleased();
        }
    }

    protected void notifyError(MediaRecorder mr, int what, int extra)
    {
        if (mListener != null)
        {
            mListener.onError(mr, what, extra);
        }
    }

    protected void notifyException(Exception e)
    {
        mRecorder.reset();
        setState(State.Idle);
        resetData();
        stopTimer();
        if (mListener != null)
        {
            mListener.onException(e);
        }
    }

    protected void notifyInfo(MediaRecorder mr, int what, int extra)
    {
        if (mListener != null)
        {
            mListener.onInfo(mr, what, extra);
        }
    }


    public enum State
    {
        /**
         * 已经释放资源
         */
        Released,
        /**
         * 空闲
         */
        Idle,
        /**
         * 录音中
         */
        Recording,
        /**
         * 重置状态
         */
        Stopped;
    }

}
