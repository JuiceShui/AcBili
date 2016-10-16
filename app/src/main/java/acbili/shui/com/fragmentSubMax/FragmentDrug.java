package acbili.shui.com.fragmentSubMax;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import acbili.shui.com.adapter.AdapterDrug;
import acbili.shui.com.base.BaseFragment;
import acbili.shui.com.bean.DrugModel;
import acbili.shui.com.callBack.DialogCallback;
import acbili.shui.com.global.Urls;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者： max_Shui on 2016/9/26.
 * 邮箱：shuicz0505@qq.com
 * 药品页
 */

public class FragmentDrug extends BaseFragment {
    private ListView listView;
    private String TAG= Urls.URL_DRUG+"list";
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listView=new ListView( getActivity() );
        return listView;
    }
    @Override
    protected void initData() {
        OkHttpUtils.get( Urls.URL_DRUG+"list" )
                .tag( TAG )
                .cacheMode( CacheMode.FIRST_CACHE_THEN_REQUEST )
                .cacheTime( 60*60*2 )
                .params( "page","1" )
                .execute( new DrugCallback( getActivity() ) );
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
    private  class  DrugCallback extends DialogCallback<DrugModel>
    {

        public DrugCallback(Activity activity) {
            super( activity, DrugModel.class );
        }

        @Override
        public void onSuccess(DrugModel drugModel, Call call, Response response) {
            listView.setAdapter( new AdapterDrug( drugModel.tngou ) );
        }

        @Override
        public void onCacheSuccess(DrugModel drugModel, Call call) {
            super.onCacheSuccess( drugModel, call );
            listView.setAdapter( new AdapterDrug( drugModel.tngou ) );
        }
    }
}
