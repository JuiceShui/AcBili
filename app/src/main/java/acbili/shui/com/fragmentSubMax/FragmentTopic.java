package acbili.shui.com.fragmentSubMax;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import acbili.shui.com.adapter.AdapterTopNews;
import acbili.shui.com.base.BaseFragment;
import acbili.shui.com.bean.HeaderMoldel;
import acbili.shui.com.bean.NewsModel;
import acbili.shui.com.callBack.DialogCallback;
import acbili.shui.com.global.Urls;
import acbili.shui.com.holder.HolderListViewHeader;
import acbili.shui.com.view.MyListView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者： max_Shui on 2016/9/26.
 * 邮箱：shuicz0505@qq.com
 */

public class FragmentTopic extends BaseFragment {
    private  String TAG="BaseFragment";
    private MyListView listView;
    private  HolderListViewHeader holderListViewHeader;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listView= new MyListView( getActivity() );
        holderListViewHeader= new HolderListViewHeader();
        listView.addHeaderView(holderListViewHeader.getView() );
        listView.setDividerHeight( 8 );
        return listView;
    }
    @Override
    protected void initData() {
        OkHttpUtils.get( Urls.URL_TOP+"list")//
                .tag(Urls.URL_TOP+"list")//
                .headers("header1", "headerValue1")//
                .cacheMode( CacheMode.REQUEST_FAILED_READ_CACHE)
                .cacheTime(60*60*2)
                .params("page", "1")//
                .execute(new TopNewsCallback( getActivity() ));
        OkHttpUtils.get( Urls.URL_IMG+"list")//
                .tag(Urls.URL_IMG+"list")//
                .headers("header1", "headerValue1")//
                .cacheMode( CacheMode.REQUEST_FAILED_READ_CACHE)
                .cacheTime(60*60*2)
                .params("page", "1")//
                .params( "row","6" )
                .execute(new PicCallBack( getActivity() ));
    }
    private class TopNewsCallback extends DialogCallback<NewsModel> {

        public TopNewsCallback(Activity activity) {
            super(activity, NewsModel.class);
        }

        @Override
        public void onSuccess(NewsModel requestInfo, Call call, Response response) {

            // view2.setText("请求成功  是否来自缓存：false  请求方式：" + call.request().method() + "\n" + "url：" + call.request().url());
            listView.setAdapter( new AdapterTopNews( requestInfo.tngou ) );
        }
        @Override
        public void onCacheSuccess(NewsModel requestInfo, Call call) {
            System.out.println("缓存请求成功");
            listView.setAdapter( new AdapterTopNews( requestInfo.tngou ) );
        }

        @Override
        public void onError(Call call, Response response, Exception e) {
            super.onError(call, response, e);
        }

        @Override
        public void onCacheError(Call call, Exception e) {
            System.out.println("缓存请求失败");
        }
    }
    class PicCallBack extends  DialogCallback<HeaderMoldel>
    {

        public PicCallBack(Activity activity) {
            super( activity, HeaderMoldel.class );
        }

        @Override
        public void onSuccess(HeaderMoldel headerMoldel, Call call, Response response) {
            holderListViewHeader.setData( headerMoldel.tngou );
        }

        @Override
        public void onCacheSuccess(HeaderMoldel headerMoldel, Call call) {
            holderListViewHeader.setData( headerMoldel.tngou );
        }
    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
        OkHttpUtils.getInstance().cancelTag( TAG );
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged( hidden );
        if (!hidden)
        {

        }
    }
}
