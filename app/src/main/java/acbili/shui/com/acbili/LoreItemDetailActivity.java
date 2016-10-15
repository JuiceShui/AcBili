package acbili.shui.com.acbili;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
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
    private TextView tv_acLoreItemEdit;
    private Dialog mDialog;//对话框
    private View dialogView;//对话框的布局
    private long getID;
    private CheckBox cb_dialog_if_shareToWeibo;//单选框 选择是否分享到微博
    private TextView tv_dialog_text_count;//分享到微博的字数
    private TextView tv_dialog_comfirToSend;//确认发送评论
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
        tv_acLoreItemEdit.setOnClickListener( this );
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
        tv_acLoreItemEdit= (TextView) findViewById( R.id.tv_acLoreItemEdit );
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
            case R.id.tv_acLoreItemEdit:
               ShowDialog();
                break;
        }
    }
    //点击输入弹出对话框
    public  void ShowDialog()
    {
        mDialog = new Dialog(this,R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        dialogView = LayoutInflater.from(this).inflate(R.layout.view_dialog, null);
        //初始化控件
        cb_dialog_if_shareToWeibo = (CheckBox) dialogView.findViewById(R.id.cb_dialog_check);
        tv_dialog_text_count = (TextView) dialogView.findViewById(R.id.tv_dialog_textcount);
        tv_dialog_comfirToSend= (TextView) dialogView.findViewById( R.id.tv_dialog_comfirm_to_send );
        tv_dialog_comfirToSend.setOnClickListener(this);
        tv_dialog_text_count.setOnClickListener(this);
        cb_dialog_if_shareToWeibo.setOnClickListener( this );
        //将布局设置给Dialog
        mDialog.setContentView(dialogView);
        //获取当前Activity所在的窗体
        Window dialogWindow = mDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
       WindowManager.LayoutParams lp = dialogWindow.getAttributes();
       // lp.y = 20;//设置Dialog距离底部的距离
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;//宽度全屏
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
//       将属性设置给窗体
      dialogWindow.setAttributes(lp);
        mDialog.show();//显示对话框
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
