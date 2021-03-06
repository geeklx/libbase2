package com.lxj.xpopupdemo.custom;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.BubbleHorizontalAttachPopupView;
import com.haier.cellarette.baselibrary.R;
import com.lxj.xpopupdemo.XpopApp;

/**
 * Description: 自定义Attach弹窗，水平方向的带气泡的弹窗
 * Create by lxj, at 2019/3/13
 */
public class CustomHorizontalBubbleAttachPopup extends BubbleHorizontalAttachPopupView {
    public CustomHorizontalBubbleAttachPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_attach_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        findViewById(R.id.tv_zan).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(XpopApp.get(), "赞", Toast.LENGTH_LONG).show();
                dismiss();
            }
        });
        findViewById(R.id.tv_comment).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(XpopApp.get(), "评论", Toast.LENGTH_LONG).show();
                dismiss();
            }
        });
    }

}
