package acbili.shui.com.adapter;

import java.util.ArrayList;

import acbili.shui.com.base.BaseHolder;
import acbili.shui.com.base.MyBaseAdapter;
import acbili.shui.com.bean.DrugModel;
import acbili.shui.com.holder.HolderDrug;

/**
 * 作者： max_Shui on 2016/9/28.
 * 邮箱：shuicz0505@qq.com
 * 药品的适配器
 */

public class AdapterDrug extends MyBaseAdapter<DrugModel.Drug> {
    public AdapterDrug(ArrayList<DrugModel.Drug> data) {
        super( data );
    }

    @Override
    public ArrayList<DrugModel.Drug> onLoadMore() {
        return null;
    }

    @Override
    public BaseHolder<DrugModel.Drug> getNormalHolder(int position) {
        return new HolderDrug();
    }

    @Override
    public BaseHolder<DrugModel.Drug> getSpecialHolder(int position) {
        return null;
    }

    @Override
    public boolean CheckType(DrugModel.Drug drug) {
        return false;
    }

    @Override
    public boolean hasMore() {
        return false;
    }
}
