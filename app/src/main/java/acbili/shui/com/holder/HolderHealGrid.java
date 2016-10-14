package acbili.shui.com.holder;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import acbili.shui.com.acbili.CateLoreDetailActivity;
import acbili.shui.com.acbili.R;
import acbili.shui.com.base.BaseHolder;
import acbili.shui.com.bean.LoreGridModel;
import acbili.shui.com.utils.UIUtils;

/**
 * 健康分类页的布局
 * @author maxShui
 *
 */
public class HolderHealGrid extends BaseHolder<LoreGridModel.LoreGrid>
{
	private int[] picHead=new int[]{R.drawable.a_heal_0,R.drawable.a_heal_1,R.drawable.a_heal_2,R.drawable.a_heal_3,
			R.drawable.a_heal_4,R.drawable.a_heal_5,R.drawable.a_heal_6,R.drawable.a_heal_7,R.drawable.a_heal_8,R.drawable.a_heal_9,
			R.drawable.a_heal_10,R.drawable.a_heal_11,R.drawable.a_heal_12,R.drawable.a_heal_13,R.drawable.a_heal_14,R.drawable.a_heal_7,
			R.drawable.a_heal_9,R.drawable.a_heal_10,R.drawable.a_heal_13,};
	private ImageView iv_heal_grid_pic;
	private TextView tv_heal_grid_title;
	private CardView cv_heal_grid_root;
	@Override
	public View initView() {
		View view= UIUtils.inflate(R.layout.holder_item_heal_grid);
		iv_heal_grid_pic = (ImageView) view.findViewById( R.id.iv_heal_grid_img);
		tv_heal_grid_title = (TextView) view.findViewById(R.id.tv_heal_grid_title);
		cv_heal_grid_root = (CardView) view.findViewById( R.id.cv_heal_grid_root);
		return view;
	}

	@Override
	public void RefreshView(final LoreGridModel.LoreGrid data) {

			tv_heal_grid_title.setText(data.name);
			iv_heal_grid_pic.setImageResource(picHead[data.id]);


		cv_heal_grid_root.setOnClickListener(new View.OnClickListener()
		{
			
			@Override
			public void onClick(View v) {
					Intent intent=new Intent();
					intent.putExtra("ID", data.id);
					intent.putExtra("Cate", "lore");
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setClass(UIUtils.getContext(), CateLoreDetailActivity.class);
				//activity之间的跳转动画
					//new MainActivity().overridePendingTransition( R.anim.activity_in_anim,R.anim.activity_out_anim );
					UIUtils.getContext().startActivity(intent);
			}
		});
	}
}
