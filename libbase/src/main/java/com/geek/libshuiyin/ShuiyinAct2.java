package com.geek.libshuiyin;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.Utils;
import com.geek.libbase.R;
import com.geek.libfileprovdider.AndroidFileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ShuiyinAct2 extends AppCompatActivity {

    private TextView tv1;
    private TextView tv2;
    private ImageView iv1;
    //
    private ImageView iv_camera_capture;
    private ImageView iv_camera_capture2;
    private ImageView btn_camera_cancel;
    private ImageView iv_camera_flash_light;

    private Camera camera = Camera.open();
    private MySurfaceView mySurfaceView;
    private byte[] buffer = null;
    private final int TYPE_FILE_IMAGE = 1;
    private final int TYPE_FILE_VEDIO = 2;
    private int openFlashLight = 0;
    public String CUTTING_IMAGE_NAME = "PhotoCopy.png";
    private static final int REQUEST_IMAGE_CUTTING = 2;
    private int cameraPosition = 1;//0代表前置摄像头，1代表后置摄像头

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuiyin2);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        iv_camera_capture = findViewById(R.id.camera_capture);
        iv_camera_capture2 = findViewById(R.id.camera_capture2);
        iv_camera_flash_light = findViewById(R.id.camera_flash_light);
        btn_camera_cancel = findViewById(R.id.camera_cancel);
        //
        btn_camera_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        iv_camera_capture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //前后摄像头
                //切换前后摄像头
                int cameraCount = 0;
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数

                for (int i = 0; i < cameraCount; i++) {
                    Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
                    if (cameraPosition == 1) {
                        //现在是后置，变更为前置
                        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                            camera.stopPreview();//停掉原来摄像头的预览
                            camera.release();//释放资源
                            camera = null;//取消原来摄像头
                            camera = Camera.open(i);//打开当前选中的摄像头
                            try {
                                if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                                    camera.setDisplayOrientation(90);
                                } else {//如果是横屏
                                    camera.setDisplayOrientation(0);
                                }
                                camera.setPreviewDisplay(mySurfaceView.getHolder());//通过surfaceview显示取景画面
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            camera.startPreview();//开始预览
                            camera.autoFocus(new ShuiyinAutoFocusManager(ShuiyinAct2.this, camera));
                            cameraPosition = 0;
                            iv_camera_flash_light.setVisibility(View.GONE);
                            break;
                        }
                    } else {
                        //现在是前置， 变更为后置
                        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                            camera.stopPreview();//停掉原来摄像头的预览
                            camera.release();//释放资源
                            camera = null;//取消原来摄像头
                            camera = Camera.open(i);//打开当前选中的摄像头
                            try {
                                if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                                    camera.setDisplayOrientation(90);
                                } else {//如果是横屏
                                    camera.setDisplayOrientation(0);
                                }
                                camera.setPreviewDisplay(mySurfaceView.getHolder());//通过surfaceview显示取景画面
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            camera.startPreview();//开始预览
                            camera.autoFocus(new ShuiyinAutoFocusManager(ShuiyinAct2.this, camera));
                            cameraPosition = 1;
                            iv_camera_flash_light.setVisibility(View.VISIBLE);
                            break;
                        }
                    }

                }

            }
        });
        iv_camera_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拍照
                camera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        if (data == null) {

                        } else {
                        }

                        buffer = new byte[data.length];
                        buffer = data.clone();

                        //保存图片
                        saveImageToFile();
                    }
                });
            }
        });
        iv_camera_flash_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //闪光灯bufen
                if (openFlashLight == 1) {
                    closeFlashLight();
                    openFlashLight = 0;
                    iv_camera_flash_light.setImageResource(R.drawable.flash_light_open);

                } else if (openFlashLight == 0) {
                    openFlashLight = 1;
                    openFlashLight();
                    iv_camera_flash_light.setImageResource(R.drawable.flash_light_close);

                }
            }
        });

        //
        mySurfaceView = new MySurfaceView(getApplicationContext(), camera);
        FrameLayout preview = findViewById(R.id.camera_preview);
        preview.addView(mySurfaceView);
//        camera.release();
//        camera = null;
//        if (camera == null) {
//            camera = getCameraInstance();
//        }
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            camera.setDisplayOrientation(90);
        } else {//如果是横屏
            camera.setDisplayOrientation(0);
        }
        //解决方向问题
        IOrientationEventListener orientationEventListener = new IOrientationEventListener(this);
        mySurfaceView.getHolder().setKeepScreenOn(true);//屏幕常亮

        mySurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            //当SurfaceHolder被创建的时候回调
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                orientationEventListener.enable();

                //设置相机的参数
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();//获取所有支持的camera尺寸
                Camera.Size optionSize = getOptimalPreviewSize(sizeList, mySurfaceView.getHeight(), mySurfaceView.getWidth());//获取一个最为适配的camera.size
                parameters.setPreviewSize(optionSize.width, optionSize.height);//把camera.size赋值到parameters
                camera.setParameters(parameters);
                camera.cancelAutoFocus();
                camera.autoFocus(new ShuiyinAutoFocusManager(ShuiyinAct2.this, camera));

            }

            //当SurfaceHolder的尺寸发生变化的时候被回调
            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            //当SurfaceHolder被销毁的时候回调
            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                orientationEventListener.disable();
            }
        });
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        camera.release();
        camera = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (camera == null) {
            camera = getCameraInstance();
        }
        //必须放在onResume中，不然会出现Home键之后，再回到该APP，黑屏
        mySurfaceView = new MySurfaceView(getApplicationContext(), camera);
        FrameLayout preview = findViewById(R.id.camera_preview);
        preview.addView(mySurfaceView);


    }

    /*得到一相机对象*/
    private Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return camera;
    }


    //-----------------------保存图片---------------------------------------
    private void saveImageToFile() {
        File file = getOutFile(TYPE_FILE_IMAGE);
        if (file == null) {
            Toast.makeText(getApplicationContext(), "文件创建失败,请检查SD卡读写权限", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i("MyPicture", "自定义相机图片路径:" + file.getPath());
//        Toast.makeText(getApplicationContext(), "图片保存路径：" + file.getPath(), Toast.LENGTH_SHORT).show();
        if (buffer == null) {
            Log.i("MyPicture", "自定义相机Buffer: null");
        } else {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(buffer);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        startPhotoZoom(file);
    }

    //-----------------------生成Uri---------------------------------------
    //得到输出文件的URI ***这里的fileProvider为了适配android9 不会的小伙伴自行百度***
    private Uri getOutFileUri(int fileType) {
//        return FileProvider.getUriForFile(this, Appuri.get().getPackageName() + ".fileprovider", getOutFile(fileType));
        return AndroidFileUtil.getUriForFile24(getOutFile(fileType));
    }

    //生成输出文件
    private File getOutFile(int fileType) {

//        String storageState = Environment.getExternalStorageState();
//        if (Environment.MEDIA_REMOVED.equals(storageState)) {
//            Toast.makeText(getApplicationContext(), "oh,no, SD卡不存在", Toast.LENGTH_SHORT).show();
//            return null;
//        }
//
//        File mediaStorageDir = new File(Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//                , "MyPictures");
        String url111 = Utils.getApp().getExternalFilesDir(null).getPath() + /*"/libbase2/shuiyin/" +*/ CUTTING_IMAGE_NAME;
        File cropFile = new File(url111);
        try {
            if (cropFile.exists()) {
                cropFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (!mediaStorageDir.exists()) {
//            if (!mediaStorageDir.mkdirs()) {
//                Log.i("MyPictures", "创建图片存储路径目录失败");
//                Log.i("MyPictures", "mediaStorageDir : " + mediaStorageDir.getPath());
//                return null;
//            }
//        }

        File file = new File(getFilePath(cropFile, fileType));

        return file;
    }

    //生成输出文件路径
    private String getFilePath(File mediaStorageDir, int fileType) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String filePath = mediaStorageDir.getPath() + File.separator;
        if (fileType == TYPE_FILE_IMAGE) {
            filePath += ("IMG_" + timeStamp + ".png");
        } else if (fileType == TYPE_FILE_VEDIO) {
            filePath += ("VIDEO_" + timeStamp + ".mp4");
        } else {
            return null;
        }
        Log.i("MyPicturessss", "创建图片存储路径目录" + filePath);
        return filePath;
    }


    /**
     * 解决预览变形问题
     *
     * @param sizes
     * @param w
     * @param h
     * @return
     */
    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null) {
            return null;
        }

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) {
                continue;
            }
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    public class IOrientationEventListener extends OrientationEventListener {

        public IOrientationEventListener(Context context) {
            super(context);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            if (ORIENTATION_UNKNOWN == orientation) {
                return;
            }
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(0, info);
            orientation = (orientation + 45) / 90 * 90;
            int rotation = 0;
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                rotation = (info.orientation - orientation + 360) % 360;
            } else {
                rotation = (info.orientation + orientation) % 360;
            }
            Log.e("TAG", "orientation: " + orientation);
            if (null != camera) {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setRotation(rotation);
                camera.setParameters(parameters);
            }
        }
    }


    /**
     * 启动系统裁剪
     *
     * @param file
     */
    private void startPhotoZoom(File file) {
        Uri uri = getOutFileUri(TYPE_FILE_IMAGE);

        //保存裁剪后的图片
//        File cropFile = new File(Environment.getExternalStorageDirectory().toString() + "/yourname", CUTTING_IMAGE_NAME);
        String url111 = Utils.getApp().getExternalFilesDir(null).getPath() + /*"/libbase2/shuiyin/" +*/ CUTTING_IMAGE_NAME;
        File cropFile = new File(url111);
        try {
            if (cropFile.exists()) {
                cropFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Uri cropUri;
        cropUri = Uri.fromFile(cropFile);

        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            //需要加上这两句话  ： uri 权限
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", 1); // 裁剪框比例
//        intent.putExtra("aspectY", 1);
//        intent.putExtra("outputX", 500); // 输出图片大小
//        intent.putExtra("outputY", 500);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, REQUEST_IMAGE_CUTTING);
    }

    /**
     * 打开闪光灯
     */
    public void openFlashLight() {
        if (camera == null) {
            return;
        }
        // 相机为前置时关闭闪光灯
        if (cameraPosition == 0) {
            return;
        }
        Camera.Parameters parameter = camera.getParameters();
        parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameter);
    }

    /**
     * 关闭闪光灯
     */
    public void closeFlashLight() {
        if (camera == null) {
            return;
        }
        // 相机为后置时关闭闪光灯
        if (cameraPosition == 1) {
            return;
        }
        Camera.Parameters parameter = camera.getParameters();
        parameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameter);
    }

    //将截取后的照片返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CUTTING) {
//            File pictureFile1 = new File(Environment.getExternalStorageDirectory().toString() + "/libbase2", CUTTING_IMAGE_NAME);
            String filePath = Utils.getApp().getExternalFilesDir(null).getPath() /*+ "/libbase2/shuiyin/"*/ + CUTTING_IMAGE_NAME;
            File pictureFile1 = new File(filePath);
            String imgPath = pictureFile1.getAbsolutePath();
            Intent intent = new Intent();
            intent.putExtra("imgPath", imgPath);
            setResult(RESULT_OK, intent);
            finish();
        }
    }


}
