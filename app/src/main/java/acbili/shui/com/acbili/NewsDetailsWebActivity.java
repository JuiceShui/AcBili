package acbili.shui.com.acbili;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import acbili.shui.com.base.BaseActivity;
import acbili.shui.com.bean.NewsModel;
import acbili.shui.com.callBack.DialogCallback;
import acbili.shui.com.global.Urls;
import okhttp3.Call;
import okhttp3.Response;


public class NewsDetailsWebActivity extends BaseActivity implements OnClickListener
{
	private WebView webView;
	private TextView tv_newsDetailTitle;
	private ImageView iv_back;
	private ProgressBar pb_loading;
	private ImageButton btn_share;
	private Intent mIntent;
	private String mTitle;
	private String mData;
	private int mId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_details_webview );
		initView();
		initData();
	}

	private void initData() {
		mIntent = getIntent();
		mId = mIntent.getIntExtra("id", 0);
		tv_newsDetailTitle.setText( "新闻详情" );
		System.out.println("传递hou：：：：：：：：：："+mId);
		mTitle = mIntent.getStringExtra("title");
		btn_share.setOnClickListener(this);
		iv_back.setOnClickListener( this );
	}

	@Override
	protected void onStart() {
		super.onStart();
		loadData();

	}

	@Override
	protected int setStatusBarColor() {
		return Color.argb( 255,49, 194, 124 );
	}

	private void loadData() {
		OkHttpUtils.get( Urls.URL_TOP+"show" )
				.cacheMode( CacheMode.FIRST_CACHE_THEN_REQUEST )
				.cacheTime( 60*60*2 )
				.tag( this )
				.params( "id" ,mId)
				.execute( new NewsDetailCallback( this ) );
				;
		/*Thread tt=new Thread(new Runnable()
		{
			@Override
			public void run() {
					try {

						mData = protocol.get(intId);
					} catch (Exception e) {
						e.printStackTrace();
					}
					cont=protocol.parseData(mData);
				  UIUtils.runOnUIThread( new Runnable()
				{
					@Override
					public void run() {
						mData=cont.message;
						tv_newsDetailTitle.setText(mTitle);
						//下面的这个方法不能加载图片  只是转化为纯文本
						//webView.loadDataWithBaseURL(null,Html.fromHtml(mData)+"", "text/html",  "utf-8", null);
						webView.loadDataWithBaseURL(null,mData+"", "text/html",  "utf-8", null);
					}
				});
			}
		});
     		tt.start();
     	try {
     		tt.join();
     	} catch (InterruptedException e) {

     		e.printStackTrace();
     	}
     	System.out.println("这里继续");	*/
}


	//webView.loadDataWithBaseURL(null,Html.fromHtml(mData)+"", "text/html",  "utf-8", null);
	private void initView() {
		webView = (WebView) findViewById(R.id.wv_news_detail);
		tv_newsDetailTitle = (TextView) findViewById(R.id.tv_news_detail_title);
		pb_loading = (ProgressBar) findViewById(R.id.pb_wvloading);
		btn_share = (ImageButton) findViewById(R.id.btn_news_details_share);
		iv_back= (ImageView) findViewById( R.id.iv_back );
	}
	@Override
	public void onClick(View v) {
		//showShare();
		switch (v.getId())
		{
			case R.id.iv_back:
				finish();
				break;
			case R.id.btn_news_details_share:
				//分享事件
				break;
			default:
				break;
		}
		
	}
	/**
	 * 分享
	 *//*
	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// oks.setTheme(OnekeyShareTheme.SKYBLUE);//設置分享的主題
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		oks.setText("我是分享文本");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://sharesdk.cn");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://sharesdk.cn");
		// 启动分享GUI
		oks.show(this);
	}*/
	private  class NewsDetailCallback extends DialogCallback<NewsModel.NewsContent>
	{

		public NewsDetailCallback(Activity activity) {
			super( activity, NewsModel.NewsContent.class );
		}

		@Override
		public void onSuccess(NewsModel.NewsContent newsContent, Call call, Response response) {
			mData=newsContent.message;
		//	tv_newsDetailTitle.setText(mTitle);
			//下面的这个方法不能加载图片  只是转化为纯文本
			//webView.loadDataWithBaseURL(null,Html.fromHtml(mData)+"", "text/html",  "utf-8", null);
			webView.loadDataWithBaseURL(null,mData+"", "text/html",  "utf-8", null);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		OkHttpUtils.getInstance().cancelTag( this );
	}

}
