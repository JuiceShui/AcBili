package acbili.shui.com.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import acbili.shui.com.acbili.R;
import acbili.shui.com.base.BaseFragment;
import acbili.shui.com.utils.UIUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends BaseFragment {


    public ExploreFragment() {
        // Required empty public constructor
    }
    @Override
    protected void initData() {

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return UIUtils.inflate( R.layout.fragment_explore );
    }

}
