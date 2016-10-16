package acbili.shui.com.fragmentSubNews;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import acbili.shui.com.acbili.R;
import acbili.shui.com.adapter.AdapterFragmentNews;
import acbili.shui.com.base.BaseFragment;
import acbili.shui.com.bean.NewsModel;
import acbili.shui.com.callBack.DialogCallback;
import acbili.shui.com.global.Urls;
import acbili.shui.com.utils.SystemStatusManager;
import acbili.shui.com.view.MyListView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者： max_Shui on 2016/9/28.
 * 邮箱：shuicz0505@qq.com
 * 最新新闻 第一页
 */

public class FragmentNews extends BaseFragment{
    private String TAG=Urls.URL_TOP+"list";
    private  MyListView listView;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listView=new MyListView( getActivity());
        listView.setOnScrollListener( new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                changeAlpha(listViewScorllY());
            }
        } );
        return listView;
    }
    @Override
    protected void initData() {
        OkHttpUtils.get( Urls.URL_TOP+"list" )
                .headers("header1", "headerValue1")//
                .params( "page","2" )
                .cacheTime( 60*60*2 )
                .cacheMode( CacheMode.REQUEST_FAILED_READ_CACHE )
                .tag( TAG )
                .execute( new FragmentNewsCallback( getActivity() ) );
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag( TAG );

    }
    protected void onInvisible() {
        super.onInvisible();
        OkHttpUtils.getInstance().cancelTag( TAG );
    }
    @Override
    public void onDetach() {
        super.onDetach();
        OkHttpUtils.getInstance().cancelTag( TAG );

    }
    private class FragmentNewsCallback extends DialogCallback<NewsModel>
    {

        public FragmentNewsCallback(Activity activity) {
            super( activity, NewsModel.class );
        }

        @Override
        public void onSuccess(NewsModel newsModel, Call call, Response response) {
            listView.setAdapter( new AdapterFragmentNews( newsModel.tngou ) );
        }

        @Override
        public void onCacheSuccess(NewsModel newsModel, Call call) {
            super.onCacheSuccess( newsModel, call );
            listView.setAdapter( new AdapterFragmentNews( newsModel.tngou ) );
        }
    }
    /**
     * 获取listview的滑动高度
     * @return
     */
    private int listViewScorllY(){
        View view = listView.getChildAt(0);
        if (view== null) {
            return 0;
        }
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int top = view.getTop();
        return -top + firstVisiblePosition * view.getHeight() ;
    }
    //根据滑动改变alpha
    protected void changeAlpha(int ScrollY) {


        float alpha = 1f / 383;

        float a = ScrollY * alpha;
        getActivity().findViewById( R.id.tv_mainactivity_title).setAlpha(a);
        float aa=(ScrollY*alpha)*255;
        int aaa=(int) aa;
        if (aaa>255) {
            aaa=255;
        }
        int color= Color.argb(aaa,49, 194, 124);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor( color );

        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0使用三方工具类
            getActivity().getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
            SystemStatusManager tintManager = new SystemStatusManager( getActivity() );
            tintManager.setStatusBarTintEnabled( true );
            tintManager.setStatusBarTintColor( color );
        }
    }
}
