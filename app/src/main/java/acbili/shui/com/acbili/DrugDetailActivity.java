package acbili.shui.com.acbili;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;

import acbili.shui.com.base.BaseActivity;
import acbili.shui.com.bean.AcDrugDetailModel;
import acbili.shui.com.callBack.DialogCallback;
import acbili.shui.com.global.GApp;
import acbili.shui.com.global.Urls;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者： max_Shui on 2016/10/11.
 * 邮箱：shuicz0505@qq.com
 * 单个药品的详细信息
 */

public class DrugDetailActivity extends BaseActivity {
    private String TAG=Urls.URL_DRUG+"show";
    private  int mId;
    private Intent mIntent;
    private RelativeLayout rl_imgAndInfo;//包含图像 价格 名称的布局
    private ImageView iv_Img;//图片
    private TextView tv_name;//药品名称
    private TextView tv_price;//药品价格
    private TextView tv_code;//条形码
    private  TextView tv_number;//国药准字
    private TextView tv_message;//信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_drug_detail );
        mIntent=getIntent();
        mId=mIntent.getIntExtra( "ID" ,0);
        //Toast.makeText(DrugDetailActivity. this,mIntent.getIntExtra( "ID" ,0)+"", Toast.LENGTH_SHORT ).show();
        InitView();
        InitData();
    }

    /**
     * 初始化数据
     */
    private void InitData() {
        OkHttpUtils.get( Urls.URL_DRUG+"show")
        .cacheMode( CacheMode.REQUEST_FAILED_READ_CACHE )
        .cacheTime( 60*60*2 )
        .tag( TAG )
        .params( "id",mId )
        .execute( new DrugDetailCallback( this ) );
    }
    /**
     * 初始化布局
     */
    private void InitView() {
        rl_imgAndInfo= (RelativeLayout) findViewById( R.id.rl_acDrugDetail_title );
        iv_Img= (ImageView) findViewById( R.id.iv_acDrugDetail );
        tv_name= (TextView) findViewById( R.id.tv_acDrugDetail_name );
        tv_price= (TextView) findViewById( R.id.tv_acDrugDetail_price );
        tv_code= (TextView) findViewById( R.id.tv_acDrugDetail_code );
        tv_number= (TextView) findViewById( R.id.tv_acDrugDetail_number );
        tv_message= (TextView) findViewById( R.id.tv_acDrugDetail_message );
    }

    @Override
    protected boolean translucentStatusBar() {
        return true;
    }
    private class  DrugDetailCallback extends DialogCallback<AcDrugDetailModel>
    {

        public DrugDetailCallback(Activity activity) {
            super( activity,AcDrugDetailModel.class );
        }

        @Override
        public void onCacheSuccess(AcDrugDetailModel acDrugDetailModel, Call call) {
            LayoutData( acDrugDetailModel );
        }

        @Override
        public void onSuccess(AcDrugDetailModel acDrugDetailModel, Call call, Response response) {
            LayoutData( acDrugDetailModel );
        }
        private void LayoutData(AcDrugDetailModel acDrugDetailModel)
        {
            AcDrugDetailModel model=new AcDrugDetailModel();
            StringBuilder sb=new StringBuilder(  );
            StringBuilder sb2=new StringBuilder(  );
            Glide.with( DrugDetailActivity.this )
                    .load( GApp.ImgHeader+ acDrugDetailModel.img)
                    .into( iv_Img );
            tv_message.setText( Html.fromHtml( acDrugDetailModel.message ) );
            tv_name.setText( "NAME:"+acDrugDetailModel.name );
            tv_price.setText( "Price:"+acDrugDetailModel.price );
            if (acDrugDetailModel.codes!=null&&acDrugDetailModel.codes.size()>1)
            {
                tv_code.setVisibility( View.VISIBLE );
                for (int i=0;i<acDrugDetailModel.codes.size();i++)
                {
                    AcDrugDetailModel.code  c=model.new code();
                    c=acDrugDetailModel.codes.get( i );
                    sb.append( i+1+":厂商："+c.factory+"\n");
                    sb.append( "条形码："+c.code );
                    sb.append( "\n" );
                }
                tv_code.setText( sb.toString() );
            }
            if (acDrugDetailModel.numbers!=null&&acDrugDetailModel.numbers.size()>1)
            {
                tv_number.setVisibility( View.VISIBLE );
                for (int i=0;i<acDrugDetailModel.numbers.size();i++)
                {
                    AcDrugDetailModel.number  c=model.new number();
                    c=acDrugDetailModel.numbers.get( i );
                    sb2.append( i+1+":厂商："+c.factory+"\n");
                    sb2.append( "条形码："+c.code );
                    sb2.append( "\n" );
                }
                tv_number.setText( sb2.toString() );
            }
        }

    }
}
