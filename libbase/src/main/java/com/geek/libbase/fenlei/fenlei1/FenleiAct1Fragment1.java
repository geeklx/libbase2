package com.geek.libbase.fenlei.fenlei1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.blankj.utilcode.util.ToastUtils;
import com.geek.libbase.R;
import com.geek.libbase.widgets.ViewPagerSlide;
import com.geek.libutils.app.FragmentHelper;
import com.geek.libutils.app.MyLogUtil;
import com.geek.tablib.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class FenleiAct1Fragment1 extends SlbBaseLazyFragmentNewCate {

    private SlidingTabLayout tabLayout;
    private ViewPagerSlide viewpager;
    private FenleiAct1ViewPagerAdapter1 fenleiViewPagerAdapter1;
    private List<FenleiAct1CateBean1> dataList;
    private ArrayList<Fragment> mFragmentList;

    @Override
    protected void updateArgumentsData(Object parentCategory) {

    }

    @Override
    protected void onReceiveMsg(Intent intent) {
        dataList = (List<FenleiAct1CateBean1>) intent.getSerializableExtra("dingwei");
        setNewData(dataList);
    }

    @Override
    public void call(Object value) {
//        String ids = (String) value;
//        ToastUtils.showLong(ids);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_co_content_fenlei1;
    }

    @Override
    protected void setup(View rootView, @Nullable Bundle savedInstanceState) {
        super.setup(rootView, savedInstanceState);
        tabLayout = rootView.findViewById(R.id.tl_fenlei1);
        viewpager = rootView.findViewById(R.id.vs_fenlei1);
//        setNewData();

    }

    private void setNewData(List<FenleiAct1CateBean1> mlist) {
        mFragmentList = new ArrayList<>();
        mFragmentList.clear();
        for (int i = 0; i < mlist.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("parentCategory", mlist.get(i));
            FenleiAct1Fragment2 fenleiFragment2 = FragmentHelper.newFragment(FenleiAct1Fragment2.class, bundle);
            mFragmentList.add(fenleiFragment2);
//            mFragmentList.add(FenleiFragment2.newInstance(mlist.get(i)));
        }
        // 标题bufen
        String[] titlesString = new String[mlist.size()];
        for (int i = 0; i < mlist.size(); i++) {
            titlesString[i] = mlist.get(i).getText_content();
        }
        if (fenleiViewPagerAdapter1 == null) {
            viewpager.removeAllViews();
            fenleiViewPagerAdapter1 = new FenleiAct1ViewPagerAdapter1(
                    getActivity().getSupportFragmentManager(),
                    getActivity(),
                    titlesString,
                    mlist,
                    mFragmentList,
                    FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            viewpager.setOffscreenPageLimit(mFragmentList.size());
            viewpager.setScroll(true);
            viewpager.setAdapter(fenleiViewPagerAdapter1);
        } else {
            fenleiViewPagerAdapter1.setdata(titlesString, mlist, mFragmentList);
            fenleiViewPagerAdapter1.notifyDataSetChanged();
        }
        tabLayout.setViewPager(viewpager);
        viewpager.setCurrentItem(0);

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


}
