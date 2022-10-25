package com.geek.libshuiyin;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShuiyinUtils {
    private static volatile ShuiyinUtils instance;
    private Context mContext;

    public ShuiyinUtils(Context context) {
        mContext = context;
    }

    public static ShuiyinUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (ShuiyinUtils.class) {
                instance = new ShuiyinUtils(context);
            }
        }
        return instance;
    }


    public void diaoyong(ImageView iv1, String lujing) {
        Bitmap bitmap = BitmapFactory.decodeFile(lujing);
        //格式化时间数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String time = sdf.format(new Date().getTime());
        String time = sdf.format(System.currentTimeMillis());
        //添加时间数据和其他数据,如获取到的地理位置数据
        Bitmap img = createBitmap(bitmap, lujing, null, time, "位置信息:位置信息:位置信息:位置信息:位置信息:位置信息:位置信息:位置信息:位置信息:位置信息: ");
        //将剪裁后照片显示出来
        iv1.setImageBitmap(img);
        //水印图片保存到相册(需要保存到相册的话调用这个方法)
//        saveImageToGallery2(img);

    }

    // 保存到相册的方法
    public void saveImageToGallery2(Bitmap image) {
        long timeStamp = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(new Date(timeStamp));
        String fileName = sd + ".png";
        final ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES
                + File.separator + "erweima16"); //Environment.DIRECTORY_SCREENSHOTS:截图,图库中显示的文件夹名。"dh"
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
        values.put(MediaStore.MediaColumns.DATE_ADDED, timeStamp / 1000);
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, timeStamp / 1000);
        values.put(MediaStore.MediaColumns.DATE_EXPIRES, (timeStamp + DateUtils.DAY_IN_MILLIS) / 1000);
        values.put(MediaStore.MediaColumns.IS_PENDING, 1);

        ContentResolver resolver = mContext.getContentResolver();
        final Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        try {
            // First, write the actual data for our screenshot
            try (OutputStream out = resolver.openOutputStream(uri)) {
                if (!image.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                    throw new IOException("Failed to compress");
                }
            }
            // Everything went well above, publish it!
            values.clear();
            values.put(MediaStore.MediaColumns.IS_PENDING, 0);
            values.putNull(MediaStore.MediaColumns.DATE_EXPIRES);
            resolver.update(uri, values, null, null);
        } catch (IOException e) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                resolver.delete(uri, null);
            }
        }
    }

    public void saveBitmapToImage(Bitmap srcBip, int quality, Bitmap.CompressFormat format, String outPath, boolean recycle) throws IOException {
        if (TextUtils.isEmpty(outPath)) {
            throw new IOException("saveBitmapToImage pathName null or nil");
        }

        File file = new File(outPath);
        file.createNewFile();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            srcBip.compress(format, quality, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            if (recycle) {
                srcBip.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 添加水印的方法
    public Bitmap createBitmap(Bitmap src, String lujing, Bitmap waterMak, String title, String location) {
        if (src == null) {
            return src;
        }
        int width = src.getWidth();
        int height = src.getHeight();

        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(newBitmap);
        mCanvas.drawBitmap(src, 0, 0, null);
        if (null != waterMak) {
            int ww = waterMak.getWidth();
            int wh = waterMak.getHeight();
            // 在src的右下角添加水印
            Paint paint = new Paint();
            //paint.setAlpha(100);
            mCanvas.drawBitmap(waterMak, width - ww - 5, height - wh - 5, paint);
        }
        // 开始加入文字
        if (null != title) {
            Paint textPaint = new Paint();
            //设置字体颜色
            textPaint.setColor(Color.RED);
            //设置字体大小
            int i = (width + height) / 300 * 5;
            textPaint.setTextSize(i);
            //阴影设置
            textPaint.setShadowLayer(3f, 1, 1, Color.DKGRAY);
            String familyName = "宋体";
            Typeface typeface = Typeface.create(familyName,
                    Typeface.BOLD_ITALIC);
            textPaint.setTypeface(typeface);
            textPaint.setTextAlign(Paint.Align.CENTER);


            TextPaint textPaint2 = new TextPaint();
            //设置字体颜色
            textPaint2.setColor(Color.RED);
            //设置字体大小
            //根据图片大小设置字体大小
            textPaint2.setTextSize(i);
            //阴影设置
            textPaint2.setShadowLayer(3f, 1, 1, Color.DKGRAY);
            String familyName2 = "宋体";
            Typeface typeface2 = Typeface.create(familyName2,
                    Typeface.BOLD_ITALIC);
            textPaint2.setTypeface(typeface2);
            textPaint2.setTextAlign(Paint.Align.CENTER);
            float textWidth = textPaint2.measureText(title);
            //文字 添加位置
            mCanvas.drawText(title, textWidth / 2 + 50, 100, textPaint);          //时间
            StaticLayout layout = new StaticLayout(location, textPaint2, width / 2, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);

            Log.e("aaa", "textWidth:" + textWidth + "width:" + width);
            mCanvas.translate(width / 4, 150);
            layout.draw(mCanvas);

        }
        mCanvas.save();
        //保存
        mCanvas.restore();
        return newBitmap;
    }

}
