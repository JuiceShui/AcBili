package acbili.shui.com.view;

import android.content.Context;
import android.media.MediaPlayer.OnPreparedListener;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.VideoView;
/**
 *自定义videoview实现全屏
 * @author maxShui
 *
 */
public class MyVideoView extends VideoView
{
	public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public MyVideoView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyVideoView(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 实现全屏
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int width=getDefaultSize(0, widthMeasureSpec);
		int height=getDefaultSize(0, heightMeasureSpec);
		setMeasuredDimension(width, height);
	}
	@Override
	public void setOnPreparedListener(OnPreparedListener l) {
		// TODO Auto-generated method stub
		super.setOnPreparedListener(l);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}
}
