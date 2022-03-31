package com.geek.libupdateapp.customview;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geek.libbase.R;
import com.geek.libupdateapp.feature.Callback;


/**
 * Created by Teprinciple on 2016/10/13.
 */
public class ConfirmDialogNew extends Dialog {

    Callback callback;
    private TextView content;
    private TextView title;
    private TextView sureBtn;
    private TextView cancleBtn;
    private LinearLayout ll_cancleBtn;

    public ConfirmDialogNew(Context context, Callback callback) {
        super(context, R.style.CustomDialoglibupdateapp);
        this.callback = callback;
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirm_new, null);
        sureBtn = mView.findViewById(R.id.dialog_confirm_sure);
        ll_cancleBtn = mView.findViewById(R.id.ll_dialog_confirm_cancle);
        cancleBtn = mView.findViewById(R.id.dialog_confirm_cancle);
        content = mView.findViewById(R.id.dialog_confirm_content);
        title = mView.findViewById(R.id.dialog_confirm_title);


        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.callback(1);
                ConfirmDialogNew.this.cancel();
            }
        });
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.callback(0);
                ConfirmDialogNew.this.cancel();
            }
        });
        super.setContentView(mView);
    }


    public ConfirmDialogNew setisForce(boolean envis) {
        if (envis) {
            ll_cancleBtn.setVisibility(View.GONE);
        } else {
            ll_cancleBtn.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public ConfirmDialogNew setContent(String s) {
        content.setText(s);
        return this;
    }

    public ConfirmDialogNew setTitle(String s) {
        title.setText(s);
        return this;
    }


}
