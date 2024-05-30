package com.haier.cellarette.baselibrary.shuiyin;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.haier.cellarette.baselibrary.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShuiyinUtils3 {
    private static volatile ShuiyinUtils3 instance;
    private Context mContext;

    public ShuiyinUtils3(Context context) {
        mContext = context;
    }

    public static ShuiyinUtils3 getInstance(Context context) {
        if (instance == null) {
            synchronized (ShuiyinUtils3.class) {
                instance = new ShuiyinUtils3(context);
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

    public void diaoyong1(View iv_shang, View iv_zhong, View iv_xia, ImageView iv1, String lujing1, String filename1) {

        //将剪裁后照片显示出来
//        iv1.setImageBitmap(bitmap);
    }


    // 成功保存到相册
    public void diaoyong2(ImageView view, String lujing2) {
        //
        Bitmap bitmap = BitmapFactory.decodeFile(lujing2);
//        //将剪裁后照片显示出来
        view.setImageBitmap(bitmap);
        //水印图片保存到相册(需要保存到相册的话调用这个方法)
//        saveImageToGallery1(bitmap, lujing2);
        //
//        ToastUtils.showLong("成功保存到相册");
    }

    // 保存到相册的方法
    public void saveImageToGallery1(String text, Bitmap image, String lujingname1, String fileName1) {
        long timeStamp = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(new Date(timeStamp));
        String fileName = sd + ".png";
        if (!TextUtils.isEmpty(fileName1)) {
            fileName = fileName1 + ".png";
        }
        final ContentValues values = new ContentValues();
//        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "erweima16"); //Environment.DIRECTORY_SCREENSHOTS:截图,图库中显示的文件夹名。"dh"
//        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES); //Environment.DIRECTORY_SCREENSHOTS:截图,图库中显示的文件夹名。"dh"
        String filepath = Utils.getApp().getExternalFilesDir(null).getPath() + File.separator + lujingname1 + File.separator;
        String filepath2 = Environment.DIRECTORY_PICTURES + File.separator + lujingname1;
//        // 创建图片文件
////        String filename = filename1 + ".jpg";
        String url = filepath2 + File.separator + fileName;
//        File cropFile = new File(url);
//        try {
//            if (cropFile.exists()) {
//                cropFile.delete();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, filepath2); //Environment.DIRECTORY_SCREENSHOTS:截图,图库中显示的文件夹名。"dh"
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
            //
            new SingleMediaScanner3(mContext, new File(url), new SingleMediaScanner3.ScanListener() {
                @Override
                public void onScanFinish() {
                    ToastUtils.showLong(text);
                }
            });
            //
//            new SingleMediaScanner(mContext, filepath2 + fileName + File.separator, new SingleMediaScanner.ScanListener() {
//                @Override
//                public void onScanFinish() {
//                    // scanning...
//                    ((Activity) mContext).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            ToastUtils.showLong(text);
//                        }
//                    });
//                }
//            });
        } catch (IOException e) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                resolver.delete(uri, null);
            }
        }
    }


    //   shuiyin2_geek    filename_geek
    public String init1(String lujing1, String filename1) {
        //创建目录
        String filepath = Utils.getApp().getExternalFilesDir(null).getPath() + File.separator + lujing1 + File.separator;
        File file = new File(filepath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.i("MyPictures", "创建图片存储路径目录失败");
                Log.i("MyPictures", "mediaStorageDir : " + file.getPath());
            }
        }
        // 创建图片文件
        String filename = filename1 + ".jpg";
        String url = filepath + File.separator + filename;
        File cropFile = new File(url);
        try {
            if (cropFile.exists()) {
                cropFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 高清截屏拼接图片(上下拼接)
     *
     * @param topView
     * @param bottomView
     * @return
     */
    public Bitmap mergeBitmapTopBottom(View topView, View bottomView) {
        int topW = topView.getWidth();
        int topH = topView.getHeight();
        int bottomW = bottomView.getWidth();
        int bottomH = bottomView.getHeight();

        Bitmap topBmp = Bitmap.createBitmap(topW, topH, Bitmap.Config.RGB_565);
        Canvas topCanvas = new Canvas(topBmp);
        /** 如果不设置canvas画布为白色，则生成透明 */
        topCanvas.drawColor(Color.WHITE);
        topView.layout(0, 0, topW, topH);
        topView.draw(topCanvas);

        Bitmap bottomBmp = Bitmap.createBitmap(bottomW, bottomH, Bitmap.Config.RGB_565);
        Canvas bottomCanvas = new Canvas(bottomBmp);
        /** 如果不设置canvas画布为白色，则生成透明 */
        bottomCanvas.drawColor(Color.WHITE);
        bottomView.layout(0, 0, bottomW, bottomH);
        bottomView.draw(bottomCanvas);

        Bitmap bitmap = Bitmap.createBitmap(topW, topH + bottomH, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);

        Rect topRect = new Rect(0, 0, topW, topH);
        Rect bottomRect = new Rect(0, 0, bottomW, bottomH);

        Rect bottomDst = new Rect(0, topH, bottomW, topH + bottomH);

        canvas.drawBitmap(topBmp, topRect, topRect, null);
        canvas.drawBitmap(bottomBmp, bottomRect, bottomDst, null);
        topBmp.recycle();
        bottomBmp.recycle();
        topBmp = null;
        bottomBmp = null;
        return bitmap;
    }

    /**
     * 高清截屏拼接图片(上下拼接)
     *
     * @param topView
     * @param bottomView
     * @return
     */
    public Bitmap mergeBitmapTopBottom2(View topView, View bottomView) {
        int topW = topView.getWidth();
        int topH = topView.getHeight();
        int bottomW = bottomView.getWidth();
        int bottomH = bottomView.getHeight();

        Bitmap topBmp = Bitmap.createBitmap(topW, topH, Bitmap.Config.RGB_565);
        Canvas topCanvas = new Canvas(topBmp);
        /** 如果不设置canvas画布为白色，则生成透明 */
        topCanvas.drawColor(Color.WHITE);
        topView.layout(0, 0, topW, topH);
        topView.draw(topCanvas);

        Bitmap bottomBmp = Bitmap.createBitmap(bottomW, bottomH, Bitmap.Config.RGB_565);
        Canvas bottomCanvas = new Canvas(bottomBmp);
        /** 如果不设置canvas画布为白色，则生成透明 */
        bottomCanvas.drawColor(Color.WHITE);
        bottomView.layout(0, 0, bottomW, bottomH);
        bottomView.draw(bottomCanvas);

        Bitmap bitmap = Bitmap.createBitmap(topW, topH + bottomH, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);

        Rect topRect = new Rect(0, 0, topW, topH);
        Rect bottomRect = new Rect(0, 0, bottomW, bottomH);

        Rect bottomDst = new Rect(0, topH, bottomW, topH + bottomH);

        canvas.drawBitmap(topBmp, topRect, topRect, null);
        canvas.drawBitmap(bottomBmp, bottomRect, bottomDst, null);
        topBmp.recycle();
        bottomBmp.recycle();
        topBmp = null;
        bottomBmp = null;
        return bitmap;
    }

    /**
     * 拼接长图，通过topNestedScrollView + bottomView实现 NestedScrollView截长图并拼接bottomView
     *
     * @param nestedScrollView
     * @param bottomView
     * @return
     */
    public Bitmap mergeBitmapTopBottomByScrollView(NestedScrollView nestedScrollView, View bottomView) {
        int topW = nestedScrollView.getWidth();
        int topH = 0;
        int bottomW = bottomView.getWidth();
        int bottomH = bottomView.getHeight();
        for (int i = 0; i < nestedScrollView.getChildCount(); i++) {
            topH += nestedScrollView.getChildAt(i).getHeight();
            nestedScrollView.getChildAt(i).setBackgroundColor(ContextCompat.getColor(nestedScrollView.getContext(), R.color.color_E6F1FF));
        }

        Bitmap topBitmap = Bitmap.createBitmap(topW, topH, Bitmap.Config.RGB_565);
        Canvas topCanvas = new Canvas(topBitmap);
        topCanvas.drawColor(Color.WHITE);
        nestedScrollView.layout(0, 0, topW, topH);
        nestedScrollView.draw(topCanvas);


        Bitmap bottomBitmap = Bitmap.createBitmap(bottomW, bottomH, Bitmap.Config.RGB_565);
        Canvas bottomCanvas = new Canvas(bottomBitmap);
        /** 如果不设置canvas画布为白色，则生成透明 */
        bottomCanvas.drawColor(Color.WHITE);
        bottomView.layout(0, 0, bottomW, bottomH);
        bottomView.draw(bottomCanvas);


        Bitmap bitmap = Bitmap.createBitmap(topW, topH + bottomH, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);


        Rect topRect = new Rect(0, 0, topW, topH);
        Rect bottomRect = new Rect(0, 0, bottomW, bottomH);

        Rect bottomDst = new Rect(0, topH, bottomW, topH + bottomH);

        canvas.drawBitmap(topBitmap, topRect, topRect, null);
        canvas.drawBitmap(bottomBitmap, bottomRect, bottomDst, null);
        topBitmap.recycle();
        bottomBitmap.recycle();
        topBitmap = null;
        bottomBitmap = null;
        return bitmap;
    }

    /**
     * 拼接长图，通过topNestedScrollView + bottomView实现 NestedScrollView截长图并拼接bottomView
     *
     * @param nestedScrollView
     * @param bottomView
     * @return
     */
    public Bitmap mergeBitmapTopBottomByScrollView2(int colorid, View topView,
                                                    LinearLayout nestedScrollView, int resid, View bottomView) {
        //
        int topW = topView.getWidth();
        int topH = topView.getHeight();
        int centerW = nestedScrollView.getWidth();
        int centerH = 0;
        nestedScrollView.setBackgroundResource(resid);
        for (int i = 0; i < nestedScrollView.getChildCount(); i++) {
            centerH += nestedScrollView.getChildAt(i).getHeight();
//            nestedScrollView.getChildAt(i).setBackgroundColor(ContextCompat.getColor(nestedScrollView.getContext(), R.color.color_E6F1FF));
//            nestedScrollView.getChildAt(i).setBackgroundResource(resid);
        }
        int bottomW = bottomView.getWidth();
        int bottomH = bottomView.getHeight();
        // 上
        Bitmap topBitmap = Bitmap.createBitmap(topW, topH, Bitmap.Config.RGB_565);
        Canvas topCanvas = new Canvas(topBitmap);
//        topCanvas.drawColor(Color.WHITE);
        topCanvas.drawColor(ContextCompat.getColor(mContext, colorid));
//        topView.layout(0, 0, topW, topH);
        topView.draw(topCanvas);
        // 中
        Bitmap centerBitmap = Bitmap.createBitmap(centerW, centerH, Bitmap.Config.RGB_565);
        Canvas centerCanvas = new Canvas(centerBitmap);
        centerCanvas.drawColor(ContextCompat.getColor(mContext, colorid));
//        nestedScrollView.layout(0, 0, centerW, centerH);
        nestedScrollView.draw(centerCanvas);
        // 下
        Bitmap bottomBitmap = Bitmap.createBitmap(bottomW, bottomH, Bitmap.Config.RGB_565);
        Canvas bottomCanvas = new Canvas(bottomBitmap);
        /** 如果不设置canvas画布为白色，则生成透明 */
        bottomCanvas.drawColor(ContextCompat.getColor(mContext, colorid));
//        bottomView.layout(0, 0, bottomW, bottomH);
        bottomView.draw(bottomCanvas);
        //
        Bitmap bitmap = Bitmap.createBitmap(topW, topH + centerH + bottomH, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //
        Rect topRect = new Rect(0, 0, topW, topH);
        Rect centerRect = new Rect(0, 0, centerW, centerH);
        Rect bottomRect = new Rect(0, 0, bottomW, bottomH);

        Rect bottomDst1 = new Rect(0, 0, topW, topH);
        Rect bottomDst2 = new Rect(0, topH, bottomW, topH + centerH);
        Rect bottomDst3 = new Rect(0, topH + centerH, bottomW, topH + centerH + bottomH);
        //
        canvas.drawBitmap(topBitmap, topRect, bottomDst1, null);
        canvas.drawBitmap(centerBitmap, centerRect, bottomDst2, null);
        canvas.drawBitmap(bottomBitmap, bottomRect, bottomDst3, null);
        topBitmap.recycle();
        centerBitmap.recycle();
        bottomBitmap.recycle();
        topBitmap = null;
        centerBitmap = null;
        bottomBitmap = null;
//        ToastUtils.showLong(text);
        return bitmap;
    }
    public Bitmap mergeBitmapTopBottomByScrollView2(int colorid, View topView,
                                                    NestedScrollView nestedScrollView, int resid, View bottomView) {
        //
        int topW = topView.getWidth();
        int topH = topView.getHeight();
        int centerW = nestedScrollView.getWidth();
        int centerH = 0;
        nestedScrollView.setBackgroundResource(resid);
        for (int i = 0; i < nestedScrollView.getChildCount(); i++) {
            centerH += nestedScrollView.getChildAt(i).getHeight();
//            nestedScrollView.getChildAt(i).setBackgroundColor(ContextCompat.getColor(nestedScrollView.getContext(), R.color.color_E6F1FF));
//            nestedScrollView.getChildAt(i).setBackgroundResource(resid);
        }
        int bottomW = bottomView.getWidth();
        int bottomH = bottomView.getHeight();
        // 上
        Bitmap topBitmap = Bitmap.createBitmap(topW, topH, Bitmap.Config.RGB_565);
        Canvas topCanvas = new Canvas(topBitmap);
//        topCanvas.drawColor(Color.WHITE);
        topCanvas.drawColor(ContextCompat.getColor(mContext, colorid));
//        topView.layout(0, 0, topW, topH);
        topView.draw(topCanvas);
        // 中
        Bitmap centerBitmap = Bitmap.createBitmap(centerW, centerH, Bitmap.Config.RGB_565);
        Canvas centerCanvas = new Canvas(centerBitmap);
        centerCanvas.drawColor(ContextCompat.getColor(mContext, colorid));
//        nestedScrollView.layout(0, 0, centerW, centerH);
        nestedScrollView.draw(centerCanvas);
        // 下
        Bitmap bottomBitmap = Bitmap.createBitmap(bottomW, bottomH, Bitmap.Config.RGB_565);
        Canvas bottomCanvas = new Canvas(bottomBitmap);
        /** 如果不设置canvas画布为白色，则生成透明 */
        bottomCanvas.drawColor(ContextCompat.getColor(mContext, colorid));
//        bottomView.layout(0, 0, bottomW, bottomH);
        bottomView.draw(bottomCanvas);
        //
        Bitmap bitmap = Bitmap.createBitmap(topW, topH + centerH + bottomH, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //
        Rect topRect = new Rect(0, 0, topW, topH);
        Rect centerRect = new Rect(0, 0, centerW, centerH);
        Rect bottomRect = new Rect(0, 0, bottomW, bottomH);

        Rect bottomDst1 = new Rect(0, 0, topW, topH);
        Rect bottomDst2 = new Rect(0, topH, bottomW, topH + centerH);
        Rect bottomDst3 = new Rect(0, topH + centerH, bottomW, topH + centerH + bottomH);
        //
        canvas.drawBitmap(topBitmap, topRect, bottomDst1, null);
        canvas.drawBitmap(centerBitmap, centerRect, bottomDst2, null);
        canvas.drawBitmap(bottomBitmap, bottomRect, bottomDst3, null);
        topBitmap.recycle();
        centerBitmap.recycle();
        bottomBitmap.recycle();
        topBitmap = null;
        centerBitmap = null;
        bottomBitmap = null;
//        ToastUtils.showLong(text);
        return bitmap;
    }
    public Bitmap mergeBitmapTopBottomByScrollView2(int colorid, View topView,
                                                    ScrollView nestedScrollView, int resid, View bottomView) {
        //
        int topW = topView.getWidth();
        int topH = topView.getHeight();
        int centerW = nestedScrollView.getWidth();
        int centerH = 0;
        nestedScrollView.setBackgroundResource(resid);
        for (int i = 0; i < nestedScrollView.getChildCount(); i++) {
            centerH += nestedScrollView.getChildAt(i).getHeight();
//            nestedScrollView.getChildAt(i).setBackgroundColor(ContextCompat.getColor(nestedScrollView.getContext(), R.color.color_E6F1FF));
//            nestedScrollView.getChildAt(i).setBackgroundResource(resid);
        }
        int bottomW = bottomView.getWidth();
        int bottomH = bottomView.getHeight();
        // 上
        Bitmap topBitmap = Bitmap.createBitmap(topW, topH, Bitmap.Config.RGB_565);
        Canvas topCanvas = new Canvas(topBitmap);
//        topCanvas.drawColor(Color.WHITE);
        topCanvas.drawColor(ContextCompat.getColor(mContext, colorid));
//        topView.layout(0, 0, topW, topH);
        topView.draw(topCanvas);
        // 中
        Bitmap centerBitmap = Bitmap.createBitmap(centerW, centerH, Bitmap.Config.RGB_565);
        Canvas centerCanvas = new Canvas(centerBitmap);
        centerCanvas.drawColor(ContextCompat.getColor(mContext, colorid));
//        nestedScrollView.layout(0, 0, centerW, centerH);
        nestedScrollView.draw(centerCanvas);
        // 下
        Bitmap bottomBitmap = Bitmap.createBitmap(bottomW, bottomH, Bitmap.Config.RGB_565);
        Canvas bottomCanvas = new Canvas(bottomBitmap);
        /** 如果不设置canvas画布为白色，则生成透明 */
        bottomCanvas.drawColor(ContextCompat.getColor(mContext, colorid));
//        bottomView.layout(0, 0, bottomW, bottomH);
        bottomView.draw(bottomCanvas);
        //
        Bitmap bitmap = Bitmap.createBitmap(topW, topH + centerH + bottomH, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //
        Rect topRect = new Rect(0, 0, topW, topH);
        Rect centerRect = new Rect(0, 0, centerW, centerH);
        Rect bottomRect = new Rect(0, 0, bottomW, bottomH);

        Rect bottomDst1 = new Rect(0, 0, topW, topH);
        Rect bottomDst2 = new Rect(0, topH, bottomW, topH + centerH);
        Rect bottomDst3 = new Rect(0, topH + centerH, bottomW, topH + centerH + bottomH);
        //
        canvas.drawBitmap(topBitmap, topRect, bottomDst1, null);
        canvas.drawBitmap(centerBitmap, centerRect, bottomDst2, null);
        canvas.drawBitmap(bottomBitmap, bottomRect, bottomDst3, null);
        topBitmap.recycle();
        centerBitmap.recycle();
        bottomBitmap.recycle();
        topBitmap = null;
        centerBitmap = null;
        bottomBitmap = null;
//        ToastUtils.showLong(text);
        return bitmap;
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
