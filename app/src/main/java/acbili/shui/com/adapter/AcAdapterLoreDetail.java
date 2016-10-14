package acbili.shui.com.adapter;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;

import acbili.shui.com.base.BaseHolder;
import acbili.shui.com.base.BaseHttp;
import acbili.shui.com.base.MyBaseAdapter;
import acbili.shui.com.bean.AcLoreDetailModel;
import acbili.shui.com.holder.AcHolderLore;

/**
 * 作者： max_Shui on 2016/10/6.
 * 邮箱：shuicz0505@qq.com
 */

public class AcAdapterLoreDetail extends MyBaseAdapter<AcLoreDetailModel.LoreDetail> {
    private ProtocolLoreDetail protocol;
    private AcLoreDetailModel model;
    private ArrayList<AcLoreDetailModel.LoreDetail> mData;
    private int id;
    public AcAdapterLoreDetail(ArrayList<AcLoreDetailModel.LoreDetail> data,int id) {
        super( data );
        this.id=id;
        protocol=new ProtocolLoreDetail();
    }

    @Override
    public ArrayList<AcLoreDetailModel.LoreDetail> onLoadMore() {
        mData=protocol.getData( ((getCount()-1)/20)+1  );
        return mData;
    }

    @Override
    public BaseHolder<AcLoreDetailModel.LoreDetail> getNormalHolder(int position) {
        return new AcHolderLore(  );
    }

    @Override
    public BaseHolder<AcLoreDetailModel.LoreDetail> getSpecialHolder(int position) {
        return null;
    }

    @Override
    public boolean CheckType(AcLoreDetailModel.LoreDetail loreDetail) {
        return false;
    }
    private  class  ProtocolLoreDetail extends BaseHttp<ArrayList<AcLoreDetailModel.LoreDetail>>
    {
        @Override
        protected int getPage() {
            return 1;
        }

        @Override
        public String getKey() {
            return "/api/lore/list?id="+id;
        }

        @Override
        public String getParams() {
            return "";
        }

        @Override
        public ArrayList<AcLoreDetailModel.LoreDetail> parseData(String data) {
            if (!TextUtils.isEmpty( data ))
            {
                model=new Gson().fromJson( data,AcLoreDetailModel.class );
                return model.tngou;

            }

            return null;
        }
    }
}
