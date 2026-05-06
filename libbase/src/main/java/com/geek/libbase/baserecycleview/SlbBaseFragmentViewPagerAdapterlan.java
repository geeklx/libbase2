package com.geek.libbase.baserecycleview;

import android.content.Context;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import java.util.List;

public class SlbBaseFragmentViewPagerAdapterlan extends FragmentPagerAdapter {
    private final FragmentManager fm;
    private final Context mContext;
    private final List<String> titles;
    private final List<Fragment> fragmentList;
    private FragmentTransaction mCurTransaction;

    public SlbBaseFragmentViewPagerAdapterlan(FragmentManager fm, Context mContext, 
                                             List<String> titles, List<Fragment> fragmentList, int behavior) {
        super(fm, behavior);
        this.fm = fm;
        this.mContext = mContext;
        this.titles = titles;
        this.fragmentList = fragmentList;
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
        if (titles.size() <= 0) {
            return null;
        }
        return titles.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
        if (fm != null && ((Fragment) object) != null) {
            fm.beginTransaction().remove((Fragment) object).commit();
        }
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE; // 强制刷新所有页面
    }

    public void clear(ViewGroup container) {
        if (mCurTransaction == null) {
            mCurTransaction = fm.beginTransaction();
        }
        
        for (int i = 0; i < fragmentList.size(); i++) {
            long itemId = getItemId(i);
            String name = makeFragmentName(container.getId(), itemId);
            Fragment fragment = fm.findFragmentByTag(name);
            
            if (fragment != null) {
                mCurTransaction.remove(fragment);
            }
        }
        
        if (mCurTransaction != null) {
            mCurTransaction.commitNowAllowingStateLoss();
            mCurTransaction = null;
        }
    }

    private String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }
}