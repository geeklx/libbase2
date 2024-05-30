package com.haier.cellarette.baselibrary.loading;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.widget.CircleProgressView2;


public class ActCircleTextProgressbar2 extends AppCompatActivity {

    private CircleProgressView2 mCircleProgressView1;
    private CircleProgressView2 mCircleProgressView2;
    private CircleProgressView2 mCircleProgressView3;
    private Button add;
    private Button change_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpb2);
        mCircleProgressView1 = findViewById(R.id.circleProgressView1);
        mCircleProgressView2 = findViewById(R.id.circleProgressView2);
        mCircleProgressView3 = findViewById(R.id.circleProgressView3);
        add = (Button) findViewById(R.id.add);
        change_color = (Button) findViewById(R.id.change_color);
        change_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCircleProgressView2.setmProgressBackgroundColor(Color.parseColor("#FF5722"));
                mCircleProgressView2.setmProgressHeadColor(Color.parseColor("#FF5722"));
                mCircleProgressView3.setmProgressColor(Color.parseColor("#689F38"));
                mCircleProgressView3.setmProgressHeadColor(Color.parseColor("#689F38"));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float v1 = mCircleProgressView1.getmProgressValue();
                mCircleProgressView1.setProgress(v1 + 10);
                mCircleProgressView2.setProgress(v1 + 10);
                mCircleProgressView3.setProgress(v1 + 10);
            }
        });
    }
}