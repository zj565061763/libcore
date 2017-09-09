package com.fanwe.library.handler;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.fanwe.library.utils.UriFileUtils;
import com.fanwe.library.utils.SDFileUtil;
import com.fanwe.library.utils.SDIntentUtil;
import com.fanwe.library.utils.SDOtherUtil;
import com.fanwe.library.utils.SDToast;

import java.io.File;

public class PhotoHandler extends OnActivityResultHandler
{
    public static final String TAKE_PHOTO_FILE_DIR_NAME = "take_photo";
    public static final int REQUEST_CODE_GET_PHOTO_FROM_CAMERA = 16542;
    public static final int REQUEST_CODE_GET_PHOTO_FROM_ALBUM = REQUEST_CODE_GET_PHOTO_FROM_CAMERA + 1;

    private PhotoHandlerListener listener;
    private File takePhotoFile;
    private File takePhotoDir;

    public void setListener(PhotoHandlerListener listener)
    {
        this.listener = listener;
    }

    public PhotoHandler(Fragment mFragment)
    {
        super(mFragment);
        init();
    }

    public PhotoHandler(FragmentActivity mActivity)
    {
        super(mActivity);
        init();
    }

    private void init()
    {
        takePhotoDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (takePhotoDir == null)
        {
            //如果图片目录为空，获取应用目录缓存目录
            takePhotoDir = mActivity.getCacheDir();
        } else
        {
            if (!takePhotoDir.exists())
            {
                takePhotoDir.mkdirs();
            }
        }
    }

    public void getPhotoFromAlbum()
    {
        try
        {
            Intent intent = SDIntentUtil.getIntentSelectLocalImage2();
            startActivityForResult(intent, REQUEST_CODE_GET_PHOTO_FROM_ALBUM);
        } catch (android.content.ActivityNotFoundException e)
        {
            SDToast.showToast("ActivityNotFoundException");
        }
    }

    public void getPhotoFromCamera()
    {
        if (takePhotoDir == null)
        {
            if (listener != null)
            {
                listener.onFailure("获取SD卡缓存目录失败");
            }
        } else
        {
            File takePhotoFile = SDFileUtil.createDefaultFileUnderDir(takePhotoDir, ".jpg");
            getPhotoFromCamera(takePhotoFile);
        }
    }

    public void getPhotoFromCamera(File saveFile)
    {
        takePhotoFile = saveFile;
        Intent intent = SDIntentUtil.getIntentTakePhoto(saveFile);
        startActivityForResult(intent, REQUEST_CODE_GET_PHOTO_FROM_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case REQUEST_CODE_GET_PHOTO_FROM_CAMERA:
                if (resultCode == Activity.RESULT_OK)
                {
                    if (listener != null)
                    {
                        if (takePhotoFile != null)
                        {
                            SDOtherUtil.scanFile(mActivity, takePhotoFile);
                            listener.onResultFromCamera(takePhotoFile);
                        } else
                        {

                        }
                    }
                }
                break;
            case REQUEST_CODE_GET_PHOTO_FROM_ALBUM:
                if (resultCode == Activity.RESULT_OK)
                {
                    //String  path = SDImageUtil.getImageFilePathFromIntent(data, mActivity);
                    String path = UriFileUtils.getPath(mActivity, data.getData());

                    if (listener != null)
                    {
                        if (TextUtils.isEmpty(path))
                        {
                            listener.onFailure("从相册获取图片失败");
                        } else
                        {
                            listener.onResultFromAlbum(new File(path));
                        }
                    }
                }
                break;

            default:
                break;
        }
    }

    public interface PhotoHandlerListener
    {
        void onResultFromAlbum(File file);

        void onResultFromCamera(File file);

        void onFailure(String msg);
    }

}
