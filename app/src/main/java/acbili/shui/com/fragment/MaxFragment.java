package acbili.shui.com.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import acbili.shui.com.acbili.R;
import acbili.shui.com.base.BaseFragment;
import acbili.shui.com.fragmentSubMax.MaxSubFragmentFractory;
import acbili.shui.com.utils.UIUtils;
import acbili.shui.com.view.ViewPagerIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaxFragment extends BaseFragment {

    private ViewPager mViewPager;
    private ViewPagerIndicator mIndicator;
    private MypagerAdapter mAdapter;
    private List<String> mTitles = Arrays.asList("最新","美食","健康","药物");
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=UIUtils.inflate( R.layout.fragment_max );
        mViewPager= (ViewPager) view.findViewById( R.id.vp_pager );
        mIndicator= (ViewPagerIndicator) view.findViewById( R.id.vpi_indicator );
        mAdapter=new MypagerAdapter(getChildFragmentManager());
        mIndicator.setTabItemTitles(mTitles);
        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager, 0);
        mViewPager.setCurrentItem(0, false);
        mViewPager.setOffscreenPageLimit(1);
        return view;
    }
    @Override
    protected void initData() {

    }

    class MypagerAdapter extends FragmentStatePagerAdapter
    {

        public MypagerAdapter(FragmentManager fm)
        {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int arg0) {
            // TODO Auto-generated method stub
            return MaxSubFragmentFractory.GetFragment(arg0);

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mTitles.size();
        }
        @Override
        public void destroyItem(View container, int position, Object object) {
            // TODO Auto-generated method stub
            //super.destroyItem(container, position, object);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
           // super.destroyItem(container, position, object);
        }

    }

}
