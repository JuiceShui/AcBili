package acbili.shui.com.fragmentSubMax;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import acbili.shui.com.adapter.AdapterCook;
import acbili.shui.com.base.BaseFragment;
import acbili.shui.com.bean.CookModel;
import acbili.shui.com.callBack.DialogCallback;
import acbili.shui.com.global.Urls;
import acbili.shui.com.view.MyListView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者： max_Shui on 2016/9/26.
 * 邮箱：shuicz0505@qq.com
 * 菜谱页
 */

public class FragmentCook extends BaseFragment {
    private String TAG=Urls.URL_COOK+"list";
    private MyListView listView;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       listView=new MyListView( getActivity() );
        listView.setDividerHeight( 8 );
        return listView;
    }
    @Override
    protected void initData() {
        OkHttpUtils.get( Urls.URL_COOK+"list" )
                .tag( TAG )
                .headers("header1", "headerValue1")//
                .cacheMode( CacheMode.REQUEST_FAILED_READ_CACHE )
                .cacheTime( 60*60*2 )
                .params( "page","1" )
                .execute( new CookCallBack( getActivity() ) );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag( TAG );
    }

    @Override
    public void onDetach() {
        super.onDetach();
        OkHttpUtils.getInstance().cancelTag( TAG );
    }
    protected void onInvisible() {
        super.onInvisible();
        OkHttpUtils.getInstance().cancelTag( TAG );
    }

    private  class  CookCallBack extends DialogCallback<CookModel>
    {

        public CookCallBack(Activity activity) {
            super( activity, CookModel.class );
        }

        @Override
        public void onSuccess(CookModel cookModel, Call call, Response response) {
            listView.setAdapter( new AdapterCook( cookModel.tngou ) );
        }

        @Override
        public void onCacheSuccess(CookModel cookModel, Call call) {
            super.onCacheSuccess( cookModel, call );
            listView.setAdapter( new AdapterCook( cookModel.tngou ) );
        }
    }
}
