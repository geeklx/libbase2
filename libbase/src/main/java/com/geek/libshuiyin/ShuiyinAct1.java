package com.geek.libshuiyin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.Utils;
import com.geek.libbase.R;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.engine.CompressFileEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.utils.DateUtils;

import java.io.File;
import java.util.ArrayList;

import top.zibin.luban.Luban;
import top.zibin.luban.OnNewCompressListener;
import top.zibin.luban.OnRenameListener;

public class ShuiyinAct1 extends AppCompatActivity {

    private TextView tv1;
    private TextView tv2;
    private ImageView iv1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuiyin1);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        iv1 = findViewById(R.id.iv1);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开拍照自定义页面
                String url111 = Utils.getApp().getExternalFilesDir(null).getPath() + "/shuiyin/";
                File cropFile = new File(url111);
                if (!cropFile.exists()) {
                    if (!cropFile.mkdirs()) {
                        Log.i("MyPictures", "创建图片存储路径目录失败");
                        Log.i("MyPictures", "mediaStorageDir : " + cropFile.getPath());
                    }
                }
                PictureSelector.create(ShuiyinAct1.this)
                        .openCamera(SelectMimeType.ofImage())
//                        .setImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
//                        .compressSavePath("")//压缩图片保存地址
//                        .cameraFileName("shuiyin.jpg")// 使用相机时保存至本地的文件名称,注意这个只在拍照时可以使用
                        .isCameraRotateImage(true)//拍照是否纠正旋转图片
//                        .setCameraImageFormat(PictureMimeType.PNG)
//                        .setCameraImageFormatForQ(PictureMimeType.PNG)
                        .setCompressEngine(new ImageFileCompressEngine())
//                        .setOutputCameraDir(url111)
                        .forResult(new OnResultCallbackListener<LocalMedia>() {

                            @Override
                            public void onResult(ArrayList<LocalMedia> result) {
                                if (TextUtils.isEmpty(result.get(0).getCompressPath())) {
                                    return;
                                }
                                if (!TextUtils.isEmpty(result.get(0).getRealPath())) {
                                    Log.e("sssssssss", result.get(0).getRealPath());
                                    FileUtils.delete(result.get(0).getRealPath());
                                }
                                ShuiyinUtils.getInstance(ShuiyinAct1.this).diaoyong(iv1, result.get(0).getCompressPath());
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 自定义打开相机页面
                Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShuiyinAct3");
                startActivityForResult(intent, REQUESTCODE);

            }
        });

    }

    /**
     * 自定义压缩
     */
    private static class ImageFileCompressEngine implements CompressFileEngine {

        @Override
        public void onStartCompress(Context context, ArrayList<Uri> source, OnKeyValueResultCallbackListener call) {
            Luban.with(context).load(source).ignoreBy(100).setRenameListener(new OnRenameListener() {
                @Override
                public String rename(String filePath) {
                    int indexOf = filePath.lastIndexOf(".");
                    String postfix = indexOf != -1 ? filePath.substring(indexOf) : PictureMimeType.JPG;
                    return DateUtils.getCreateFileName("CMP_") + postfix;
                }
            }).setCompressListener(new OnNewCompressListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(String source, File compressFile) {
                    if (call != null) {
                        call.onCallback(source, compressFile.getAbsolutePath());
                    }
                }

                @Override
                public void onError(String source, Throwable e) {
                    if (call != null) {
                        call.onCallback(source, null);
                    }
                }
            }).launch();
        }
    }

    private static final int REQUESTCODE = 10086111;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUESTCODE) {
                String img_url = data.getStringExtra("imgPath");
                ShuiyinUtils.getInstance(ShuiyinAct1.this).diaoyong(iv1, img_url);
            }
        }
    }

}
