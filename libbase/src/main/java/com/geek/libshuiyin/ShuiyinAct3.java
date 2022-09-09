package com.geek.libshuiyin;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blankj.utilcode.util.Utils;
import com.geek.libbase.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ShuiyinAct3 extends Activity implements SurfaceHolder.Callback {
    private ImageView back, position;//返回和切换前后置摄像头
    private SurfaceView surface;
    private ImageView shutter;//快门
    private ImageView iv_camera_flash_light;
    private SurfaceHolder holder;
    private Camera camera;//声明相机
    private String filepath = "";//照片保存路径
    private int cameraPosition = 1;//0代表前置摄像头，1代表后置摄像头
    private int openFlashLight = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//没有标题
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//拍照过程屏幕一直处于高亮
        //设置手机屏幕朝向，一共有7种
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        //SCREEN_ORIENTATION_BEHIND： 继承Activity堆栈中当前Activity下面的那个Activity的方向
        //SCREEN_ORIENTATION_LANDSCAPE： 横屏(风景照) ，显示时宽度大于高度 
        //SCREEN_ORIENTATION_PORTRAIT： 竖屏 (肖像照) ， 显示时高度大于宽度 
        //SCREEN_ORIENTATION_SENSOR  由重力感应器来决定屏幕的朝向,它取决于用户如何持有设备,当设备被旋转时方向会随之在横屏与竖屏之间变化
        //SCREEN_ORIENTATION_NOSENSOR： 忽略物理感应器——即显示方向与物理感应器无关，不管用户如何旋转设备显示方向都不会随着改变("unspecified"设置除外)
        //SCREEN_ORIENTATION_UNSPECIFIED： 未指定，此为默认值，由Android系统自己选择适当的方向，选择策略视具体设备的配置情况而定，因此不同的设备会有不同的方向选择
        //SCREEN_ORIENTATION_USER： 用户当前的首选方向

        setContentView(R.layout.activity_shuiyin3);

        back = (ImageView) findViewById(R.id.camera_cancel);
        position = (ImageView) findViewById(R.id.camera_capture2);
        surface = (SurfaceView) findViewById(R.id.camera_preview);
        shutter = (ImageView) findViewById(R.id.camera_capture);
        iv_camera_flash_light = (ImageView) findViewById(R.id.camera_flash_light);
        holder = surface.getHolder();//获得句柄
        holder.addCallback(this);//添加回调
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//surfaceview不维护自己的缓冲区，等待屏幕渲染引擎将内容推送到用户面前

        //设置监听
        back.setOnClickListener(listener);
        position.setOnClickListener(listener);
        shutter.setOnClickListener(listener);
        //
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
    }

    public static int getDisplayRotation(Activity activity) {
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        switch (rotation) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
        return 0;
    }

    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, Camera camera) {
        // See android.hardware.Camera.setCameraDisplayOrientation for
        // documentation.
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int degrees = getDisplayRotation(activity);
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else { // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
        Camera.Parameters parameters = camera.getParameters();
        parameters.setPictureFormat(PixelFormat.JPEG);//图片格式
        parameters.setRotation(result);
        camera.setParameters(parameters);
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
//        // 相机为后置时关闭闪光灯
//        if (cameraPosition == 1) {
//            return;
//        }
        Camera.Parameters parameter = camera.getParameters();
        parameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameter);
    }


    //响应点击事件
    public OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int id = v.getId();
            if (id == R.id.camera_cancel) {//返回
                ShuiyinAct3.this.finish();
            } else if (id == R.id.camera_capture2) {//切换前后摄像头
                int cameraCount = 0;
                CameraInfo cameraInfo = new CameraInfo();
                cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数
                for (int i = 0; i < cameraCount; i++) {
                    Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
                    if (cameraPosition == 1) {
                        //现在是后置，变更为前置
                        if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                            camera.stopPreview();//停掉原来摄像头的预览
                            camera.release();//释放资源
                            camera = null;//取消原来摄像头
                            camera = Camera.open(i);//打开当前选中的摄像头
                            setCameraDisplayOrientation(ShuiyinAct3.this, 0, camera);
                            try {
                                camera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            camera.startPreview();//开始预览
                            camera.cancelAutoFocus();
                            camera.autoFocus(new ShuiyinAutoFocusManager(ShuiyinAct3.this, camera));
                            cameraPosition = 0;
                            iv_camera_flash_light.setVisibility(View.GONE);
                            break;
                        }
                    } else {
                        //现在是前置， 变更为后置
                        if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                            camera.stopPreview();//停掉原来摄像头的预览
                            camera.release();//释放资源
                            camera = null;//取消原来摄像头
                            camera = Camera.open(i);//打开当前选中的摄像头
                            setCameraDisplayOrientation(ShuiyinAct3.this, 0, camera);
                            try {
                                camera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            camera.startPreview();//开始预览
                            camera.cancelAutoFocus();
                            camera.autoFocus(new ShuiyinAutoFocusManager(ShuiyinAct3.this, camera));
                            cameraPosition = 1;
                            iv_camera_flash_light.setVisibility(View.VISIBLE);
                            break;
                        }
                    }

                }
            } else if (id == R.id.camera_capture) {//快门
                camera.autoFocus(new AutoFocusCallback() {//自动对焦
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        // TODO Auto-generated method stub
                        if (success) {
                            //设置参数，并拍照
                            Parameters params = camera.getParameters();
                            params.setPictureFormat(PixelFormat.JPEG);//图片格式
//                            params.setPreviewSize(800, 480);//图片大小
//                            setCameraDisplayOrientation(ShuiyinAct3.this, 0, camera);
                            camera.setParameters(params);//将参数设置到我的camera
                            camera.takePicture(null, null, jpeg);//将拍摄到的照片给自定义的对象

                        }
                    }
                });
            }
        }
    };

    /*surfaceHolder他是系统提供的一个用来设置surfaceView的一个对象，而它通过surfaceView.getHolder()这个方法来获得。
     Camera提供一个setPreviewDisplay(SurfaceHolder)的方法来连接*/

    //SurfaceHolder.Callback,这是个holder用来显示surfaceView 数据的接口,他必须实现以下3个方法
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub
//        setCameraDisplayOrientation(ShuiyinAct3.this, 0, camera);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        //当surfaceview创建时开启相机
        if (camera == null) {
            camera = Camera.open();
            try {
                setCameraDisplayOrientation(ShuiyinAct3.this, 0, camera);
                camera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                camera.startPreview();//开始预览
                camera.cancelAutoFocus();
                camera.autoFocus(new ShuiyinAutoFocusManager(ShuiyinAct3.this, camera));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        //当surfaceview关闭时，关闭预览并释放资源
        camera.stopPreview();
        camera.release();
        camera = null;
        holder = null;
        surface = null;
    }

    //创建jpeg图片回调数据对象
    public PictureCallback jpeg = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                // 将得到的照片进行270°旋转，使其竖直
                Matrix matrix = new Matrix();
                if (cameraPosition == 1) {
                    // 后置
                    matrix.preRotate(90);
                    // https://blog.csdn.net/weixin_39527768/article/details/117727647?spm=1001.2101.3001.6650.3&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-3-117727647-blog-117502556.pc_relevant_aa&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-3-117727647-blog-117502556.pc_relevant_aa&utm_relevant_index=6
//                    matrix.setScale(-1, 1);
                } else {
                    // 前置
                    matrix.preRotate(-90);
                    matrix.postScale(-1, 1);
                    // https://blog.csdn.net/weixin_39864738/article/details/117502556
//                    float[] mirrorY = {-1, 0, 0, 0, 1, 0, 0, 0, 1};
//                    Matrix matrixMirrorY = new Matrix();
//                    matrixMirrorY.setValues(mirrorY);
//                    matrix.postConcat(matrixMirrorY);
                }
                Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//                bitmap.recycle();
                //自定义文件保存路径  以拍摄时间区分命名
//                String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".jpg";
                String filename = "geeklibbase2" + ".jpg";
                filepath = Utils.getApp().getExternalFilesDir(null).getPath() + "/shuiyin2/";
                File file = new File(filepath);
                if (!file.exists()) {
                    if (!file.mkdirs()) {
                        Log.i("MyPictures", "创建图片存储路径目录失败");
                        Log.i("MyPictures", "mediaStorageDir : " + file.getPath());
                    }
                }
                //
                String url111 = Utils.getApp().getExternalFilesDir(null).getPath() + "/shuiyin2/" + File.separator + filename;
                File cropFile = new File(url111);
                try {
                    if (cropFile.exists()) {
                        cropFile.delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(cropFile));
                bmRotated.compress(Bitmap.CompressFormat.JPEG, 50, bos);//将图片压缩的流里面
                bos.flush();// 刷新此缓冲区的输出流
                bos.close();// 关闭此输出流并释放与此流有关的所有系统资源
                camera.stopPreview();//关闭预览 处理数据
                camera.startPreview();//数据处理完后继续开始预览
                bmRotated.recycle();//回收bitmap空间
                //
                String url1 = Utils.getApp().getExternalFilesDir(null).getPath() + "/shuiyin2/" + File.separator + filename;
                //
//                Uri uri = Uri.fromFile(cropFile);
//                Bitmap bitmap1 = CamerHandler.GetCamerHandlerInstance()
//                        .handleSamplingAndRotationBitmap(ShuiyinAct3.this, uri);
//                ShuiyinBitmapUtil.saveBitmap2file(bitmap, filename);
                //
                Intent intent = new Intent();
                intent.putExtra("imgPath", url1);
                setResult(RESULT_OK, intent);
                finish();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };
}
