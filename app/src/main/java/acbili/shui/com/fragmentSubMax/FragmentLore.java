package acbili.shui.com.fragmentSubMax;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import acbili.shui.com.adapter.AdapterLoreGrid;
import acbili.shui.com.base.BaseFragment;
import acbili.shui.com.bean.LoreGridModel;
import acbili.shui.com.callBack.DialogCallback;
import acbili.shui.com.global.Urls;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者： max_Shui on 2016/9/26.
 * 邮箱：shuicz0505@qq.com
 */

public class FragmentLore extends BaseFragment {
    private String TAG=Urls.URL_LORE+"classify" ;
    private GridView gridView;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        gridView=new GridView( getActivity() );
        gridView.setNumColumns(3);
        gridView.setVerticalSpacing(10);
        gridView.setHorizontalSpacing(8);
        return gridView;
    }
    @Override
    protected void initData() {
        OkHttpUtils.get( Urls.URL_LORE+"classify" )
                .cacheTime( 60*60*2 )
                .headers("header1", "headerValue1")//
                .cacheMode( CacheMode.REQUEST_FAILED_READ_CACHE )
                .tag( TAG )
                .execute( new LoreGridCallback( getActivity()) );
    }

    private class LoreGridCallback extends DialogCallback<LoreGridModel>
    {

        public LoreGridCallback(Activity activity) {
            super( activity, LoreGridModel.class );
        }

        @Override
        public void onSuccess(LoreGridModel loreGridModel, Call call, Response response) {
            gridView.setAdapter( new AdapterLoreGrid( loreGridModel.tngou ) );
        }

        @Override
        public void onCacheSuccess(LoreGridModel loreGridModel, Call call) {
            super.onCacheSuccess( loreGridModel, call );
            gridView.setAdapter( new AdapterLoreGrid( loreGridModel.tngou ) );
        }
    }
}
