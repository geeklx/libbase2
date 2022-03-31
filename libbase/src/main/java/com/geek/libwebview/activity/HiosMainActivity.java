package com.geek.libwebview.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.geek.libbase.R;


public class HiosMainActivity extends AppCompatActivity {

    private int mAction; // default 0
    private String mSkuId = ""; // maybe null
    private String mCategoryId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_two);

        mAction = getIntent().getIntExtra("act", 0);
        mSkuId = getIntent().getStringExtra("sku_id");
        mCategoryId = getIntent().getStringExtra("category_id");

        Toast.makeText(this, mAction + ", " + mSkuId + ", " + mCategoryId, Toast.LENGTH_LONG).show();

        findViewById(R.id.tv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //back
                finish();
            }
        });


    }
}
