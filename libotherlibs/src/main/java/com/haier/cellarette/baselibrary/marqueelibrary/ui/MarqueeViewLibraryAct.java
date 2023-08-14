package com.haier.cellarette.baselibrary.marqueelibrary.ui;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.assetsfitandroid.AssetsDemoBean;
import com.haier.cellarette.baselibrary.marqueelibrary.MarqueeFactory;
import com.haier.cellarette.baselibrary.marqueelibrary.MarqueeView;
import com.haier.cellarette.baselibrary.marqueelibrary.SimpleMF;
import com.haier.cellarette.baselibrary.marqueelibrary.SimpleMarqueeView;
import com.haier.cellarette.baselibrary.marqueelibrary.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MarqueeViewLibraryAct extends AppCompatActivity {
    private final List<String> datas = Arrays.asList("《赋得古原草送别》", "离离原上草，一岁一枯荣。", "野火烧不尽，春风吹又生。", "远芳侵古道，晴翠接荒城。", "又送王孙去，萋萋满别情。", "测试测试测试测试测试测试测试测试测试测试测试");
    private SimpleMarqueeView<String> marqueeView1, marqueeView2, marqueeView5, marqueeView6;
    private SimpleMarqueeView<Spanned> marqueeView3;
    private ImageView yellowSpeaker;
    private MarqueeView<RelativeLayout, ComplexItemEntity> marqueeView4;

    private WeakHandler mHandler = new WeakHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marqueeviewlibrary);
        marqueeView1 = findViewById(R.id.marqueeView1);
        marqueeView2 = findViewById(R.id.marqueeView2);
        marqueeView3 = findViewById(R.id.marqueeView3);
        yellowSpeaker = findViewById(R.id.yellowSpeaker);
        marqueeView4 = findViewById(R.id.marqueeView4);
        marqueeView5 = findViewById(R.id.marqueeView5);
        marqueeView6 = findViewById(R.id.marqueeView6);
        DrawableCompat.setTint(DrawableCompat.wrap(yellowSpeaker.getDrawable().mutate()), getResources().getColor(R.color.yellow));

        initMarqueeView1();
        initMarqueeView2();
        initMarqueeView3();
        initMarqueeView4();
        initMarqueeView5();
        initMarqueeView6();
    }

    private void initMarqueeView1() {
        SimpleMF<String> marqueeFactory = new SimpleMF(MarqueeViewLibraryAct.this);
        marqueeFactory.setData(datas);
        marqueeView1.setMarqueeFactory(marqueeFactory);
        marqueeView1.startFlipping();
        marqueeView1.setOnItemClickListener(onSimpleItemClickListener);
    }

    private void initMarqueeView2() {
        SimpleMF<String> marqueeFactory2 = new SimpleMF(MarqueeViewLibraryAct.this);
        marqueeFactory2.setData(datas);
        marqueeView2.setMarqueeFactory(marqueeFactory2);
        marqueeView2.startFlipping();
        marqueeView2.setOnItemClickListener(onSimpleItemClickListener);
    }

    private void initMarqueeView3() {
        SimpleMF<Spanned> marqueeFactory3 = new SimpleMF<>(MarqueeViewLibraryAct.this);
        List<Spanned> datas3 = new ArrayList<>();
        datas3.add(Html.fromHtml("<font color=\"#ff0000\">《赋得古原草送别》</font>"));
        datas3.add(Html.fromHtml("<font color=\"#00ff00\">离离原上草，</font>一岁一枯荣。"));
        datas3.add(Html.fromHtml("野火烧不尽，<font color=\"#0000ff\">春风吹又生。</font>"));
        datas3.add(Html.fromHtml("<font color=\"#333333\">远芳侵古道，晴翠接荒城。</font>"));
        datas3.add(Html.fromHtml("<font color=\"#ffffff\">又送王孙去，萋萋满别情。</font>"));
        marqueeFactory3.setData(datas3);
        marqueeView3.setMarqueeFactory(marqueeFactory3);
        marqueeView3.startFlipping();
        marqueeView3.setOnItemClickListener(new OnItemClickListener<TextView, Spanned>() {
            @Override
            public void onItemClickListener(TextView mView, Spanned mData, int mPosition) {
                Toast.makeText(MarqueeViewLibraryAct.this, String.format("mPosition:%s,mData:%s,mView:%s,.", mPosition, mData, mView), Toast.LENGTH_SHORT).show();
            }
        });
    }
    ComplexItemEntity mData;

    private void initMarqueeView4() {
        List<ComplexItemEntity> complexDatas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            complexDatas.add(new ComplexItemEntity("标题 " + i, "副标题 " + i, "时间 " + i));
        }
        MarqueeFactory<RelativeLayout, ComplexItemEntity> marqueeFactory = new ComplexViewMF(MarqueeViewLibraryAct.this);

        marqueeFactory.setData(complexDatas);
        marqueeView4.setOnItemClickListener(new OnItemClickListener<RelativeLayout, ComplexItemEntity>() {
            @Override
            public void onItemClickListener(RelativeLayout mView, ComplexItemEntity mData, int mPosition) {

                Toast.makeText(MarqueeViewLibraryAct.this, String.format("mPosition:%s,mData:%s,mView:%s,.", mPosition, mData, mView), Toast.LENGTH_SHORT).show();
            }
        });
        marqueeView4.setInAndOutAnim(R.anim.in_top, R.anim.out_bottom);
        marqueeView4.setMarqueeFactory(marqueeFactory);
        marqueeView4.setMarqueeFactory(new MarqueeFactory<RelativeLayout, ComplexItemEntity>(this) {
            private LayoutInflater inflater;
            @Override
            protected RelativeLayout generateMarqueeItemView(ComplexItemEntity data) {
                inflater = LayoutInflater.from(mContext);
                RelativeLayout mView = (RelativeLayout) inflater.inflate(R.layout.complex_view, null);
                ((TextView) mView.findViewById(R.id.title)).setText(data.getTitle());
                ((TextView) mView.findViewById(R.id.secondTitle)).setText(data.getSecondTitle());
                ((TextView) mView.findViewById(R.id.time)).setText(data.getTime());
                return mView;
            }
        });
        marqueeView4.startFlipping();
    }

    private void initMarqueeView5() {
        SimpleMF<String> marqueeFactory = new SimpleMF(this);
        marqueeFactory.setData(datas);
        marqueeView5.setOnItemClickListener(onSimpleItemClickListener);
        marqueeView5.setMarqueeFactory(marqueeFactory);
        marqueeView5.startFlipping();
    }

    private void initMarqueeView6() {
        final SimpleMF<String> marqueeFactory = new SimpleMF<>(this);
        marqueeFactory.setData(datas);
        marqueeView6.setOnItemClickListener(onSimpleItemClickListener);
        marqueeView6.setMarqueeFactory(marqueeFactory);
        marqueeView6.startFlipping();

        //测试重置数据效果
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                int delayMillis = (random.nextInt(5) + 4) * 1000;
                marqueeFactory.setData(datas);
                mHandler.postDelayed(this, delayMillis);
            }
        }, 8000);
    }

    @Override
    public void onStart() {
        super.onStart();
        marqueeView1.startFlipping();
        marqueeView2.startFlipping();
        marqueeView3.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        marqueeView1.stopFlipping();
        marqueeView2.stopFlipping();
        marqueeView3.stopFlipping();
    }

    private OnItemClickListener<TextView, String> onSimpleItemClickListener = new OnItemClickListener<TextView, String>() {
        @Override
        public void onItemClickListener(TextView mView, String mData, int mPosition) {
            Toast.makeText(MarqueeViewLibraryAct.this, String.format("mPosition:%s,mData:%s,mView:%s,.", mPosition, mData, mView), Toast.LENGTH_SHORT).show();
        }
    };
}
