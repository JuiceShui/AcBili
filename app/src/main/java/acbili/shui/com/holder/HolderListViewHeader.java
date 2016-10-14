package acbili.shui.com.holder;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import acbili.shui.com.acbili.R;
import acbili.shui.com.adapter.AdapterListViewHeader;
import acbili.shui.com.base.BaseHolder;
import acbili.shui.com.bean.HeaderMoldel;
import acbili.shui.com.utils.UIUtils;

/**
 * 轮播的图片
 * @author maxShui
 *
 */
public class HolderListViewHeader extends BaseHolder<ArrayList<HeaderMoldel.ListViewHeader>>
{
	private ArrayList< HeaderMoldel.ListViewHeader > data;
	private ViewPager mViewpager;
	private LinearLayout ll_container;
	private TextView tv_title;
	private  int Id=1;
	private int mCurPos=0;//记录小圆点指示器的当前位置
	private RelativeLayout rlRoot;
	@Override
	public View initView() {
		rlRoot = new RelativeLayout( UIUtils.getContext());
		//跟布局的布局参数  因为是放在listview上 所以用的是listview的布局
		AbsListView.LayoutParams params=new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, UIUtils.dip2px(250));
		//添加布局参数到布局
		rlRoot.setLayoutParams(params);
		//viewpager的布局参数
		RelativeLayout.LayoutParams vp_params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		//实例化一个viewpager
		mViewpager=new ViewPager(UIUtils.getContext());
		//将布局参数设置到viewpager
		mViewpager.setLayoutParams(vp_params);
		//将viewpager添加到跟布局
		rlRoot.addView(mViewpager);
		//实例化小圆点指示器的父容器
		ll_container=new LinearLayout(UIUtils.getContext());
		//随便设置的一个id  因为不允许传int
		ll_container.setId( R.id.tv_mm );
		//小圆点指示器的父容器参数
		RelativeLayout.LayoutParams ll_params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		//添加规则  将ll-container添加在父容器的右下角
		ll_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		ll_params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		//设置padding
		int padding=UIUtils.dip2px(10);
		ll_container.setPadding(padding, padding, padding, padding);
		ll_container.setOrientation(LinearLayout.HORIZONTAL);//设置为水平
		rlRoot.addView(ll_container, ll_params);
		
		//图片标题
		tv_title=new TextView(UIUtils.getContext());
		//小圆点指示器的父容器参数
		RelativeLayout.LayoutParams tv_parmas=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		//添加规则  将ll-container添加在父容器的右下角
		tv_parmas.addRule(RelativeLayout.ABOVE,ll_container.getId());
		tv_parmas.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		//设置padding
		int padding2=UIUtils.dip2px(3);
		tv_title.setBackgroundColor(Color.BLACK);
		tv_title.getBackground().setAlpha(128);
		tv_title.setPadding(padding2, padding2, padding2, padding2);
		tv_title.setTextColor(UIUtils.getColor( R.color.color_green));
		tv_title.setTextSize(UIUtils.getDimen(R.dimen.text_size_8));
		tv_title.setMaxLines(1);
		rlRoot.addView(tv_title, tv_parmas);
		return rlRoot;
	}
	@Override
	public void RefreshView(final ArrayList<HeaderMoldel.ListViewHeader> data) {
		// TODO Auto-generated method stub
		this.data=data;
		mViewpager.setAdapter(new AdapterListViewHeader(data));
		mViewpager.setCurrentItem(data.size()*1000);
		//第一次时 手动为tv_title赋值
		tv_title.setText(data.get(0).title);
		int margin=UIUtils.dip2px(5);
		/**
		 * 初始化指示器的小圆点
		 */
		for(int i=0;i<data.size();i++)
		{
			ImageView imageView=new ImageView(UIUtils.getContext());
			LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			if (i==0) {
				imageView.setImageResource(R.drawable.indicator_selected);//第一个设置为默认选中
			}
			else {
				imageView.setImageResource(R.drawable.indicator_normal);
				params.leftMargin=margin;
			}
			imageView.setLayoutParams(params);
			ll_container.addView(imageView, params);//添加到父容器
		}
		mViewpager.setOnPageChangeListener(new OnPageChangeListener()
		{
			
			@Override
			public void onPageSelected(int arg0) {
			final int position=arg0%(HolderListViewHeader.this.data.size());//获取当前的位置
			ImageView view=(ImageView) ll_container.getChildAt(position);//获取当前位置的小圆点
			view.setImageResource(R.drawable.indicator_selected);//设置当前为选中
			ImageView preView=(ImageView) ll_container.getChildAt(mCurPos);//获取上一个小圆点
			preView.setImageResource(R.drawable.indicator_normal);//将上一个设置为普通状态
			tv_title.setText(data.get(position).title);//给当前的图片设置标题
			mCurPos=position;//重置当前位置	
			
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		// * 如果使用handler的handlemessage的话 就要重写 但是如果直接用handler的post的话
		 //* 就可以直接post一个runnable 在runnable里面执行想执行的操作
		HolderListViewHeaderTask mTask=new HolderListViewHeaderTask();
		mTask.start();
		/**
		 * 循环发送消息 以实现无限循环轮播
		 */
	}
	class HolderListViewHeaderTask implements Runnable
	{
		public void start()
		{
			UIUtils.getHandler().removeCallbacksAndMessages(null);//移除所示消息 避免重发发送
			UIUtils.getHandler().postDelayed(this, 2500);//延迟2.5秒后发送消息 然后走run方法
		}
		public void run() {
			int CurrentPos=mViewpager.getCurrentItem();//获取当前的位置
			CurrentPos++;//将目标移动到下一个
			mViewpager.setCurrentItem(CurrentPos);//设置为下一个
			UIUtils.getHandler().postDelayed(this, 2500);//继续发送消息 实现内循环
		}
	}
}
