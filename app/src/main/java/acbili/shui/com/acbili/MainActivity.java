package acbili.shui.com.acbili;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import acbili.shui.com.base.BaseActivity;
import acbili.shui.com.fragment.ExploreFragment;
import acbili.shui.com.fragment.MaxFragment;
import acbili.shui.com.fragment.MeFragment;
import acbili.shui.com.fragment.NewsFragment;
import acbili.shui.com.utils.UIUtils;

public class MainActivity extends BaseActivity {
    private FrameLayout fl_container;//fragment容器
    private RadioGroup rgGroup;
    private  RadioButton rbMax;
    private  RadioButton rbNews;
    private  RadioButton rbExplore;
    private  RadioButton rbMe;
    private int statuBarHeigth;
    private Fragment maxFragment;
    private Fragment meFragment;
    private Fragment exploreFragment;
    private Fragment newsFragment;
    public  static final  String FragmentMaxTag="MaxFragmentTag";
    public  static final  String FragmentNewsTag="NewsFragmentTag";
    public  static final  String FragmentExploreTag="ExploreFragmentTag";
    public  static final  String FragmentMeTag="MeFragmentTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_main);
        statuBarHeigth = getStatusBarHeight();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //window.setStatusBarColor(Color.argb(255,49, 194, 124));//设置状态栏的颜色
            window.setStatusBarColor( UIUtils.getColor(R.color.color_green));//设置状态栏的颜色
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        initView();
        initStatuBar();
        if (savedInstanceState==null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = new MaxFragment();
            fragmentManager.beginTransaction().add( R.id.fl_container, fragment, FragmentMaxTag ).hide( fragment ).show( fragment ).commit();
        }
    }
    private void initView() {
        rgGroup = (RadioGroup) findViewById(R.id.rg_group);
        rbMax= (RadioButton) findViewById( R.id.rb_max );
        rbMax.setTag( FragmentMaxTag );
        rbNews= (RadioButton) findViewById( R.id.rb_news );
        rbNews.setTag( FragmentNewsTag );
        rbExplore= (RadioButton) findViewById( R.id.rb_explore );
        rbExplore.setTag( FragmentExploreTag );
        rbMe= (RadioButton) findViewById( R.id.rb_me );
        rbMe.setTag( FragmentMeTag );
        rgGroup.setOnCheckedChangeListener( new myCheckChangeListener() );

    }

    /**
     * 对状态栏设置
     */
    private void initStatuBar() {
        // TODO Auto-generated method stub
        //logo布局
        LinearLayout linearLayout=(LinearLayout) findViewById(R.id.ll_mainactivity_header);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//定义一个LayoutParams
        layoutParams.setMargins(0, statuBarHeigth, 0, 0);
        linearLayout.setLayoutParams(layoutParams);


        //标题布局
        TextView view=(TextView) findViewById(R.id.tv_mainactivity_title);
        RelativeLayout.LayoutParams layoutParams2=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams2.setMargins(0, statuBarHeigth, 0, 0);
        view.setVisibility(View.GONE);
        view.setLayoutParams(layoutParams2);
    }
    @Override
    public int setStatusBarColor() {
        return Color.argb( 255,49, 194, 124 );
    }
    public   MainActivity getMainActivity ()
    {
        return  MainActivity.this;
    }
    @Override
    protected boolean translucentStatusBar() {
        return  false;
    }

    /**
     * 退出时 提醒 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            dialog();
            return true;
        }
        return true;
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("确定要退出吗?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // MainActivity.this.finish();
                // System.exit(1);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        builder.setNegativeButton("取消", new android.content.DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    /********************************************************************************* * */
    private  class  myCheckChangeListener implements RadioGroup.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            maxFragment = fm.findFragmentByTag(FragmentMaxTag);
            newsFragment= fm.findFragmentByTag(FragmentNewsTag);
            exploreFragment = fm.findFragmentByTag(FragmentExploreTag);
            meFragment= fm.findFragmentByTag(FragmentMeTag);
            if (maxFragment != null) {
                ft.hide(maxFragment);
            }
            if (newsFragment != null) {
                ft.hide(newsFragment);
            }
            if (exploreFragment != null) {
                ft.hide(exploreFragment);
            }
            if (meFragment != null) {
                ft.hide(meFragment);
            }

            switch (checkedId) {
                case R.id.rb_max:
                    if (maxFragment == null) {
                        maxFragment = new MaxFragment();
                        ft.add(R.id.fl_container, maxFragment, FragmentMaxTag);
                    } else {
                        ft.show(maxFragment);
                    }
                    break;
                case R.id.rb_news:
                    if (newsFragment == null) {
                        newsFragment = new NewsFragment();
                        ft.add(R.id.fl_container, newsFragment, FragmentNewsTag).hide( newsFragment ).show( newsFragment );
                    } else {
                        ft.show(newsFragment);
                    }
                    break;
                case R.id.rb_explore:
                    if (exploreFragment == null) {
                        exploreFragment = new ExploreFragment();
                        ft.add(R.id.fl_container, exploreFragment, FragmentExploreTag).hide( exploreFragment ).show( exploreFragment );
                    } else {
                        ft.show(exploreFragment);
                    }
                    break;
                case R.id.rb_me:
                    if (meFragment == null) {
                        meFragment = new MeFragment();
                        ft.add(R.id.fl_container, meFragment, FragmentMeTag).hide( meFragment ).show( meFragment );
                    } else {
                        ft.show(meFragment);
                    }
                    break;
                default:
                    break;
            }
            ft.commit();
        }

    }

    /**
     * 防止离开activity再次回来时fragment重叠
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState( savedInstanceState );
        for (int i = 0; i < rgGroup.getChildCount(); i++) {
            RadioButton mTab = (RadioButton) rgGroup.getChildAt(i);
            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentByTag((String) mTab.getTag());
            FragmentTransaction ft = fm.beginTransaction();
            if (fragment != null) {
                if (!mTab.isChecked()) {
                    ft.hide(fragment);
                }
            }
            ft.commit();
        }
    }
    //获取状态栏高度
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
