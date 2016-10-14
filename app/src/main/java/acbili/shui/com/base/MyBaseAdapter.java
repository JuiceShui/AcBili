package acbili.shui.com.base;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import acbili.shui.com.holder.MoreHolder;
import acbili.shui.com.utils.UIUtils;

/**
 * listview的数据适配器的基类
 * @author maxShui
 *
 * @param <T> 数据的泛型
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter
{	
	private static final int TYPE_MORE = 0;// 加载更多的布局
	private final static int TYPE_NOMORL = 1;// 普通的布局
	private static final int TYPE_SPECIAL=2;//特殊的布局
	private ArrayList<T> data=new ArrayList<T>();//适配器运用的数据
	public MyBaseAdapter(ArrayList<T> data)
	{
		if (data!=null) {
			this.data=data;
		}
		else {
			Log.e(getClass().getSimpleName(), "数据时空的");
		}
		
	}
	/**
	 * 返回数据的大小
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size()+1;
	}
/**
 * 返回指定位置的数据
 */
	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}
/**
 * 获取对应位置的id
 * 
 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return mGetViewTypeCount();
	}
	/**
	 * 判断显示哪个布局
	 */
	@Override
	public int getItemViewType(int position) {
		if(position==getCount()-1)//是最后一个 显示加载更多布局
		{
			return TYPE_MORE;
		}
		if (CheckType(data.get(position))) {
			return TYPE_SPECIAL;
		}
		else {
			return TYPE_NOMORL;
		}
		
	}
	/**
	 * 子类可重写 写入布局的种类个数
	 * @return 布局个数
	 */
	public int mGetViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseHolder holder;
		if (convertView==null) {
			if (getItemViewType(position)==TYPE_MORE) {
				holder=new MoreHolder(hasMore());
			}
			else if (getItemViewType(position)==TYPE_NOMORL) {
				// 初始化holder 就完成了以下三个步骤
				// 1加载布局
				// 2初始化控件 findviewbyid
				// 3给布局对象打标记 tag
				holder=getNormalHolder(position);
			}
			else {
				holder=getSpecialHolder(position);
			}
		}
		else {
			holder=(BaseHolder) convertView.getTag();
		}
		if (getItemViewType(position)!=TYPE_MORE) {//如果不是加载更多布局
			holder.setData(getItem(position));//获取当前位置的数据并 设置数据
		}
		else {// 是加载更多时
			// 一旦加载更多的布局展示出来 就开始加载更多！
		MoreHolder moreHolder = (MoreHolder) holder;
		// 只有在有更多数据的情况下才加载更多
		if (moreHolder.getData() == MoreHolder.STATE_MORE_MORE) {
				loadMore(moreHolder);
		}

		}
		return holder.getView();
	}
	
	private boolean isLoadMore = false;// 标记是否正在执行加载更多
	/**
	 * 加载更多
	 * @param
	 */
	public void loadMore(final MoreHolder holder) {
		if (!isLoadMore) {
			isLoadMore = true;
			
			
			  new Thread(){
			 
			 @Override public void run() {
			  
			          final ArrayList<T> moreData=onLoadMore();//加载更多返回的数据
			           //此方法是在子线程中执行的 所以可以执行耗时的操作 
			           UIUtils.runOnUIThread( new
			           Runnable() {
			 
			  @Override public void run() { // TODO Auto-generated method stub
			            if (moreData!=null) {//加载更多 //每一页有20条数据 如果返回的数据小于20条
			           //就认为到了最后一页 
			           if (moreData.size()<20) {
			            holder.setData(MoreHolder.STATE_MORE_NOMORE);
			            Toast.makeText(UIUtils.getContext(), "没有更多数据了",
			            Toast.LENGTH_SHORT).show(); } else { //还有更多数据
			            holder.setData(MoreHolder.STATE_MORE_MORE);
			  
			            } //将更多数据追加到当前集合中 
			           data.addAll(moreData); //然后再刷新界面
			           MyBaseAdapter.this.notifyDataSetChanged(); } 
			            else {
			            //加载更多失败 
			        	holder.setData(MoreHolder.STATE_MORE_ERROR); }
			            isLoadMore=false; } });			  
			            }; }.start();		 
		}
			 
	}
	public abstract ArrayList<T> onLoadMore();
	/**
	 * 普通布局和特殊布局 需要子类实现
	 * @param position
	 * @return
	 */
	public abstract BaseHolder<T> getNormalHolder(int position);
	public abstract BaseHolder<T> getSpecialHolder(int position);
	/**
	 * 子类实现  判断选择布局
	 * @param t 
	 * @return
	 */
	public abstract boolean CheckType(T t);
	// 是否有加载更多 默认有 但是子类可以重新
	public boolean hasMore() {
			return true;
		}

	
}
