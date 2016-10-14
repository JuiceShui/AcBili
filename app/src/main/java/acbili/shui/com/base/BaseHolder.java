package acbili.shui.com.base;

import android.view.View;

/**
 * ViewHolder的基类
 * @author maxShui
 * @param <T>
 *
 */
public abstract class BaseHolder<T>
{
	protected View mRootView;//一个item的跟布局
	protected T data;//当前位置的数据
	/**
	 * 调用构造方法时 。加载布局 初始化控件 设置tag
	 */
	public BaseHolder()
	{
		mRootView=initView();
		mRootView.setTag(this);//打标记
	}
	// 1加载布局
		// 2初始化控件 findviewbyid
	public abstract View initView();
	public void setData(T data)//将数据赋值到全局变量
	{
		this.data=data;
		RefreshView(data);//获取到数据过后更新View
	}
	/**
	 * 获取item的跟布局
	 * @return 跟布局
	 */
	public View getView()
	{
		return mRootView;
	}
	public abstract void RefreshView(T data);
	/**
	 *获取数据
	 * @return 返回对应的数据
	 */
	public T getData()
	{
		return this.data;
	}
}
