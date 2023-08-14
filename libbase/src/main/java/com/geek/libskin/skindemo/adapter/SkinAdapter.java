package com.geek.libskin.skindemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geek.libbase.R;
import com.geek.libskin.skinbase.SkinManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SkinAdapter
 * @Author: 史大拿
 * @CreateDate: 1/4/23$ 4:00 PM$
 * TODO
 */
public class SkinAdapter extends RecyclerView.Adapter<SkinAdapter.ViewHolder> {

    private final List<String> mData = new ArrayList<>();

    public SkinAdapter() {
        for (int i = 0; i < 20; i++) {
            mData.add("测试数据" + i);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.createView(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvItem.setText(mData.get(position));

        holder.imageItem.setImageDrawable(SkinManager.getInstance().getDrawable("skin_item_background"));

        holder.tvItem.setBackgroundColor(SkinManager.getInstance().getColor("skin_global_background"));
        holder.tvItem.setTextColor(SkinManager.getInstance().getColor("skin_global_text_color"));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView imageItem;
        public final TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageItem = itemView.findViewById(R.id.image_item);
            tvItem = itemView.findViewById(R.id.tv_item);
        }

        public static ViewHolder createView(ViewGroup parent) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.skin_item_skin, parent, false));
        }
    }
}
