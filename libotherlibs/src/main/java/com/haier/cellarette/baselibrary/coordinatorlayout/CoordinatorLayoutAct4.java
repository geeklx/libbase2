package com.haier.cellarette.baselibrary.coordinatorlayout;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.haier.cellarette.baselibrary.R;

import java.util.ArrayList;
import java.util.List;

public class CoordinatorLayoutAct4 extends AppCompatActivity {

    private DrawerLayout drawerlayout;
    private CollapsingToolbarLayoutState state;
    private AppBarLayout app_bar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView iv;
    private ImageView iv_icon;
    private ImageView iv_icon2;
    private Toolbar toolbar;
    private TabLayout tablayout;
    private ViewPager viewpager;
    private NavigationView nav_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_coordinatorlayoutact4);
        findview();
        onclick();
        donetwork();


    }

    private void donetwork() {

    }

    private void onclick() {
        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerlayout.isDrawerOpen(nav_view)) {
                    drawerlayout.closeDrawer(nav_view);
                } else {
                    drawerlayout.openDrawer(nav_view);
                }
            }
        });
        iv_icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CoordinatorLayoutAct4.this, "?????????~", Toast.LENGTH_SHORT).show();
            }
        });
        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //?????????????????????
                float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());
                //??????????????????????????????
                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;//???????????????????????????
                        collapsingToolbarLayout.setTitle("EXPANDED");//??????title???EXPANDED
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        collapsingToolbarLayout.setTitle("");//??????title?????????
                        iv_icon2.setVisibility(View.VISIBLE);//??????????????????
                        state = CollapsingToolbarLayoutState.COLLAPSED;//???????????????????????????
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                            iv_icon2.setVisibility(View.GONE);//????????????????????????????????????????????????
                        }
                        collapsingToolbarLayout.setTitle("INTERNEDIATE");//??????title???INTERNEDIATE
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//???????????????????????????
                    }
                }
            }
        });

    }

    private void findview() {
        drawerlayout = findViewById(R.id.drawerlayout);
        app_bar = findViewById(R.id.app_bar);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        iv = findViewById(R.id.iv);
        iv_icon = findViewById(R.id.iv_icon);
        iv_icon2 = findViewById(R.id.iv_icon2);
        toolbar = findViewById(R.id.toolbar);
        tablayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.viewpager);
        nav_view = findViewById(R.id.nav_view);
        iv.setImageResource(R.drawable.img00);
        // toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        // CollapsingToolbarLayout
        collapsingToolbarLayout.setTitleEnabled(false);
        // ????????????  setSupportActionBar(toolbar); ??? drawerlayout.addDrawerListener(toggle);??????????????????
        toolbar.setNavigationIcon(null);
        // viewpager
        setupViewPager(viewpager);
        tablayout.setupWithViewPager(viewpager);

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new TabFragment(), "Tab 1");
        adapter.addFrag(new TabFragment(), "Tab 2");
        adapter.addFrag(new TabFragment(), "Tab 3");
        adapter.addFrag(new TabFragment(), "Tab 4");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}