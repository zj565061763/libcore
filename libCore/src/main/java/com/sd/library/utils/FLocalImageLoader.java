package com.sd.library.utils;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import java.util.ArrayList;
import java.util.List;

/**
 * 加载本地的图片
 */
public class FLocalImageLoader
{
    public static final Uri URI_ALL_IMAGE = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    private FragmentActivity mActivity;
    private Loader<Cursor> mLoader;
    private Callback mCallback;

    public FLocalImageLoader(FragmentActivity activity)
    {
        this.mActivity = activity;
        if (activity == null)
        {
            throw new NullPointerException("activity must not be null");
        }
    }

    public void setCallback(Callback callback)
    {
        mCallback = callback;
    }

    /**
     * 开始加载
     */
    public void startLoading()
    {
        startLoading(URI_ALL_IMAGE);
    }

    /**
     * 开始加载
     *
     * @param uri
     */
    public void startLoading(final Uri uri)
    {
        stopLoading();
        mLoader = mActivity.getSupportLoaderManager().initLoader(0, null, new LoaderCallbacks<Cursor>()
        {

            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args)
            {
                CursorLoader loader = new CursorLoader(mActivity);
                loader.setUri(uri);
                loader.setSortOrder(MediaStore.Images.Media.DATE_ADDED + " DESC");
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
            {
                List<ImageModel> listImage = getImageFromCursor(cursor);
                if (mCallback != null)
                {
                    mCallback.onResult(listImage);
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader)
            {
            }
        });
        mLoader.startLoading();
    }

    /**
     * 停止加载
     */
    public void stopLoading()
    {
        if (mLoader != null)
        {
            mLoader.stopLoading();
        }
    }

    private List<ImageModel> getImageFromCursor(Cursor cursor)
    {
        List<ImageModel> listImage = new ArrayList<>();
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                int id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
                String bucketId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_ID));
                String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                long dateAdded = cursor.getLong(cursor.getColumnIndex((MediaStore.MediaColumns.DATE_ADDED)));
                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.SIZE));

                if (size < 1)
                {
                    continue;
                }

                ImageModel model = new ImageModel();
                model.id = id;
                model.bucketId = bucketId;
                model.displayName = displayName;
                model.dateAdded = dateAdded;
                model.uri = data;
                model.size = size;
                listImage.add(model);
            }
        }
        return listImage;
    }

    public static class ImageModel
    {
        public int id;
        public String bucketId;
        /**
         * 照片添加日期
         */
        public long dateAdded;
        /**
         * 照片路径
         */
        public String uri;
        /**
         * 大小
         */
        public long size;
        public String displayName;
    }

    public interface Callback
    {
        void onResult(List<ImageModel> listImage);
    }
}
