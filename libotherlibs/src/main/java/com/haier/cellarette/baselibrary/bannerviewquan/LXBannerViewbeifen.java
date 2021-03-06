package com.haier.cellarette.baselibrary.bannerviewquan;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import androidx.annotation.AttrRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StyleRes;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.bannerviewquan.holder.LXHolderCreator;
import com.haier.cellarette.baselibrary.bannerviewquan.holder.LXViewHolder;
import com.haier.cellarette.baselibrary.bannerviewquan.transformer.CoverModeTransformer;
import com.haier.cellarette.baselibrary.bannerviewquan.transformer.ScaleYTransformer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

//import android.support.annotation.AttrRes;
//import android.support.annotation.DrawableRes;
//
//
//import android.support.annotation.RequiresApi;
//import android.support.annotation.StyleRes;
//import androidx.core.view.PagerAdapter;
//import androidx.core.view.ViewPager;

/**
 * <com.github.geeklxbanner.LXBannerView
 * android:id="@+id/banner"
 * android:layout_width="match_parent"
 * android:layout_height="400dp"
 * android:layout_marginTop="10dp"
 * app:canLoop="true"
 * app:indicatorAlign="center"
 * app:indicatorPaddingLeft="10dp"
 * app:middle_page_cover="true"
 * app:open_mz_mode="true"
 * app:outside_bottom="false" />
 *
 * @param <T>
 */
public class LXBannerViewbeifen<T> extends RelativeLayout {
    private static final String TAG = "MZBannerView";
    private CustomViewPager mViewPager;
    private MZPagerAdapter mAdapter;
    private List<T> mDatas;
    private boolean mIsAutoPlay = true;// ??????????????????
    private int mCurrentItem = 0;//????????????
    private Handler mHandler = new Handler();
    private int mDelayedTime = 3000;// Banner ??????????????????
    private ViewPagerScroller mViewPagerScroller;//??????ViewPager???????????????Scroller
    private boolean mIsOpenMZEffect = true;// ????????????Banner??????
    private boolean mIsOutSideBottom = true;// ???????????????????????????
    private boolean mIsCanLoop = true;// ??????????????????
    private LinearLayout mIndicatorContainer;//indicator??????
    private ArrayList<ImageView> mIndicators = new ArrayList<>();
    //mIndicatorRes[0] ???????????????mIndicatorRes[1]?????????
    private int[] mIndicatorRes = new int[]{R.drawable.indicator_normal, R.drawable.indicator_selected};
    private int mIndicatorPaddingLeft = 0;// indicator ?????????????????????
    private int mIndicatorPaddingRight = 0;//indicator ?????????????????????
    private int mIndicatorPaddingTop = 0;//indicator ?????????????????????
    private int mIndicatorPaddingBottom = 0;//indicator ?????????????????????
    private int mMZModePadding = 0;//??????????????????????????????????????????????????????????????????????????????????????????????????????padding
    private int mIndicatorAlign = 1;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private BannerPageClickListener mBannerPageClickListener;

    public enum IndicatorAlign {
        LEFT,//?????????
        CENTER,//????????????
        RIGHT //?????????
    }

    /**
     * ??????Page?????????????????????????????????
     */
    private boolean mIsMiddlePageCover = true;

    public LXBannerViewbeifen(@NonNull Context context) {
        super(context);
        init();
    }

    public LXBannerViewbeifen(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        readAttrs(context, attrs);
        init();
    }

    public LXBannerViewbeifen(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(context, attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LXBannerViewbeifen(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        readAttrs(context, attrs);
        init();
    }

    private void readAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LXBannerView);
        mIsOpenMZEffect = typedArray.getBoolean(R.styleable.LXBannerView_open_mz_mode, true);
        mIsOutSideBottom = typedArray.getBoolean(R.styleable.LXBannerView_outside_bottom, false);
        mIsMiddlePageCover = typedArray.getBoolean(R.styleable.LXBannerView_middle_page_cover, true);
        mIsCanLoop = typedArray.getBoolean(R.styleable.LXBannerView_canLoop, true);
        mIndicatorAlign = typedArray.getInt(R.styleable.LXBannerView_indicatorAlign, 1);
        mIndicatorPaddingLeft = typedArray.getDimensionPixelSize(R.styleable.LXBannerView_indicatorPaddingLeft, 0);
        mIndicatorPaddingRight = typedArray.getDimensionPixelSize(R.styleable.LXBannerView_indicatorPaddingRight, 0);
        mIndicatorPaddingTop = typedArray.getDimensionPixelSize(R.styleable.LXBannerView_indicatorPaddingTop, 0);
        mIndicatorPaddingBottom = typedArray.getDimensionPixelSize(R.styleable.LXBannerView_indicatorPaddingBottom, 0);
    }


    private void init() {
        View view = null;
        Log.e("---geekyun--", mIsOutSideBottom + "");
        if (mIsOpenMZEffect) {
            if (!mIsOutSideBottom) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.mz_banner_effect_layout, this, true);
            } else {
                view = LayoutInflater.from(getContext()).inflate(R.layout.mz_banner_effect_layout2, this, true);
            }
        } else {
            if (!mIsOutSideBottom) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.mz_banner_normal_layout, this, true);
            } else {
                view = LayoutInflater.from(getContext()).inflate(R.layout.mz_banner_normal_layout2, this, true);
            }
        }
        mIndicatorContainer = view.findViewById(R.id.banner_indicator_container);
        mViewPager = view.findViewById(R.id.mzbanner_vp);
        mViewPager.setOffscreenPageLimit(4);

        mMZModePadding = dpToPx(30);
        // ?????????Scroller
        initViewPagerScroll();

        if (mIndicatorAlign == 0) {
            setIndicatorAlign(IndicatorAlign.LEFT);
        } else if (mIndicatorAlign == 1) {
            setIndicatorAlign(IndicatorAlign.CENTER);
        } else {
            setIndicatorAlign(IndicatorAlign.RIGHT);
        }


    }

    /**
     * ????????????????????????
     */
    private void setOpenMZEffect() {
        // ????????????
        if (mIsOpenMZEffect) {
            if (mIsMiddlePageCover) {
                // ????????????????????????????????????APP ???banner ???????????????
                mViewPager.setPageTransformer(true, new CoverModeTransformer(mViewPager));
            } else {
                // ?????????????????????????????????????????????Y?????????
                mViewPager.setPageTransformer(false, new ScaleYTransformer());
            }

        }
    }

    /**
     * ??????ViewPager???????????????
     */
    private void initViewPagerScroll() {
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mViewPagerScroller = new ViewPagerScroller(
                    mViewPager.getContext());
            mScroller.set(mViewPager, mViewPagerScroller);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private final Runnable mLoopRunnable = new Runnable() {
        @Override
        public void run() {
            if (mIsAutoPlay) {
                mCurrentItem = mViewPager.getCurrentItem();
                mCurrentItem++;
                if (mCurrentItem == mAdapter.getCount() - 1) {
                    mCurrentItem = 0;
                    mViewPager.setCurrentItem(mCurrentItem, false);
                    mHandler.postDelayed(this, mDelayedTime);
                } else {
                    mViewPager.setCurrentItem(mCurrentItem);
                    mHandler.postDelayed(this, mDelayedTime);
                }
            } else {
                mHandler.postDelayed(this, mDelayedTime);
            }
        }
    };


    /**
     * ??????????????????Indicator
     */
    private void initIndicator() {
        mIndicatorContainer.removeAllViews();
        mIndicators.clear();
        for (int i = 0; i < mDatas.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            if (mIndicatorAlign == IndicatorAlign.LEFT.ordinal()) {
                if (i == 0) {
                    int paddingLeft = mIsOpenMZEffect ? mIndicatorPaddingLeft + mMZModePadding : mIndicatorPaddingLeft;
                    imageView.setPadding(paddingLeft + 6, 0, 6, 0);
                } else {
                    imageView.setPadding(6, 0, 6, 0);
                }

            } else if (mIndicatorAlign == IndicatorAlign.RIGHT.ordinal()) {
                if (i == mDatas.size() - 1) {
                    int paddingRight = mIsOpenMZEffect ? mMZModePadding + mIndicatorPaddingRight : mIndicatorPaddingRight;
                    imageView.setPadding(6, 0, 6 + paddingRight, 0);
                } else {
                    imageView.setPadding(6, 0, 6, 0);
                }

            } else {
                imageView.setPadding(6, 0, 6, 0);
            }

            if (i == (mCurrentItem % mDatas.size())) {
                imageView.setImageResource(mIndicatorRes[1]);
            } else {
                imageView.setImageResource(mIndicatorRes[0]);
            }

            mIndicators.add(imageView);
            mIndicatorContainer.addView(imageView);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!mIsCanLoop) {
            return super.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            // ??????Banner??????????????????????????????
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_DOWN:
                int paddingLeft = mViewPager.getLeft();
                float touchX = ev.getRawX();
                // ?????????????????????????????????????????????
                if (touchX >= paddingLeft && touchX < getScreenWidth(getContext()) - paddingLeft) {
                    mIsAutoPlay = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                mIsAutoPlay = true;
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public static int getScreenWidth(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        return width;
    }

    /******************************************************************************************************/
    /**                             ??????API                                                               **/
    /******************************************************************************************************/
    /**
     * ????????????
     * <p>???????????????????????????{@link LXBannerViewbeifen {@link #setPages(List, LXHolderCreator)}} ????????????????????????????????????</p>
     */
    public void start() {
        // ??????Adapter???null, ?????????????????????????????????????????????????????????Banner
        if (mAdapter == null) {
            return;
        }
        if (mIsCanLoop) {
            mIsAutoPlay = true;
            mHandler.postDelayed(mLoopRunnable, mDelayedTime);
        }
    }

    /**
     * ????????????
     */
    public void pause() {
        mIsAutoPlay = false;
        mHandler.removeCallbacks(mLoopRunnable);
    }

    /**
     * ??????BannerView ?????????????????????
     *
     * @param delayedTime
     */
    public void setDelayedTime(int delayedTime) {
        mDelayedTime = delayedTime;
    }

    public void addPageChangeLisnter(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    /**
     * ??????Page????????????
     *
     * @param bannerPageClickListener {@link BannerPageClickListener}
     */
    public void setBannerPageClickListener(BannerPageClickListener bannerPageClickListener) {
        mBannerPageClickListener = bannerPageClickListener;
    }

    /**
     * ????????????Indicator
     *
     * @param visible true ??????Indicator??????????????????
     */
    public void setIndicatorVisible(boolean visible) {
        if (visible) {
//            mIndicatorContainer.setBackgroundResource(R.drawable.indicator_bg_trans);
//            mIndicatorContainer.setPadding(40,10,40,10);
            mIndicatorContainer.setVisibility(VISIBLE);
        } else {
            mIndicatorContainer.setVisibility(GONE);
        }
    }

    /**
     * ??????ViewPager
     *
     * @return {@link ViewPager}
     */
    public CustomViewPager getViewPager() {
        return mViewPager;
    }

    /**
     * ??????indicator ????????????
     *
     * @param unSelectRes ???????????????????????????
     * @param selectRes   ????????????????????????
     */
    public void setIndicatorRes(@DrawableRes int unSelectRes, @DrawableRes int selectRes) {
        mIndicatorRes[0] = unSelectRes;
        mIndicatorRes[1] = selectRes;
    }

    /**
     * ????????????????????????????????????????????????
     * <p>????????????????????????????????????????????????</p>
     *
     * @param datas           Banner ?????????????????????
     * @param mzHolderCreator ViewHolder????????? {@link LXHolderCreator} And {@link LXViewHolder}
     */
    public void setPages(List<T> datas, LXHolderCreator mzHolderCreator) {
        if (datas == null || mzHolderCreator == null) {
            return;
        }
        mDatas = datas;
        //???????????????????????????????????????
        pause();

        //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????3,????????????????????????Banner??????
        //???????????????:open_mz_mode ?????????????????????true

        if (datas.size() < 3) {
            mIsOpenMZEffect = false;
            MarginLayoutParams layoutParams = (MarginLayoutParams) mViewPager.getLayoutParams();
            layoutParams.setMargins(0, 0, 0, 0);
            mViewPager.setLayoutParams(layoutParams);
            setClipChildren(true);
            mViewPager.setClipChildren(true);
        }
        setOpenMZEffect();
        // 2017.7.20 fix??????Indicator???????????????Adapter??????????????????????????????????????????????????????crush.
        //?????????Indicator
        initIndicator();

        mAdapter = new MZPagerAdapter(datas, mzHolderCreator, mIsCanLoop);
        mAdapter.setUpViewViewPager(mViewPager);
        mAdapter.setPageClickListener(mBannerPageClickListener);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                int realPosition = position % mIndicators.size();
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentItem = position;


                // ??????indicator
                int realSelectPosition = mCurrentItem % mIndicators.size();
                for (int i = 0; i < mDatas.size(); i++) {
                    if (mIndicators.size() <= 0) {
                        return;
                    }
                    if (i == realSelectPosition) {
                        mIndicators.get(i).setImageResource(mIndicatorRes[1]);
                    } else {
                        mIndicators.get(i).setImageResource(mIndicatorRes[0]);
                    }
                }
                // ???????????????mOnPageChangeListener ?????????ViewPager ,???????????????position ????????????positon
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageSelected(realSelectPosition);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        mIsAutoPlay = false;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        mIsAutoPlay = true;
                        break;
                    default:
                        break;

                }
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });


    }

    public int getCurrent() {
        return mViewPager.getCurrentItem();
    }

    public void setCurrent(int current) {
        mViewPager.setCurrentItem(current);
    }

    /**
     * ??????Indicator ???????????????
     *
     * @param indicatorAlign {@link IndicatorAlign#CENTER }{@link IndicatorAlign#LEFT }{@link IndicatorAlign#RIGHT }
     */
    public void setIndicatorAlign(IndicatorAlign indicatorAlign) {
        mIndicatorAlign = indicatorAlign.ordinal();
        LayoutParams layoutParams = (LayoutParams) mIndicatorContainer.getLayoutParams();
        if (indicatorAlign == IndicatorAlign.LEFT) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else if (indicatorAlign == IndicatorAlign.RIGHT) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        }

        // 2017.8.27 ?????????????????????Indicator ??????????????????

        layoutParams.setMargins(0, mIndicatorPaddingTop, 0, mIndicatorPaddingBottom);
        mIndicatorContainer.setLayoutParams(layoutParams);

    }

    /**
     * ??????????????????bufen
     *
     * @return
     */
    public boolean ismIsCanLoop() {
        return mIsCanLoop;
    }

    public void setmIsCanLoop(boolean mIsCanLoop) {
        this.mIsCanLoop = mIsCanLoop;
    }

    public boolean ismIsOutSideBottom() {
        return mIsOutSideBottom;
    }

    public void setmIsOutSideBottom(boolean mIsOutSideBottom) {
        this.mIsOutSideBottom = mIsOutSideBottom;
    }

    /**
     * ??????ViewPager???????????????
     *
     * @param duration ??????????????????
     */
    public void setDuration(int duration) {
        mViewPagerScroller.setDuration(duration);
    }

    /**
     * ??????????????????ViewPager????????????????????????
     *
     * @param useDefaultDuration ??????????????????
     */
    public void setUseDefaultDuration(boolean useDefaultDuration) {
        mViewPagerScroller.setUseDefaultDuration(useDefaultDuration);
    }

    /**
     * ??????Banner????????????????????????
     *
     * @return
     */
    public int getDuration() {
        return mViewPagerScroller.getScrollDuration();
    }

    /**
     * ??????mIndicatorContainer?????????
     */
    public LinearLayout getmIndicatorContainer() {
        return mIndicatorContainer;
    }


    public static class MZPagerAdapter<T> extends PagerAdapter {
        private List<T> mDatas;
        private LXHolderCreator mMZHolderCreator;
        private ViewPager mViewPager;
        private boolean canLoop;
        private BannerPageClickListener mPageClickListener;
        private final int mLooperCountFactor = 500;

        public MZPagerAdapter(List<T> datas, LXHolderCreator MZHolderCreator, boolean canLoop) {
            if (mDatas == null) {
                mDatas = new ArrayList<>();
            }
            //mDatas.add(datas.get(datas.size()-1));// ??????????????????
            for (T t : datas) {
                mDatas.add(t);
            }
            // mDatas.add(datas.get(0));//??????????????????????????????
            mMZHolderCreator = MZHolderCreator;
            this.canLoop = canLoop;
        }

        public void setPageClickListener(BannerPageClickListener pageClickListener) {
            mPageClickListener = pageClickListener;
        }

        /**
         * ?????????Adapter????????????????????????Item
         *
         * @param viewPager
         */
        public void setUpViewViewPager(ViewPager viewPager) {
            mViewPager = viewPager;
            mViewPager.setAdapter(this);
            mViewPager.getAdapter().notifyDataSetChanged();
            int currentItem = canLoop ? getStartSelectItem() : 0;
            //?????????????????????Item
            mViewPager.setCurrentItem(currentItem);
        }

        private int getStartSelectItem() {
            // ????????????????????????????????????Integer.MAX_VALUE / 2,??????????????????????????????
            // ???????????????????????????getRealPosition ??? ?????????0????????????????????????????????????
            int currentItem = getRealCount() * mLooperCountFactor / 2;
            if (currentItem % getRealCount() == 0) {
                return currentItem;
            }
            // ???????????????0???????????????
            while (currentItem % getRealCount() != 0) {
                currentItem++;
            }
            return currentItem;
        }

        public void setDatas(List<T> datas) {
            mDatas = datas;
        }

        @Override
        public int getCount() {
            // 2017.6.10 bug fix
            // ??????getCount ???????????????Integer.MAX_VALUE ??????????????????setCurrentItem????????????ANR(?????????onCreate ????????????)
            return canLoop ? getRealCount() * mLooperCountFactor : getRealCount();//ViewPager??????int ?????????
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = getView(position, container);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            // ?????????????????????
            if (canLoop) {
                int position = mViewPager.getCurrentItem();
                if (position == getCount() - 1) {
                    position = 0;
                    setCurrentItem(position);
                }
            }

        }

        private void setCurrentItem(int position) {
            try {
                mViewPager.setCurrentItem(position, false);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

        /**
         * ???????????????Count
         *
         * @return
         */
        private int getRealCount() {
            return mDatas == null ? 0 : mDatas.size();
        }

        /**
         * @param position
         * @param container
         * @return
         */
        private View getView(int position, ViewGroup container) {

            final int realPosition = position % getRealCount();
            LXViewHolder holder = null;
            // create holder
            holder = mMZHolderCreator.createViewHolder();

            if (holder == null) {
                throw new RuntimeException("can not return a null holder");
            }
            // create View
            View view = holder.createView(container.getContext());

            if (mDatas != null && mDatas.size() > 0) {
                holder.onBind(container.getContext(), realPosition, mDatas.get(realPosition));
            }

            // ??????????????????
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPageClickListener != null) {
                        mPageClickListener.onPageClick(v, realPosition);
                    }
                }
            });

            return view;
        }


    }

    /**
     * ?????????ViewPager ????????????????????????????????????????????????Scroller ????????????????????????
     * <p>????????????ViewPager ????????????????????????Scroller?????????????????????????????????????????????</p>
     * <p>????????????ViewPager ??? mScroller ???????????????????????????????????????Scroller</p>
     */
    public static class ViewPagerScroller extends Scroller {
        private int mDuration = 800;// ViewPager???????????????Duration ???600,????????????????????????????????????????????????
        private boolean mIsUseDefaultDuration = false;

        public ViewPagerScroller(Context context) {
            super(context);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, mDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, mIsUseDefaultDuration ? duration : mDuration);
        }

        public void setUseDefaultDuration(boolean useDefaultDuration) {
            mIsUseDefaultDuration = useDefaultDuration;
        }

        public boolean isUseDefaultDuration() {
            return mIsUseDefaultDuration;
        }

        public void setDuration(int duration) {
            mDuration = duration;
        }


        public int getScrollDuration() {
            return mDuration;
        }
    }

    /**
     * Banner page ????????????
     */
    public interface BannerPageClickListener {
        void onPageClick(View view, int position);
    }

    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

