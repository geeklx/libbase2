package com.geek.libbase.baserecycleview.yewu3;

import android.content.Context;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class FenleiAct1ViewPagerAdapter1<T> extends FragmentPagerAdapter {

    private Context mContext;
    private List<Fragment> fragmentList;
    private String[] titles;
    private List<T> bean;
    private FragmentManager fm;

    public FenleiAct1ViewPagerAdapter1(FragmentManager fm, Context context, String[] titles, List<T> bean, List<Fragment> fragmentList, int behavior) {
        super(fm, behavior);
        this.fm = fm;
        this.mContext = context;
        this.bean = bean;
        this.fragmentList = fragmentList;
        this.titles = titles;
    }

    public void setdata(String[] titles, List<T> bean, List<Fragment> fragmentList) {
        this.bean = bean;
        this.fragmentList = fragmentList;
        this.titles = titles;
    }


    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        FenleiAct1Fragment2 fragment = (FenleiAct1Fragment2) super.instantiateItem(container, position);
//        fragment.updateArguments(bean.get(position));
//        return fragment;
//
//    }


//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
//
//    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        fm.beginTransaction().remove((Fragment) object);
    }

    @Override
    public int getItemPosition(Object object) {
        // 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
        return POSITION_NONE;
    }
}