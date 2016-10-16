package acbili.shui.com.acbili;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import java.util.ArrayList;

import acbili.shui.com.adapter.AcAdapterLoreDetail;
import acbili.shui.com.base.BaseActivity;
import acbili.shui.com.bean.AcLoreDetailModel;
import acbili.shui.com.callBack.DialogCallback;
import acbili.shui.com.global.Urls;
import acbili.shui.com.utils.SystemStatusManager;
import acbili.shui.com.utils.UIUtils;
import acbili.shui.com.view.MyListView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者： max_Shui on 2016/10/4.
 * 邮箱：shuicz0505@qq.com
 * 健康分类下的每个分类的详情条目activity
 */

public class CateLoreDetailActivity extends BaseActivity {
    private String TAG=Urls.URL_LORE+"list";
    private MyListView listView;
    private Intent intent;
    private  int Id;
    private ArrayList<AcLoreDetailModel.LoreDetail> data;
    private FrameLayout fl_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_loredetail );
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
            SystemStatusManager tintManager = new SystemStatusManager( this );
            tintManager.setStatusBarTintEnabled( true );
            tintManager.setStatusBarTintColor( Color.TRANSPARENT );
        }
        listView=new MyListView( this );
        listView.setDividerHeight( UIUtils.dip2px( 10 ) );
        int padding=UIUtils.dip2px( 5 );
        listView.setPadding( padding,0,padding,padding );
        intent=getIntent();
        Id=intent.getIntExtra( "ID",0 );
        initView();
    }

    private void initView() {
            fl_container= (FrameLayout) findViewById( R.id.fl_detail_container );
            fl_container.addView( listView );
    }

    @Override
    protected void onStart() {
        super.onStart();
        OkHttpUtils.get( Urls.URL_LORE+"list" )
        .params( "id",Id )
        .tag( TAG )
        .cacheMode( CacheMode.REQUEST_FAILED_READ_CACHE )
        .cacheTime( 60*606*2 )
        .execute(  new CateLoreDetailCallback( this ));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag( TAG );
    }
    private  class  CateLoreDetailCallback extends DialogCallback<AcLoreDetailModel>
    {

        public CateLoreDetailCallback(Activity activity) {
            super( activity, AcLoreDetailModel.class );
        }

        @Override
        public void onCacheSuccess(AcLoreDetailModel acLoreDetailModel, Call call) {
            data=acLoreDetailModel.tngou;
            listView.setAdapter( new AcAdapterLoreDetail( data,Id ) );
        }

        @Override
        public void onSuccess(AcLoreDetailModel acLoreDetailModel, Call call, Response response) {
            data=acLoreDetailModel.tngou;
            listView.setAdapter( new AcAdapterLoreDetail( data,Id ) );
            /**
             * 为listview的每个item注册点击事件    这里在AcHolderLore中执行！
             * listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  ObjectAnimator animator=ObjectAnimator.ofFloat( view,"rotationY",0f,360f );
                    animator.setDuration( 1000 );
                    animator.setInterpolator( new LinearInterpolator( ) );
                    animator.start();
                }
            } );**/
        }
    }
}
