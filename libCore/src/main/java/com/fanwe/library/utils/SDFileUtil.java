package com.fanwe.library.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class SDFileUtil
{
    /**
     * sd卡是否存在
     *
     * @return
     */
    public static boolean isSdcardExist()
    {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获得sd卡根目录file对象引用
     *
     * @return
     */
    public static File getSdCardFile()
    {
        if (isSdcardExist())
        {
            return Environment.getExternalStorageDirectory();
        } else
        {
            return null;
        }
    }

    /**
     * 判断路径下的文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean isFileExist(String path)
    {
        boolean result = false;
        if (path != null)
        {
            result = new File(path).exists();
        }
        return result;
    }

    /**
     * 获得缓存目录
     *
     * @param context
     * @param dirName 缓存目录下的文件夹名字
     * @return
     */
    public static File getCacheDir(Context context, String dirName)
    {
        File result;
        if (isSdcardExist())
        {
            File cacheDir = context.getExternalCacheDir();
            if (cacheDir == null)
            {
                result = new File(Environment.getExternalStorageDirectory(),
                        "Android/data/" + context.getPackageName() + "/cache/" + dirName);
            } else
            {
                result = new File(cacheDir, dirName);
            }
        } else
        {
            result = new File(context.getCacheDir(), dirName);
        }
        if (result.exists() || result.mkdirs())
        {
            return result;
        } else
        {
            return null;
        }
    }

    /**
     * 获得公共的相册目录
     *
     * @return
     */
    public static File getPicturesDir()
    {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        return dir;
    }

    public static boolean copy(String fromPath, String toPath)
    {
        boolean result = false;
        File from = new File(fromPath);
        if (!from.exists())
        {
            return result;
        }

        File toFile = new File(toPath);
        deleteFileOrDir(toFile);
        File toDir = toFile.getParentFile();
        if (toDir.exists() || toDir.mkdirs())
        {
            FileInputStream in = null;
            FileOutputStream out = null;
            try
            {
                in = new FileInputStream(from);
                out = new FileOutputStream(toFile);
                IOUtil.copy(in, out);
                result = true;
            } catch (Throwable ex)
            {
                result = false;
            } finally
            {
                IOUtil.closeQuietly(in);
                IOUtil.closeQuietly(out);
            }
        }
        return result;
    }

    public static boolean writeToFile(String content, String filePath)
    {
        PrintWriter pw = null;
        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                file.createNewFile();
            }
            pw = new PrintWriter(file);
            pw.write(content);
            pw.flush();
            return true;
        } catch (Exception e)
        {
            return false;
        } finally
        {
            IOUtil.closeQuietly(pw);
        }
    }

    public static String readFromFile(String filePath)
    {
        BufferedReader reader = null;
        try
        {
            File file = new File(filePath);
            reader = new BufferedReader(new FileReader(file));
            StringBuffer strBuffer = new StringBuffer();
            String strLine = "";
            while ((strLine = reader.readLine()) != null)
            {
                strBuffer.append(strLine);
            }
            return strBuffer.toString();
        } catch (Exception e)
        {
            return null;
        } finally
        {
            IOUtil.closeQuietly(reader);
        }
    }

    /**
     * 获得文件或者文件夹下所有文件的大小
     *
     * @param file
     * @return
     */
    public static long getFileOrDirSize(File file)
    {
        if (file == null)
        {
            return 0;
        }
        if (!file.exists())
        {
            return 0;
        }
        if (!file.isDirectory())
        {
            return file.length();
        }
        long length = 0;
        File[] list = file.listFiles();
        if (list != null)
        {
            for (File item : list)
            {
                length += getFileOrDirSize(item);
            }
        }
        return length;
    }

    public static boolean deleteFileOrDir(File path)
    {
        if (path == null || !path.exists())
        {
            return true;
        }
        if (path.isFile())
        {
            return path.delete();
        }
        File[] files = path.listFiles();
        if (files != null)
        {
            for (File file : files)
            {
                deleteFileOrDir(file);
            }
        }
        return path.delete();
    }

    /**
     * 格式化文件大小
     *
     * @param fileLength
     * @return
     */
    public static String formatFileSize(long fileLength)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileLength <= 0)
        {
            fileSizeString = "0.00B";
        } else if (fileLength < 1024)
        {
            fileSizeString = df.format((double) fileLength) + "B";
        } else if (fileLength < 1048576)
        {
            fileSizeString = df.format((double) fileLength / 1024) + "KB";
        } else if (fileLength < 1073741824)
        {
            fileSizeString = df.format((double) fileLength / 1048576) + "MB";
        } else
        {
            fileSizeString = df.format((double) fileLength / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    public static String getExtString(File file)
    {
        String extString = null;
        if (file != null && file.exists())
        {
            String path = file.getAbsolutePath();
            int dotIndex = path.lastIndexOf(".");
            if (dotIndex >= 0)
            {
                extString = path.substring(dotIndex + 1);
            }
        }
        return extString;
    }

    public static String getMimeType(File file)
    {
        String mime = null;
        String extString = getExtString(file);
        mime = SDMimeTypeUtil.getMimeType(extString);
        return mime;
    }

    public static File createDefaultFileUnderDir(File dir)
    {
        return createDefaultFileUnderDir(dir, null);
    }

    public static File createDefaultFileUnderDir(File dir, String ext)
    {
        try
        {
            if (ext == null)
            {
                ext = "";
            }

            long current = System.currentTimeMillis();
            File file = new File(dir, String.valueOf(current + ext));
            while (file.exists())
            {
                current++;
                file = new File(dir, String.valueOf(current + ext));
            }
            return file;
        } catch (Exception e)
        {
            return null;
        }
    }

    public static void copyAnrToCache(Context context)
    {
        try
        {
            File saveDir = getCacheDir(context, "anr");
            if (saveDir == null)
            {
                return;
            }
            File anrFile = new File("/data/anr/traces.txt");
            if (!anrFile.exists())
            {
                return;
            }
            String content = readFromFile(anrFile.getAbsolutePath());
            if (TextUtils.isEmpty(content))
            {
                return;
            }

            File saveFile = new File(saveDir, "arn.txt");
            if (!saveFile.exists())
            {
                saveFile.createNewFile();
            }
            writeToFile(content, saveFile.getAbsolutePath());
            anrFile.delete();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
