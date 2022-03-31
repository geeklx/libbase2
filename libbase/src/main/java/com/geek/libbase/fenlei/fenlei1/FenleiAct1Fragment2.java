package com.geek.libbase.fenlei.fenlei1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.geek.libbase.R;
import com.geek.libutils.app.MyLogUtil;

import org.jetbrains.annotations.NotNull;

public class FenleiAct1Fragment2 extends SlbBaseLazyFragmentNewCate {

    private String tablayoutId;
    private FenleiAct1CateBean1 parentCategory;
    private ImageView iv1;

    @Override
    protected void onReceiveMsg(Intent intent) {
//        img = intent.getIntExtra("query1", R.drawable.slb_run2);
////                    iv1.loadLocalImage(img, R.drawable.ic_defs_loading);
//        MyLogUtil.e("tablayoutId4444->", "onCreate->" + img);
//        iv1.setImage(img);
    }

    @Override
    protected void updateArgumentsData(Object parentCategory) {
        this.parentCategory = (FenleiAct1CateBean1) parentCategory;
        if (iv1 != null) {
            setview(this.parentCategory);
        }
    }

    private void setview(FenleiAct1CateBean1 parentCategory) {
        MyLogUtil.e("tablayoutId2222->", "onCreate->" + parentCategory.getText_content());
//        iv1.setImage(parentCategory.getText_icon());
        addSeletorFromNet(parentCategory.getImg(), parentCategory.getUrl(), iv1);
    }

    @Override
    public void call(Object value) {
        tablayoutId = (String) value;
        ToastUtils.showLong("call->" + tablayoutId);
        MyLogUtil.e("tablayoutId3333->", "call->" + tablayoutId);

    }

//    private int img = R.drawable.dt20_ct3;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_co_content_fenlei1_f1;
    }

    @Override
    protected void setup(final View rootView, @Nullable Bundle savedInstanceState) {
        super.setup(rootView, savedInstanceState);
        iv1 = rootView.findViewById(R.id.iv_fenlei1);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        iv1.setPressed(true);
        if (getArguments() != null) {
            this.parentCategory = (FenleiAct1CateBean1) getArguments().getSerializable("parentCategory");
            if (this.parentCategory != null) {
                setview(this.parentCategory);
            }
        }
    }

    /**
     * 底部点击bufen
     *
     * @param cateId
     * @param isrefresh
     */
    @Override
    public void getCate(String cateId, boolean isrefresh) {

        if (!isrefresh) {
            // 从缓存中拿出头像bufen

            return;
        }
        ToastUtils.showLong("下拉刷新啦");
    }

    /**
     * 当切换底部的时候通知每个fragment切换的id是哪个bufen
     *
     * @param cateId
     */
    @Override
    public void give_id(String cateId) {
//        ToastUtils.showLong("下拉刷新啦");
        MyLogUtil.e("tablayoutId->", "give_id->" + cateId);
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onResume() {
        // 从缓存中拿出头像bufen

        super.onResume();
    }

    private void addSeletorFromNet(final String pic1, final String pic2, final ImageView imageView) {
        if (imageView == null || TextUtils.isEmpty(pic1)) {
            return;
        }
        final StateListDrawable drawable = new StateListDrawable();
        Glide.with(getContext())
                .asBitmap()
                .load(pic1)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NotNull Bitmap bitmap, Transition<? super Bitmap> transition) {
                        Drawable newDraw = new BitmapDrawable(bitmap);
                        drawable.addState(new int[]{-android.R.attr.state_pressed}, newDraw);

                        Glide.with(getContext())
                                .asBitmap()
                                .load(pic2)
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(@NotNull Bitmap bitmap, Transition<? super Bitmap> transition) {
                                        Drawable newDraw = new BitmapDrawable(bitmap);
                                        drawable.addState(new int[]{android.R.attr.state_pressed}, newDraw);

                                        imageView.setImageDrawable(drawable);
                                    }
                                });

                    }
                });
    }


    /**
     * --------------------------------业务逻辑分割线----------------------------------
     */


}
