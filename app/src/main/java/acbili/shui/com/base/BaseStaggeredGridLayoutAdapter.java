package acbili.shui.com.base;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 作者： max_Shui on 2016/10/5.
 * 邮箱：shuicz0505@qq.com、
 * 瀑布流adapter的基类
 */

public abstract class BaseStaggeredGridLayoutAdapter <T extends BaseRecyclerViewAdapter.Item> extends BaseRecyclerViewAdapter<T>{
    public BaseStaggeredGridLayoutAdapter(List<T> list) {
        super( list );
    }

    public BaseStaggeredGridLayoutAdapter(List<T> list, View headerViewRes) {
        super( list, headerViewRes );
    }

    public BaseStaggeredGridLayoutAdapter(List<T> list, View headerViewRes, View footerViewRes) {
        super( list, headerViewRes, footerViewRes );
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isStaggeredGridLayout(holder)) {
            handleLayoutIfStaggeredGridLayout(holder, holder.getLayoutPosition());
        }
    }

    private boolean isStaggeredGridLayout(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            return true;
        }
        return false;
    }

    protected void handleLayoutIfStaggeredGridLayout(RecyclerView.ViewHolder holder, int position) {
        if (isHeader(position) || isFooter(position)) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            p.setFullSpan(true);
        }
    }

}
