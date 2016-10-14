package acbili.shui.com.adapter;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;

import acbili.shui.com.base.BaseHolder;
import acbili.shui.com.base.BaseHttp;
import acbili.shui.com.base.MyBaseAdapter;
import acbili.shui.com.bean.NewsModel;
import acbili.shui.com.holder.HolderFragmentNews;

/**
 * 作者： max_Shui on 2016/9/28.
 * 邮箱：shuicz0505@qq.com
 */

public class AdapterFragmentNews extends MyBaseAdapter<NewsModel.NewsContent> {
    private  NewsModel model;
    private  ArrayList<NewsModel.NewsContent> mData;
    private  ProtocolFragmentNews protocol;
    public AdapterFragmentNews(ArrayList<NewsModel.NewsContent> data) {
        super( data );
        protocol=new ProtocolFragmentNews();
    }

    @Override
    public ArrayList<NewsModel.NewsContent> onLoadMore() {
        mData=protocol.getData( ((getCount()-1)/20)+1  );
        return mData;
    }

    @Override
    public BaseHolder<NewsModel.NewsContent> getNormalHolder(int position) {
        return new HolderFragmentNews();
    }

    @Override
    public BaseHolder<NewsModel.NewsContent> getSpecialHolder(int position) {
        return null;
    }

    @Override
    public boolean CheckType(NewsModel.NewsContent newsContent) {
        return false;
    }
    private  class  ProtocolFragmentNews extends BaseHttp<ArrayList<NewsModel.NewsContent>>
    {

        @Override
        public String getKey() {
            return "/api/top/list";
        }

        @Override
        public String getParams() {
            return "";
        }

        @Override
        public ArrayList<NewsModel.NewsContent> parseData(String data) {
            if (!TextUtils.isEmpty( data ))
            {
                model=new Gson().fromJson( data,NewsModel.class );
                return model.tngou;
            }
            return null;
        }
    }
}
