package acbili.shui.com.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

import acbili.shui.com.acbili.ViewPagerActivity;
import acbili.shui.com.bean.AcPicViewpagerModel;
import acbili.shui.com.global.GApp;
import acbili.shui.com.utils.BitmapHelper;
import acbili.shui.com.utils.UIUtils;

/**
 * viewpager activity的viewpager的适配器
 * @author maxShui
 *
 */
public class AcAdapterViewpager extends PagerAdapter
{
	private ArrayList<AcPicViewpagerModel.picture> pics;
	private BitmapUtils mBitmapUtils;
	private ViewPagerActivity mActivity;
	public AcAdapterViewpager(ArrayList<AcPicViewpagerModel.picture> dArrayList,ViewPagerActivity activity)
	{
		this.mActivity=activity;
		this.pics=dArrayList;
		this.mBitmapUtils= BitmapHelper.getBitmapUtils();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pics.size();
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
	public Object instantiateItem(ViewGroup container, final int position) {
	
		ImageView view =new ImageView( UIUtils.getContext());
		//mBitmapUtils.display(view, GApp.ImgHeader+pics.get(position).src);
		//mBitmapUtils.configDefaultLoadFailedImage( R.drawable.load);
		/**Glide.with( mActivity)
				.load( GApp.ImgHeader+pics.get( position ).src )
				.priority( Priority.NORMAL )
				.placeholder( R.drawable.dogeload )
				.error( R.drawable.loaderror )
				.into( view );**/
		//new Thread(){
		UIUtils.GlideUtils( mActivity, GApp.ImgHeader+pics.get( position ).src,view );
		//	public void run() {
			//	bitmap=getPicture(GlobalApplication.ImgHeader+pics.get(position).src);
			//};
		//}.start();
		view.setScaleType(ScaleType.CENTER_INSIDE);//设置图片是适应方式
		//Drawable drawable=new BitmapDrawable(bitmap);
		//mActivity.rl_viewpager_container.setBackground(drawable);
		//view.setBackground(drawable);
		container.addView(view);
		return view;
	}
}
