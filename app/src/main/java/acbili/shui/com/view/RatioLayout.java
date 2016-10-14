package acbili.shui.com.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import acbili.shui.com.acbili.R;

/**
 * 自定义控件 按照比例来决定布局高度
 * 
 * @author maxShui
 *
 */
public class RatioLayout extends FrameLayout
{

	private Float ratio;

	public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public RatioLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// 获取属性值
		// attrs.getAttributeFloatValue(namespace,"ratio", -1);
		// 当自定义属性时 系统会自动生成相关的id 此id通过R.Styleable来引用
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);// 获取attr的数组
		// id= 属性名+字段名 此id 是系统自动生成的
		ratio = array.getFloat(R.styleable.RatioLayout_ratio, -1);
		array.recycle();// 回收typearray 提高性能
	}

	public RatioLayout(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		// 1 获取高度
		// 2 根据宽高比例ratio 计算控件的高度
		// 3 重新测量控件
		int width = MeasureSpec.getSize(widthMeasureSpec);// 获取宽度值
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);// 获取宽度模式
		int height = MeasureSpec.getSize(heightMeasureSpec);// 获取高度度值
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);// 获取高度模式
		// MeasureSpec.AT_MOST :至多模式 控件有多大显示多大 类似 wrap_content
		// MeasureSpec.EXACTLY :确定模式 类似控件写死dip match_parent
		// MeasureSpec.UNSPECIFIED： 未指定模式

		// 宽度确定 高度不定 ratio合法 才要计算高度值
		if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.UNSPECIFIED && ratio > 0) {
			// 图片的宽度=控件宽度-左侧内边距-右侧内边距
			int imageWidth = width - getPaddingLeft() - getPaddingRight();
			// 图片的高度 =图片宽度/ratio
			int imageHeight = (int) (imageWidth / ratio + 0.5f);
			// 控件的高度=图片的高度+上册内边距+下册内边距
			height = imageHeight + getPaddingTop() + getPaddingBottom();
			// 根据最新的高度给heightMeasureSpec重新赋值 高度的模式换为确定模式
			// 供底层的super.onMeasure(widthMeasureSpec, heightMeasureSpec)调用
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
		}
		// 按照最新的高度来测量控件
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
