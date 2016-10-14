package acbili.shui.com.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.Random;

import acbili.shui.com.acbili.R;
import acbili.shui.com.base.BaseHolder;
import acbili.shui.com.bean.CookModel;
import acbili.shui.com.global.GApp;
import acbili.shui.com.utils.BitmapHelper;
import acbili.shui.com.utils.UIUtils;

/**
 * 笑话的一个item布局文件
 * @author maxShui
 *
 */
public class HolderJokerNormal extends BaseHolder<CookModel.CookDetail>
{
	
	private TextView tv_joker_author;
	private TextView tv_joker_content;
	private ImageView iv_joker_pic;
	private BitmapUtils mBitmapUtils;

	@Override
	public View initView() {
		View view= UIUtils.inflate(R.layout.holder_item_joker_nomal);
		tv_joker_author = (TextView) view.findViewById( R.id.tv_joker_author);
		tv_joker_content = (TextView) view.findViewById(R.id.tv_joker_content);
		iv_joker_pic = (ImageView) view.findViewById(R.id.iv_joker_pic);
		mBitmapUtils= BitmapHelper.getBitmapUtils();
		return view;
	}

	@Override
	public void RefreshView(CookModel.CookDetail data) {
		tv_joker_author.setText(data.name);
		tv_joker_content.setText(data.description);
		int number = new Random().nextInt(2) + 1;
		if (number>1) {
			//mBitmapUtils.configDefaultLoadingImage( R.drawable.dogeload );
			mBitmapUtils.display(iv_joker_pic, GApp.ImgHeader+data.img);
			//UIUtils.GlideUtils( UIUtils.getContext(), GApp.ImgHeader+data.img,iv_joker_pic );
			iv_joker_pic.setVisibility(View.VISIBLE);
		}
		else{
			iv_joker_pic.setVisibility(View.GONE);
		}		
	}
	
}
