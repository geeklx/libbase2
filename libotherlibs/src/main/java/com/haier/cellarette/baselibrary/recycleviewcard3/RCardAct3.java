package com.haier.cellarette.baselibrary.recycleviewcard3;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.haier.cellarette.baselibrary.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RCardAct3 extends AppCompatActivity {

    private PileLayout pileLayout;
    private List<ItemEntity> dataList;

    /**
     * 从asset读取文件json数据
     */
    private void initDataList() {
        dataList = new ArrayList<>();
        try {
            InputStream in = getAssets().open("preset.config");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            String jsonStr = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.optJSONArray("result");
            if (null != jsonArray) {
                int len = jsonArray.length();
                for (int j = 0; j < 3; j++) {
                    for (int i = 0; i < len; i++) {
                        JSONObject itemJsonObject = jsonArray.getJSONObject(i);
                        ItemEntity itemEntity = new ItemEntity(itemJsonObject);
                        dataList.add(itemEntity);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3d_lunbo1);
        findview();
        onclick();
        donetwork();
    }

    private void donetwork() {
        initDataList();
        pileLayout.setAdapter(new PileLayout.Adapter() {
            @Override
            public int getLayoutId() {
                return R.layout.activity_3d_lunbo1_items;
            }

            @Override
            public void bindView(View view, int position) {
                ViewHolder viewHolder = (ViewHolder) view.getTag();
                if (viewHolder == null) {
                    viewHolder = new ViewHolder();
                    viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
                    view.setTag(viewHolder);
                }

                Glide.with(RCardAct3.this).load(dataList.get(position).getCoverImageUrl()).into(viewHolder.imageView);
            }

            @Override
            public int getItemCount() {
                return dataList.size();
            }

            @Override
            public void displaying(int position) {
                // yewu

            }

            @Override
            public void onItemClick(View view, int position) {
                super.onItemClick(view, position);
            }

            class ViewHolder {
                ImageView imageView;
            }
        });
    }

    private void onclick() {

    }

    private void findview() {
        pileLayout = (PileLayout) findViewById(R.id.pileLayout);

    }
}
