package com.geek.libshuiyin;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

import com.blankj.utilcode.util.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ShuiyinBitmapUtil {
    /**
     * 保存Bitmap为文件;可能报空指针是因为没有配置权限
     *
     * @param bmp
     * @param filename
     * @return
     */
    public static void saveBitmap2file(Bitmap bmp, String filename) {
        CompressFormat format = CompressFormat.PNG;
        int quality = 100;
        OutputStream stream = null;
        try {
            String filePath = Utils.getApp().getExternalFilesDir(null).getPath() + "/shuiyin2/" + File.separator + filename;
            File file = new File(filePath);
            stream = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bmp.compress(format, quality, stream);
        try {
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件为Bitmap
     *
     * @param filename
     * @return
     * @throws FileNotFoundException
     */
    public static Bitmap getBitmapFromFile(String filename) {
        try {
            String filePath = Utils.getApp().getExternalFilesDir(null).getPath() + "/face" + filename + ".png";
            File file = new File(filePath);
            return BitmapFactory.decodeStream(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
