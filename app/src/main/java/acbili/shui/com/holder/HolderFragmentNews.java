package acbili.shui.com.holder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import acbili.shui.com.acbili.NewsDetailActivity;
import acbili.shui.com.acbili.R;
import acbili.shui.com.base.BaseHolder;
import acbili.shui.com.bean.NewsModel;
import acbili.shui.com.global.GApp;
import acbili.shui.com.utils.BitmapHelper;
import acbili.shui.com.utils.UIUtils;

/**
 * 作者： max_Shui on 2016/9/28.
 * 邮箱：shuicz0505@qq.com
 * 新闻热门页
 */

public class HolderFragmentNews extends BaseHolder<NewsModel.NewsContent> {
    private ImageView iv_news_pic;
    private TextView tv_news_title;
    private TextView tv_news_content;
    private BitmapUtils mBitmapUtils;
    private boolean isOpen=false;//标记是否展开
    private TextView tv_fragment_add;
    private ImageView iv_fragment_arrow;
    private RelativeLayout rl_news_toogle;
    private LinearLayout ll_fragmentNewsContainer;
    @Override
    public View initView() {
        View view= UIUtils.inflate(R.layout.holder_item_fragment_news);
        ll_fragmentNewsContainer= (LinearLayout) view.findViewById( R.id.ll_fragmentNewsContainer );
        iv_news_pic = (ImageView) view.findViewById( R.id.iv_fragment_news_pic);
        tv_news_title = (TextView) view.findViewById(R.id.tv_fragment_news_title);
        tv_news_content = (TextView) view.findViewById(R.id.tv_fragment_news_content);
        rl_news_toogle = (RelativeLayout) view.findViewById(R.id.rl_fragment_news_toggle);
        iv_fragment_arrow = (ImageView) view.findViewById(R.id.iv_fragment_news_arrow);
        tv_fragment_add = (TextView) view.findViewById(R.id.tv_fragment_news_add);
        ;		mBitmapUtils = BitmapHelper.getBitmapUtils();
        rl_news_toogle.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                toogle();
            }
        });
        return view;
    }
    @Override
    public void RefreshView(final NewsModel.NewsContent data) {
        tv_news_title.setText( data.title );
        tv_news_content.setText( data.description );
        mBitmapUtils.display( iv_news_pic, GApp.ImgHeader+data.img );
        ll_fragmentNewsContainer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(  );
                intent.setClass( UIUtils.getContext(), NewsDetailActivity.class );
                intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                intent.putExtra( "ID",data.id );
                UIUtils.getContext().startActivity( intent );
            }
        } );
        // 给tvDetailDes设置初始高度为只展示1行的高度
        /**
         * 放在消息队列中运行 解决当只有1行描述数据也有6行高度的bug
         */
        // 把设置参数放在looper中 可以避免 先绘制完了 才加载数据 导致界面有空白
        tv_news_content.post( new Runnable() {
            @Override
            public void run() {
                int shortHeight=getShortHeight();
                LinearLayout.LayoutParams parmas= (LinearLayout.LayoutParams) tv_news_content.getLayoutParams();
                parmas.height=shortHeight;
                tv_news_content.setLayoutParams( parmas );
            }
        } );
    }

    /**
     * 根据状态判读是否展开
     */
    private void toogle() {
        final LinearLayout.LayoutParams parmas= (LinearLayout.LayoutParams) tv_news_content.getLayoutParams();
        int longHeight=getLongHeight();
        int shortHeight=getShortHeight();
        ValueAnimator animator=null;
            if (!isOpen)//如果不是展开状态
            {
                isOpen=true;//改变状态
                if (longHeight>shortHeight)//只有当原本的字符多与省略的字符时
                {
                    animator=ValueAnimator.ofInt( shortHeight,longHeight );
                }
            }
        else {//如果是打开状态
                isOpen=false;//改变状态
                if (longHeight>shortHeight)
                {
                    animator=ValueAnimator.ofInt( longHeight,shortHeight );
                }
            }
        if (animator!=null)
        {
            animator.addUpdateListener( new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int height= (int) animation.getAnimatedValue();//获取实时的高度
                    parmas.height=height;//赋值给布局参数 实时更新
                    tv_news_content.setLayoutParams( parmas );//赋值给布局 更新布局
                }
            } );
            animator.addListener( new Animator.AnimatorListener() {//根据动画状态改变布局
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {//当动画执行完毕后 对开关状态进行判断

                    if (isOpen)
                    {
                        //当前是打开的
                        iv_fragment_arrow.setImageResource( R.drawable.arrow_up);
                    }
                    else {
                        iv_fragment_arrow.setImageResource( R.drawable.arrow_down );
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            } );
            animator.setDuration( 300 );
            //开启动画
            animator.start();
        }
    }

    /**
     * 获取原始数据的高度
     * @return
     */
    private int getLongHeight()
    {
        int measureWidth=tv_news_content.getMeasuredWidth();
        TextView view=new TextView( UIUtils.getContext() );
        view.setTextSize( TypedValue.COMPLEX_UNIT_DIP,14 );
        view.setText( getData().description );
        int measureWidthSpc= View.MeasureSpec.makeMeasureSpec( measureWidth, View.MeasureSpec.EXACTLY );
        int measureHeightSpc= View.MeasureSpec.makeMeasureSpec( 2000, View.MeasureSpec.AT_MOST );
        view.measure( measureWidthSpc,measureHeightSpc );
        return view.getMeasuredHeight();
    }

    /**
     * 获取模拟一行的高度
     * @return
     */
    private int getShortHeight()
    {   int measureWidth=tv_news_content.getMeasuredWidth();//获取原始的高度
        TextView view=new TextView( UIUtils.getContext() );
        /**
         * 模拟一个和原生的tv_news_content一样的textview，显示的字符和字体大小都要和tv_news_content一样 ，模拟只显示一行的高度
         */
        view.setText( getData().description );
        view.setMaxLines( 1 );
        view.setTextSize( TypedValue.COMPLEX_UNIT_DIP,14 );//设置与原始控件一样的高度
        // 宽不变 确定值 填充父窗体
        int measureWidthSpc= View.MeasureSpec.makeMeasureSpec( measureWidth, View.MeasureSpec.EXACTLY );
        // 高度包裹内容 wrap_conten当包裹内容时参1表示尺寸大小暂写2000也可以写屏幕高度
        int measureHeightSpc= View.MeasureSpec.makeMeasureSpec( 2000, View.MeasureSpec.AT_MOST );
        view.measure( measureWidthSpc,measureHeightSpc );

        return view.getMeasuredHeight();
    }
}
