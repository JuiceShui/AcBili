package acbili.shui.com.acbili;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import acbili.shui.com.base.BaseActivity;
import acbili.shui.com.bean.NewsModel;
import acbili.shui.com.callBack.DialogCallback;
import acbili.shui.com.global.GApp;
import acbili.shui.com.global.Urls;
import acbili.shui.com.utils.HtmlImageGetter;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者： max_Shui on 2016/10/13.
 * 邮箱：shuicz0505@qq.com
 * 非webview的新闻详情页
 */

public class NewsDetailActivity extends BaseActivity {
    private Intent mIntent;
    private ImageView iv_NewsDetailPic;
    private TextView tv_NewsDetailTitle;
    private TextView tv_NewsDetailDes;
    private TextView tv_NewsDetailContent;
    private int ID;
    private  static Drawable drawable =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_news_detail );
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mIntent=getIntent();
        ID=mIntent.getIntExtra( "ID",0 );
    }

    /**
     * 初始化布局
     */
    private void initView() {
        iv_NewsDetailPic= (ImageView) findViewById( R.id.iv_newDetailPic );
        tv_NewsDetailContent= (TextView) findViewById( R.id.tv_newsDetailContent );
        tv_NewsDetailDes= (TextView) findViewById( R.id.tv_newsDetailDes );
        tv_NewsDetailTitle= (TextView) findViewById( R.id.tv_newsDetailTitle );
    }
    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        OkHttpUtils.get( Urls.URL_TOP+"show" )
                .params( "id",ID )
                .cacheMode( CacheMode.FIRST_CACHE_THEN_REQUEST )
                .cacheTime( 60*60*2 )
                .tag( this )
                .execute( new NewsDetailCallback( this ) );
                ;
    }
    private  class  NewsDetailCallback extends DialogCallback<NewsModel.NewsContent>
    {

        public NewsDetailCallback(Activity activity) {
            super( activity, NewsModel.NewsContent.class );
        }

        @Override
        public void onSuccess(NewsModel.NewsContent newsContent, Call call, Response response) {
                tv_NewsDetailTitle.setText( newsContent.title );
                tv_NewsDetailDes.setText( newsContent.description );
                Glide.with( NewsDetailActivity.this )
                    .load( GApp.ImgHeader+newsContent.img )
                        .placeholder( R.drawable.dogeload )
                    .into( iv_NewsDetailPic );
            HtmlImageGetter imageGetter=new HtmlImageGetter(tv_NewsDetailContent);
            Spanned spanned = Html.fromHtml(newsContent.message, imageGetter, null);
            tv_NewsDetailContent.setText(spanned);
        }
    }

}
