package acbili.shui.com.utils;

import android.content.Context;

/**
 * dp转px的工具类
 * @author maxShui
 *
 */
public class DensityUtils {
		public static int dp2px(float dp,Context context) {
		float density=context.getResources().getDisplayMetrics().density;//获取屏幕的密度
			int px= (int) (dp*density+0.5f);		//dp转成px  
 			return px;
		}
		public static float px2dp(float px,Context context) {
			float density=context.getResources().getDisplayMetrics().density;//获取屏幕的密度
				float dp= (px/density);		//px 转成 dp
	 			return dp;
			}
}
