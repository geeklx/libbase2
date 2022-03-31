//package com.haier.cellarette.baselibrary.floatwindows;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Gravity;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.ImageView;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.geek.appindex.R;
//import com.geek.appindex.index.ShouyeActivity;
//import com.geek.appindex.musicplayer.liebiao.MusicAct1;
//import com.geek.libbase.base.SlbBaseActivity;
//import com.hjq.permissions.OnPermissionCallback;
//import com.hjq.permissions.Permission;
//import com.hjq.permissions.XXPermissions;
//import com.lzf.easyfloat.EasyFloat;
//import com.lzf.easyfloat.enums.ShowPattern;
//import com.lzf.easyfloat.enums.SidePattern;
//import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
//import com.lzf.easyfloat.interfaces.OnInvokeView;
//
//import java.util.List;
//
//public class MusicAct extends SlbBaseActivity {
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_musicact;
//    }
//
//    @Override
//    protected void setup(@Nullable Bundle savedInstanceState) {
//        super.setup(savedInstanceState);
//        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                XXPermissions.with(MusicAct.this)
//                        .permission(Permission.SYSTEM_ALERT_WINDOW)
//                        .request(new OnPermissionCallback() {
//
//                            @Override
//                            public void onGranted(List<String> permissions, boolean all) {
////                            toast("获取悬浮窗权限成功");
//                                showAppFloat();
//                            }
//
//                            @Override
//                            public void onDenied(List<String> permissions, boolean never) {
//
//                            }
//                        });
//            }
//        });
//        findViewById(R.id.tv2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MusicAct.this, MusicAct1.class));
//            }
//        });
//    }
//
//    private void showAppFloat() {
//        EasyFloat.with(this)
//                .setShowPattern(ShowPattern.ALL_TIME)
//                .setSidePattern(SidePattern.RESULT_SIDE)
//                .setImmersionStatusBar(false)
//                // 设置系统浮窗的不需要显示的页面
////                .setFilter(ShouyeActivity.class,MusicAct.class)
//                .setGravity(Gravity.BOTTOM, 0, -400)
//                .setLayout(R.layout.float_app, new OnInvokeView() {
//                    private ImageView ivClose;
//                    private TextView tvOpenMain;
//                    private CheckBox checkbox;
//                    private SeekBar seekBar;
//                    private RoundProgressBar roundProgressBar;
//
//                    @Override
//                    public void invoke(View view) {
//                        ivClose = view.findViewById(R.id.ivClose);
//                        tvOpenMain = view.findViewById(R.id.tvOpenMain);
//                        checkbox = view.findViewById(R.id.checkbox);
//                        seekBar = view.findViewById(R.id.seekBar);
//                        roundProgressBar = view.findViewById(R.id.roundProgressBar);
//                        ivClose.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                EasyFloat.dismiss();
//                            }
//                        });
//                        tvOpenMain.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                startActivity(new Intent(MusicAct.this, ShouyeActivity.class));
//                            }
//                        });
//                        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                                EasyFloat.dragEnable(isChecked);
//                            }
//                        });
//                        roundProgressBar.setProgress(66, "66");
//                        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                            @Override
//                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                                roundProgressBar.setProgress(progress, progress + "");
//                            }
//
//                            @Override
//                            public void onStartTrackingTouch(SeekBar seekBar) {
//
//                            }
//
//                            @Override
//                            public void onStopTrackingTouch(SeekBar seekBar) {
//
//                            }
//                        });
//                    }
//                })
////                // 解决 ListView 拖拽滑动冲突
////                it.findViewById<ListView>(R.id.lv_test).apply {
////                    adapter = MyAdapter(
////                        this@MainActivity,
////                        arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "...")
////                    )
////
////                    // 监听 ListView 的触摸事件，手指触摸时关闭拖拽，手指离开重新开启拖拽
////                    setOnTouchListener { _, event ->
////                        logger.e("listView: ${event.action}")
////                        EasyFloat.appFloatDragEnable(event?.action == MotionEvent.ACTION_UP)
////                        false
////                    }
////                }
//                .registerCallbacks(new OnFloatCallbacks() {
//                    @Override
//                    public void createdResult(boolean b, @Nullable String s, @Nullable View view) {
//
//                    }
//
//                    @Override
//                    public void show(@NonNull View view) {
//
//                    }
//
//                    @Override
//                    public void hide(@NonNull View view) {
//
//                    }
//
//                    @Override
//                    public void dismiss() {
//
//                    }
//
//                    @Override
//                    public void touchEvent(@NonNull View view, @NonNull MotionEvent motionEvent) {
//
//                    }
//
//                    @Override
//                    public void drag(@NonNull View view, @NonNull MotionEvent motionEvent) {
//                        // 业务处理bufen
////                        DragUtils.registerDragClose(motionEvent, object : OnTouchRangeListener {
////                            override fun touchInRange(inRange: Boolean, view: BaseSwitchView) {
////                                setVibrator(inRange)
////                                view.findViewById<TextView>(com.lzf.easyfloat.R.id.tv_delete).text =
////                                if (inRange) "松手删除" else "删除浮窗"
////
////                                view.findViewById<ImageView>(com.lzf.easyfloat.R.id.iv_delete)
////                                        .setImageResource(
////                                if (inRange) com.lzf.easyfloat.R.drawable.icon_delete_selected
////                                else com.lzf.easyfloat.R.drawable.icon_delete_normal
////                    )
////                            }
////
////                            override fun touchUpInRange() {
////                                EasyFloat.dismiss()
////                            }
////                        }, showPattern = ShowPattern.ALL_TIME)
//                    }
//
//                    @Override
//                    public void dragEnd(@NonNull View view) {
//
//                    }
//                })
//                .show();
//    }
//}
