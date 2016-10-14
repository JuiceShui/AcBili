package acbili.shui.com.adapter;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

import acbili.shui.com.acbili.R;
import acbili.shui.com.acbili.ViewPagerActivity;
import acbili.shui.com.bean.HeaderMoldel;
import acbili.shui.com.global.GApp;
import acbili.shui.com.utils.BitmapHelper;
import acbili.shui.com.utils.UIUtils;

/**
 * ListView的头布局的适配器
 * @author maxShui
 *
 */
public class AdapterListViewHeader extends PagerAdapter
{	private BitmapUtils mBitmapUtils;
	private ArrayList<HeaderMoldel.ListViewHeader> data;
	public AdapterListViewHeader(ArrayList<HeaderMoldel.ListViewHeader> data)
	{
		this.data=data;
		mBitmapUtils= BitmapHelper.getBitmapUtils();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;//设置为最大 用于无限循环
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView((View) object);
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		position=position%data.size();//超过图片张数就重置
		final int mCurPos=position;//记录当前的位置
		ImageView view=new ImageView( UIUtils.getContext());
		//mBitmapUtils.configDefaultLoadFailedImage( R.drawable.load);
		//mBitmapUtils.display(view, GApp.ImgHeader+data.get(position).img, new BitmapDisplayConfig());
		Glide.with(  UIUtils.getContext())
				.load( GApp.ImgHeader+data.get( position ).img )
				.priority( Priority.NORMAL )
				.placeholder( R.drawable.dogeload )
				.error( R.drawable.loaderror )
				.into( view );
		view.setScaleType(ScaleType.FIT_XY);//设置图片是适应方式
		view.setOnClickListener(new View.OnClickListener()//给当前的图片设置点击事件
		{
			
			@Override
			public void onClick(View v) {
				loadThis(mCurPos);
			}
		});
		container.addView(view);
		return view;
	}

	//处理点击事件
	private void loadThis(int pos)
	{	String title=data.get(pos).title;
		int id=data.get(pos).id;
		Intent intent=new Intent();
		intent.setClass(UIUtils.getContext(), ViewPagerActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);		
		intent.putExtra("title",title);
		intent.putExtra("id", id);
		UIUtils.getContext().startActivity(intent);
	}
}
