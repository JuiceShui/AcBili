package acbili.shui.com.acbili;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import acbili.shui.com.base.BaseActivity;
import acbili.shui.com.bean.AcLoreDetailModel;
import acbili.shui.com.callBack.DialogCallback;
import acbili.shui.com.global.GApp;
import acbili.shui.com.global.Urls;
import acbili.shui.com.utils.HtmlImageGetter;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者： max_Shui on 2016/10/14.
 * 邮箱：shuicz0505@qq.com
 * 健康知识的item详情页
 */

public class LoreItemDetailActivity extends BaseActivity implements View.OnClickListener {
    private Intent mIntent;
    private TextView tv_acLoreItemTitle;
    private TextView tv_acLoreItemDes;
    private TextView tv_acLoreItemContent;
    private ImageView iv_acLoreItemBack;
    private ImageView iv_acLoreItemMore;
    private ImageView iv_acLoreItemPic;
    private ImageView iv_acLoreItemRead;
    private ImageView iv_acLoreItemCollect;
    private ImageView iv_acLoreItemShare;
    private EditText ev_acLoreItemEdit;
    private long getID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout .activity_loreitem_detail);
        initView();
        initData();
        initEvent();
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        iv_acLoreItemBack.setOnClickListener( this );
        iv_acLoreItemMore.setOnClickListener( this );
    }

    @Override
    protected int setStatusBarColor() {
        return Color.argb( 255,49, 194, 124 );
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mIntent=getIntent();
        getID=mIntent.getLongExtra( "ID",0 );
    }
    /**
     * 初始化布局
     */
    private void initView() {
        tv_acLoreItemContent= (TextView) findViewById( R.id.tv_acLoreItemContent );
        tv_acLoreItemDes= (TextView) findViewById( R.id.tv_acLoreItemDes );
        tv_acLoreItemTitle= (TextView) findViewById( R.id.tv_acLoreItemTitle );
        iv_acLoreItemBack= (ImageView) findViewById( R.id.iv_acLoreItemBack );
        iv_acLoreItemCollect= (ImageView) findViewById( R.id.iv__acLoreItemCollect );
        iv_acLoreItemMore= (ImageView) findViewById( R.id.iv_acLoreItemMore );
        iv_acLoreItemRead= (ImageView) findViewById( R.id.iv__acLoreItemRead );
        iv_acLoreItemShare= (ImageView) findViewById( R.id.iv__acLoreItemShare );
        iv_acLoreItemPic= (ImageView) findViewById( R.id.iv_acLoreItemPic );
    }

    @Override
    protected void onStart() {
        super.onStart();
        OkHttpUtils.get( Urls.URL_LORE+"show" )
                .tag( this )
                .params( "id",getID )
                .cacheTime( 60*60*2 )
                .cacheMode( CacheMode.FIRST_CACHE_THEN_REQUEST )
                .execute( new LoreItemCallback( this ) );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case  R.id.iv_acLoreItemBack:
                finish();
                break;
            case R.id.iv_acLoreItemMore:
                break;
            case R.id.iv__acLoreItemCollect:
                break;
            case R.id.iv__acLoreItemShare:
                break;
            case R.id.iv__acLoreItemRead:
                break;
        }
    }

    class LoreItemCallback extends DialogCallback<AcLoreDetailModel.LoreDetail>
    {

        public LoreItemCallback(Activity activity) {
            super( activity, AcLoreDetailModel.LoreDetail.class );
        }

        @Override
        public void onSuccess(AcLoreDetailModel.LoreDetail loreDetail, Call call, Response response) {
            HtmlImageGetter imageGetter=new HtmlImageGetter( tv_acLoreItemContent );
            Spanned spanned=Html.fromHtml( loreDetail.message,imageGetter,null );
            tv_acLoreItemTitle.setText( loreDetail.title );
            tv_acLoreItemDes.setText( loreDetail.description );
            tv_acLoreItemContent.setText( spanned );
            Glide.with( LoreItemDetailActivity.this )
                    .load( GApp.ImgHeader+loreDetail.img )
                    .placeholder( R.drawable.dogeload )
                    .into( iv_acLoreItemPic );
        }
    }
}
