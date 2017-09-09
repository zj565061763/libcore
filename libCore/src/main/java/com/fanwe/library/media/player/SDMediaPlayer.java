package com.fanwe.library.media.player;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.text.TextUtils;

import com.fanwe.library.looper.ISDLooper;
import com.fanwe.library.looper.impl.SDSimpleLooper;

@Deprecated
public class SDMediaPlayer
{
    private static SDMediaPlayer instance;

    private MediaPlayer mPlayer;
    private State mState = State.Idle;

    private String mDataFilePath;
    private String mDataUrl;
    private int mDataRawResId;

    private boolean mHasInitialized;
    private ISDLooper mLooper;

    private PlayerCallback mCallback;
    private ProgressCallback mProgressCallback;

    private SDMediaPlayer()
    {
        init();
    }

    public static SDMediaPlayer getInstance()
    {
        if (instance == null)
        {
            instance = new SDMediaPlayer();
        }
        return instance;
    }

    /**
     * 初始化播放器，调用release()后如果想要继续使用，要调用此方法初始化
     */
    public void init()
    {
        if (mPlayer != null)
        {
            release();
        }
        mPlayer = new MediaPlayer();
        mPlayer.setOnErrorListener(mOnErrorListener);
        mPlayer.setOnPreparedListener(mOnPreparedListener);
        mPlayer.setOnCompletionListener(mOnCompletionListener);
    }

    private void startLooper()
    {
        if (mLooper == null)
        {
            mLooper = new SDSimpleLooper();
        }

        mLooper.start(300, mLooperRunnable);
    }

    private void stopLooper()
    {
        if (mLooper != null)
        {
            mLooper.stop();
        }
    }

    private void notifyProgress()
    {
        if (mProgressCallback != null)
        {
            mProgressCallback.onProgress(mPlayer.getCurrentPosition(), mPlayer.getDuration());
        }
    }

    private Runnable mLooperRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            if (mProgressCallback != null)
            {
                if (hasInitialized())
                {
                    notifyProgress();
                } else
                {
                    stopLooper();
                }
            }
        }
    };

    public State getState()
    {
        return mState;
    }

    public void setCallback(PlayerCallback callback)
    {
        this.mCallback = callback;
    }

    public void setProgressCallback(ProgressCallback progressCallback)
    {
        this.mProgressCallback = progressCallback;
    }

    public String getDataFilePath()
    {
        return mDataFilePath;
    }

    public String getDataUrl()
    {
        return mDataUrl;
    }

    public int getDataRawResId()
    {
        return mDataRawResId;
    }

    @Deprecated
    public void playAudioFile(String path)
    {
        reset();
        try
        {
            mPlayer.setDataSource(path);
            this.mDataFilePath = path;
            setState(State.Initialized);
            notifyInitialized();
            start();
        } catch (Exception e)
        {
            notifyException(e);
        }
    }

    @Deprecated
    public boolean isPlayingAudioFile(String path)
    {
        boolean result = false;
        if (isPlaying() && !TextUtils.isEmpty(path))
        {
            result = path.equals(mDataFilePath);
        }
        return result;
    }

    @Deprecated
    public void performPlayAudioFile(String path)
    {
        if (!TextUtils.isEmpty(path))
        {
            if (isPlayingAudioFile(path))
            {
                stop();
            } else
            {
                playAudioFile(path);
            }
        }
    }

    public void seekTo(int position)
    {
        try
        {
            if (hasInitialized())
            {
                mPlayer.seekTo(position);
            }
        } catch (Exception e)
        {
            notifyException(e);
        }
    }

    /**
     * 播放和暂停
     */
    public void performPlayPause()
    {
        performPlayInside(false);
    }

    /**
     * 重新播放和停止播放
     */
    public void performRestartPlayStop()
    {
        performPlayInside(true);
    }

    /**
     * 模拟暂停恢复播放
     *
     * @param restart true-恢复的时候重头播放
     */
    private void performPlayInside(boolean restart)
    {
        try
        {
            if (hasInitialized())
            {
                if (isPlaying())
                {
                    if (restart)
                    {
                        mPlayer.seekTo(0);
                    }
                    pause();
                } else
                {
                    start();
                }
            }
        } catch (Exception e)
        {
            notifyException(e);
        }
    }

    /**
     * 设置文件本地路径
     *
     * @param path
     */
    public boolean setDataFilePath(String path)
    {
        try
        {
            if (hasDataFilePath(path))
            {
                return true;
            }

            reset();
            mPlayer.setDataSource(path);
            this.mDataFilePath = path;
            setState(State.Initialized);
            notifyInitialized();
            return true;
        } catch (Exception e)
        {
            notifyException(e);
            return false;
        }
    }

    /**
     * 设置文件网络链接
     *
     * @param url
     */
    public boolean setDataUrl(String url)
    {
        try
        {
            if (hasDataUrl(url))
            {
                return true;
            }

            reset();
            mPlayer.setDataSource(url);
            this.mDataUrl = url;
            setState(State.Initialized);
            notifyInitialized();
            return true;
        } catch (Exception e)
        {
            notifyException(e);
            return false;
        }
    }

    /**
     * 设置文件rawResId
     *
     * @param rawResId
     * @param context
     */
    public boolean setDataRawResId(int rawResId, Context context)
    {
        try
        {
            if (hasDataRawResId(rawResId))
            {
                return true;
            }

            reset();
            AssetFileDescriptor afd = context.getResources().openRawResourceFd(rawResId);
            mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            this.mDataRawResId = rawResId;
            setState(State.Initialized);
            notifyInitialized();
            return true;
        } catch (Exception e)
        {
            notifyException(e);
            return false;
        }
    }

    /**
     * 当前已经设置的url是否和新的url一致
     *
     * @param url
     * @return
     */
    public boolean hasDataUrl(String url)
    {
        if (!TextUtils.isEmpty(mDataUrl) && mDataUrl.equals(url))
        {
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * 当前当前已经设置的文件路径是否和新的path一致
     *
     * @param path
     * @return
     */
    public boolean hasDataFilePath(String path)
    {
        if (!TextUtils.isEmpty(mDataFilePath) && mDataFilePath.equals(path))
        {
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * 当前当前已经设置的rawResId是否和新的rawResId一致
     *
     * @param rawResId
     * @return
     */
    public boolean hasDataRawResId(int rawResId)
    {
        if (this.mDataRawResId != 0 && this.mDataRawResId == rawResId)
        {
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * 是否播放状态
     *
     * @return
     */
    public boolean isPlaying()
    {
        return State.Playing == mState;
    }

    /**
     * 是否暂停状态
     *
     * @return
     */
    public boolean isPaused()
    {
        return State.Paused == mState;
    }

    /**
     * 是否已经初始化
     *
     * @return
     */
    public boolean hasInitialized()
    {
        return mHasInitialized;
    }

    /**
     * 开始播放
     */
    public void start()
    {
        switch (mState)
        {
            case Idle:

                break;
            case Initialized:
                prepareAsyncPlayer();
                break;
            case Preparing:

                break;
            case Prepared:
                startPlayer();
                break;
            case Playing:

                break;
            case Paused:
                startPlayer();
                break;
            case Completed:
                startPlayer();
                break;
            case Stopped:
                prepareAsyncPlayer();
                break;

            default:
                break;
        }
    }

    /**
     * 暂停播放
     */
    public void pause()
    {
        switch (mState)
        {
            case Idle:

                break;
            case Initialized:

                break;
            case Preparing:

                break;
            case Prepared:

                break;
            case Playing:
                pausePlayer();
                break;
            case Paused:

                break;
            case Completed:

                break;
            case Stopped:

                break;

            default:
                break;
        }
    }

    /**
     * 停止播放
     */
    public void stop()
    {
        switch (mState)
        {
            case Idle:

                break;
            case Initialized:

                break;
            case Preparing:

                break;
            case Prepared:
                stopPlayer();
                break;
            case Playing:
                stopPlayer();
                break;
            case Paused:
                stopPlayer();
                break;
            case Completed:
                stopPlayer();
                break;
            case Stopped:

                break;

            default:
                break;
        }
    }

    /**
     * 重置播放器，一般用于关闭播放界面的时候调用
     */
    public void reset()
    {
        stop();
        resetPlayer();
    }

    /**
     * 释放播放器，用于不再需要播放器的时候调用，调用此方法后，需要手动调用init()方法初始化后才可以使用
     */
    public void release()
    {
        stop();
        releasePlayer();
    }


    private void setState(State state)
    {
        this.mState = state;
    }

    private void prepareAsyncPlayer()
    {
        setState(State.Preparing);
        notifyPreparing();
        mPlayer.prepareAsync();
    }

    private void startPlayer()
    {
        setState(State.Playing);
        notifyPlaying();
        mPlayer.start();

        startLooper();
    }

    private void pausePlayer()
    {
        setState(State.Paused);
        notifyPaused();
        mPlayer.pause();

        stopLooper();
    }

    private void stopPlayer()
    {
        setState(State.Stopped);
        notifyStopped();
        mPlayer.stop();

        stopLooper();
    }

    private void resetPlayer()
    {
        setState(State.Idle);
        notifyReset();
        mPlayer.reset();

        stopLooper();
        mDataFilePath = null;
        mDataUrl = null;
        mDataRawResId = 0;
        mHasInitialized = false;
    }

    private void releasePlayer()
    {
        setState(State.Released);
        notifyReleased();
        mPlayer.release();
    }

    // notify
    protected void notifyPreparing()
    {
        if (mCallback != null)
        {
            mCallback.onPreparing();
        }
    }

    protected void notifyPrepared()
    {
        if (mCallback != null)
        {
            mCallback.onPrepared();
        }
    }

    protected void notifyPlaying()
    {
        if (mCallback != null)
        {
            mCallback.onPlaying();
        }
    }

    protected void notifyPaused()
    {
        if (mCallback != null)
        {
            mCallback.onPaused();
        }
    }

    protected void notifyCompletion()
    {
        if (mCallback != null)
        {
            mCallback.onCompletion();
        }
    }

    protected void notifyStopped()
    {
        if (mCallback != null)
        {
            mCallback.onStopped();
        }
    }

    protected void notifyReset()
    {
        if (mCallback != null)
        {
            mCallback.onReset();
        }
    }

    protected void notifyInitialized()
    {
        mHasInitialized = true;
        if (mCallback != null)
        {
            mCallback.onInitialized();
        }
    }

    protected void notifyReleased()
    {
        if (mCallback != null)
        {
            mCallback.onReleased();
        }
    }

    protected void notifyError(MediaPlayer mp, int what, int extra)
    {
        if (mCallback != null)
        {
            mCallback.onError(what, extra);
        }
    }

    protected void notifyException(Exception e)
    {
        if (mCallback != null)
        {
            mCallback.onException(e);
        }
    }

    // 监听
    private OnErrorListener mOnErrorListener = new OnErrorListener()
    {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra)
        {
            resetPlayer();
            notifyError(mp, what, extra);
            return true;
        }
    };

    private OnPreparedListener mOnPreparedListener = new OnPreparedListener()
    {

        @Override
        public void onPrepared(MediaPlayer mp)
        {
            setState(State.Prepared);
            notifyPrepared();
            start();
        }
    };

    private OnCompletionListener mOnCompletionListener = new OnCompletionListener()
    {

        @Override
        public void onCompletion(MediaPlayer mp)
        {
            stopLooper();
            notifyProgress();

            setState(State.Completed);
            notifyCompletion();
        }
    };

    public enum State
    {
        /**
         * 已经释放资源
         */
        Released,
        /**
         * 空闲，还没设置dataSource
         */
        Idle,
        /**
         * 已经设置dataSource，还未播放
         */
        Initialized,
        /**
         * 准备中
         */
        Preparing,
        /**
         * 准备完毕
         */
        Prepared,
        /**
         * 已经启动播放
         */
        Playing,
        /**
         * 已经暂停播放
         */
        Paused,
        /**
         * 已经播放完毕
         */
        Completed,
        /**
         * 调用stop方法后的状态
         */
        Stopped;
    }

    public interface PlayerCallback
    {
        void onReleased();

        void onReset();

        void onInitialized();

        void onPreparing();

        void onPrepared();

        void onPlaying();

        void onPaused();

        void onCompletion();

        void onStopped();

        void onError(int what, int extra);

        void onException(Exception e);
    }

    public static class SimplePlayerCallback implements PlayerCallback
    {
        @Override
        public void onReleased()
        {
        }

        @Override
        public void onReset()
        {
        }

        @Override
        public void onInitialized()
        {
        }

        @Override
        public void onPreparing()
        {
        }

        @Override
        public void onPrepared()
        {
        }

        @Override
        public void onPlaying()
        {
        }

        @Override
        public void onPaused()
        {
        }

        @Override
        public void onCompletion()
        {
        }

        @Override
        public void onStopped()
        {
        }

        @Override
        public void onError(int what, int extra)
        {
        }

        @Override
        public void onException(Exception e)
        {
        }
    }

    public interface ProgressCallback
    {
        void onProgress(long current, long total);
    }
}
