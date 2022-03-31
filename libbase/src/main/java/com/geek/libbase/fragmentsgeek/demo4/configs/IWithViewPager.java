package com.geek.libbase.fragmentsgeek.demo4.configs;

import androidx.viewpager.widget.ViewPager;

public interface IWithViewPager {
    void showIndicator(boolean show);
    void withViewPager(ViewPager viewPager);
}