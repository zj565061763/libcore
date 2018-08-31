package com.sd.libcore.utils;

import android.content.Context;
import android.os.Environment;

import com.sd.libcore.FLibrary;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class ImageFileCompresser
{

    public static final String COMPRESSED_IMAGE_FILE_DIR_NAME = "compressed_image";
    private long mMaxLength = 1024 * 1024;

    private File mCompressedFileDir;

    public ImageFileCompresser()
    {
        mCompressedFileDir = getCacheDir(COMPRESSED_IMAGE_FILE_DIR_NAME, FLibrary.getInstance().getContext());
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
                            copy(imageFile, fileCompressed);
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
        deleteFileOrDir(mCompressedFileDir);
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

    private static File getCacheDir(String dirName, Context context)
    {
        File dir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            dir = new File(context.getExternalCacheDir(), dirName);
        } else
        {
            dir = new File(context.getCacheDir(), dirName);
        }
        return mkdirs(dir);
    }

    private static File mkdirs(File dir)
    {
        if (dir == null || dir.exists())
            return dir;

        try
        {
            return dir.mkdirs() ? dir : null;
        } catch (Exception e)
        {
            return null;
        }
    }

    private static boolean deleteFileOrDir(File file)
    {
        if (file == null || !file.exists())
            return true;

        if (file.isFile())
            return file.delete();

        final File[] files = file.listFiles();
        if (files != null)
        {
            for (File item : files)
            {
                deleteFileOrDir(item);
            }
        }
        return file.delete();
    }

    private static boolean copy(File fileFrom, File fileTo)
    {
        if (fileFrom == null || !fileFrom.exists())
        {
            return false;
        }
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try
        {
            if (!fileTo.exists())
            {
                fileTo.createNewFile();
            }
            inputStream = new FileInputStream(fileFrom);
            outputStream = new FileOutputStream(fileTo);
            copy(inputStream, outputStream);
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        } finally
        {
            closeQuietly(inputStream);
            closeQuietly(outputStream);
        }
    }

    private static void copy(InputStream inputStream, OutputStream outputStream) throws IOException
    {
        if (!(inputStream instanceof BufferedInputStream))
        {
            inputStream = new BufferedInputStream(inputStream);
        }
        if (!(outputStream instanceof BufferedOutputStream))
        {
            outputStream = new BufferedOutputStream(outputStream);
        }
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = inputStream.read(buffer)) != -1)
        {
            outputStream.write(buffer, 0, len);
        }
        outputStream.flush();
    }

    private static void closeQuietly(Closeable closeable)
    {
        if (closeable != null)
        {
            try
            {
                closeable.close();
            } catch (Throwable ignored)
            {
            }
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
