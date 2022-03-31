package com.geek.libbase.recycleviewcard2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.geek.libbase.R;
import com.geek.libutils.app.BaseApp;

import java.util.List;

/**
 * Created by CJJ on 2017/3/7.
 */

public class StackAdapter extends RecyclerView.Adapter<StackAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<StackBean> datas;
    private Context context;
    private boolean vertical;

    public StackAdapter(List<StackBean> datas) {
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            this.context = parent.getContext();
            inflater = LayoutInflater.from(parent.getContext());
        }
        if (vertical) {
            return new ViewHolder(inflater.inflate(R.layout.activity_recard2_vertical_item_card, parent, false));
        }
        return new ViewHolder(inflater.inflate(R.layout.activity_recard2_item_card, parent, false));
    }

    public StackAdapter vertical() {
        this.vertical = true;
        return this;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(BaseApp.get().getApplicationContext()).load(datas.get(position % datas.size()).getImg()).into(holder.cover);
//        holder.index.setText(datas.get(holder.getAdapterPosition()));
        holder.index.setText(position % datas.size() + "");
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

//    @Override
//    public int getItemCount() {
//        return datas == null ? 0 : datas.size();
//    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView index;

        public ViewHolder(View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.cover);
            index = itemView.findViewById(R.id.index);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context.getApplicationContext(), String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context.getApplicationContext(), String.valueOf(getLayoutPosition() % datas.size() + ""), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
