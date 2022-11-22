package com.haier.cellarette.baselibrary.assetsfitandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.assetsfitandroid.fileandroid.FitAndroidActivity;
import com.haier.cellarette.baselibrary.assetsfitandroid.fileassets.FileAssetsActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class AssetsMainActivity extends AppCompatActivity {

    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assetsmain);
        tv1 = findViewById(R.id.tv1);
        // 读取网络txt
        new Thread(runnable).start();

    }

    StringBuilder stringBuilder = new StringBuilder();

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.d("mylog", "请求结果-->" + val);
            tv1.setText(val);
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //
            // TODO: http request.
            try {
                URL url = new URL("http://t-oss.ireign.cn:8380/resource-handle/2022-08-30/d36f1a32-7e6b-4780-a2d9-bf6a9cbc563d.txt");
                String line;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
            } catch (IOException e) {
            }
            //
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", stringBuilder.toString());
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };

    public void FileAssetsActivity(View view) {
        startActivity(new Intent(this, FileAssetsActivity.class));
    }

    public void FitAndroidActivity(View view) {
        startActivity(new Intent(this, FitAndroidActivity.class));
    }

}
