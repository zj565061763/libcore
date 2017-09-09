package com.fanwe.library.utils;

import android.app.Activity;
import android.content.Context;
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
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;

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
                IOUtil.closeQuietly(fos);
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
                IOUtil.closeQuietly(fos);
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
                        SDFileUtil.copy(oldFile.getAbsolutePath(), newFile.getAbsolutePath());
                    }
                    return true;
                }
            } catch (Exception e)
            {
                return false;
            } finally
            {
                IOUtil.closeQuietly(fos);
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

    /**
     * 图片模糊处理
     *
     * @param bitmap  要处理的bitmap
     * @param radius  radius(0-25]
     * @param context
     * @return
     */
    public static Bitmap blurBitmap(Bitmap bitmap, float radius, Context context)
    {
        if (radius <= 0)
        {
            return bitmap;
        }
        if (radius > 25)
        {
            radius = 25;
        }

        // Let's create an empty bitmap with the same size of the bitmap we want
        // to blur
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        // Instantiate a new Renderscript
        RenderScript rs = RenderScript.create(context);

        // Create an Intrinsic Blur Script using the Renderscript
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        // Create the Allocations (in/out) with the Renderscript and the in/out
        // bitmaps
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        // Set the radius of the blur
        blurScript.setRadius(25.f);

        // Perform the Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        // Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);

        // recycle the original bitmap
        bitmap.recycle();

        // After finishing everything, we destroy the Renderscript.
        rs.destroy();

        return outBitmap;
    }

    public static Bitmap blurBitmap(Bitmap sentBitmap, int radius, boolean canReuseInBitmap)
    {

        // Stack Blur v1.0 from
        // http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
        //
        // Java Author: Mario Klingemann <mario at quasimondo.com>
        // http://incubator.quasimondo.com
        // created Feburary 29, 2004
        // Android port : Yahel Bouaziz <yahel at kayenko.com>
        // http://www.kayenko.com
        // ported april 5th, 2012

        // This is a compromise between Gaussian Blur and Box blur
        // It creates much better looking blurs than Box Blur, but is
        // 7x faster than my Gaussian Blur implementation.
        //
        // I called it Stack Blur because this describes best how this
        // filter works internally: it creates a kind of moving stack
        // of colors whilst scanning through the image. Thereby it
        // just has to add one new block of color to the right side
        // of the stack and remove the leftmost color. The remaining
        // colors on the topmost layer of the stack are either added on
        // or reduced by one, depending on if they are on the right or
        // on the left side of the stack.
        //
        // If you are using this algorithm in your code please add
        // the following line:
        //
        // Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>

        Bitmap bitmap;
        if (canReuseInBitmap)
        {
            bitmap = sentBitmap;
        } else
        {
            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        }

        if (radius < 1)
        {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++)
        {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++)
        {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++)
            {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0)
                {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else
                {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++)
            {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0)
                {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++)
        {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++)
            {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0)
                {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else
                {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm)
                {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++)
            {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0)
                {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }

}
