package acbili.shui.com.acbili;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import acbili.shui.com.base.BaseActivity;
import acbili.shui.com.bean.NewsModel;
import acbili.shui.com.callBack.DialogCallback;
import acbili.shui.com.global.GApp;
import acbili.shui.com.global.Urls;
import acbili.shui.com.utils.UIUtils;
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
            HtmlImageGetter imageGetter=new HtmlImageGetter();
            Spanned spanned = Html.fromHtml(newsContent.message, imageGetter, null);
            tv_NewsDetailContent.setText(spanned);
        }
    }

    /**
     * Imagegetter的复写
     */
    class HtmlImageGetter implements Html.ImageGetter {

        /**
         * 获取图片
         */
        @Override
        public Drawable getDrawable(String source) {
            LevelListDrawable d = new LevelListDrawable();
            Drawable empty = getResources().getDrawable(
                    R.drawable.dogeload);//默认图片
            d.addLevel(0, 0, empty);//默认图片为第一级图片
            d.setBounds(0, 0, UIUtils.getScreenSize( NewsDetailActivity.this )[0],
                    empty.getIntrinsicHeight());
            new LoadImage().execute(source, d);
            return d;
        }

        /**
         * 异步下载图片类
         *
         * @author Ruffian
         * @date 2016年1月15日
         *
         */
        class LoadImage extends AsyncTask<Object, Void, Bitmap> {

            private LevelListDrawable mDrawable;

            @Override
            protected Bitmap doInBackground(Object... params) {
                String source = (String) params[0];
                mDrawable = (LevelListDrawable) params[1];
                try {
                    InputStream is = new URL(source).openStream();
                    return BitmapFactory.decodeStream(is);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            /**
             * 图片下载完成后执行
             */
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    BitmapDrawable d = new BitmapDrawable(bitmap);
                    mDrawable.addLevel(1, 1, d);//加载后的图片设置为第二级图片
                    /**
                     * 适配图片大小 <br/>
                     * 默认大小：bitmap.getWidth(), bitmap.getHeight()<br/>
                     * 适配屏幕：getDrawableAdapter
                     */
                    mDrawable = getDrawableAdapter(NewsDetailActivity.this, mDrawable,
                            bitmap.getWidth(), bitmap.getHeight());

                    // mDrawable.setBounds(0, 0, bitmap.getWidth(),
                    // bitmap.getHeight());

                    mDrawable.setLevel(1);//加载成功后 设置展示的图片为第二级图片

                    /**
                     * 图片下载完成之后重新赋值textView<br/>
                     * tv_NewsDetailContent:项目中使用的textView
                     *
                     */
                    tv_NewsDetailContent.invalidate();//重绘
                    CharSequence t = tv_NewsDetailContent.getText();
                    tv_NewsDetailContent.setText(t);

                }
            }

            /**
             * 加载网络图片,适配大小
             *
             * @param context
             * @param drawable
             * @param oldWidth
             * @param oldHeight
             * @return
             * @author Ruffian
             * @date 2016年1月15日
             */
            public LevelListDrawable getDrawableAdapter(Context context,
                                                        LevelListDrawable drawable, int oldWidth, int oldHeight) {
                LevelListDrawable newDrawable = drawable;
                long newHeight = 0;// 未知数
                int newWidth = UIUtils.getScreenSize(UIUtils.getContext())[0];// 默认屏幕宽
                newHeight = (newWidth * oldHeight) / oldWidth;
                // LogUtils.w("oldWidth:" + oldWidth + "oldHeight:" +
                // oldHeight);
                // LogUtils.w("newHeight:" + newHeight + "newWidth:" +
                // newWidth);
                newDrawable.setBounds(0, 0, newWidth, (int) newHeight);
                return newDrawable;
            }
        }

    }
}
