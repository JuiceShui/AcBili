package acbili.shui.com.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import acbili.shui.com.acbili.R;
import acbili.shui.com.base.BaseFragment;
import acbili.shui.com.fragmentSubNews.FragmentNews;
import acbili.shui.com.utils.SystemStatusManager;
import acbili.shui.com.utils.UIUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment {
    private FrameLayout flNewsFragment;
    private FragmentNews fragmentNews;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=UIUtils.inflate(R.layout.fragment_news );
        return view;
    }
    @Override
    protected void initData() {
        FragmentManager manager=getChildFragmentManager();
        fragmentNews=new FragmentNews();
        manager.beginTransaction().add( R.id.fl_news_fragment, fragmentNews).hide( fragmentNews ).show( fragmentNews ).commit();
        //manager.beginTransaction().replace( R.id.fl_news_fragment,fragmentNews ).commit();
    }
    @Override
    protected void onInvisible() {
        super.onInvisible();
        getActivity().findViewById( R.id.ll_mainactivity_header).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.tv_mainactivity_title).setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor( Color.argb( 255,49, 194, 124 ) );
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0使用三方工具类
            getActivity().getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
            SystemStatusManager tintManager = new SystemStatusManager( getActivity() );
            tintManager.setStatusBarTintEnabled( true );
            tintManager.setStatusBarTintColor( Color.argb( 255,49, 194, 124 ) );
        }
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        getActivity().findViewById( R.id.ll_mainactivity_header).setVisibility(View.GONE);
        getActivity().findViewById(R.id.tv_mainactivity_title).setVisibility(View.VISIBLE);
    }
}
