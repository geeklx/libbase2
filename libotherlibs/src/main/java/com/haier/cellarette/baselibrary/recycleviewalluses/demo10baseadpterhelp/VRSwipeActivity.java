package com.haier.cellarette.baselibrary.recycleviewalluses.demo10baseadpterhelp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.haier.cellarette.baselibrary.R;
import com.haier.cellarette.baselibrary.swiperecycleview.ItemAnimCallback;
import com.haier.cellarette.baselibrary.swiperecycleview.OnSwipeListener;
import com.haier.cellarette.baselibrary.swiperecycleview.SwipeAdapter;
import com.haier.cellarette.baselibrary.swiperecycleview.SwipeBaseViewHolder;
import com.haier.cellarette.baselibrary.swiperecycleview.SwipeRecyclerView;

import java.util.ArrayList;

public class VRSwipeActivity extends AppCompatActivity {
    private SwipeAdapter<VRBean> swipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vrswipe);

        final ArrayList<VRBean> list_bean = new ArrayList<VRBean>();
        for (int i = 0; i < 100; i++) {
            list_bean.add(new VRBean("内容" + i));
        }

        swipeAdapter = new SwipeAdapter<VRBean>() {
            @Override
            public void bindDataToView(final SwipeBaseViewHolder holder, final int position, VRBean bean) {
                holder.setText(R.id.tv, bean.getStr());

                holder.setOnClickListener(R.id.tv_zhiding, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeOpened();
                        ToastUtils.showLong("点击置顶");
                    }
                });
                holder.setOnClickListener(R.id.tv_biaoweiweidu, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeOpened();
                        ToastUtils.showLong("点击标为未读");
                    }
                });
                Log.e("position", position + "");

                holder.setOnClickListener(R.id.layout_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeOpened(new OnSwipeListener() {
                            @Override
                            public void onClosed() {
                                //必须关闭动画完毕后才remove item,否则，会因为缓存导致,其他item闪烁
                                Log.e("position_remove", position + "");
                                /**
                                 *  此处要注意，函数体内，创建匿名内部类，而且内部类持有函数参数，只能将函数参数定义为final，但容易产生BUG，
                                 * 假remove数据项后，如没有全部item都刷新，这里得到的position永远是当前holder最原始的position，
                                 * 所幸的是：holder自身保存了position，
                                 * holder.getAdapterPosition()获取的position是当前holder在整个recyclerView中的正确位置，
                                 */
//                                getSimpleAdapter().remove(position);
                                getAdapter().remove(holder.getBindingAdapterPosition());
                            }
                        });
                    }
                });
            }

            @Override
            public int getItemLayoutID(int position, VRBean bean) {
                return R.layout.item_swipe;
            }

            @Override
            public void onItemClick(SwipeBaseViewHolder holder, int position, VRBean bean) {
                ToastUtils.showLong("点击内容" + position);
            }
        };
        ((SwipeRecyclerView) findViewById(R.id.srv))
                .addItemTouchAnim(new ItemAnimCallback(swipeAdapter.getAdapter())).setAdapter(swipeAdapter);
        swipeAdapter.getAdapter().add(list_bean);

    }

}
