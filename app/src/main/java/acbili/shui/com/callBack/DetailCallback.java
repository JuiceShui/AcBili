package acbili.shui.com.callBack;

/**
 * 作者： max_Shui on 2016/9/27.
 * 邮箱：shuicz0505@qq.com
 * 所有详细回调的存在类
 */

public class DetailCallback {

    private static DetailCallback mDetailCallback;
    public  static  DetailCallback getInstance()
    {
        if (mDetailCallback==null)
        {
            synchronized (DetailCallback.class) {
                mDetailCallback = new DetailCallback();
            }
        }
        return  mDetailCallback;
    }
}
