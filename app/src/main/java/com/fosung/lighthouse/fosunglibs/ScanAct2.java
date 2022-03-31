//package com.fosung.lighthouse.fosunglibs;
//
//import android.content.Context;
//import android.content.pm.ActivityInfo;
//import android.graphics.Bitmap;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.blankj.utilcode.util.ToastUtils;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.target.SimpleTarget;
//import com.bumptech.glide.request.transition.Transition;
//import com.luck.picture.lib.PictureSelector;
//import com.luck.picture.lib.animators.AnimationType;
//import com.luck.picture.lib.app.PictureAppMaster;
//import com.luck.picture.lib.config.PictureConfig;
//import com.luck.picture.lib.config.PictureMimeType;
//import com.luck.picture.lib.entity.LocalMedia;
//import com.luck.picture.lib.entity.MediaExtraInfo;
//import com.luck.picture.lib.listener.OnResultCallbackListener;
//import com.luck.picture.lib.style.PictureSelectorUIStyle;
//import com.luck.picture.lib.style.PictureWindowAnimationStyle;
//import com.luck.picture.lib.tools.MediaUtils;
//import com.luck.picture.lib.tools.SdkVersionUtils;
//
//import org.jetbrains.annotations.NotNull;
//import org.tensorflow.lite.Interpreter;
//
//import java.util.List;
//
//
//public class ScanAct2 extends AppCompatActivity {
//
//    private final String TAG = getClass().getSimpleName();
//
//    TextView tv;
//    TextView tv0;
//    TextView tv1;
//    ImageView iv0;
//    ImageView iv1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scan_act2);
//        tv = findViewById(R.id.tv);
//        tv0 = findViewById(R.id.tv0);
//        tv1 = findViewById(R.id.tv1);
//        iv0 = findViewById(R.id.iv0);
//        iv1 = findViewById(R.id.iv1);
//    }
//
//    public void onClickBtn(View v) {
////        Intent intent = new Intent(this, ScannerAct2.class);
////        startActivity(intent);
//    }
//
//    public void onClickBtn2(View v) {
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
//////                String path = getExternalFilesDir("image").getAbsolutePath() + "/a.jpg";
//////                String s = ScannerUtils.decodeText(path);
//////                BankCardInfoBean b = ScannerUtils.getBankCardInfo("6222600260001072444");
//////                String s = b == null ? null : b.toString();
////                String path = getExternalFilesDir("image").getAbsolutePath() + "/a.png";
////                final String s = ScannerUtils.decodeText(path);
////                runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////                        tv.setText("result=" + s);
////                    }
////                });
////            }
////        }).start();
//        //
//        PictureSelector.create(ScanAct2.this)
//                .openCamera(PictureMimeType.ofAll())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
//                .imageEngine(GlideEngine2.createGlideEngine())// 外部传入图片加载引擎，必传项
//                //.theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
//                .setPictureUIStyle(PictureSelectorUIStyle.ofDefaultStyle())
//                //.setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
//                //.setPictureCropStyle(mCropParameterStyle)// 动态自定义裁剪主题
//                .setPictureWindowAnimationStyle(PictureWindowAnimationStyle.ofDefaultWindowAnimationStyle())// 自定义相册启动退出动画
//                .isWeChatStyle(true)// 是否开启微信图片选择风格
//                .isUseCustomCamera(false)// 是否使用自定义相机
////                                        .setLan,guage(language)// 设置语言，默认中文
////                                        .isPageStrategy(cbPage.isChecked())// 是否开启分页策略 & 每页多少条；默认开启
//                .setRecyclerAnimationMode(AnimationType.DEFAULT_ANIMATION)// 列表动画效果
//                .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
//                //.isSyncCover(true)// 是否强制从MediaStore里同步相册封面，如果相册封面没显示异常则没必要设置
//                //.isCameraAroundState(false) // 是否开启前置摄像头，默认false，如果使用系统拍照 可能部分机型会有兼容性问题
//                //.isCameraRotateImage(false) // 拍照图片旋转是否自动纠正
//                //.isAutoRotating(false)// 压缩时自动纠正有旋转的图片
////                                        .isMaxSelectEnabledMask(cbEnabledMask.isChecked())// 选择数到了最大阀值列表是否启用蒙层效果
//                //.isAutomaticTitleRecyclerTop(false)// 连续点击标题栏RecyclerView是否自动回到顶部,默认true
//                //.loadCacheResourcesCallback(GlideCacheEngine.createCacheEngine())// 获取图片资源缓存，主要是解决华为10部分机型在拷贝文件过多时会出现卡的问题，这里可以判断只在会出现一直转圈问题机型上使用
//                //.setOutputCameraPath(createCustomCameraOutPath())// 自定义相机输出目录
//                //.setButtonFeatures(CustomCameraView.BUTTON_STATE_BOTH)// 设置自定义相机按钮状态
////                        .setCaptureLoadingColor(ContextCompat.getColor(mActivity, R.color.app_color_blue))
////                                        .maxSelectNum(maxSelectNum)// 最大图片选择数量
//                .minSelectNum(1)// 最小选择数量
//                .maxVideoSelectNum(1) // 视频最大选择数量
//                //.minVideoSelectNum(1)// 视频最小选择数量
//                //.closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// 关闭在AndroidQ下获取图片或视频宽高相反自动转换
//                .imageSpanCount(4)// 每行显示个数
//                //.queryFileSize() // 过滤最大资源,已废弃
//                //.filterMinFileSize(5)// 过滤最小资源，单位kb
//                //.filterMaxFileSize()// 过滤最大资源，单位kb
//                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
//                .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高,默认为true
//                .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// 如果视频有旋转角度则对换宽高,默认为false
//                .isAndroidQTransform(true)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对compress(false); && .isEnableCrop(false);有效,默认处理
//                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
//                .isOriginalImageControl(false)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
//                //.bindCustomPlayVideoCallback(new MyVideoSelectedPlayCallback(getContext()))// 自定义视频播放回调控制，用户可以使用自己的视频播放界面
//                //.bindCustomPreviewCallback(new MyCustomPreviewInterfaceListener())// 自定义图片预览回调接口
//                //.bindCustomCameraInterfaceListener(new MyCustomCameraInterfaceListener())// 提供给用户的一些额外的自定义操作回调
//                //.bindCustomPermissionsObtainListener(new MyPermissionsObtainCallback())// 自定义权限拦截
//                //.cameraFileName(System.currentTimeMillis() +".jpg")    // 重命名拍照文件名、如果是相册拍照则内部会自动拼上当前时间戳防止重复，注意这个只在使用相机时可以使用，如果使用相机又开启了压缩或裁剪 需要配合压缩和裁剪文件名api
//                //.renameCompressFile(System.currentTimeMillis() +".jpg")// 重命名压缩文件名、 如果是多张压缩则内部会自动拼上当前时间戳防止重复
//                //.renameCropFileName(System.currentTimeMillis() + ".jpg")// 重命名裁剪文件名、 如果是多张裁剪则内部会自动拼上当前时间戳防止重复
//                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
//                .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
//                .isPreviewImage(true)// 是否可预览图片
//                .isPreviewVideo(false)// 是否可预览视频
//                //.querySpecifiedFormatSuffix(PictureMimeType.ofJPEG())// 查询指定后缀格式资源
//                //.queryMimeTypeConditions(PictureMimeType.ofWEBP())
//                .isEnablePreviewAudio(false) // 是否可播放音频
//                .isCamera(true)// 是否显示拍照按钮
//                //.isMultipleSkipCrop(false)// 多图裁剪时是否支持跳过，默认支持
//                //.isMultipleRecyclerAnimation(false)// 多图裁剪底部列表显示动画效果
//                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg,Android Q使用PictureMimeType.PNG_Q
//                .isEnableCrop(true)// 是否裁剪
//                //.basicUCropConfig()//对外提供所有UCropOptions参数配制，但如果PictureSelector原本支持设置的还是会使用原有的设置
//                .isCompress(true)// 是否压缩
//                //.compressFocusAlpha(true)// 压缩时是否开启透明通道
//                //.compressEngine(ImageCompressEngine.createCompressEngine()) // 自定义压缩引擎
//                //.compressQuality(80)// 图片压缩后输出质量 0~ 100
//                .synOrAsy(false)//同步true或异步false 压缩 默认同步
//                //.queryMaxFileSize(10)// 只查多少M以内的图片、视频、音频  单位M
//                //.compressSavePath(getPath())//压缩图片保存地址
//                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效 注：已废弃
//                //.glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度 注：已废弃
//                .withAspectRatio(0, 0)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
//                .isGif(true)// 是否显示gif图片
//                //.isWebp(false)// 是否显示webp图片,默认显示
//                //.isBmp(false)//是否显示bmp图片,默认显示
//                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
//                .circleDimmedLayer(false)// 是否圆形裁剪
//                //.setCropDimmedColor(ContextCompat.getColor(getContext(), R.color.app_color_white))// 设置裁剪背景色值
//                //.setCircleDimmedBorderColor(ContextCompat.getColor(getApplicationContext(), R.color.app_color_white))// 设置圆形裁剪边框色值
//                //.setCircleStrokeWidth(3)// 设置圆形裁剪边框粗细
//                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                .isOpenClickSound(false)// 是否开启点击声音
////                                        .selectionData(mAdapter.getData())// 是否传入已选图片
//                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                //.videoMinSecond(10)// 查询多少秒以内的视频
//                //.videoMaxSecond(15)// 查询多少秒以内的视频
//                //.recordVideoSecond(10)//录制视频秒数 默认60s
//                //.isPreviewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
//                //.cropCompressQuality(90)// 注：已废弃 改用cutOutQuality()
//                .cutOutQuality(90)// 裁剪输出质量 默认100
//                //.cutCompressFormat(Bitmap.CompressFormat.PNG.name())//裁剪图片输出Format格式，默认JPEG
//                .minimumCompressSize(100)// 小于多少kb的图片不压缩
//                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
//                //.cropImageWideHigh()// 裁剪宽高比，设置如果大于图片本身宽高则无效
//                //.rotateEnabled(false) // 裁剪是否可旋转图片
//                //.scaleEnabled(false)// 裁剪是否可放大缩小图片
//                //.videoQuality()// 视频录制质量 0 or 1
//                //.forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
//                .forResult(new OnResultCallbackListener<LocalMedia>() {
//                    @Override
//                    public void onResult(List<LocalMedia> result) {
//                        for (LocalMedia media : result) {
//                            if (media.getWidth() == 0 || media.getHeight() == 0) {
//                                if (PictureMimeType.isHasImage(media.getMimeType())) {
//                                    MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(media.getPath());
//                                    media.setWidth(imageExtraInfo.getWidth());
//                                    media.setHeight(imageExtraInfo.getHeight());
//                                } else if (PictureMimeType.isHasVideo(media.getMimeType())) {
//                                    MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(PictureAppMaster.getInstance().getAppContext(), media.getPath());
//                                    media.setWidth(videoExtraInfo.getWidth());
//                                    media.setHeight(videoExtraInfo.getHeight());
//                                }
//                            }
//                            Log.i("TAG", "文件名: " + media.getFileName());
//                            Log.i("TAG", "是否压缩:" + media.isCompressed());
//                            Log.i("TAG", "压缩:" + media.getCompressPath());
//                            Log.i("TAG", "原图:" + media.getPath());
//                            Log.i("TAG", "绝对路径:" + media.getRealPath());
//                            Log.i("TAG", "是否裁剪:" + media.isCut());
//                            Log.i("TAG", "裁剪:" + media.getCutPath());
//                            Log.i("TAG", "是否开启原图:" + media.isOriginal());
//                            Log.i("TAG", "原图路径:" + media.getOriginalPath());
//                            Log.i("TAG", "Android Q 特有Path:" + media.getAndroidQToPath());
//                            Log.i("TAG", "宽高: " + media.getWidth() + "x" + media.getHeight());
//                            Log.i("TAG", "Size: " + media.getSize());
//
//                            Log.i("TAG", "onResult: " + media.toString());
//
//                            // TODO 可以通过PictureSelectorExternalUtils.getExifInterface();方法获取一些额外的资源信息，如旋转角度、经纬度等信息
//                        }
//                        String ImgUrl = result.get(0).getAndroidQToPath();
////                        Glide.with(ScanAct1.this).load(ImgUrl).into(iv1);
//                        Glide.with(ScanAct2.this)
//                                .asBitmap()
//                                .load(ImgUrl)
//                                .into(new SimpleTarget<Bitmap>() {
//                                    @Override
//                                    public void onResourceReady(@NotNull Bitmap bitmap, Transition<? super Bitmap> transition) {
//                                        Drawable newDraw = new BitmapDrawable(bitmap);
//                                        iv0.setImageDrawable(newDraw);
//                                        //
////                                        final String s = TextRecognition.recognize(ImgUrl);
////                                        tv0.setText("result=" + s);
//                                    }
//                                });
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//                });
//    }
//
//    public void onClickBtn3(View v) {
//        //                String path = getExternalFilesDir("image").getAbsolutePath() + "/a.jpg";
////                String s = ScannerUtils.decodeText(path);
////                BankCardInfoBean b = ScannerUtils.getBankCardInfo("6222600260001072444");
////                String s = b == null ? null : b.toString();
//        PictureSelector.create(ScanAct2.this)
//                .openCamera(PictureMimeType.ofAll())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
//                .imageEngine(GlideEngine2.createGlideEngine())// 外部传入图片加载引擎，必传项
//                //.theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
//                .setPictureUIStyle(PictureSelectorUIStyle.ofDefaultStyle())
//                //.setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
//                //.setPictureCropStyle(mCropParameterStyle)// 动态自定义裁剪主题
//                .setPictureWindowAnimationStyle(PictureWindowAnimationStyle.ofDefaultWindowAnimationStyle())// 自定义相册启动退出动画
//                .isWeChatStyle(true)// 是否开启微信图片选择风格
//                .isUseCustomCamera(false)// 是否使用自定义相机
////                                        .setLan,guage(language)// 设置语言，默认中文
////                                        .isPageStrategy(cbPage.isChecked())// 是否开启分页策略 & 每页多少条；默认开启
//                .setRecyclerAnimationMode(AnimationType.DEFAULT_ANIMATION)// 列表动画效果
//                .isWithVideoImage(true)// 图片和视频是否可以同选,只在ofAll模式下有效
//                //.isSyncCover(true)// 是否强制从MediaStore里同步相册封面，如果相册封面没显示异常则没必要设置
//                //.isCameraAroundState(false) // 是否开启前置摄像头，默认false，如果使用系统拍照 可能部分机型会有兼容性问题
//                //.isCameraRotateImage(false) // 拍照图片旋转是否自动纠正
//                //.isAutoRotating(false)// 压缩时自动纠正有旋转的图片
////                                        .isMaxSelectEnabledMask(cbEnabledMask.isChecked())// 选择数到了最大阀值列表是否启用蒙层效果
//                //.isAutomaticTitleRecyclerTop(false)// 连续点击标题栏RecyclerView是否自动回到顶部,默认true
//                //.loadCacheResourcesCallback(GlideCacheEngine.createCacheEngine())// 获取图片资源缓存，主要是解决华为10部分机型在拷贝文件过多时会出现卡的问题，这里可以判断只在会出现一直转圈问题机型上使用
//                //.setOutputCameraPath(createCustomCameraOutPath())// 自定义相机输出目录
//                //.setButtonFeatures(CustomCameraView.BUTTON_STATE_BOTH)// 设置自定义相机按钮状态
////                        .setCaptureLoadingColor(ContextCompat.getColor(mActivity, R.color.app_color_blue))
////                                        .maxSelectNum(maxSelectNum)// 最大图片选择数量
//                .minSelectNum(1)// 最小选择数量
//                .maxVideoSelectNum(1) // 视频最大选择数量
//                //.minVideoSelectNum(1)// 视频最小选择数量
//                //.closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// 关闭在AndroidQ下获取图片或视频宽高相反自动转换
//                .imageSpanCount(4)// 每行显示个数
//                //.queryFileSize() // 过滤最大资源,已废弃
//                //.filterMinFileSize(5)// 过滤最小资源，单位kb
//                //.filterMaxFileSize()// 过滤最大资源，单位kb
//                .isReturnEmpty(false)// 未选择数据时点击按钮是否可以返回
//                .closeAndroidQChangeWH(true)//如果图片有旋转角度则对换宽高,默认为true
//                .closeAndroidQChangeVideoWH(!SdkVersionUtils.checkedAndroid_Q())// 如果视频有旋转角度则对换宽高,默认为false
//                .isAndroidQTransform(true)// 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对compress(false); && .isEnableCrop(false);有效,默认处理
//                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
//                .isOriginalImageControl(false)// 是否显示原图控制按钮，如果设置为true则用户可以自由选择是否使用原图，压缩、裁剪功能将会失效
//                //.bindCustomPlayVideoCallback(new MyVideoSelectedPlayCallback(getContext()))// 自定义视频播放回调控制，用户可以使用自己的视频播放界面
//                //.bindCustomPreviewCallback(new MyCustomPreviewInterfaceListener())// 自定义图片预览回调接口
//                //.bindCustomCameraInterfaceListener(new MyCustomCameraInterfaceListener())// 提供给用户的一些额外的自定义操作回调
//                //.bindCustomPermissionsObtainListener(new MyPermissionsObtainCallback())// 自定义权限拦截
//                //.cameraFileName(System.currentTimeMillis() +".jpg")    // 重命名拍照文件名、如果是相册拍照则内部会自动拼上当前时间戳防止重复，注意这个只在使用相机时可以使用，如果使用相机又开启了压缩或裁剪 需要配合压缩和裁剪文件名api
//                //.renameCompressFile(System.currentTimeMillis() +".jpg")// 重命名压缩文件名、 如果是多张压缩则内部会自动拼上当前时间戳防止重复
//                //.renameCropFileName(System.currentTimeMillis() + ".jpg")// 重命名裁剪文件名、 如果是多张裁剪则内部会自动拼上当前时间戳防止重复
//                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
//                .isSingleDirectReturn(true)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
//                .isPreviewImage(true)// 是否可预览图片
//                .isPreviewVideo(false)// 是否可预览视频
//                //.querySpecifiedFormatSuffix(PictureMimeType.ofJPEG())// 查询指定后缀格式资源
//                //.queryMimeTypeConditions(PictureMimeType.ofWEBP())
//                .isEnablePreviewAudio(false) // 是否可播放音频
//                .isCamera(true)// 是否显示拍照按钮
//                //.isMultipleSkipCrop(false)// 多图裁剪时是否支持跳过，默认支持
//                //.isMultipleRecyclerAnimation(false)// 多图裁剪底部列表显示动画效果
//                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg,Android Q使用PictureMimeType.PNG_Q
//                .isEnableCrop(true)// 是否裁剪
//                //.basicUCropConfig()//对外提供所有UCropOptions参数配制，但如果PictureSelector原本支持设置的还是会使用原有的设置
//                .isCompress(true)// 是否压缩
//                //.compressFocusAlpha(true)// 压缩时是否开启透明通道
//                //.compressEngine(ImageCompressEngine.createCompressEngine()) // 自定义压缩引擎
//                //.compressQuality(80)// 图片压缩后输出质量 0~ 100
//                .synOrAsy(false)//同步true或异步false 压缩 默认同步
//                //.queryMaxFileSize(10)// 只查多少M以内的图片、视频、音频  单位M
//                //.compressSavePath(getPath())//压缩图片保存地址
//                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效 注：已废弃
//                //.glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度 注：已废弃
//                .withAspectRatio(0, 0)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
//                .isGif(true)// 是否显示gif图片
//                //.isWebp(false)// 是否显示webp图片,默认显示
//                //.isBmp(false)//是否显示bmp图片,默认显示
//                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
//                .circleDimmedLayer(false)// 是否圆形裁剪
//                //.setCropDimmedColor(ContextCompat.getColor(getContext(), R.color.app_color_white))// 设置裁剪背景色值
//                //.setCircleDimmedBorderColor(ContextCompat.getColor(getApplicationContext(), R.color.app_color_white))// 设置圆形裁剪边框色值
//                //.setCircleStrokeWidth(3)// 设置圆形裁剪边框粗细
//                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                .isOpenClickSound(false)// 是否开启点击声音
////                                        .selectionData(mAdapter.getData())// 是否传入已选图片
//                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                //.videoMinSecond(10)// 查询多少秒以内的视频
//                //.videoMaxSecond(15)// 查询多少秒以内的视频
//                //.recordVideoSecond(10)//录制视频秒数 默认60s
//                //.isPreviewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
//                //.cropCompressQuality(90)// 注：已废弃 改用cutOutQuality()
//                .cutOutQuality(90)// 裁剪输出质量 默认100
//                //.cutCompressFormat(Bitmap.CompressFormat.PNG.name())//裁剪图片输出Format格式，默认JPEG
//                .minimumCompressSize(100)// 小于多少kb的图片不压缩
//                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
//                //.cropImageWideHigh()// 裁剪宽高比，设置如果大于图片本身宽高则无效
//                //.rotateEnabled(false) // 裁剪是否可旋转图片
//                //.scaleEnabled(false)// 裁剪是否可放大缩小图片
//                //.videoQuality()// 视频录制质量 0 or 1
//                //.forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
//                .forResult(new OnResultCallbackListener<LocalMedia>() {
//                    @Override
//                    public void onResult(List<LocalMedia> result) {
//                        for (LocalMedia media : result) {
//                            if (media.getWidth() == 0 || media.getHeight() == 0) {
//                                if (PictureMimeType.isHasImage(media.getMimeType())) {
//                                    MediaExtraInfo imageExtraInfo = MediaUtils.getImageSize(media.getPath());
//                                    media.setWidth(imageExtraInfo.getWidth());
//                                    media.setHeight(imageExtraInfo.getHeight());
//                                } else if (PictureMimeType.isHasVideo(media.getMimeType())) {
//                                    MediaExtraInfo videoExtraInfo = MediaUtils.getVideoSize(PictureAppMaster.getInstance().getAppContext(), media.getPath());
//                                    media.setWidth(videoExtraInfo.getWidth());
//                                    media.setHeight(videoExtraInfo.getHeight());
//                                }
//                            }
//                            Log.i("TAG", "文件名: " + media.getFileName());
//                            Log.i("TAG", "是否压缩:" + media.isCompressed());
//                            Log.i("TAG", "压缩:" + media.getCompressPath());
//                            Log.i("TAG", "原图:" + media.getPath());
//                            Log.i("TAG", "绝对路径:" + media.getRealPath());
//                            Log.i("TAG", "是否裁剪:" + media.isCut());
//                            Log.i("TAG", "裁剪:" + media.getCutPath());
//                            Log.i("TAG", "是否开启原图:" + media.isOriginal());
//                            Log.i("TAG", "原图路径:" + media.getOriginalPath());
//                            Log.i("TAG", "Android Q 特有Path:" + media.getAndroidQToPath());
//                            Log.i("TAG", "宽高: " + media.getWidth() + "x" + media.getHeight());
//                            Log.i("TAG", "Size: " + media.getSize());
//
//                            Log.i("TAG", "onResult: " + media.toString());
//
//                            // TODO 可以通过PictureSelectorExternalUtils.getExifInterface();方法获取一些额外的资源信息，如旋转角度、经纬度等信息
//                        }
//                        String ImgUrl = result.get(0).getAndroidQToPath();
////                        Glide.with(ScanAct1.this).load(ImgUrl).into(iv1);
//                        Glide.with(ScanAct2.this)
//                                .asBitmap()
//                                .load(ImgUrl)
//                                .into(new SimpleTarget<Bitmap>() {
//                                    @Override
//                                    public void onResourceReady(@NotNull Bitmap bitmap, Transition<? super Bitmap> transition) {
//                                        Drawable newDraw = new BitmapDrawable(bitmap);
//                                        iv1.setImageDrawable(newDraw);
//                                        //
//                                        final String s;
//                                        try {
//                                            s = String.valueOf(decodeNsfw(ScanAct2.this, bitmap));
//                                            ToastUtils.showLong("黄图参数：" + s);
//                                            tv1.setText("黄图参数：" + s);
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//
//                                    }
//                                });
//                    }
//
//                    @Override
//                    public void onCancel() {
//
//                    }
//                });
//
//    }
//
//    public void onClickBtn4(View v) {
////        Intent intent = new Intent(this, ScannerAct2.class);
////        startActivity(intent);
//    }
//
//    public void onClickBtn5(View v) {
////        Intent intent = new Intent(this, ScannerAct2.class);
////        startActivity(intent);
//    }
//
//    public void onClickBtn6(View v) {
////        Intent intent = new Intent(this, ScannerAct2.class);
////        startActivity(intent);
//    }
//
//    /**
//     * 黄图识别，建议在子线程运行
//     *
//     * @param context
//     * @param bmp
//     * @return 大于0.3可以说图片涉黄，根据实际情况取值
//     * @throws Exception
//     */
//    public static float decodeNsfw(Context context, Bitmap bmp) throws Exception {
////        Interpreter tflite = NsfwUtils.getInterpreter(context);
////        float f = NsfwUtils.decode(tflite, bmp);
////        NsfwUtils.release(tflite);
//        return 1f;
//    }
//
//
//}
