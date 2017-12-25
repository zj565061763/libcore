package com.fanwe.library.utils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.fanwe.lib.utils.FIOUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class SDImageUtil
{

    public static Bitmap drawable2Bitmap(Drawable drawable)
    {

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        // canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static byte[] Bitmap2Bytes(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap bytes2Bimap(byte[] b)
    {
        if (b != null && b.length != 0)
        {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else
        {
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    public static Drawable Bitmap2Drawable(Bitmap bitmap)
    {
        BitmapDrawable bd = new BitmapDrawable(bitmap);
        return bd;
    }

    @SuppressWarnings("deprecation")
    public static String getImageFilePathFromIntent(Intent intent, Activity activity)
    {
        if (intent != null && activity != null)
        {
            Uri uri = intent.getData();
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            return path;
        } else
        {
            return null;
        }
    }

    public static byte[] compressImageFileToNewFileSize(File imageFile, int deltaQuality, long finalSize)
    {
        byte[] bmpByte = null;
        if (imageFile != null && imageFile.exists())
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try
            {
                int quality = 100;
                long length = 0;
                Bitmap bmpTemp = null;
                while (true)
                {
                    if (quality <= 1)
                    {
                        break;
                    }
                    baos.reset();
                    bmpTemp = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    bmpTemp.compress(Bitmap.CompressFormat.JPEG, quality, baos);
                    bmpTemp.recycle();

                    length = baos.size();
                    if (length > finalSize)
                    {
                        quality = quality - deltaQuality;
                        continue;
                    } else
                    {
                        // 压缩到指定大小成功
                        bmpByte = baos.toByteArray();
                        break;
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                baos.reset();
            }
        }
        return bmpByte;
    }

    public static boolean compressImageFileToNewFileSize(File oldFile, File newFile, long finalSize)
    {
        int targetWidth = 720;
        int deltaQuality = 5;
        return compressImageFileToNewFileSize(oldFile, newFile, targetWidth, deltaQuality, finalSize);
    }

    public static boolean compressImageFileToNewFileSize(File oldFile, File newFile, int targetWidth, int deltaQuality, long finalSize)
    {
        boolean result = false;
        if (compressImageFileToNewFileWidth(oldFile, newFile, targetWidth))
        {
            FileOutputStream fos = null;
            try
            {
                byte[] byteFile = compressImageFileToNewFileSize(newFile, deltaQuality, finalSize);
                if (byteFile != null)
                {
                    // 压缩到指定大小成功，保存byte数据到文件
                    if (newFile.exists())
                    {
                        newFile.delete();
                    }
                    newFile.createNewFile();
                    fos = new FileOutputStream(newFile);
                    fos.write(byteFile);
                    result = true;
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            } finally
            {
                FIOUtil.closeQuietly(fos);
            }
        }
        return result;
    }

    public static boolean compressImageFileToNewFileQuality(File oldFile, File newFile, int quality)
    {
        if (oldFile != null && newFile != null && oldFile.exists())
        {
            FileOutputStream fos = null;
            try
            {
                if (newFile.exists())
                {
                    newFile.delete();
                }
                newFile.createNewFile();

                if (newFile.exists())
                {
                    fos = new FileOutputStream(newFile);
                    Bitmap bmp = BitmapFactory.decodeFile(oldFile.getAbsolutePath());
                    bmp.compress(Bitmap.CompressFormat.JPEG, quality, fos);
                    bmp.recycle();
                    return true;
                }
            } catch (Exception e)
            {
                return false;
            } finally
            {
                FIOUtil.closeQuietly(fos);
            }
        }
        return false;
    }

    public static BitmapFactory.Options inJustDecodeBounds(String path)
    {
        BitmapFactory.Options options = null;
        try
        {
            if (!TextUtils.isEmpty(path) && new File(path).exists())
            {
                options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path, options);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return options;
    }

    public static boolean compressImageFileToNewFileWidth(File oldFile, File newFile, int targetWidth)
    {
        if (oldFile != null && newFile != null && oldFile.exists())
        {
            FileOutputStream fos = null;
            try
            {
                if (newFile.exists())
                {
                    newFile.delete();
                }
                newFile.createNewFile();

                if (newFile.exists())
                {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;

                    BitmapFactory.decodeFile(oldFile.getAbsolutePath(), options);
                    int originalWidth = options.outWidth;
                    int originalHeight = options.outHeight;
                    if (targetWidth < originalWidth)
                    {
                        int targetHeight = SDViewUtil.getScaleHeight(originalWidth, originalHeight, targetWidth);

                        BitmapSize maxSize = new BitmapSize(targetWidth, targetHeight);
                        Bitmap bmpSampled = BitmapDecoder.decodeSampledBitmapFromFile(oldFile.getAbsolutePath(), maxSize, null);

                        Bitmap bmpFinal = null;
                        if (bmpSampled.getWidth() == targetWidth && bmpSampled.getHeight() == targetHeight)
                        {
                            bmpFinal = bmpSampled;
                        } else
                        {
                            bmpFinal = ThumbnailUtils.extractThumbnail(bmpSampled, targetWidth, targetHeight);
                            bmpSampled.recycle();
                        }

                        fos = new FileOutputStream(newFile);
                        bmpFinal.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        bmpFinal.recycle();
                    } else
                    {
                        // 只做copy
                        FIOUtil.copy(oldFile, newFile);
                    }
                    return true;
                }
            } catch (Exception e)
            {
                return false;
            } finally
            {
                FIOUtil.closeQuietly(fos);
            }
        }
        return false;
    }

    public static Bitmap getGrayBitmap(Bitmap bmp)
    {
        Bitmap bmpGray = null;
        if (bmp != null)
        {
            int width = bmp.getWidth();
            int height = bmp.getHeight();

            bmpGray = Bitmap.createBitmap(width, height, Config.RGB_565);
            Canvas c = new Canvas(bmpGray);
            Paint paint = new Paint();
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0);
            ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
            paint.setColorFilter(f);
            c.drawBitmap(bmp, 0, 0, paint);
        }
        return bmpGray;
    }
}
