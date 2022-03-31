package com.zaaach.citypicker.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.R;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

public class DemoAct1 extends AppCompatActivity {
    private FragmentManager mFragmentManager;
    private DemoF1 mFragment1; //
    private TextView tv1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citypickeract2);
        if (savedInstanceState != null) {
            mFragment1 = (DemoF1) mFragmentManager.findFragmentByTag("LIST_TAG1");
        }
        tv1 = findViewById(R.id.tv1);
        mFragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (mFragment1 != null) {
            transaction.hide(mFragment1);
        }
        if (mFragment1 == null) {
            mFragment1 = new DemoF1();
            Bundle args = new Bundle();
            args.putString("tablayoutId", "111");
            mFragment1.setArguments(args);
            transaction.add(R.id.container, mFragment1, "LIST_TAG1");
        } else {
            transaction.show(mFragment1);
//            mFragment1.getCate(current_pos + "", isrefresh);
        }
        transaction.commitAllowingStateLoss();
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<HotCity> hotCities = new ArrayList<>();
                hotCities.add(new HotCity("北京", "北京", "101010100"));
                hotCities.add(new HotCity("上海", "上海", "101020100"));
                hotCities.add(new HotCity("广州", "广东", "101280101"));
                hotCities.add(new HotCity("深圳", "广东", "101280601"));
                hotCities.add(new HotCity("杭州", "浙江", "101210101"));
                CityPicker.from(DemoAct1.this)
                        .enableAnimation(true)
                        .setAnimationStyle(com.zaaach.citypicker.R.style.CityPickerStyleCustomAnim)
                        .setLocatedCity(null)
                        .setHotCities(hotCities)
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                tv1.setText(String.format("当前城市：%s，%s", data.getName(), data.getCode()));
//                                Toast.makeText(
//                                        getApplicationContext(),
//                                        String.format("点击的数据：%s，%s", data.getName(), data.getCode()),
//                                        Toast.LENGTH_SHORT)
//                                        .show();
                            }

                            @Override
                            public void onCancel() {
//                                Toast.makeText(getApplicationContext(), "取消选择", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onLocate() {
                                //开始定位，这里模拟一下定位
                                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        CityPicker.from(DemoAct1.this).locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
                                    }
                                }, 3000);
                            }
                        })
                        .show();
            }
        });
    }
}
