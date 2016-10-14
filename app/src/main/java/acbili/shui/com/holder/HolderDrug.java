package acbili.shui.com.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import acbili.shui.com.acbili.R;
import acbili.shui.com.base.BaseHolder;
import acbili.shui.com.bean.DrugModel;
import acbili.shui.com.global.GApp;
import acbili.shui.com.utils.BitmapHelper;
import acbili.shui.com.utils.UIUtils;

/**
 * 作者： max_Shui on 2016/9/28.
 * 邮箱：shuicz0505@qq.com
 * 药品页
 */

public class HolderDrug extends BaseHolder<DrugModel.Drug>{
    private ImageView iv_drug_pic;
    private TextView tv_drug_name;
    private TextView tv_drug_key;
    private TextView tv_drug_des;
    private TextView tv_drug_tag;
    private TextView tv_drug_price;
    private BitmapUtils mBitmapUtils;


    @Override
    public View initView() {
        View view= UIUtils.inflate(R.layout.holder_item_food_normal);
        iv_drug_pic = (ImageView) view.findViewById( R.id.iv_item_drug_normal);
        tv_drug_name = (TextView) view.findViewById(R.id.tv_drug_title);
        tv_drug_key = (TextView) view.findViewById(R.id.tv_drug_keyword);
        tv_drug_des = (TextView) view.findViewById(R.id.tv_drug_content);
        tv_drug_tag=(TextView) view.findViewById(R.id.tv_drug_tag);
        tv_drug_price=(TextView) view.findViewById(R.id.tv_drug_price);
        mBitmapUtils = BitmapHelper.getBitmapUtils();
        return view;

    }

    @Override
    public void RefreshView(DrugModel.Drug data) {
        tv_drug_des.setText("功能"+data.description);
        tv_drug_key.setText(data.keywords);
        tv_drug_name.setText(data.name);
        tv_drug_tag.setTag("标签："+data.tag);
        tv_drug_price.setText("价格："+data.price);
        mBitmapUtils.display(iv_drug_pic, GApp.ImgHeader+data.img);

    }
}
