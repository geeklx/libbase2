package xyz.doikki.dkplayer.activity.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import xyz.doikki.dkplayer.R;
import xyz.doikki.dkplayer.activity.BaseActivityDk;
import xyz.doikki.dkplayer.bean.HTyxs1Bean;
import xyz.doikki.dkplayer.util.IntentKeysDk;
import xyz.doikki.dkplayer.util.ProgressManagerImplDk1;
import xyz.doikki.dkplayer.util.UtilsDk;
import xyz.doikki.dkplayer.widget.component.DebugInfoViewDk;
import xyz.doikki.videocontroller.StandardVideoController;
import xyz.doikki.videocontroller.component.CompleteView;
import xyz.doikki.videocontroller.component.ErrorView;
import xyz.doikki.videocontroller.component.GestureView;
import xyz.doikki.videocontroller.component.PrepareView;
import xyz.doikki.videocontroller.component.TitleView;
import xyz.doikki.videocontroller.component.VodControlView;
import xyz.doikki.videoplayer.controller.ControlWrapper;
import xyz.doikki.videoplayer.controller.IControlComponent;
import xyz.doikki.videoplayer.player.AbstractPlayer;
import xyz.doikki.videoplayer.player.VideoView;
import xyz.doikki.videoplayer.util.L;

public class PlayerActivityDk extends BaseActivityDk<VideoView<AbstractPlayer>> {

    private static final String THUMB = "https://cms-bucket.nosdn.127.net/eb411c2810f04ffa8aaafc42052b233820180418095416.jpeg";
    private String url;
    private ScheduledExecutorService mExecutorService;

    public static void start(Context context, String url, String title, boolean isLive) {
        Intent intent = new Intent(context, PlayerActivityDk.class);
        intent.putExtra(IntentKeysDk.URL, url);
        intent.putExtra(IntentKeysDk.IS_LIVE, isLive);
        intent.putExtra(IntentKeysDk.TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_playerdk;
    }

    @Override
    protected void initView() {
        super.initView();
        mVideoView = findViewById(R.id.player);
        //播放其他视频
        EditText etOtherVideo = findViewById(R.id.et_other_video);
        findViewById(R.id.btn_start_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.release();
                mVideoView.setUrl(etOtherVideo.getText().toString());
                mVideoView.start();
            }
        });
        //测试
        HTyxs1Bean hTyxs1Bean = new HTyxs1Bean();
        hTyxs1Bean.setDrag(true);
        hTyxs1Bean.setStudyStatus("未学习");
        hTyxs1Bean.setStudyTimes(SPUtils.getInstance().getString("xianzhi_times", "0"));
        setvideo(hTyxs1Bean);

    }

    private VodControlView vodControlView = null;

    private void setvideo(HTyxs1Bean versionInfoBean) {
        Intent intent = getIntent();
        if (intent != null) {
            StandardVideoController controller = new StandardVideoController(this);
            //根据屏幕方向自动进入/退出全屏
            controller.setEnableOrientation(true);
            PrepareView prepareView = new PrepareView(this);//准备播放界面
            ImageView thumb = prepareView.findViewById(R.id.thumb);//封面图
            Glide.with(this).load(THUMB).into(thumb);
            controller.addControlComponent(prepareView);
            controller.addControlComponent(new CompleteView(this));//自动完成播放界面
            controller.addControlComponent(new ErrorView(this));//错误界面
            TitleView titleView = new TitleView(this);//标题栏
            controller.addControlComponent(titleView);
            //根据是否为直播设置不同的底部控制条
            boolean isLive = intent.getBooleanExtra(IntentKeysDk.IS_LIVE, false);
            vodControlView = new VodControlView(this);//点播控制条
            //是否显示底部进度条。默认显示
            vodControlView.showBottomProgress(true);
            controller.addControlComponent(vodControlView);
            GestureView gestureControlView = new GestureView(this);//滑动控制视图
            controller.addControlComponent(gestureControlView);
            //根据是否为直播决定是否需要滑动调节进度
            controller.setCanChangePosition(!isLive);
            //设置标题
            String title = intent.getStringExtra(IntentKeysDk.TITLE);
            titleView.setTitle(title);
            //在控制器上显示调试信息
            controller.addControlComponent(new DebugInfoViewDk(this));
            //在LogCat显示调试信息
//            controller.addControlComponent(new PlayerMonitorDk());
            controller.addControlComponent(new IControlComponent() {
                @Override
                public void attach(@NonNull ControlWrapper controlWrapper) {

                }

                @Override
                public View getView() {
                    return null;
                }

                @Override
                public void onVisibilityChanged(boolean isVisible, Animation anim) {

                }

                @Override
                public void onPlayStateChanged(int playState) {

                }

                @Override
                public void onPlayerStateChanged(int playerState) {

                }

                @SuppressLint("LongLogTag")
                @Override
                public void setProgress(int duration, int position) {
//                    Log.e("VideoPlayerAct-mVideoViewsetProgress", duration + "");
                    Log.e("VideoPlayerAct-mVideoViewsetProgress-position", position + "");
                    long max = Long.parseLong(SPUtils.getInstance().getString("xianzhi_times", "0"));
                    Log.e("VideoPlayerAct-mVideoViewsetProgress-max", max + "");
                    //
                    if (position != 0) {
                        // 销毁pos=0，不做存储考虑
                        SPUtils.getInstance().put(String.valueOf(url.hashCode()), max + "");
                    }
                    if (position > max) {
                        SPUtils.getInstance().put("xianzhi_times", (long) position + "");
                    }
                }

                @Override
                public void onLockStateChanged(boolean isLocked) {

                }
            });
//            //根据接口设置跳转到哪里开始播放bufen
//            if (versionInfoBean.isDrag()) {
//                // 限制
//                Log.e("VideoPlayerAct-接口限制传值", versionInfoBean.getStudyTimes());
//
//                if (Long.parseLong(SPUtils.getInstance().getString(String.valueOf(url.hashCode()), "0")) <
//                        (long) (Integer.parseInt(versionInfoBean.getStudyTimes())) * 1000) {
//                } else {
//                    SPUtils.getInstance().put(String.valueOf(url.hashCode()), (long) (Integer.parseInt(versionInfoBean.getStudyTimes())) * 1000 + "");
//                }
//            }
            // 限制观看拖动MAX
            vodControlView.setmIsxianzhi(versionInfoBean.isDrag());
            //保存播放进度
            mVideoView.setProgressManager(new ProgressManagerImplDk1());
            //如果你不想要UI，不要设置控制器即可
            mVideoView.setVideoController(controller);

            url = intent.getStringExtra(IntentKeysDk.URL);

            //点击文件管理器中的视频，选择DKPlayer打开，将会走以下代码
            if (TextUtils.isEmpty(url)
                    && Intent.ACTION_VIEW.equals(intent.getAction())) {
                //获取intent中的视频地址
                url = UtilsDk.getFileFromContentUri(this, intent.getData());
            }
            mVideoView.setUrl(url);
            //播放状态监听
            mVideoView.addOnStateChangeListener(mOnStateChangeListener);
            //获取进度条秒
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("mVideoView", mVideoView.getCurrentPosition() + "");// 内存不准确
                    Log.e("mVideoView2", Long.parseLong(SPUtils.getInstance().getString(String.valueOf(url.hashCode()), "0")) + "");// 硬盘准确
                }
            }, 1000);

            //临时切换播放核心，如需全局请通过VideoConfig配置，详见MyApplication
            //使用IjkPlayer解码
//            mVideoView.setPlayerFactory(IjkPlayerFactory.create());
            //使用ExoPlayer解码
//            mVideoView.setPlayerFactory(ExoMediaPlayerFactory.create());
            //使用MediaPlayer解码
//            mVideoView.setPlayerFactory(AndroidMediaPlayerFactory.create());

            //设置静音播放
//            mVideoView.setMute(true);
            mVideoView.start();
            //开始上传数据bufen
            setTime(versionInfoBean);

        }
    }

    private String userId;
    private String courseCode;
    private String source_sys;
    private String orgType;
    private String actionCode;

    private VideoView.OnStateChangeListener mOnStateChangeListener = new VideoView.SimpleOnStateChangeListener() {
        @Override
        public void onPlayerStateChanged(int playerState) {
            switch (playerState) {
                case VideoView.PLAYER_NORMAL://小屏
                    break;
                case VideoView.PLAYER_FULL_SCREEN://全屏
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPlayStateChanged(int playState) {
            switch (playState) {
                case VideoView.STATE_IDLE:
                    break;
                case VideoView.STATE_PREPARING:
                    break;
                case VideoView.STATE_PREPARED:
                    break;
                case VideoView.STATE_PLAYING:
                    //需在此时获取视频宽高
                    int[] videoSize = mVideoView.getVideoSize();
                    L.d("视频宽：" + videoSize[0]);
                    L.d("视频高：" + videoSize[1]);

                    break;
                case VideoView.STATE_PAUSED:
                    break;
                case VideoView.STATE_BUFFERING:
                    break;
                case VideoView.STATE_BUFFERED:
                    break;
                case VideoView.STATE_PLAYBACK_COMPLETED:
                    // 完成播放
                    Log.e("VideoPlayerAct-", "完成播放");
                    if (mExecutorService != null) {
                        mExecutorService.shutdown();
                    }
//                    hTyxs1Presenter.getHTyxs1Presenter(userId, courseCode, source_sys, orgType, actionCode);
//                    hTyxs2Presenter.getHTyxs2Presenter(userId, courseCode, 15 + "");
//                    hTyxs3Presenter.getHTyxs3Presenter(userId, courseCode, source_sys, orgType, actionCode);
                    break;
                case VideoView.STATE_ERROR:
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (mExecutorService != null) {
            mExecutorService.shutdown();
            mExecutorService = null;
        }
        super.onDestroy();
    }

    private static int COMPLETED = 1;

    private void setTime(HTyxs1Bean versionInfoBean) {
        // 完成播放
        Log.e("VideoPlayerAct-", "开始上传数据bufen");
        if (!versionInfoBean.isDrag()) {
            return;
        }
        mExecutorService = Executors.newScheduledThreadPool(1);
        mExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = COMPLETED;
                handler.sendMessage(message);

            }
        }, 0, 5000, TimeUnit.MILLISECONDS);
    }

    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                // 上传接口数据bufen
                long second = Long.parseLong(SPUtils.getInstance().getString(String.valueOf(url.hashCode()), "0")) / 1000;
                int seconds = Integer.parseInt(second + "");
                Log.e("VideoPlayerAct-接口打点传值", seconds + "");
//                hTyxs2Presenter.getHTyxs2Presenter(userId, courseCode, seconds + "");

            }
        }
    };

    private int i = 0;

    public void onButtonClick(View view) {
        int id = view.getId();
        if (id == R.id.scale_default) {
            mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_DEFAULT);
        } else if (id == R.id.scale_169) {
            mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_16_9);
        } else if (id == R.id.scale_43) {
            mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_4_3);
        } else if (id == R.id.scale_original) {
            mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_ORIGINAL);
        } else if (id == R.id.scale_match_parent) {
            mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_MATCH_PARENT);
        } else if (id == R.id.scale_center_crop) {
            mVideoView.setScreenScaleType(VideoView.SCREEN_SCALE_CENTER_CROP);
        } else if (id == R.id.speed_0_5) {
            mVideoView.setSpeed(0.5f);
        } else if (id == R.id.speed_0_75) {
            mVideoView.setSpeed(0.75f);
        } else if (id == R.id.speed_1_0) {
            mVideoView.setSpeed(1.0f);
        } else if (id == R.id.speed_1_5) {
            mVideoView.setSpeed(1.5f);
        } else if (id == R.id.speed_2_0) {
            mVideoView.setSpeed(2.0f);
        } else if (id == R.id.screen_shot) {
            ImageView imageView = findViewById(R.id.iv_screen_shot);
            Bitmap bitmap = mVideoView.doScreenShot();
            imageView.setImageBitmap(bitmap);
        } else if (id == R.id.mirror_rotate) {
            mVideoView.setMirrorRotation(i % 2 == 0);
            i++;
        } else if (id == R.id.btn_mute) {
            mVideoView.setMute(!mVideoView.isMute());
        }
    }
}
