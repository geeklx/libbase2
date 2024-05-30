package com.haier.cellarette.baselibrary.loading;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.widget.RingProgressBar;


public class ActCircleTextProgressbar3 extends AppCompatActivity {
    private RingProgressBar mRingProgressBar1;

    private RingProgressBar mRingProgressBar2;

    private int progress = 0;

    private Handler mHandler = new Handler(Looper.myLooper()) {

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0) {
                if (progress < 100) {
                    progress++;
                    mRingProgressBar1.setProgress(progress);
                    mRingProgressBar2.setProgress(progress);
                    mRingProgressBar1.setOnProgressListener(new RingProgressBar.OnProgressListener() {

                        @Override
                        public void progressToComplete() {
                            // Here after the completion of the processing
                        }
                    });

                    mRingProgressBar2.setOnProgressListener(new RingProgressBar.OnProgressListener() {

                        @Override
                        public void progressToComplete() {
                            // Here after the completion of the processing
                        }
                    });
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circletextprogressbar3);

        mRingProgressBar1 = (RingProgressBar) findViewById(R.id.progress_bar_1);
        mRingProgressBar2 = (RingProgressBar) findViewById(R.id.progress_bar_2);

        new Thread(new Runnable() {

            @Override
            public void run() {

                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(100);

                        mHandler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();

        mHandler.removeCallbacksAndMessages(null);
    }
}