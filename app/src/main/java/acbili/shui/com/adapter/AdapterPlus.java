package acbili.shui.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.HashMap;
import java.util.List;

import acbili.shui.com.acbili.R;
import acbili.shui.com.base.BaseStaggeredGridLayoutAdapter;
import acbili.shui.com.bean.DrugModel;
import acbili.shui.com.global.GApp;
import acbili.shui.com.utils.BitmapHelper;
import acbili.shui.com.utils.UIUtils;

/**
 * 作者： max_Shui on 2016/10/5.
 * 邮箱：shuicz0505@qq.com
 */

public class AdapterPlus extends BaseStaggeredGridLayoutAdapter<DrugModel.Drug> {
    private List<DrugModel.Drug> dataResource;
    private LayoutInflater inflater;
    private OnItemClickLitener listener;
    private BitmapUtils bitmapUtils;
    private final static float SIZE_SCALE_01 = 4 / 3f;
    private final static float SIZE_SCALE_02 = 4 / 4f;

    private HashMap<Integer, Float> indexMap = new HashMap<Integer, Float>();

    private int screenWidth;

    private Context context;

    public void setListener(OnItemClickLitener listener) {
        this.listener = listener;
    }

    public AdapterPlus(Context context, List<DrugModel.Drug> list, View headerViewRes, View footerViewRes) {
        super(list, headerViewRes, footerViewRes);
        initData(context,list);
    }

    public AdapterPlus(Context context, List<DrugModel.Drug> list, View headerViewRes) {
        super(list, headerViewRes);
        initData(context,list);
    }


    public AdapterPlus(Context context, List<DrugModel.Drug> list) {
        super(list);
        initData(context,list);
        bitmapUtils= BitmapHelper.getBitmapUtils();
    }

    private void initData(Context context, List<DrugModel.Drug> list) {
        dataResource = list;
        inflater = LayoutInflater.from(context);
        this.context = context;
        /**
         * 11dp为item间隔宽度
         */
        screenWidth = UIUtils.getScreenSize(context)[0] - UIUtils.dip2px( 8) * 3;
    }

    public void setDataResource(List<DrugModel.Drug> dataResource) {
        this.dataResource = dataResource;
        setList(dataResource);
    }

    @Override
    protected void onBindItemView(final RecyclerView.ViewHolder holder, final DrugModel.Drug item, final int position) {
        MyHolder itemViewHolder = (MyHolder) holder;
        itemViewHolder.tv_key.setText( item.keywords );
        itemViewHolder.tv_title.setText( item.name );
        bitmapUtils.display( itemViewHolder.iv_pic, GApp.ImgHeader+item.img );
        itemViewHolder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (listener!=null)
              {
                  listener.onItemClick( holder.itemView,position,item );
              }
                else {
                  System.out.println("jin  bu  lai");
              }
            }
        } );
        resizeItemView(itemViewHolder.iv_pic, getScaleType(position));
    }

    private float getScaleType(int position) {
        if (!indexMap.containsKey(position)) {
            float scaleType;
            if (hasHeader()) {
                if (position == 1) {
                    scaleType = SIZE_SCALE_01;
                } else if (position == 2) {
                    scaleType = SIZE_SCALE_02;
                } else {
                    scaleType = UIUtils.getRandomInt() % 2 == 0 ? SIZE_SCALE_01 : SIZE_SCALE_02;
                }
            } else {
                if (position == 0) {
                    scaleType = SIZE_SCALE_01;
                } else if (position == 1) {
                    scaleType = SIZE_SCALE_02;
                } else {
                    scaleType = UIUtils.getRandomInt() % 2 == 0 ? SIZE_SCALE_01 : SIZE_SCALE_02;
                }
            }
            indexMap.put(position, scaleType);
        }

        return indexMap.get(position);
    }

    private void resizeItemView(ImageView frontCoverImage, float scaleType) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) frontCoverImage.getLayoutParams();
        params.width = screenWidth / 2;
        params.height = (int) (params.width / scaleType) - UIUtils.dip2px( 8);
        frontCoverImage.setLayoutParams(params);
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position, DrugModel.Drug item);
    }

    @Override
    public MyHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view=UIUtils.inflate( R.layout.holder_item_waterfall );
        return new MyHolder(view);
    }

    @Override
    protected void onBindFooterView(View footerView) {
    }

    @Override
    protected void onBindHeaderView(View headerView) {
    }
}

class MyHolder extends RecyclerView.ViewHolder
{
    TextView tv_title;
    ImageView iv_pic;
    TextView tv_key;
    public MyHolder(View itemView) {
        super( itemView );
        tv_title= (TextView) itemView.findViewById( R .id.tv_fragment_plus_title);
        tv_key= (TextView) itemView.findViewById( R.id.tv_fragment_plus_keyword );
        iv_pic= (ImageView) itemView.findViewById( R.id.iv_fragment_plus_pic );
    }

}
