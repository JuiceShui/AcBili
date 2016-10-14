package acbili.shui.com.acbili;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import acbili.shui.com.base.BaseActivity;
import acbili.shui.com.view.MyVideoView;

/**
 * 作者： max_Shui on 2016/9/26.
 * 邮箱：shuicz0505@qq.com
 * 程序入口的欢迎页
 */

public class WelcomeActivity extends BaseActivity {
    private MyVideoView videoView_Welcome;
    private Button btn_enter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        supportRequestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.activity_welcome );
        init();
    }

    private void init() {
        videoView_Welcome= (MyVideoView) findViewById( R.id.vv_welcome );
        btn_enter= (Button) findViewById( R.id.btn_welcome );
    }

    @Override
    protected boolean translucentStatusBar() {
        return  true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        videoView_Welcome.setVideoURI( Uri.parse( "android.resource://"+getPackageName()+"/"+R.raw.kr36 ) );
        videoView_Welcome.start();
        videoView_Welcome.setOnCompletionListener( new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView_Welcome.start();
            }
        } );
        btn_enter.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView_Welcome.isPlaying())
                {
                    videoView_Welcome.stopPlayback();
                    videoView_Welcome=null;
                }
                Intent intent=new Intent( WelcomeActivity.this,MainActivity.class );
                startActivity( intent );
                WelcomeActivity.this.finish();
            }
        } );
    }
}
