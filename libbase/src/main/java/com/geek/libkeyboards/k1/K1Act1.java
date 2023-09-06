package com.geek.libkeyboards.k1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.geek.libbase.R;

public class K1Act1 extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_k1);

        findViewById(R.id.btn_test_activity).setOnClickListener(this);
        findViewById(R.id.btn_test_fragment).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_test_activity) {
            startActivity(new Intent(this, K1FirstActivity.class));
        } else if (id == R.id.btn_test_fragment) {
            startActivity(new Intent(this, K1SecondActivity.class));
        }
    }
}