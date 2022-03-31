package com.geek.libbase.recycleviewcard2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.libbase.R;
import com.geek.libbase.base.SlbBaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StackActVertical extends SlbBaseActivity {

    private TextView tv1;
    private RecyclerView verticalRecyclerview;
    private StackLayoutManager layoutManager;
    private List<Integer> imageUrls = Arrays.asList(
            R.drawable.icon_lunbo1,
            R.drawable.icon_lunbo2,
            R.drawable.icon_lunbo1,
            R.drawable.icon_lunbo1,
            R.drawable.icon_lunbo1,
            R.drawable.icon_lunbo2,
            R.drawable.icon_lunbo1,
            R.drawable.icon_lunbo2
    );

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recard2_vertical;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        setContentView(R.layout.activity_recard2_vertical);
        tv1 = findViewById(R.id.tv1);
        verticalRecyclerview = findViewById(R.id.recyclerview_vertical);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vr();
            }
        });
        vr();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void vr() {
        List<StackBean> datas = new ArrayList<>();
        for (int i = 0; i < imageUrls.size(); i++) {
            datas.add(new StackBean(imageUrls.get(i), ""));
        }
        //
        StackConfig config = new StackConfig();
        config.secondaryScale = 0.95f;
        config.scaleRatio = 0.4f;
        config.maxStackCount = 4;
        config.initialStackCount = 4;
        config.space = 45;
        config.parallex = 1.5f;
        config.align = StackAlign.TOP;
        layoutManager = new StackLayoutManager(config, this);
        layoutManager.stopScroll();
        layoutManager.startScroll();
        verticalRecyclerview.setLayoutManager(layoutManager);
        verticalRecyclerview.setAdapter(new StackAdapter(datas).vertical());
    }

    @Override
    protected void onDestroy() {
        if (layoutManager != null) {
            layoutManager.stopScroll();
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        if (layoutManager != null) {
            layoutManager.startScroll();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (layoutManager != null) {
            layoutManager.stopScroll();
        }
        super.onPause();
    }

}
