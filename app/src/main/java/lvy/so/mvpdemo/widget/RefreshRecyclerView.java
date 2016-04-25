package lvy.so.mvpdemo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @author gping  email: gping.vip@gmail.com
 * @date Created by 2016/4/22.19:00
 * @filename RefreshRecyclerView.class
 * @description 重写 实现自动加载更多功能
 * @TODO
 */
public class RefreshRecyclerView extends RecyclerView {
    public RefreshRecyclerView(Context context) {
        this(context, null);
    }

    public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void onScrollStateChanged(int state) {
        LinearLayoutManager llm = (LinearLayoutManager) getLayoutManager();
        if (state == RefreshRecyclerView.SCROLL_STATE_IDLE) {
            int lastVisiblePosition = llm.findLastCompletelyVisibleItemPosition();
            int totalVisiblePosition = llm.getItemCount();
            if (lastVisiblePosition == (totalVisiblePosition - 1)) {  //
                if (loadMoreListener != null) {
                    loadMoreListener.loadMore();
                }
            }
        }
        super.onScrollStateChanged(state);
    }

    private OnLoadMoreListener loadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.loadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener {
        void loadMore();
    }
}
