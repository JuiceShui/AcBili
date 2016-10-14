package acbili.shui.com.holder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import acbili.shui.com.acbili.LoreItemDetailActivity;
import acbili.shui.com.acbili.R;
import acbili.shui.com.base.BaseHolder;
import acbili.shui.com.bean.AcLoreDetailModel;
import acbili.shui.com.global.GApp;
import acbili.shui.com.utils.BitmapHelper;
import acbili.shui.com.utils.UIUtils;

/**
 * 作者： max_Shui on 2016/10/6.
 * 邮箱：shuicz0505@qq.com
 */

public class AcHolderLore extends BaseHolder<AcLoreDetailModel.LoreDetail> {
    private ImageView iv_pic;
    private TextView tv_title;
    private  TextView tv_content;
    private BitmapUtils bitmapUtils;
    private CardView cv_container;
    private ObjectAnimator animator;
    private View view;//布局
    @Override
    public View initView() {
        view= UIUtils.inflate( R.layout.holder_item_waterfall );
        tv_title= (TextView) view.findViewById( R.id.tv_fragment_plus_title );
        tv_content= (TextView) view.findViewById( R.id.tv_fragment_plus_keyword );
        iv_pic= (ImageView) view.findViewById( R.id.iv_fragment_plus_pic );
        cv_container= (CardView) view.findViewById( R.id.cvContainer );
        bitmapUtils= BitmapHelper.getBitmapUtils();
        return view;
    }

    @Override
    public void RefreshView(final AcLoreDetailModel.LoreDetail data) {
            tv_content.setText( data.description );
            tv_title.setText( data.title );
            bitmapUtils.display( iv_pic, GApp.ImgHeader+data.img );
            //动画
            animator =ObjectAnimator.ofFloat( view,"rotationY",0f,360f );
            animator.setDuration( 1000 );
            animator.setInterpolator( new LinearInterpolator( ) );
            //点击进行动画
            cv_container.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    animator.start();
                }
            } );
        //动画结束的监听事件   动画结束后跳转页面
            animator.addListener( new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd( animation );
                    Intent intent=new Intent(  );
                    intent.setClass( UIUtils.getContext(), LoreItemDetailActivity.class );
                    intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                    intent.putExtra( "ID",data.id );
                    UIUtils.getContext().startActivity( intent );
                }
            } );
    }
}
