package acbili.shui.com.acbili;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import acbili.shui.com.adapter.AcAdapterViewpager;
import acbili.shui.com.animation.MyTransformation;
import acbili.shui.com.base.BaseActivity;
import acbili.shui.com.bean.AcPicViewpagerModel;
import acbili.shui.com.callBack.DialogCallback;
import acbili.shui.com.global.Urls;
import acbili.shui.com.utils.SystemStatusManager;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者： max_Shui on 2016/9/28.
 * 邮箱：shuicz0505@qq.com
 * 查看图片的活动
 */
public class ViewPagerActivity extends BaseActivity {
    public RelativeLayout rl_viewpager_container;
    private ViewPager mViewPager;
    private TextView mTextView;
    private Intent intent;
    private int mId;
    private String mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_viewpager );
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
        initView();
    }

    private void initView() {
        intent=getIntent();
        mTitle = intent.getStringExtra("title");
        mId = intent.getIntExtra("id", 935);
        mViewPager = (ViewPager) findViewById(R.id.vp_activityviewpager);
        mTextView = (TextView) findViewById(R.id.tv_activityviewpager_title);
        rl_viewpager_container=(RelativeLayout) findViewById(R.id.rl_activityviewpager_container);
        mTextView.setBackgroundColor(Color.BLACK);
        mTextView.getBackground().setAlpha(128);
    }

    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //根据 Tag 取消请求
        OkHttpUtils.getInstance().cancelTag(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        OkHttpUtils.get( Urls.URL_IMG+"show" )
                .params( "id",mId )
                .tag( this )
                .cacheMode( CacheMode.FIRST_CACHE_THEN_REQUEST )
                .cacheTime( 60*60*2 )
                .execute( new ViewpagerPicCallback( this ) );
    }
    private  class  ViewpagerPicCallback extends DialogCallback<AcPicViewpagerModel>
    {

        public ViewpagerPicCallback(Activity activity) {
            super( activity, AcPicViewpagerModel.class );
        }

        @Override
        public void onSuccess(AcPicViewpagerModel acPicViewpagerModel, Call call, Response response) {
                mTextView.setText( mTitle );
                 mViewPager.setPageTransformer(true, new MyTransformation());
                 mViewPager.setAdapter( new AcAdapterViewpager( acPicViewpagerModel.list,ViewPagerActivity.this ) );
        }
    }
}
