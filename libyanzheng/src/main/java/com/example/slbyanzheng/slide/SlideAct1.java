package com.example.slbyanzheng.slide;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.slbyanzheng.R;


public class SlideAct1 extends AppCompatActivity {

    private Captcha captcha;
    private Button btnMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideact1);
        captcha = (Captcha) findViewById(R.id.captCha);
        btnMode = (Button) findViewById(R.id.btn_mode);
        btnMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (captcha.getMode() == Captcha.MODE_BAR) {
                    captcha.setMode(Captcha.MODE_NONBAR);
                    btnMode.setText("滑动条模式");
                } else {
                    captcha.setMode(Captcha.MODE_BAR);
                    btnMode.setText("无滑动条模式");
                }
            }
        });
        captcha.setCaptchaListener(new Captcha.CaptchaListener() {
            @Override
            public String onAccess(long time) {
                Toast.makeText(SlideAct1.this, "验证成功", Toast.LENGTH_SHORT).show();
                return "验证通过";
            }

            @Override
            public String onFailed(int count) {
                Toast.makeText(SlideAct1.this, "验证失败,失败次数" + count, Toast.LENGTH_SHORT).show();
                return "验证失败";
            }

            @Override
            public String onMaxFailed() {
                Toast.makeText(SlideAct1.this, "验证超过次数，你的帐号被封锁", Toast.LENGTH_SHORT).show();
                return "可以走了";
            }

        });
    }


    boolean isCat = true;
    public void changePicture(View view){
        if(isCat){
            captcha.setBitmap("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.zhimg.com%2Fv2-00890791653a55a7f0f1abe874b56f79_r.jpg%3Fsource%3D1940ef5c&refer=http%3A%2F%2Fpic1.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642062483&t=a541abbf36d5e8403bef2eba0d34d114");
        }else{
            captcha.setBitmap(R.drawable.cat);
        }
        isCat=!isCat;
    }

    boolean isSeekbar1 = false;
    public void changeProgressDrawable(View view){
        if(isSeekbar1){
            captcha.setSeekBarStyle(R.drawable.po_seekbar,R.drawable.thumb);
        }else{
            captcha.setSeekBarStyle(R.drawable.po_seekbar1,R.drawable.thumb1);
        }
        isSeekbar1=!isSeekbar1;
    }
}
