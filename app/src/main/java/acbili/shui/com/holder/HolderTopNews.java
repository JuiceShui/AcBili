package acbili.shui.com.holder;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import acbili.shui.com.acbili.NewsDetailsWebActivity;
import acbili.shui.com.acbili.R;
import acbili.shui.com.base.BaseHolder;
import acbili.shui.com.bean.NewsModel;
import acbili.shui.com.global.GApp;
import acbili.shui.com.utils.BitmapHelper;
import acbili.shui.com.utils.UIUtils;

/**
 * 
 * @author maxShui
 *
 */
public class HolderTopNews extends BaseHolder<NewsModel.NewsContent>
{

	private TextView tv_content;
	private TextView tv_title;
	private ImageView iv_pic;
	private BitmapUtils mUtils;
	private TextView tv_count;
	private CardView cv_Container;

	@Override
	public View initView() {
		View view= UIUtils.inflate(R.layout.holder_item_news_normal);
		iv_pic = (ImageView) view.findViewById( R.id.iv_item_new_normal);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_content = (TextView) view.findViewById(R.id.tv_content);
		tv_count = (TextView) view.findViewById(R.id.tv_count);
		cv_Container = (CardView) view.findViewById(R.id.cv_item_news_container);
		mUtils= BitmapHelper.getBitmapUtils();
		return view;
	}

	@Override
	public void RefreshView(final NewsModel.NewsContent data) {
		mUtils.display(iv_pic, GApp.ImgHeader+data.img);
		tv_title.setText(data.title);
		tv_content.setText(data.description);
		tv_count.setText(data.count+"..");	
		cv_Container.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(UIUtils.getContext(), NewsDetailsWebActivity.class);
				intent.putExtra("id", data.id);
				intent.putExtra("title", data.title);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				UIUtils.getContext().startActivity(intent);
			}
		});
	}

}
