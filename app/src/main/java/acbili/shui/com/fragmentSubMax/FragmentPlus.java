package acbili.shui.com.fragmentSubMax;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;

import java.util.ArrayList;

import acbili.shui.com.acbili.DrugDetailActivity;
import acbili.shui.com.acbili.R;
import acbili.shui.com.adapter.AdapterPluss;
import acbili.shui.com.base.BaseFragment;
import acbili.shui.com.bean.DrugModel;
import acbili.shui.com.callBack.DialogCallback;
import acbili.shui.com.global.NetState;
import acbili.shui.com.global.Urls;
import acbili.shui.com.utils.UIUtils;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 作者： max_Shui on 2016/10/5.
 * 邮箱：shuicz0505@qq.com
 */

public class FragmentPlus extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private  StaggeredGridLayoutManager layoutManager;
    private  SpacesItemDecoration spacesItemDecoration;
    private AdapterPluss mAdapter;
    private  boolean lastVisibleItem;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<DrugModel.Drug> data;
    private String TAG=Urls.URL_DRUG+"list";
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= UIUtils.inflate( R.layout.layout_waterfall );
        mSwipeRefreshLayout= (SwipeRefreshLayout) view.findViewById( R.id.srl_refresh );
        recyclerView= (RecyclerView) view.findViewById( R.id.rv_waterfall );
        mSwipeRefreshLayout.setOnRefreshListener( this );
        spacesItemDecoration=new SpacesItemDecoration(16 );

        initRecyclerView();
        return view;
    }
    private void initRecyclerView() {
        layoutManager=new StaggeredGridLayoutManager( 2,StaggeredGridLayoutManager.VERTICAL );
        recyclerView.setLayoutManager( layoutManager );
        recyclerView.setItemAnimator( new DefaultItemAnimator() );
        recyclerView.addItemDecoration( spacesItemDecoration );
        recyclerView.setOnScrollListener( new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled( recyclerView, dx, dy );
              //  lastVisibleItem=layoutManager.findLastVisibleItemPositions(null);
                //检测是否最后一个item已经展示
                lastVisibleItem=isSlideToBottom( recyclerView );
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged( recyclerView, newState );
                //如果最后一个item已经展示 且滑动状态为停止
                if (NetState.isNetworkConnected( UIUtils.getContext() )&&newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem)
                {
                    int size=mAdapter.getItemCount();
                    //mSwipeRefreshLayout.setRefreshing( true );
                    //加载更多数据
                    OkHttpUtils.get( Urls.URL_DRUG+"list" )
                            .tag( this )
                            .cacheMode( CacheMode.REQUEST_FAILED_READ_CACHE )
                            .cacheTime( 60*60*2 )
                            .params( "page",(size/20)+1 )
                            .execute( new LoadMore() );
                }
            }
        } );
    }
    @Override
    protected void initData() {
        OkHttpUtils.get( Urls.URL_DRUG+"list" )
                .tag( TAG )
                .cacheMode( CacheMode.REQUEST_FAILED_READ_CACHE )
                .cacheTime( 60*60*2 )
                .params( "page","1" )
                .execute( new DrugCallback( getActivity() ) );
    }

    @Override
    public void onRefresh() {
        Toast.makeText( getActivity(),"aaaaaaaaaaaaaa",Toast.LENGTH_SHORT ).show();
    }

    /**
     * 检验是否已经显示到了最后一个item
     * @param recyclerView
     * @return 返回是或否
     */
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
    private  class  DrugCallback extends DialogCallback<DrugModel>
    {

        public DrugCallback(Activity activity) {
            super( activity, DrugModel.class );
        }

        @Override
        public void onSuccess(final DrugModel drugModel, Call call, Response response) {
            LayoutData( drugModel );
        }

        @Override
        public void onCacheSuccess(DrugModel drugModel, Call call) {
            LayoutData( drugModel );
        }
        private void  LayoutData(final DrugModel drugModel)
        {
            data=drugModel.tngou;
            mAdapter= new AdapterPluss( data );
            mAdapter.setListener( new AdapterPluss.OnItemClickLitener() {
                @Override
                public void onItemClick(View view, final int position) {
                    ObjectAnimator animator=ObjectAnimator.ofFloat( view,"rotationX",0f,360f );
                    animator.setDuration( 2000 );
//                    //ObjectAnimator animator2=ObjectAnimator.ofFloat( view,"rotationY",0f,360f );
//                   // animator.setDuration( 2000 );
//                   // AnimatorSet set = new AnimatorSet() ;
//                   // set.setInterpolator( new DecelerateInterpolator(  ) );
//                   // set.play( animator ).with( animator2 );
                    animator.setInterpolator( new OvershootInterpolator( 1 ) );
                    animator.start();
                    animator.addListener( new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd( animation );
                            /** Toast.makeText( getActivity(),"点击了item"+position+"::内容"+
                             drugModel.tngou.get( position ).name+"id"+drugModel.tngou.get( position ).id, Toast.LENGTH_SHORT ).show();
                             */
                            Intent intent=new Intent(  );
                            intent.setClass( getActivity(), DrugDetailActivity.class );
                            intent.putExtra( "ID",drugModel.tngou.get( position ).id );
                            startActivity( intent );
                        }
                    } );
                }
            } );
            recyclerView.setAdapter(mAdapter);
        }
    }
    private class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space=space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left=space;
            outRect.right=space;
            outRect.bottom=space;
            if(parent.getChildAdapterPosition(view)==0){
                outRect.top=space;
            }
        }
    }
    //加载更多的布局
    private   class  LoadMore extends StringCallback
    {

        @Override
        public void onSuccess(String s, Call call, Response response) {
            DrugModel model=new Gson().fromJson( s,DrugModel.class );
            ArrayList<DrugModel.Drug> tngou=model.tngou;
            data.addAll( tngou );
            mAdapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing( false );
        }
    }
}
