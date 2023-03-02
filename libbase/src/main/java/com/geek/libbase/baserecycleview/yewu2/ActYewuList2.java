package com.geek.libbase.baserecycleview.yewu2;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AppUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.geek.libbase.R;
import com.geek.libbase.baserecycleview.SlbBaseActivityViewPageTabBean1;
import com.geek.libbase.baserecycleview.SlbBaseActivityYewuList;
import com.geek.libbase.utils.CommonUtils;
import com.geek.libbase.widgets.GridSpacingItemDecoration;
import com.geek.libswipebacklayout.SwipeBack;
import com.geek.libglide47.base.util.DisplayUtil;
import com.geek.libutils.app.LocalBroadcastManagers;
import com.geek.libutils.app.MyLogUtil;
import com.geek.libwebview.hois2.HiosHelper;
import com.just.agentweb.geek.fragment.AgentWebFragment;

import java.util.ArrayList;
import java.util.List;

@SwipeBack(value = true)
public class ActYewuList2 extends SlbBaseActivityYewuList implements View.OnClickListener {
//public class ActYewuList1 extends SlbBaseFragmentViewPageYewuList implements View.OnClickListener {

    //    private HMyorderPresenter presenter1;
    //
    private YewuList2CommonAdapter mAdapter1;
    private List<SlbBaseActivityViewPageTabBean1> mList1;
    private String tablayoutId;

    @Override
    public void onResume() {
        // 从缓存中拿出头像bufen
        MyLogUtil.e("sssssss", "onResume");
        super.onResume();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_common_index1;
    }

    @Override
    protected void findviewAdd() {
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("说明");
        // 1
//        recyclerView1.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
        // 4
        recyclerView1.setLayoutManager(new GridLayoutManager(this, 4, RecyclerView.VERTICAL, false));
        recyclerView1.addItemDecoration(new GridSpacingItemDecoration(4, (int) (DisplayUtil.getScreenWidth(this) * 8f / 375), true));
        mList1 = new ArrayList<>();
        mAdapter1 = new YewuList2CommonAdapter();
        recyclerView1.setAdapter(mAdapter1);
        init_adapter(mAdapter1);
    }

    @Override
    protected void donetworkAdd() {
        tvCenterContent.setText("我的订单");
        tablayoutId = getIntent().getStringExtra("id");
        PAGE_SIZE = 5;
        mNextRequestPage = 0;
        //HIOS协议
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        if (appLinkIntent != null) {
            String appLinkAction = appLinkIntent.getAction();
            if (appLinkAction != null) {
                Uri appLinkData = appLinkIntent.getData();
                if (appLinkData != null) {
                    tablayoutId = appLinkData.getQueryParameter("query1");

                }
            }
        }
        // 接口初始化bufen
//        if (presenter1 == null) {
//            presenter1 = new HMyorderPresenter();
//            presenter1.onCreate(this);
//        }
    }

    @Override
    protected void retryDataAdd() {
        String limit = PAGE_SIZE + "";
        String page = mNextRequestPage + "";
        String orderStatus = tablayoutId;
        MyLogUtil.e("--FragmentYewuList1-limit----", limit);
        MyLogUtil.e("--FragmentYewuList1-page----", page);
        MyLogUtil.e("--FragmentYewuList1-orderStatus----", tablayoutId);
        // 接口bufen
//        presenter1.get_myorderlist2(page, limit, orderStatus);
        //TODO test
        mList1 = new ArrayList<>();
////        mList1 = hMyorderBean.getList();
        for (int i = 0; i < PAGE_SIZE; i++) {
            mList1.add(new SlbBaseActivityViewPageTabBean1(i + "1", "geek" + i, false));
        }
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                OnOrderSuccess(mList1);
            }
        }, 1000);
    }

    @Override
    protected void refreshLayoutAdd() {
        // 业务bufen
//      mAdapter1.cancle_timer_all();
    }

    @Override
    protected void emptyviewAdd() {
        // 业务bufen
//      mAdapter1.cancle_timer_all();
    }

    @Override
    protected void onclickAdd() {
        tvCenterContent.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        mAdapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SlbBaseActivityViewPageTabBean1 bean = (SlbBaseActivityViewPageTabBean1) adapter.getData().get(position);
                //订单详情
//                Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.OrderDetailActivity");
//                intent.putExtra("orderType", bean.getOrderType());
//                intent.putExtra("orderId", bean.getId());
//                startActivity(intent);
                // demo
                //                final SlbBaseActivityViewPageTabBean1 bean = (SlbBaseActivityViewPageTabBean1) adapter.getData().get(position);
//                int i = view.getId();
//                if (i == com.geek.libbase.R.id.tv1) {
//                    // 入学通知书
////                    Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.NoticeDetailActivity");
////                    intent.putExtra("orderType", bean.getOrderType());
////                    intent.putExtra("orderId", bean.getId());
////                    startActivity(intent);
//                }
//                BjyyBeanYewu3 bean = (BjyyBeanYewu3) adapter.getData().get(position);
////                HiosHelperNew.resolveAd(ShouyeCateAct1.this, ShouyeCateAct1.this, bean.getUrl());
//                if (bean.getUrl().startsWith("gh")) {
//                    // 调用
//                    String appId = "wxa3fa50c49fcd271c"; // 填应用AppId
//                    IWXAPI api = WXAPIFactory.createWXAPI(ShouyeCateAct1.this, appId);
//                    WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
//                    req.userName = "gh_d43f693ca31f"; // 填小程序原始id
////                req.path = "path";                  //拉起小程序页面的可带参路径，不填默认拉起小程序首页
//                    req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;// 可选打开 开发版，体验版和正式版
//                    api.sendReq(req);
//                } else if (bean.getUrl().startsWith("http")) {
//                    Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.WeChatH5JsWebActivity");
//                    intent.putExtra(AgentWebFragment.URL_KEY, bean.getUrl());
//                    startActivity(intent);
////                    HiosHelperNew.resolveAd(getActivity(), getActivity(),  bean.getUrl());
//                } else {
//                    HiosHelperNew.resolveAd(ShouyeCateAct1.this, ShouyeCateAct1.this, bean.getUrl());
//                }
//                FCate4Bean2 addressBean = (FCate4Bean2) adapter.getItem(position);
//                if (addressBean.getUrl().startsWith("http")) {
//                    Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.WeChatH5JsWebActivity");
//                    intent.putExtra(AgentWebFragment.URL_KEY, addressBean.getUrl());
//                    startActivity(intent);
////                    HiosHelperNew.resolveAd(getActivity(), getActivity(), addressBean.getmBean().getUrl());
//                } else {
//                    Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.ShouyeCateAct1");
//                    intent.putExtra(AppCommonUtils.intent_id, addressBean.getId());
//                    intent.putExtra(AppCommonUtils.intent_title, addressBean.getName());
//                    startActivity(intent);
////                    HiosHelperNew.resolveAd(getActivity(), getActivity(), addressBean.getmBean().getUrl());
//                }
            }
        });
        mAdapter1.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
                final SlbBaseActivityViewPageTabBean1 bean = (SlbBaseActivityViewPageTabBean1) adapter.getData().get(position);
                int i = view.getId();
                if (i == R.id.tv1) {
                    // 入学通知书
//                    Intent intent = new Intent(AppUtils.getAppPackageName() + ".hs.act.slbapp.NoticeDetailActivity");
//                    intent.putExtra("orderType", bean.getOrderType());
//                    intent.putExtra("orderId", bean.getId());
//                    startActivity(intent);
                }

            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.baserecycleview_tv_center_content1) {
            Intent msgIntent = new Intent();
            msgIntent.setAction(CommonUtils.ActYewuList1);
            if (!isEnscrolly()) {
                // fragment传值 刷新
                msgIntent.putExtra(CommonUtils.SlbBaseActivityViewPageAct1, CommonUtils.SlbBaseAct_update);
                LocalBroadcastManagers.getInstance(ActYewuList2.this).sendBroadcast(msgIntent);
            } else {
                // fragment传值 滚动
                msgIntent.putExtra(CommonUtils.SlbBaseActivityViewPageAct1, CommonUtils.SlbBaseAct_scroll);
                LocalBroadcastManagers.getInstance(ActYewuList2.this).sendBroadcast(msgIntent);
            }
        } else if (view.getId() == R.id.baserecycleview_tv_right1) {
            // 用户协议
            HiosHelper.resolveAd(ActYewuList2.this, this, "http://pc.jiuzhidao.com/portal/page/index/id/9.html");
        } else {

        }
    }


    /**
     * --------------------------------业务逻辑分割线----------------------------------
     */

//    @Override
//    public void OnMyorderNewSuccess(SlbBaseActivityViewPageTabBean1 hMyorderBean) {
//        mList1 = new ArrayList<>();
////        mList1 = hMyorderBean.getList();
//        OnOrderSuccess(mList1);
//    }
//
//    @Override
//    public void OnMyorderNewNodata(String s) {
//        OnOrderNodata();
//
//    }
//
//    @Override
//    public void OnMyorderNewFail(String s) {
//        OnOrderFail();
//    }

}
