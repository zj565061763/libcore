package com.fanwe.library.utils;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.fanwe.library.model.ImageModel;

import java.util.ArrayList;
import java.util.List;

public class LocalImageFinder
{

    public static final Uri URI_ALL_IMAGE = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    private FragmentActivity mActivity;

    public LocalImageFinder(FragmentActivity activity)
    {
        this.mActivity = activity;
    }

    public void getLocalImage(final LocalImageFinderListener listener)
    {
        getLocalImage(URI_ALL_IMAGE, listener);
    }

    public void getLocalImage(final Uri uri, final LocalImageFinderListener listener)
    {
        Loader<Cursor> loader = mActivity.getSupportLoaderManager().initLoader(0, null, new LoaderCallbacks<Cursor>()
        {

            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args)
            {
                CursorLoader cursorLoader = new CursorLoader(mActivity);
                cursorLoader.setUri(uri);
                cursorLoader.setSortOrder(MediaStore.Images.Media.DATE_ADDED + " DESC");
                return cursorLoader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
            {
                List<ImageModel> listImage = getImageFromCursor(cursor);
                if (listener != null)
                {
                    listener.onResult(listImage);
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader)
            {
            }
        });
    }

    public List<ImageModel> getImageFromCursor(Cursor cursor)
    {
        List<ImageModel> listImage = new ArrayList<ImageModel>();

        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                int id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
                String bucketId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_ID));
                String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                long date_added = cursor.getLong(cursor.getColumnIndex((MediaStore.MediaColumns.DATE_ADDED)));
                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.SIZE));

                if (size < 1)
                {
                    continue;
                }

                ImageModel model = new ImageModel();
                model.setId(id);
                model.setBucketId(bucketId);
                model.setDisplayName(displayName);
                model.setAddDate(date_added);
                model.setUri(data);
                model.setSize(size);
                listImage.add(model);
            }
        }
        return listImage;
    }

    public interface LocalImageFinderListener
    {
        void onResult(List<ImageModel> listImage);
    }

}
