package com.haier.cellarette.baselibrary.swiperecycleview;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * 类似策略模式,引入IAdapter接口，面向多态编程
 */

public abstract class SwipeAdapter<T> implements IAdapter<T, SwipeBaseViewHolder, SwipeSimpleAdapter> {
    private SwipeSimpleAdapter<T> simpleAdapter;
    private SwipeLayout swipeLayout_opened;
    private SwipeLayout swipeLayout_scrolled;

    public SwipeAdapter() {
        simpleAdapter = new SwipeSimpleAdapter<T>() {
            @Override
            public void bindDataToView(final SwipeBaseViewHolder holder, int position, T bean) {
                dealSwipe(holder, bean);
                SwipeAdapter.this.bindDataToView(holder, position, bean);
            }

            @Override
            public void onViewRecycled(SwipeBaseViewHolder holder, int position, T bean) {
                super.onViewRecycled(holder, position, bean);
                SwipeAdapter.this.onViewRecycled(holder, position, bean);
            }

            @Override
            public int getItemLayoutID(int position, T bean) {
                return SwipeAdapter.this.getItemLayoutID(position, bean);
            }

            @Override
            public long getItemId(int position) {
                return SwipeAdapter.this.getItemId(position);
            }

            @Override
            public boolean hasStableIds_() {
                return SwipeAdapter.this.hasStableIds_();
            }

            @Override
            public void onItemClick(SwipeBaseViewHolder holder, int position, T bean) {
                SwipeAdapter.this.onItemClick(holder, position, bean);
            }

            @Override
            public void onItemLongClick(SwipeBaseViewHolder holder, int position, T bean) {
                SwipeAdapter.this.onItemLongClick(holder, position, bean);
            }

            @Override
            public void onViewAttachedToWindow(@NonNull SwipeBaseViewHolder holder) {
                super.onViewAttachedToWindow(holder);
                SwipeAdapter.this.onViewAttachedToWindow(holder);

            }
        };
    }

    private void dealSwipe(final SwipeBaseViewHolder holder, final T bean) {
        ((SwipeLayout) holder.itemView).getContentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getBindingAdapterPosition();
                if (position < 0 || position >= simpleAdapter.getList_bean().size()) {
                    return;
                }
                simpleAdapter.onItemClick(holder, position, bean);
            }
        });
        ((SwipeLayout) holder.itemView).setOnSwipeListener(new OnSwipeListener() {
            @Override
            public void onScrolled(int dx) {
                swipeLayout_scrolled = (SwipeLayout) holder.itemView;
                int position = holder.getBindingAdapterPosition();
                if (position < 0 || position >= simpleAdapter.getList_bean().size()) {
                    return;
                }
                SwipeAdapter.this.onScrolled(holder, position, bean, dx);
            }

            @Override
            public void onOpened() {
                swipeLayout_opened = (SwipeLayout) holder.itemView;
                int position = holder.getBindingAdapterPosition();
                if (position < 0 || position >= simpleAdapter.getList_bean().size()) {
                    return;
                }
                SwipeAdapter.this.onOpened(holder, position, bean);
            }

            @Override
            public void onClosed() {
                swipeLayout_opened = null;
                int position = holder.getBindingAdapterPosition();
                if (position < 0 || position >= simpleAdapter.getList_bean().size()) {
                    return;
                }
                SwipeAdapter.this.onClosed(holder, position, bean);
            }
        });
    }

    @Override
    public void onViewRecycled(SwipeBaseViewHolder holder, int position, T bean) {

    }

    public void onScrolled(SwipeBaseViewHolder holder, int position, T bean, int dx) {
    }

    public void onOpened(SwipeBaseViewHolder holder, int position, T bean) {
    }

    public void onClosed(SwipeBaseViewHolder holder, int position, T bean) {
    }

    @Override
    public void onItemLongClick(SwipeBaseViewHolder holder, int position, T bean) {

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds_() {
        return true;
    }

    /**
     * 再次彰显面向多态编程的威力，接口的强扩展
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(SwipeBaseViewHolder holder) {

    }


    public SwipeLayout getOpened() {
        return swipeLayout_opened;
    }

    public void closeOpened() {
        swipeLayout_opened.close();
        swipeLayout_opened = null;
    }

    public void closeOpened(OnSwipeListener onSwipeListener) {
        swipeLayout_opened.close(onSwipeListener);
        swipeLayout_opened = null;
    }

    public SwipeLayout getScrolled() {
        return swipeLayout_scrolled;
    }

    public void closeScrolled() {
        swipeLayout_scrolled.close();
        swipeLayout_scrolled = null;
    }

    @Override
    public SwipeSimpleAdapter<T> getAdapter() {
        return simpleAdapter;
    }
}
