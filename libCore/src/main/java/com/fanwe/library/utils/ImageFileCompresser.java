package com.fanwe.library.utils;

import com.fanwe.library.SDLibrary;
import com.fanwe.library.common.SDHandlerManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageFileCompresser
{

    public static final String COMPRESSED_IMAGE_FILE_DIR_NAME = "compressed_image";
    private long mMaxLength = 1024 * 1024;

    private File mCompressedFileDir;

    public ImageFileCompresser()
    {
        mCompressedFileDir = SDFileUtil.getCacheDir(SDLibrary.getInstance().getContext(), COMPRESSED_IMAGE_FILE_DIR_NAME);
    }

    private ImageFileCompresserListener mListener;

    public void setmListener(ImageFileCompresserListener mListener)
    {
        this.mListener = mListener;
    }

    public long getmMaxLength()
    {
        return mMaxLength;
    }

    public void setmMaxLength(long mMaxLength)
    {
        this.mMaxLength = mMaxLength;
    }

    public void compressImageFile(final List<File> listImageFile)
    {
        if (listImageFile == null)
        {
            return;
        }

        if (listImageFile.size() <= 0)
        {
            return;
        }

        if (mCompressedFileDir == null)
        {
            notifyFailure("获取SD卡缓存目录失败");
            return;
        }

        // 开线程执行
        SDHandlerManager.getBackgroundHandler().post(new Runnable()
        {
            @Override
            public void run()
            {
                notifyStart();
                for (int i = 0; i < listImageFile.size(); i++)
                {
                    File imageFile = listImageFile.get(i);
                    if (imageFile != null && imageFile.exists())
                    {
                        final File fileCompressed = createCompressedImageFile();
                        if (mMaxLength > 0 && imageFile.length() > mMaxLength)
                        {
                            SDImageUtil.compressImageFileToNewFileSize(imageFile, fileCompressed, mMaxLength);
                        } else
                        {
                            SDFileUtil.copy(imageFile.getAbsolutePath(), fileCompressed.getAbsolutePath());
                        }
                        notifySuccess(fileCompressed);
                    } else
                    {
                        notifyFailure("第" + i + "张图片不存在，已跳过");
                    }
                }
                notifyFinish();
            }
        });
    }

    public void compressImageFile(File imageFile)
    {
        List<File> listImageFile = new ArrayList<File>();
        listImageFile.add(imageFile);
        compressImageFile(listImageFile);
    }

    private File createCompressedImageFile()
    {
        long current = System.currentTimeMillis();
        File compressedImageFile = new File(mCompressedFileDir, current + ".jpg");
        try
        {
            while (compressedImageFile.exists())
            {
                current++;
                compressedImageFile = new File(mCompressedFileDir, current + ".jpg");
            }
        } catch (Exception e)
        {
            notifyFailure("创建照片文件失败:" + e.toString());
        }
        return compressedImageFile;
    }

    public void deleteCompressedImageFile()
    {
        if (mCompressedFileDir != null)
        {
            SDFileUtil.deleteFileOrDir(mCompressedFileDir);
        }
    }

    protected void notifyStart()
    {
        if (mListener != null)
        {
            SDHandlerManager.getMainHandler().post(new Runnable()
            {

                @Override
                public void run()
                {
                    mListener.onStart();
                }
            });
        }
    }

    protected void notifySuccess(final File file)
    {
        if (mListener != null)
        {
            SDHandlerManager.getMainHandler().post(new Runnable()
            {

                @Override
                public void run()
                {
                    mListener.onSuccess(file);
                }
            });
        }
    }

    protected void notifyFailure(final String msg)
    {
        if (mListener != null)
        {
            SDHandlerManager.getMainHandler().post(new Runnable()
            {

                @Override
                public void run()
                {
                    mListener.onFailure(msg);
                }
            });
        }
    }

    protected void notifyFinish()
    {
        if (mListener != null)
        {
            SDHandlerManager.getMainHandler().post(new Runnable()
            {

                @Override
                public void run()
                {
                    mListener.onFinish();
                }
            });
        }
    }

    public interface ImageFileCompresserListener
    {
        void onStart();

        void onSuccess(File fileCompressed);

        void onFailure(String msg);

        void onFinish();
    }

}
