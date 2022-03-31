package com.geek.libbase.recycleviewcard2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.libbase.R;
import com.geek.libbase.base.SlbBaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StackAct extends SlbBaseActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerview;
    private RecyclerView hrRecyclerView;
    private Button button;
    private Button button1;
    private Button button2;
    private Button scroll_to_specific_item;
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
        return R.layout.activity_recard2;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        setContentView(R.layout.activity_recard2);
        recyclerview = findViewById(R.id.recyclerview);
        hrRecyclerView = findViewById(R.id.recyclerview1);
        button = findViewById(R.id.button);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        scroll_to_specific_item = findViewById(R.id.scroll_to_specific_item);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetDefault();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetRight();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewVertical();
            }
        });
        scroll_to_specific_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onScrollToItem();
            }
        });
        resetDefault();
        resetRight();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void resetDefault() {
        List<StackBean> datas = new ArrayList<>();
        for (int i = 0; i < imageUrls.size(); i++) {
            datas.add(new StackBean(imageUrls.get(i), ""));
        }
        //
        StackConfig config = new StackConfig();
        config.secondaryScale = 0.8f;
        config.scaleRatio = 0.4f;
        config.maxStackCount = 4;
        config.initialStackCount = 2;
        config.mScrollTime = 2000;
        config.space = 8;
        config.align = StackAlign.LEFT;
        layoutManager = new StackLayoutManager(config, this);
        layoutManager.stopScroll();
        layoutManager.startScroll();
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(new StackAdapter(datas));

    }

    public void resetRight() {
        List<StackBean> datas = new ArrayList<>();
        for (int i = 0; i < imageUrls.size(); i++) {
            datas.add(new StackBean(imageUrls.get(i), ""));
        }
        //
        StackConfig config = new StackConfig();
        config.secondaryScale = 0.8f;
        config.scaleRatio = 0.4f;
        config.maxStackCount = 4;
        config.initialStackCount = 2;
        config.space = getResources().getDimensionPixelOffset(R.dimen.item_space);
        config.align = StackAlign.RIGHT;
        layoutManager = new StackLayoutManager(config, this);
        layoutManager.stopScroll();
        layoutManager.startScroll();
        hrRecyclerView.setLayoutManager(layoutManager);
        hrRecyclerView.setAdapter(new StackAdapter(datas));
    }

    public void viewVertical() {
        startActivity(new Intent(this, StackActVertical.class));
    }

    public void onScrollToItem() {
        layoutManager.scrollToPosition(10);
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
