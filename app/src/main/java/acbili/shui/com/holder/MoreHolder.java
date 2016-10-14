package acbili.shui.com.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import acbili.shui.com.acbili.R;
import acbili.shui.com.base.BaseHolder;
import acbili.shui.com.utils.UIUtils;

/**
 * 加载更多的布局
 * @author maxShui
 *
 */
public class MoreHolder extends BaseHolder<Integer>
{	
	// 加载更多的状态
		// 1 可以加载更多
		// 2 加载更多失败
		// 3 没有更多数据
		public static final int STATE_MORE_MORE = 1;// 加载更多
		public static final int STATE_MORE_ERROR = 2;// 加载更多失败
		public static final int STATE_MORE_NOMORE = 3;// 没有加载更多
		private LinearLayout llLoadMore;
		private TextView tvError;
	public MoreHolder(boolean hasMore)
		{ // 将状态传递给父类的data 父类会同时刷新界面 在子类中会接收到这个数据 即在本类中的refresh中会收到
			if (hasMore) {
				setData(STATE_MORE_MORE);// 有更多数据 setData过后一定会调refreshData(Integer
											// data)
			} else {
				setData(STATE_MORE_NOMORE);// 没有更多数据
			}
		}

	@Override
	public View initView() {
		// TODO Auto-generated method stub
		View view = UIUtils.inflate(R.layout.list_item_more);
		llLoadMore = (LinearLayout) view.findViewById( R.id.ll_loadmore);
		tvError = (TextView) view.findViewById(R.id.tv_loaderror);
		return view;
	}

	@Override
	public void RefreshView(Integer data) {
		// TODO Auto-generated method stub
		switch (data) {
		case STATE_MORE_MORE:
			// 显示加载更多
			llLoadMore.setVisibility(View.VISIBLE);
			tvError.setVisibility(View.GONE);
			break;
		case STATE_MORE_NOMORE:
			// 隐藏加载更多
			llLoadMore.setVisibility(View.GONE);
			tvError.setVisibility(View.GONE);
			break;
		case STATE_MORE_ERROR:
			// 加载错误
			llLoadMore.setVisibility(View.GONE);
			tvError.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}
	}
		

