package com.fanwe.library.utils;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

public class SDLocalImageFinder
{

	public static final Uri URI_IMAGES = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	public static final Uri URI_IMAGES_THUMBNAILS = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;;

	private FragmentActivity mActivity;

	public SDLocalImageFinder(FragmentActivity activity)
	{
		this.mActivity = activity;
	}

	public void loadLocalImagesThumbnails(SDLocalImageFinderListener listener)
	{
		loadCursor(URI_IMAGES_THUMBNAILS, listener);
	}

	public void loadLocalImages(SDLocalImageFinderListener listener)
	{
		loadCursor(URI_IMAGES, listener);
	}

	private CursorLoader createCursorLoader(Uri uri)
	{
		CursorLoader cursorLoader = null;
		if (uri == URI_IMAGES)
		{
			cursorLoader = new CursorLoader(mActivity, uri, null, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
		} else if (uri == URI_IMAGES_THUMBNAILS)
		{
			cursorLoader = new CursorLoader(mActivity, uri, null, null, null, MediaStore.Images.Thumbnails.DEFAULT_SORT_ORDER);
		}
		return cursorLoader;
	}

	private void loadCursor(final Uri uri, final SDLocalImageFinderListener listener)
	{
		Loader<Cursor> loader = mActivity.getSupportLoaderManager().initLoader(0, null, new LoaderCallbacks<Cursor>()
		{

			@Override
			public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1)
			{
				CursorLoader cursorLoader = createCursorLoader(uri);
				return cursorLoader;
			}

			@Override
			public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
			{
				loader.stopLoading();
				onLoadCursorFinished(uri, loader, cursor, listener);
			}

			@Override
			public void onLoaderReset(Loader<Cursor> loader)
			{

			}
		});
		loader.startLoading();
	}

	protected void onLoadCursorFinished(Uri uri, Loader<Cursor> loader, Cursor cursor, SDLocalImageFinderListener listener)
	{
		List<SDLocalImageModel> listModel = new ArrayList<SDLocalImageModel>();
		if (uri == URI_IMAGES)
		{
			listModel = getImagesFromCursor(cursor);
		} else if (uri == URI_IMAGES_THUMBNAILS)
		{
			listModel = getImagesThumbnailsFromCursor(cursor);
		}

		if (listener != null)
		{
			listener.onSuccess(listModel);
		}
	}

	public List<SDLocalImageModel> getImagesThumbnailsFromCursor(Cursor cursor)
	{
		List<SDLocalImageModel> listModel = new ArrayList<SDLocalImageModel>();
		if (cursor != null)
		{
			while (cursor.moveToNext())
			{
				SDLocalImageModel model = new SDLocalImageModel();
				String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
				model.path = path;
				listModel.add(model);
			}
		}
		return listModel;
	}

	public List<SDLocalImageModel> getImagesFromCursor(Cursor cursor)
	{
		List<SDLocalImageModel> listModel = new ArrayList<SDLocalImageModel>();
		if (cursor != null)
		{
			while (cursor.moveToNext())
			{
				SDLocalImageModel model = new SDLocalImageModel();
				String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
				model.path = path;
				listModel.add(model);
			}
		}
		return listModel;
	}

	public interface SDLocalImageFinderListener
	{
		public void onSuccess(List<SDLocalImageModel> listPath);
	}

	public static class SDLocalImageModel
	{
		/** 图片文件路径 */
		public String path;
	}

	public static class SDLocalImageThumbnailModel
	{
		/** 缩略图文件路径 */
		public String path;
		/** 缩略图对应的大图在数据库中的id */
		public long imageId;
	}

}
