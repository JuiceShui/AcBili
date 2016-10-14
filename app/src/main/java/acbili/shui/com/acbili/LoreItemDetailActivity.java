package acbili.shui.com.acbili;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import acbili.shui.com.base.BaseActivity;
import acbili.shui.com.bean.AcLoreDetailModel;
import acbili.shui.com.callBack.DialogCallback;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者： max_Shui on 2016/10/14.
 * 邮箱：shuicz0505@qq.com
 * 健康知识的item详情页
 */

public class LoreItemDetailActivity extends BaseActivity{
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
    private int getID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout .activity_loreitem_detail);
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mIntent=getIntent();
        getID=mIntent.getIntExtra( "ID",0 );
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
    protected boolean translucentStatusBar() {
        return true;
    }
    class LoreItemCallback extends DialogCallback<AcLoreDetailModel.LoreDetail>
    {

        public LoreItemCallback(Activity activity) {
            super( activity, AcLoreDetailModel.LoreDetail.class );
        }

        @Override
        public void onSuccess(AcLoreDetailModel.LoreDetail loreDetail, Call call, Response response) {
            tv_acLoreItemTitle.setText( loreDetail.title );
            tv_acLoreItemDes.setText( loreDetail.description );
            tv_acLoreItemContent.setText( Html.fromHtml( loreDetail.message) );
        }
    }
}
