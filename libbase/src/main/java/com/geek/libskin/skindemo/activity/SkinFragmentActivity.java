package com.geek.libskin.skindemo.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.geek.libbase.R;
import com.geek.libskin.skindemo.fragment.SkinFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: FragmentActivity
 * @Author: 史大拿
 * @CreateDate: 1/4/23$ 2:43 PM$
 * TODO
 */
public class SkinFragmentActivity extends SkinBaseActivity {


    @Override
    protected void initCreate(Bundle savedInstanceState) {
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.parent_view, new SkinFragment())
//                .commit();


        ViewPager2 viewPager = findViewById(R.id.view_pager2);
        TabLayout tableLayout = findViewById(R.id.table_layout);

        ArrayList<SkinFragment> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new SkinFragment());
        }


        viewPager.setAdapter(new SkinViewPagerAdapter(this, list));

        new TabLayoutMediator(tableLayout, viewPager, true, (tab, position) -> tab.setText("测试" + position)).attach();

    }

    @Override
    protected int layoutId() {
        return R.layout.skin_activity_fragment;
    }

    private static class SkinViewPagerAdapter extends FragmentStateAdapter {
        private final List<SkinFragment> list;

        public SkinViewPagerAdapter(@NonNull SkinFragmentActivity fragmentManager, List<SkinFragment> list) {
            super(fragmentManager);
            this.list = list;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

}
