package acbili.shui.com.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.HashMap;

import acbili.shui.com.acbili.R;
import acbili.shui.com.bean.DrugModel;
import acbili.shui.com.global.GApp;
import acbili.shui.com.utils.BitmapHelper;
import acbili.shui.com.utils.UIUtils;

/**
 * 作者： max_Shui on 2016/10/5.
 * 邮箱：shuicz0505@qq.com
 */

public class AdapterPluss extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private  ArrayList<DrugModel.Drug> data;
    private final static float SIZE_SCALE_01 = 4 / 3f;
    private final static float SIZE_SCALE_02 = 4 / 4f;
    //布局类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;
    private int screenWidth;
    private int mFooterCount=1;//尾布局的个数
    private int mHeaderCount=0;//头布局的个数
    private OnItemClickLitener listener;
    private BitmapUtils bitmapUtils;
    private HashMap<Integer, Float> indexMap = new HashMap<Integer, Float>();
    public AdapterPluss(ArrayList<DrugModel.Drug> data) {
        this.data=data;
        screenWidth = UIUtils.getScreenSize(UIUtils.getContext())[0] - UIUtils.dip2px( 8) * 3;
        bitmapUtils= BitmapHelper.getBitmapUtils();
    }

    /**
     * 当前的item是否是尾布局
     * @param position 当前位置
     * @return 是||否
     */
    public boolean isFooterView(int position)
    {
        return mFooterCount!=0&&(position>=getContentSize()+mHeaderCount);
    }

    /**
     * 当前item是否是头布局
     * @param position
     * @return
     */
    public boolean isHeaderView(int position)
    {
        return mHeaderCount!=0&&(position<mHeaderCount);
    }

    /**
     * 获取内容的个数
     * @return
     */
    public  int getContentSize()
    {
        return data.size();
    }

    /**
     * 根据位置判断当前的view是什么
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        int dataCount=getContentSize();//获取数据的个数 并赋值给dataCount
        if (position<mHeaderCount&&mHeaderCount!=0)
        {
            return  ITEM_TYPE_HEADER;//头布局
        }
        else if (mFooterCount!=0&&(position>=(mHeaderCount+dataCount)))
        {
            return  ITEM_TYPE_BOTTOM;
        }
        else {
            return  ITEM_TYPE_CONTENT;
        }
    }
    //判断是否需要将某个item设置为占据整行！
    //这里将尾布局设置为占据整行！！
    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow( holder );
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(holder.getItemViewType() ==ITEM_TYPE_BOTTOM);//当当前holder的type是footer时  设置为占据整行
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType==ITEM_TYPE_CONTENT) {
            view = UIUtils.inflate( R.layout.holder_item_waterfall );
            return new MyViewHolder( view );
        }
        else if (viewType==ITEM_TYPE_BOTTOM)
        {
            view=UIUtils.inflate( R.layout.holder_footer );
            return  new FooterHolder( view );
        }
        else {
            view=UIUtils.inflate( R.layout.holder_header );
            return  new HeaderHolder( view );
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof  MyViewHolder) {
            MyViewHolder myViewHolder= (MyViewHolder) holder;
            myViewHolder.tv_key.setText( data.get( position ).keywords );
            myViewHolder.tv_title.setText( data.get( position ).name );
            //bitmapUtils.display( holder.iv_pic, GApp.ImgHeader+data.get( position ).img );
            UIUtils.GlideUtils( UIUtils.getContext(), GApp.ImgHeader + data.get( position ).img, myViewHolder.iv_pic );
            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick( holder.itemView, position );
                    }
                }
            } );
            resizeItemView( myViewHolder.iv_pic, getScaleType( position ) );
        }
        else if (holder instanceof HeaderHolder)
        {

        }
        else
        {

        }
    }

    @Override
    public int getItemCount() {
        return data.size()+mHeaderCount+mFooterCount;
    }
    private float getScaleType(int position) {
        if (!indexMap.containsKey(position)) {
            float scaleType;

                if (position == 0) {
                    scaleType = SIZE_SCALE_01;
                } else if (position == 1) {
                    scaleType = SIZE_SCALE_02;
                } else {
                    scaleType = UIUtils.getRandomInt() % 2 == 0 ? SIZE_SCALE_01 : SIZE_SCALE_02;
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
        void onItemClick(View view, int position);
    }
    public  void  setListener(OnItemClickLitener listener)
    {
        this.listener=listener;
    }
}

/**
 * 内容布局holder
 */
class  MyViewHolder extends  RecyclerView.ViewHolder
{

    TextView tv_title;
    ImageView iv_pic;
    TextView tv_key;
    public MyViewHolder(View itemView) {
        super( itemView );
        tv_title= (TextView) itemView.findViewById( R.id.tv_fragment_plus_title);
        tv_key= (TextView) itemView.findViewById( R.id.tv_fragment_plus_keyword );
        iv_pic= (ImageView) itemView.findViewById( R.id.iv_fragment_plus_pic );
    }
}
class  HeaderHolder extends RecyclerView.ViewHolder
{

    public HeaderHolder(View itemView) {
        super( itemView );
    }
}
class FooterHolder extends RecyclerView.ViewHolder
{
    public FooterHolder(View itemView) {
        super( itemView );
    }
}
