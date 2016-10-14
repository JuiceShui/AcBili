package acbili.shui.com.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 作者： max_Shui on 2016/10/5.
 * 邮箱：shuicz0505@qq.com
 *RecyclerViewAdapter的基类
 */

public abstract class BaseRecyclerViewAdapter<T extends BaseRecyclerViewAdapter.Item> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public interface Item {
        int TYPE_HEADER = 0;
        int TYPE_FOOTER = 1;
        /**
         * 返回item类型，其值不能为0或者1；
         *
         * @return
         */
        int getItemType();
    }

    protected List<T> list = null;
    protected View headerViewRes;
    protected View footerViewRes;
    protected boolean hasHeader = false;
    protected boolean hasFooter = false;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public boolean isHeader(int position) {
        return hasHeader() && position == 0;
    }

    public boolean isFooter(int position) {
        if(hasHeader()){
            return hasFooter() && position == list.size() + 1;
        }else {
            return hasFooter() && position == list.size();
        }
    }

    public View getHeaderView() {
        return headerViewRes;
    }

    public View getFooterView() {
        return footerViewRes;
    }

    public void setHeaderView(View headerViewRes) {
        if (headerViewRes != null) {
            if (!hasHeader()){
                this.headerViewRes = headerViewRes;
                this.hasHeader = true;
                notifyItemInserted(0);
            }else{
                this.headerViewRes = headerViewRes;
                notifyDataSetChanged();
            }
        } else {
            if (hasHeader()){
                this.hasHeader = false;
                notifyItemRemoved(0);
            }
        }
    }

    public void setFooterView(View footerViewRes) {
        if (footerViewRes != null) {
            if (!hasFooter()){
                this.footerViewRes = footerViewRes;
                this.hasFooter = true;
                if (hasHeader()){
                    notifyItemInserted(list.size()+1);
                }else{
                    notifyItemInserted(list.size());
                }
            }else{
                this.footerViewRes = footerViewRes;
                notifyDataSetChanged();
            }

        } else {
            if(hasFooter()){
                this.hasFooter = false;
                if (hasHeader()){
                    notifyItemRemoved(list.size()+1);
                }else{
                    notifyItemRemoved(list.size());
                }

            }
        }
    }

    public boolean hasHeader() {
        return hasHeader;
    }

    public boolean hasFooter() {
        return hasFooter;
    }

    public BaseRecyclerViewAdapter(List<T> list) {
        this.list = list;
    }

    public BaseRecyclerViewAdapter(List<T> list, View headerViewRes) {
        this.list = list;
        setHeaderView(headerViewRes);
    }

    public BaseRecyclerViewAdapter(List<T> list, View headerViewRes, View footerViewRes) {
        this.list = list;
        setHeaderView(headerViewRes);
        setFooterView(footerViewRes);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (hasHeader() && viewType == Item.TYPE_HEADER) {
            return new HeaderViewHolder(getHeaderView());
        } else if (hasFooter() && viewType == Item.TYPE_FOOTER) {
            return new FooterViewHolder(getFooterView());
        } else {
            return onCreateHolder(parent, viewType);
        }
    }

    public abstract RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType);

    protected abstract void onBindHeaderView(View headerView);

    protected abstract void onBindFooterView(View footerView);

    protected abstract void onBindItemView(RecyclerView.ViewHolder holder, T item, int position);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == Item.TYPE_HEADER) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            View headerView = headerHolder.itemView;

            onBindHeaderView(headerView);
        } else if (getItemViewType(position) == Item.TYPE_FOOTER) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            View footerView = footerHolder.itemView;
            onBindFooterView(footerView);
        } else {
            T i = getItemByPosition(position);
            onBindItemView(holder, i, position);
        }
    }

    protected T getItemByPosition(int position) {
        if (hasHeader()) {
            return list.get(position - 1);
        } else {
            return list.get(position);
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        count += (hasHeader() ? 1 : 0);
        count += (hasFooter() ? 1 : 0);
        count += list.size();
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int size = list.size();
        if (hasHeader()) {
            if (position == 0) {
                return Item.TYPE_HEADER;
            } else {
                if (position == size + 1 && hasFooter()) {
                    return Item.TYPE_FOOTER;
                } else {
                    return list.get(position - 1).getItemType();
                }
            }
        } else {
            if (position == size && hasFooter()) {
                return Item.TYPE_FOOTER;
            } else {
                return list.get(position).getItemType();
            }
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
