package com.geek.libskin.skindemo.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.libbase.R;
import com.geek.libskin.skindemo.adapter.SkinAdapter;


/**
 * @ClassName: RecyclerViewActivity
 * @Author: 史大拿
 * @CreateDate: 1/4/23$ 3:58 PM$
 * TODO
 */
public class SkinRecyclerViewActivity extends SkinBaseActivity {

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SkinAdapter());
    }

    @Override
    protected int layoutId() {
        return R.layout.skin_activity_recycler_view;
    }
}
