package com.haier.cellarette.baselibrary.loading;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.widget.CircleTextProgressbar;

public class ActCircleTextProgressbar extends AppCompatActivity {
    /**
     * 默认类型。
     */
    private CircleTextProgressbar mTvDefault;
    /**
     * 五个字的。
     */
    private CircleTextProgressbar mTvFive;
    /**
     * 圆心点击变色。
     */
    private CircleTextProgressbar mTvCicleColor;

    /**
     * 顺数进度条。
     */
    private CircleTextProgressbar mTvCount;
    /**
     * 宽进度条。
     */
    private CircleTextProgressbar mTvWide;
    /**
     * 窄进度条。
     */
    private CircleTextProgressbar mTvNarrow;
    /**
     * 红色进度条。
     */
    private CircleTextProgressbar mTvRedPro;
    /**
     * 红色边框。
     */
    private CircleTextProgressbar mTvRedFrame;
    /**
     * 红色圆心。
     */
    private CircleTextProgressbar mTvRedCircle;
    /**
     * 跳过。
     */
    private CircleTextProgressbar mTvSkip;
    /**
     * 更新进度条文字。
     */
    private CircleTextProgressbar mTvProgressBar1, mTvProgressBar2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circletextprogressbar1);

        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTvDefault.reStart();
                mTvFive.reStart();
                mTvCicleColor.reStart();
                mTvCount.reStart();
                mTvWide.reStart();
                mTvNarrow.reStart();
                mTvRedPro.reStart();
                mTvRedFrame.reStart();
                mTvRedCircle.reStart();
                mTvProgressBar1.reStart();
                mTvProgressBar2.reStart();
                mTvSkip.reStart();
            }
        });

        mTvDefault = (CircleTextProgressbar) findViewById(R.id.tv_default);
        mTvFive = (CircleTextProgressbar) findViewById(R.id.tv_five_text);
        mTvCicleColor = (CircleTextProgressbar) findViewById(R.id.tv_red_circle_color);

        // 和系统进度条一样，由0-100。
        mTvCount = (CircleTextProgressbar) findViewById(R.id.tv_count);
        mTvCount.setProgressType(CircleTextProgressbar.ProgressType.COUNT);

        // 宽进度条。
        mTvWide = (CircleTextProgressbar) findViewById(R.id.tv_five_wide);
        mTvWide.setProgressLineWidth(30);//写入宽度。
        mTvWide.setTimeMillis(3500);// 把倒计时时间改长一点。

        // 窄进度条。
        mTvNarrow = (CircleTextProgressbar) findViewById(R.id.tv_five_narrow);
        mTvNarrow.setProgressLineWidth(3);// 写入宽度。

        // 红色进度条。
        mTvRedPro = (CircleTextProgressbar) findViewById(R.id.tv_red_progress);
        mTvRedPro.setProgressColor(Color.RED);

        // 红色边框进度条。
        mTvRedFrame = (CircleTextProgressbar) findViewById(R.id.tv_red_frame);
        mTvRedFrame.setOutLineColor(Color.RED);

        // 红色圆心进度条。
        mTvRedCircle = (CircleTextProgressbar) findViewById(R.id.tv_red_circle);
        mTvRedCircle.setInCircleColor(Color.RED);

        mTvProgressBar1 = (CircleTextProgressbar) findViewById(R.id.tv_red_progress_text1);
        mTvProgressBar1.setCountdownProgressListener(1, progressListener);
        mTvProgressBar1.setProgressType(CircleTextProgressbar.ProgressType.COUNT);

        mTvProgressBar2 = (CircleTextProgressbar) findViewById(R.id.tv_red_progress_text2);
        mTvProgressBar2.setCountdownProgressListener(2, progressListener);


        // 模拟网易新闻跳过。
        mTvSkip = (CircleTextProgressbar) findViewById(R.id.tv_red_skip);
        mTvSkip.setOutLineColor(Color.TRANSPARENT);
        mTvSkip.setInCircleColor(Color.parseColor("#AAC6C6C6"));
        mTvSkip.setProgressColor(Color.DKGRAY);
        mTvSkip.setProgressLineWidth(3);
    }

    private CircleTextProgressbar.OnCountdownProgressListener progressListener = new CircleTextProgressbar.OnCountdownProgressListener() {
        @Override
        public void onProgress(int what, int progress) {
            if (what == 1) {
                mTvProgressBar1.setText(progress + "%");
            } else if (what == 2) {
                mTvProgressBar2.setText(progress + "%");
            }
            // 比如在首页，这里可以判断进度，进度到了100或者0的时候，你可以做跳过操作。
        }
    };

    public void onClick(View view) {
    }

}