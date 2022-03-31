package com.geek.libbase.fenlei.fenlei1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.geek.libbase.R;
import com.geek.libbase.base.SlbBaseActivity;
import com.geek.libutils.app.LocalBroadcastManagers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FenleiAct1 extends SlbBaseActivity {

    private TextView tv1;
    private TextView tv2;

    public List<FenleiAct1CateBean1> addList1() {
        List<FenleiAct1CateBean1> mList = new ArrayList<>();
        mList.add(new FenleiAct1CateBean1("id1", "全部", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_2.png", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_2_u.png", R.drawable.slb_run1, 0, false));
        mList.add(new FenleiAct1CateBean1("id2", "自建栏目1", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_2.png", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_2_u.png",  R.drawable.slb_run1, 0, false));
        mList.add(new FenleiAct1CateBean1("id3", "自建栏目2", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_2.png", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_2_u.png",  R.drawable.slb_run1, 0, false));
        mList.add(new FenleiAct1CateBean1("id4", "自建栏目3", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_2.png", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_2_u.png",  R.drawable.slb_run1, 0, false));
        mList.add(new FenleiAct1CateBean1("id5", "自建栏目4", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_2.png", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_2_u.png",  R.drawable.slb_run1, 0, false));
        mList.add(new FenleiAct1CateBean1("id6", "自建栏目5", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_2.png", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_2_u.png",  R.drawable.slb_run1, 0, false));
        mList.add(new FenleiAct1CateBean1("id7", "自建栏目6", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_2.png", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_2_u.png",  R.drawable.slb_run1, 0, false));
        return mList;
    }

    public List<FenleiAct1CateBean1> addList2() {
        List<FenleiAct1CateBean1> mList = new ArrayList<>();
        mList.add(new FenleiAct1CateBean1("id11", "全部", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_3.png", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_3_uu.png", R.drawable.slb_run2, 0, false));
        mList.add(new FenleiAct1CateBean1("id22", "栏目1", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_3.png", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_3_uu.png", R.drawable.slb_run2, 0, false));
        mList.add(new FenleiAct1CateBean1("id33", "栏目2", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_3.png", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_3_uu.png", R.drawable.slb_run2, 0, false));
        mList.add(new FenleiAct1CateBean1("id44", "栏目3", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_3.png", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_3_uu.png", R.drawable.slb_run2, 0, false));
        mList.add(new FenleiAct1CateBean1("id55", "栏目4", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_3.png", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_3_uu.png", R.drawable.slb_run2, 0, false));
        mList.add(new FenleiAct1CateBean1("id66", "栏目5", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_3.png", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_3_uu.png", R.drawable.slb_run2, 0, false));
        mList.add(new FenleiAct1CateBean1("id77", "栏目6", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_3.png", "http://119.188.115.252:8090/resource-handle/uploads/image/ic_table_3_uu.png", R.drawable.slb_run2, 0, false));
        return mList;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_co_content_fenlei1;
    }

    @Override
    protected void setup(@Nullable Bundle savedInstanceState) {
        super.setup(savedInstanceState);
        findview();
        onclick();
        donetwork();

    }

    private void donetwork() {
        Intent msgIntent = new Intent();
        msgIntent.setAction("SlbBaseLazyFragmentNewCate");
        msgIntent.putExtra("dingwei", (Serializable) addList1());
        LocalBroadcastManagers.getInstance(FenleiAct1.this).sendBroadcast(msgIntent);
    }

    private void onclick() {
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent msgIntent = new Intent();
                msgIntent.setAction("SlbBaseLazyFragmentNewCate");
                msgIntent.putExtra("dingwei", (Serializable) addList1());
                LocalBroadcastManagers.getInstance(FenleiAct1.this).sendBroadcast(msgIntent);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent msgIntent = new Intent();
                msgIntent.setAction("SlbBaseLazyFragmentNewCate");
                msgIntent.putExtra("dingwei", (Serializable) addList2());
                LocalBroadcastManagers.getInstance(FenleiAct1.this).sendBroadcast(msgIntent);
            }
        });
    }

    private void findview() {
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
    }
}
