package com.haier.cellarette.baselibrary.assetsfitandroid;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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
        tv1 =findViewById(R.id.tv1);

        // 读取网络txt
        StringBuilder stringBuilder = new StringBuilder();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://t-oss.ireign.cn:8380/resource-handle/2022-08-30/d36f1a32-7e6b-4780-a2d9-bf6a9cbc563d.txt");
                    String line;
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    bufferedReader.close();
                    tv1.setText(stringBuilder.toString());
                } catch (MalformedURLException e) {
                } catch (IOException e) {
                }
            }
        }).start();

    }

    public void FileAssetsActivity(View view) {
        startActivity(new Intent(this, FileAssetsActivity.class));
    }

    public void FitAndroidActivity(View view) {
        startActivity(new Intent(this, FitAndroidActivity.class));
    }

}
