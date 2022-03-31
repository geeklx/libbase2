package com.just.agentweb.geek.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.BarUtils;
import com.geek.libbase.R;
import com.just.agentweb.geek.fragment.EasyWebFragment;

/**
 * Created by cenxiaozhong on 2017/7/22.
 */

public class ContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.transparent));
        BarUtils.setStatusBarLightMode(this, false);
        setContentView(R.layout.activity_common);

        Fragment mFragment=null;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_framelayout,mFragment= EasyWebFragment.getInstance(new Bundle()),EasyWebFragment.class.getName())
                .show(mFragment)
                .commit();
    }
}
