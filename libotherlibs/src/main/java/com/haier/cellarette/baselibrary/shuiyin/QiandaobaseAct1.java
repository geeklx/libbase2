package com.haier.cellarette.baselibrary.shuiyin;

import android.Manifest;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.geek.libutils.app.MyLogUtil;
import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.btnonclick.view.BounceView;
import com.haier.cellarette.baselibrary.toasts3.MProgressDialog;
import com.haier.cellarette.baselibrary.toasts3.config.MDialogConfig;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.AutoSizeCompat;
import pub.devrel.easypermissions.EasyPermissions;

public abstract class QiandaobaseAct1 extends AppCompatActivity implements SurfaceHolder.Callback {

    protected TextView tv_left;
    protected TextView tv_center;
    protected TextClock tv1;
    protected TextClock tv2;
    protected TextView tv3;
    protected TextView tv4;
    private String mulu;

    //
    private ImageView position;//返回和切换前后置摄像头
    private SurfaceView surface;
    private TextView camera_capture3;//重拍
    private TextView camera_capture4;//确认接口bufen
    private TextView shutter;//快门
    private ImageView iv_camera_flash_light;
    private SurfaceHolder mHolder;
    private Camera camera;//声明相机
    private String filepath = "";//照片保存路径
    private String filename = "";//照片
    private String url = "";//照片
    private File cropFile;//照片
    private int cameraPosition = 0;//0代表前置摄像头，1代表后置摄像头
    private int openFlashLight = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//拍照过程屏幕一直处于高亮

        setContentView(R.layout.activity_qh_shuiyin1);
        //
        tv_left = findViewById(R.id.tv_left);
        tv_center = findViewById(R.id.tv_center);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        String timeZone = "GMT+08:00";
        tv1.setTimeZone(timeZone);
        tv2.setTimeZone(timeZone);
        tv1.setFormat24Hour("HH:mm");
        tv1.setFormat12Hour("HH:mm");
        tv2.setFormat24Hour("yyyy年MM月dd日");
        tv2.setFormat12Hour("yyyy年MM月dd日");

        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tv_center.setText("签到拍照");
//        tv_center.setVisibility(View.GONE);
        //
//        LocationBean locationBean = MmkvUtils.getInstance().get_common_json("location", LocationBean.class);
//        if (locationBean != null && !TextUtils.isEmpty(locationBean.getAddress())) {
//            tv3.setText(locationBean.getAddress());
//            FgrxxBean2 fgrxxBean2 = MmkvUtils.getInstance().get_common_json(AppCommonUtils.userInfo, FgrxxBean2.class);
//            tv4.setText(fgrxxBean2.getName());
//        }
        //

        //
        position = findViewById(R.id.camera_capture2);

        shutter = findViewById(R.id.camera_capture);
        camera_capture3 = findViewById(R.id.camera_capture3);
        camera_capture4 = findViewById(R.id.camera_capture4);
        iv_camera_flash_light = findViewById(R.id.camera_flash_light);
        //
        BounceView.addAnimTo(position);
        BounceView.addAnimTo(shutter);
        BounceView.addAnimTo(camera_capture3);
        BounceView.addAnimTo(camera_capture4);
        BounceView.addAnimTo(iv_camera_flash_light);
        surface = findViewById(R.id.camera_preview);
        mHolder = surface.getHolder();//获得句柄
        mHolder.addCallback(this);//添加回调
//        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//surfaceview不维护自己的缓冲区，等待屏幕渲染引擎将内容推送到用户面前

        //设置监听
        position.setOnClickListener(listener);
        shutter.setOnClickListener(listener);
        camera_capture3.setOnClickListener(listener);
        camera_capture4.setOnClickListener(listener);
        //
        position.setVisibility(View.VISIBLE);
        shutter.setVisibility(View.VISIBLE);
        camera_capture3.setVisibility(View.GONE);
        camera_capture4.setVisibility(View.GONE);
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
        //创建目录
        filepath = Utils.getApp().getExternalFilesDir(null).getPath() + "/shuiyin2/";
        File file = new File(filepath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.i("MyPictures", "创建图片存储路径目录失败");
                Log.i("MyPictures", "mediaStorageDir : " + file.getPath());
            }
        }
        // 创建无水印第二张图片文件
        filename = "geeklibbase22" + ".jpg";
        url = filepath + File.separator + filename;
        cropFile = new File(url);
        try {
            if (cropFile.exists()) {
                cropFile.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        oncreatenew();
    }

    public void oncreatenew() {

    }

    @Override
    protected void onResume() {
        String[] perms = {Manifest.permission.CAMERA};
        if (!EasyPermissions.hasPermissions(this, perms)) {//有权限
            ToastUtils.showShort("相机权限未申请，请先申请权限");
            finish();
        }
        //
//        if (camera != null) {
////            camera.stopPreview();//关闭预览 处理数据
//            camera.startPreview();//数据处理完后继续开始预览
//        }
        // 授权bufen
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (is_shouquan()) {
//                    Looper.prepare();
//                    Toast.makeText(getContext(), "TensorFlow SO need update.", Toast.LENGTH_LONG).show();
//                    Looper.loop();
                    SPUtils.getInstance().put("shouquan", true);
                } else {
                    SPUtils.getInstance().put("shouquan", false);
                }
            }
        }).start();
        super.onResume();

    }


    @Override
    protected void onPause() {
//        if (camera != null) {
//            camera.stopPreview();//关闭预览 处理数据
////            camera.release();
//        }
        super.onPause();
    }

    public static int getDisplayRotation(Activity activity) {
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
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

    public static void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
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
        try {
            camera.setParameters(parameters);
        } catch (Exception e) {
            //个别机型在SupportPreviewSizes里汇报了支持某种预览尺寸，但实际是不支持的，设置进去就会抛出RuntimeException.
//            Camera.Size previewSize = findBestPreviewSize(parameters);
            try {
                //遇到上面所说的情况，只能设置一个最小的预览尺寸
//                parameters.setPreviewSize(previewSize.width, previewSize.height);
                parameters.setPreviewSize(1920, 1080);
                camera.setParameters(parameters);
            } catch (Exception e1) {
                //到这里还有问题，就是拍照尺寸的锅了，同样只能设置一个最小的拍照尺寸
                e1.printStackTrace();
                try {
//                    parameters.setPreviewSize(previewSize.width, previewSize.height);
                    parameters.setPreviewSize(1920, 1080);
                    camera.setParameters(parameters);
                } catch (Exception ignored) {
                }
            }
        }
//        camera.setParameters(parameters);
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
    public View.OnClickListener listener = new View.OnClickListener() {
        //        @SingleClick(2000)
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.camera_capture2) {//切换前后摄像头
                int cameraCount = 0;
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数
                for (int i = 0; i < cameraCount; i++) {
                    Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
                    if (cameraPosition == 1) {
                        //现在是后置，变更为前置
                        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                            //
                            cameraPosition = 0;
                            iv_camera_flash_light.setVisibility(View.GONE);
                            camera.stopPreview();//停掉原来摄像头的预览
                            camera.release();//释放资源
                            camera = null;//取消原来摄像头
                            camera = Camera.open(i);//打开当前选中的摄像头
                            setCameraDisplayOrientation(QiandaobaseAct1.this, Camera.CameraInfo.CAMERA_FACING_BACK, camera);
                            try {
                                set_auto_wh();
                                camera.setPreviewDisplay(mHolder);//通过surfaceview显示取景画面
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            camera.startPreview();//开始预览
                            camera.cancelAutoFocus();
                            camera.autoFocus(new ShuiyinAutoFocusManager2(QiandaobaseAct1.this, camera));
//                            cameraPosition = 0;
//                            iv_camera_flash_light.setVisibility(View.GONE);
                            break;
                        }
                    } else {
                        //现在是前置， 变更为后置
                        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                            cameraPosition = 1;
                            iv_camera_flash_light.setVisibility(View.VISIBLE);
                            camera.stopPreview();//停掉原来摄像头的预览
                            camera.release();//释放资源
                            camera = null;//取消原来摄像头
                            camera = Camera.open(i);//打开当前选中的摄像头
                            setCameraDisplayOrientation(QiandaobaseAct1.this, Camera.CameraInfo.CAMERA_FACING_FRONT, camera);
                            try {
                                set_auto_wh();
                                camera.setPreviewDisplay(mHolder);//通过surfaceview显示取景画面
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            camera.startPreview();//开始预览
                            camera.cancelAutoFocus();
                            camera.autoFocus(new ShuiyinAutoFocusManager2(QiandaobaseAct1.this, camera));
//                            cameraPosition = 1;
//                            iv_camera_flash_light.setVisibility(View.VISIBLE);
                            break;
                        }
                    }

                }
            } else if (id == R.id.camera_capture) {//快门
                if (!SPUtils.getInstance().getBoolean("shouquan", false)) {
                    Toast.makeText(QiandaobaseAct1.this, "TensorFlow SO need update.", Toast.LENGTH_LONG).show();
                    return;
                }
                //
                if (mDialogConfig == null) {
                    mDialogConfig = new MDialogConfig.Builder().build();
                    mDialogConfig.canceledOnTouchOutside = false;
                    mDialogConfig.cancelable = false;
                }
                MProgressDialog.showProgress(QiandaobaseAct1.this, "正在拍照", mDialogConfig);
                //
                camera.autoFocus(new Camera.AutoFocusCallback() {//自动对焦
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        // TODO Auto-generated method stub
                        if (success) {
                            //设置参数，并拍照
//                            Camera.Parameters params = camera.getParameters();
//                            params.setPictureFormat(PixelFormat.JPEG);//图片格式
////                            params.setPreviewSize(800, 480);//图片大小
////                            setCameraDisplayOrientation(QiandaoAct3.this, 0, camera);
//                            camera.setParameters(params);//将参数设置到我的camera
//                            //
                            position.setVisibility(View.GONE);
                            shutter.setVisibility(View.GONE);
                            camera_capture3.setVisibility(View.VISIBLE);
                            camera_capture4.setVisibility(View.VISIBLE);
                            //
                            camera.takePicture(null, null, jpeg);//将拍摄到的照片给自定义的对象

                        }
                    }
                });
            } else if (id == R.id.camera_capture3) {//重拍
                //
                camera.stopPreview();//关闭预览 处理数据
                camera.startPreview();//数据处理完后继续开始预览
                //
                position.setVisibility(View.VISIBLE);
                shutter.setVisibility(View.VISIBLE);
                camera_capture3.setVisibility(View.GONE);
                camera_capture4.setVisibility(View.GONE);
            } else if (id == R.id.camera_capture4) {//确认
                //
                if (mDialogConfig == null) {
                    mDialogConfig = new MDialogConfig.Builder().build();
                    mDialogConfig.canceledOnTouchOutside = true;
                    mDialogConfig.cancelable = true;
                }
                MProgressDialog.showProgress(QiandaobaseAct1.this, "正在上传", mDialogConfig);
                //
                if (onQiandaoClickListener != null) {
                    onQiandaoClickListener.onQiandaoClick(cropFile);
                }
//                if (fImg1Presenter != null) {
//                    if (TextUtils.equals(BanbenCommonUtilszbiz1.banben_comm, "线上")) {
//                        fImg1Presenter.getimg1("/api/oss/resource-oss/upload", cropFile);// oss
//                    } else if (TextUtils.equals(BanbenCommonUtilszbiz1.banben_comm, "测试")) {
//                        fImg1Presenter.getimg1("/api/oss/resource-test/upload", cropFile);// test
//                    }
//                }
            }
        }
    };

    private boolean is_shouquan() {
        //
        String reviseBpmnFile = "http://cdn2.cdn.haier-jiuzhidao.com/tensorflowso/version.xml";
        InputStream in = null;
        String pg_name = AppUtils.getAppPackageName();
        List<ShuiyinXmlBean> mlist1 = new ArrayList<>();
        try {
            in = ShuiyinLoadFile.downLoadFile(reviseBpmnFile);
            mlist1 = ShuiyinDOMService.getPersons(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String[] str = fileString.split(",");
//        mlist1 = Arrays.asList(str);
        for (int i = 0; i < mlist1.size(); i++) {
            if (TextUtils.equals(mlist1.get(i).getAge(), pg_name)) {
                return true;
            }
        }
        return false;
    }

    public OnQiandaoClickListener onQiandaoClickListener;

    public void setOnQiandaoClickListener(OnQiandaoClickListener onQiandaoClickListener) {
        this.onQiandaoClickListener = onQiandaoClickListener;
    }
    /*surfaceHolder他是系统提供的一个用来设置surfaceView的一个对象，而它通过surfaceView.getHolder()这个方法来获得。
     Camera提供一个setPreviewDisplay(SurfaceHolder)的方法来连接*/

    //SurfaceHolder.Callback,这是个holder用来显示surfaceView 数据的接口,他必须实现以下3个方法
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub
//        setCameraDisplayOrientation(QiandaoAct3.this, 0, camera);
        //当SurfaceView尺寸变化时（包括设备横屏竖屏改变时时），需要重新设定相关参数
//        if (mHolder.getSurface() == null) {
//            //检查SurfaceView是否存在
//            return;
//        }
//
//        //改变设置前先关闭相机
//        try {
//            camera.stopPreview();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //使用最佳比例配置重启相机
//        try {
//            camera.setPreviewDisplay(mHolder);
//            final Camera.Parameters parameters = camera.getParameters();
//            final Camera.Size size = getBestPreviewSize(width, height);
//            MyLogUtil.e("surfaceCreated-surfaceChanged", size.width + "," + size.height + "");
//            parameters.setPreviewSize(size.width, size.height);
//            camera.setParameters(parameters);
//            camera.startPreview();
//        } catch (Exception e) {
//            Log.d("TAG", "Error starting camera preview: " + e.getMessage());
//        }
    }

    private MDialogConfig mDialogConfig;

    private Camera.Size getBestPreviewSize(int width, int height) {
        Camera.Size result = null;
        final Camera.Parameters p = camera.getParameters();
        //特别注意此处需要规定rate的比是大的比小的，不然有可能出现rate = height/width，但是后面遍历的时候，current_rate = width/height,所以我们限定都为大的比小的。
        float rate = (float) Math.max(width, height) / (float) Math.min(width, height);
        float tmp_diff;
        float min_diff = -1f;
        for (Camera.Size size : p.getSupportedPreviewSizes()) {
            float current_rate = (float) Math.max(size.width, size.height) / (float) Math.min(size.width, size.height);
            tmp_diff = Math.abs(current_rate - rate);
            if (min_diff < 0) {
                min_diff = tmp_diff;
                result = size;
            }
            if (tmp_diff < min_diff) {
                min_diff = tmp_diff;
                result = size;
            }
        }
        return result;
    }

    private void resizeSurfaceView(int width, int height) {
        FrameLayout view = findViewById(R.id.f1);
        ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) view.getLayoutParams();
        layoutParams.width = ShuiyinDisplayUtil.getScreenWidth(this);
        MyLogUtil.e("surfaceCreated-resizeSurfaceView-width", ShuiyinDisplayUtil.getScreenWidth(this) + "");
        DecimalFormat df = new DecimalFormat("0.00");
        String sssss = df.format((float) (width * ShuiyinDisplayUtil.getScreenWidth(this) / height));
        double aaaaa = Double.parseDouble(sssss);
        layoutParams.height = (int) aaaaa;
        MyLogUtil.e("surfaceCreated-resizeSurfaceView-height", layoutParams.height + "");
    }

    private void resizeSurfaceView2(int width, int height) {
        FrameLayout view = findViewById(R.id.f1);
        ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) view.getLayoutParams();
//        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.width = width;
        layoutParams.height = height;
    }

    private void set_auto_wh() {
        CamcorderProfile profile = (CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT, CamcorderProfile.QUALITY_HIGH));
        Camera.Parameters parameters = camera.getParameters();
        parameters.setPreviewSize(profile.videoFrameWidth, profile.videoFrameHeight);
//                parameters.setPreviewSize(profile.videoFrameHeight, profile.videoFrameWidth);
//                parameters.setPreviewSize(holder.getSurfaceFrame().height(), holder.getSurfaceFrame().width());
//                parameters.setPreviewSize(800, 600);
        try {
            camera.setParameters(parameters);
        } catch (Exception e) {
            //个别机型在SupportPreviewSizes里汇报了支持某种预览尺寸，但实际是不支持的，设置进去就会抛出RuntimeException.
//            Camera.Size previewSize = findBestPreviewSize(parameters);
            try {
                //遇到上面所说的情况，只能设置一个最小的预览尺寸
//                parameters.setPreviewSize(previewSize.width, previewSize.height);
                parameters.setPreviewSize(1920, 1080);
                camera.setParameters(parameters);
            } catch (Exception e1) {
                //到这里还有问题，就是拍照尺寸的锅了，同样只能设置一个最小的拍照尺寸
                e1.printStackTrace();
                try {
//                    parameters.setPreviewSize(previewSize.width, previewSize.height);
                    parameters.setPreviewSize(1920, 1080);
                    camera.setParameters(parameters);
                } catch (Exception ignored) {
                }
            }
        }
//        camera.setParameters(parameters);
        //
        if (cameraPosition == 0) {
            // 前置
            resizeSurfaceView(profile.videoFrameWidth, profile.videoFrameHeight);
        } else {
            // 后置
//            resizeSurfaceView2(profile.videoFrameWidth, profile.videoFrameHeight);
        }
        //
        MyLogUtil.e("surfaceCreated-Camera", profile.videoFrameWidth + "," + profile.videoFrameHeight + "");
        MyLogUtil.e("surfaceCreated-SurfaceView", surface.getWidth() + "," + surface.getHeight() + "");
        MyLogUtil.e("surfaceCreated-SurfaceView-holder", mHolder.getSurfaceFrame().width() + "," + mHolder.getSurfaceFrame().height() + "");

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        //当surfaceview创建时开启前置相机
        if (camera == null) {
            if (cameraPosition == 0) {
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                // 设置前置bufen
                cameraPosition = 0;
                iv_camera_flash_light.setVisibility(View.GONE);
                try {
                    set_auto_wh();
//                camera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                    camera.setPreviewDisplay(mHolder);//通过surfaceview显示取景画面
                    camera.startPreview();//开始预览
                    setCameraDisplayOrientation(QiandaobaseAct1.this, Camera.CameraInfo.CAMERA_FACING_FRONT, camera);
                    //
                    camera.cancelAutoFocus();
                    camera.autoFocus(new ShuiyinAutoFocusManager2(QiandaobaseAct1.this, camera));
//                // 设置前置bufen
//                cameraPosition = 0;
//                iv_camera_flash_light.setVisibility(View.GONE);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (cameraPosition == 1) {
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                // 设置前置bufen
                cameraPosition = 1;
                iv_camera_flash_light.setVisibility(View.VISIBLE);
                try {
                    set_auto_wh();
//                camera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                    camera.setPreviewDisplay(mHolder);//通过surfaceview显示取景画面
                    camera.startPreview();//开始预览
                    setCameraDisplayOrientation(QiandaobaseAct1.this, Camera.CameraInfo.CAMERA_FACING_BACK, camera);
                    //
                    camera.cancelAutoFocus();
                    camera.autoFocus(new ShuiyinAutoFocusManager2(QiandaobaseAct1.this, camera));
//                // 设置前置bufen
//                cameraPosition = 0;
//                iv_camera_flash_light.setVisibility(View.GONE);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        //当surfaceview关闭时，关闭预览并释放资源
//        camera.stopPreview();
//        camera.release();
//        camera = null;
//        holder = null;
//        surface = null;
        if (camera != null) {
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }

    //创建jpeg图片回调数据对象
    public Camera.PictureCallback jpeg = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //
            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                Matrix matrix = new Matrix();
                if (cameraPosition == 1) {
                    // 后置
                    if (DeviceUtils.getManufacturer().toLowerCase().equals("xiaomi")) {
                        matrix.preRotate(90);
                    } else {
                        matrix.preRotate(0);
                    }
                } else {
                    // 前置
                    if (DeviceUtils.getManufacturer().toLowerCase().equals("xiaomi")) {
                        matrix.preRotate(-90);
                    } else {
                        matrix.preRotate(180);
                    }
//                    matrix.preRotate(-90);
                    matrix.postScale(-1, 1);
                }
                Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sdf.format(System.currentTimeMillis());
//                LocationBean locationBean = MmkvUtils.getInstance().get_common_json("location", LocationBean.class);
                if (!TextUtils.isEmpty(tv3.getText().toString())) {
//                    FgrxxBean2 fgrxxBean2 = MmkvUtils.getInstance().get_common_json(AppCommonUtils.userInfo, FgrxxBean2.class);
                    Bitmap img = ShuiyinUtils2.getInstance(QiandaobaseAct1.this).createBitmap(bmRotated, url, null, time, tv3.getText().toString(), tv4.getText().toString());
                    //将剪裁后照片显示出来
//                    iv1.setImageBitmap(img);
                    //第二张图片
                    BufferedOutputStream bos2 = new BufferedOutputStream(new FileOutputStream(cropFile));
                    img.compress(Bitmap.CompressFormat.JPEG, 50, bos2);//将图片压缩的流里面
                    bos2.flush();// 刷新此缓冲区的输出流
                    bos2.close();// 关闭此输出流并释放与此流有关的所有系统资源
                    camera.stopPreview();//关闭预览 处理数据
//                    camera.startPreview();//数据处理完后继续开始预览
                    img.recycle();//回收bitmap空间
//                    //
//                    position.setVisibility(View.GONE);
//                    shutter.setVisibility(View.GONE);
//                    camera_capture3.setVisibility(View.VISIBLE);
//                    camera_capture4.setVisibility(View.VISIBLE);
                    MProgressDialog.dismissProgress();
//                    //
//                    if (fImg1Presenter != null) {
//                        if (TextUtils.equals(BanbenCommonUtilszbiz1.banben_comm, "线上")) {
//                            fImg1Presenter.getimg1("/api/oss/resource-oss/upload", cropFile);// oss
//                        } else if (TextUtils.equals(BanbenCommonUtilszbiz1.banben_comm, "测试")) {
//                            fImg1Presenter.getimg1("/api/oss/resource-test/upload", cropFile);// test
//                        }
//                    }

                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };


//    private FImg1Presenter fImg1Presenter;
//    private FImg2Presenter fImg2Presenter;
//    private MDialogConfig mDialogConfig;

    @Override
    protected void onDestroy() {
//        if (fImg1Presenter != null) {
//            fImg1Presenter.onDestory();
//        }
//        if (fImg2Presenter != null) {
//            fImg2Presenter.onDestory();
//        }
//        if (camera != null) {
//            camera = null;
//            mHolder = null;
//            surface = null;
//        }
        releaseCameraAndPreview();
        super.onDestroy();
    }


    private void releaseCameraAndPreview() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
            mHolder = null;
            surface = null;
        }
//        if (mPreview != null) {
//            mPreview.destroyDrawingCache();
//        }
    }

//    @Override
//    public void OnImg1Success(FImg1Bean bean) {
////        Bitmap bitmap1 = BitmapFactory.decodeFile(url1);
//        LocationBean locationBean = MmkvUtils.getInstance().get_common_json("location", LocationBean.class);
//        if (locationBean != null && !TextUtils.isEmpty(locationBean.getAddress())) {
//            fImg2Presenter.getimg2("/api/web/clock/ding", bean.getUrl(), locationBean.getLatitude(), locationBean.getLongitude(), locationBean.getAddress());
//        } else {
//            MProgressDialog.dismissProgress();
//            camera.stopPreview();//关闭预览 处理数据
//            camera.startPreview();//数据处理完后继续开始预览
//        }
//    }
//
//    @Override
//    public void OnImg1Nodata(String bean) {
//        MProgressDialog.dismissProgress();
//        camera.stopPreview();//关闭预览 处理数据
//        camera.startPreview();//数据处理完后继续开始预览
//    }
//
//    @Override
//    public void OnImg1Fail(String msg) {
//        MProgressDialog.dismissProgress();
//        camera.stopPreview();//关闭预览 处理数据
//        camera.startPreview();//数据处理完后继续开始预览
//    }
//
//    @Override
//    public void OnImg2Success(String bean) {
////        ToastUtils.showLong(bean);
//        MProgressDialog.dismissProgress();
//        camera.stopPreview();//关闭预览 处理数据
////        camera.startPreview();//数据处理完后继续开始预览
//        //
//        GetAssetsFileMP3TXTJSONAPKUtil.getInstance(QiandaoAct3.this).playMusic(QiandaoAct3.this, true, "http://cdn2.cdn.haier-jiuzhidao.com/tensorflowso/yinxiao4926.mp3");
//        new XPopup.Builder(QiandaoAct3.this).isDestroyOnDismiss(false) //对于只使用一次的弹窗，推荐设置这个
////              .isViewMode(true)
//                .dismissOnTouchOutside(false).asCustom(new CenterPopupView(QiandaoAct3.this) {
//                    private TextView btnClose;
//                    private TextView btnOk;
//
//                    @Override
//                    protected int getImplLayoutId() {
//                        return R.layout.dialog_agreement5;
//                    }
//
//                    @Override
//                    protected void onCreate() {
//                        super.onCreate();
//                        btnClose = findViewById(R.id.btn_cancle);
////                        BounceView.addAnimTo(btnClose);
//                        btnClose.setOnClickListener(new OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dismiss();
//                                finish();
//                            }
//                        });
//                    }
//
//                }).show();
//    }
//
//    @Override
//    public void OnImg2Nodata(String bean) {
//        ToastUtils.showLong(bean);
////        //TODO 发送广播bufen
////        Intent msgIntent = new Intent();
////        msgIntent.setAction("QiandaoFragment1");
////        msgIntent.putExtra("query1", bean);
////        LocalBroadcastManagers.getInstance(QiandaoAct3.this).sendBroadcast(msgIntent);
//        MProgressDialog.dismissProgress();
////        finish();
//        camera.stopPreview();//关闭预览 处理数据
//        camera.startPreview();//数据处理完后继续开始预览
//    }
//
//    @Override
//    public void OnImg2Fail(String msg) {
//        ToastUtils.showLong(msg);
//        MProgressDialog.dismissProgress();
//        camera.stopPreview();//关闭预览 处理数据
//        camera.startPreview();//数据处理完后继续开始预览
//    }

    public void set_camera1() {
        MProgressDialog.dismissProgress();
        camera.stopPreview();//关闭预览 处理数据
        camera.startPreview();//数据处理完后继续开始预览
    }

    public void set_camera2() {
        MProgressDialog.dismissProgress();
        camera.stopPreview();//关闭预览 处理数据
//        camera.startPreview();//数据处理完后继续开始预览
    }


    @Override
    public Resources getResources() {
        //需要升级到 v1.1.2 及以上版本才能使用 AutoSizeCompat
        if (Looper.myLooper() == Looper.getMainLooper()) {
            AutoSizeCompat.autoConvertDensityOfGlobal((super.getResources()));//如果没有自定义需求用这个方法
            // AutoSizeCompat.autoConvertDensity((super.getResources()), 667, false);//如果有自定义需求就用这个方法
        }
        return super.getResources();
    }
}
