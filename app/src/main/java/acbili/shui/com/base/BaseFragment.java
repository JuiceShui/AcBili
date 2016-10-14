package acbili.shui.com.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者： max_Shui on 2016/9/25.
 * 邮箱：shuicz0505@qq.com
 * fragment的基类
 */

public abstract class BaseFragment extends Fragment {
    protected LayoutInflater inflater;
    private  boolean isVisible;//标记当前fragment是否可见
    private  boolean isPrepared;//标记view是否初始化完成
    private  boolean isFirstLoad=true;//标记是否是第一次加载
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater=inflater;
        isFirstLoad=true;
        View view=initView(inflater,container,savedInstanceState);
        isPrepared=true;
        lazyLoad();
        return view;
    }

    /**
     * 与viewpager一起用时调用setUserVisibleHint
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint( isVisibleToUser );
        if (getUserVisibleHint())
        {
            isVisible=true;
            onVisible();
        }
        else {
            isVisible=false;
            onInvisible();
        }
    }

    protected void onInvisible() {
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void lazyLoad() {
        if (!isVisible||!isPrepared||!isFirstLoad)//如果不满足是第一次 view准备好，且 可见 直接返回
        {
            return;
        }
        else {
            isFirstLoad=false;
            initData();
        }
    }

    /**
     * 当 fragment是通过fragmenttransition调用hiden和show时调用
     * 如果是初始化就show的fragment为了触法该事件需要先hiden再show
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged( hidden );
        if (!hidden)
        {
            isVisible=true;
            onVisible();
        }
        else {
            isVisible=false;
            onInvisible();
        }
    }

    /**
     * 初始化数据
     */
    protected abstract void initData() ;

    protected abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) ;
}
