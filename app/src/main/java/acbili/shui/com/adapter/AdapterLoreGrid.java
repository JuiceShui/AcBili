package acbili.shui.com.adapter;

import java.util.ArrayList;

import acbili.shui.com.base.BaseHolder;
import acbili.shui.com.base.MyBaseAdapter;
import acbili.shui.com.bean.LoreGridModel;
import acbili.shui.com.holder.HolderHealGrid;

/**
 * 作者： max_Shui on 2016/9/28.
 * 邮箱：shuicz0505@qq.com
 * 健康知识分类的适配器
 */

public class AdapterLoreGrid extends MyBaseAdapter<LoreGridModel.LoreGrid>{
    public AdapterLoreGrid(ArrayList<LoreGridModel.LoreGrid> data) {
        super( data );
    }

    @Override
    public ArrayList<LoreGridModel.LoreGrid> onLoadMore() {
        return null;
    }

    @Override
    public BaseHolder<LoreGridModel.LoreGrid> getNormalHolder(int position) {
        return new HolderHealGrid();
    }

    @Override
    public BaseHolder<LoreGridModel.LoreGrid> getSpecialHolder(int position) {
        return null;
    }

    @Override
    public boolean CheckType(LoreGridModel.LoreGrid loreGrid) {
        return false;
    }

    @Override
    public boolean hasMore() {
        return false;
    }
}
