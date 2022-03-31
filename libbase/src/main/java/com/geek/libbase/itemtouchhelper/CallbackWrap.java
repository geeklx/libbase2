package com.geek.libbase.itemtouchhelper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.Collections;

// 可拖拽的item
public class CallbackWrap extends ItemTouchHelper.Callback implements DragMethod {

    private BaseQuickAdapter adapter;
    private Context context;

    private int mBackgroundColor = Color.WHITE;
    private int mDragcolor = Color.parseColor("#00000000");// #f5f5f5

    public CallbackWrap(BaseQuickAdapter adapter, Context context) {
        this.adapter = adapter;
        this.context = context;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            //final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }


    @Override
    public void onMove(int fromPosition, int toPosition) {
//        if (fromPosition == getData().size() - 1 || toPosition == getData().size() - 1) {
//            return;
//        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(adapter.getData(), i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(adapter.getData(), i, i - 1);
            }
        }
        adapter.notifyItemMoved(fromPosition, toPosition);
//        List<BjyyBeanYewu3> beanList = adapter.getData();
//        MyLogUtil.e("sssssssssssss2", beanList.get(0).getId() + "," + beanList.get(1).getId());
    }

    @Override
    public void onSwiped(int position) {
        adapter.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getLayoutPosition();//得到拖动ViewHolder的position
        int toPosition = target.getLayoutPosition();//得到目标ViewHolder的position
        onMove(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getLayoutPosition();
        onSwiped(position);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//            滑动时改变Item的透明度
            final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundColor(mDragcolor);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setAlpha(1.0f);
        viewHolder.itemView.setBackgroundColor(mBackgroundColor);
    }

    /**
     * 设置普通状态的背景色
     *
     * @param backgroundColor
     */
    public void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
    }

    /**
     * 设置被拖动时候的背景色
     *
     * @param dragcolor
     */
    public void setDragcolor(int dragcolor) {
        mDragcolor = dragcolor;
    }


}
