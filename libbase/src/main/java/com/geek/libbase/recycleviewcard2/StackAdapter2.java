//package com.haier.cellarette.baselibrary.recycleviewcard2;
//
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.blankj.utilcode.util.ToastUtils;
//import com.bumptech.glide.Glide;
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//import com.geek.libutils.app.MyLogUtil;
//import com.haier.cellarette.baselibrary.R;
//
//public class StackAdapter2 extends BaseQuickAdapter<StackBean, BaseViewHolder> {
//
//
//    public StackAdapter2(int layoutResId) {
//        super(layoutResId);
//    }
//
//    @Override
//    public int getItemCount() {
//        return Integer.MAX_VALUE;
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, StackBean item) {
//        ImageView cover = helper.itemView.findViewById(R.id.cover);
//        TextView index = helper.itemView.findViewById(R.id.index);
//        MyLogUtil.e("ssssssssssssssssssssssssssss",helper.getPosition() + "");
//        index.setText(helper.getAbsoluteAdapterPosition() % getData().size() + "");
//        Glide.with(mContext).load(getData().get(helper.getAbsoluteAdapterPosition() % getData().size()).getImg()).into(cover);
//        index.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtils.showLong(helper.getAbsoluteAdapterPosition() % getData().size() + "");
//            }
//        });
//        helper.addOnClickListener(R.id.index);
//    }
//}
//
