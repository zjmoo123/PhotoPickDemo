package com.example.zjm.photopickdemo.vedioplayer;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.zjm.photopickdemo.R;
import com.example.zjm.photopickdemo.base.BaseActivity;
import com.orhanobut.logger.Logger;

import java.io.IOException;

public class VideoSurfaceDemoActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnVideoSizeChangedListener, MediaPlayer.OnBufferingUpdateListener {

    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private MediaPlayer player;
    private int vWidth, vHeight;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向

        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {

        }
        setContentView(R.layout.activity_video_surface_demo);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_video_surface_demo);
        surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        holder = surfaceView.getHolder();
        holder.addCallback(this);//sufface添加callback监听
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//为了可以播放视频或者使用Camera预览，我们需要指定其Buffer类型
        String url = "http://9890.vod.myqcloud.com/9890_9c1fa3e2aea011e59fc841df10c92278.f20.mp4";
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
        player.setOnInfoListener(this);
        player.setOnPreparedListener(this);
        player.setOnSeekCompleteListener(this);
        player.setOnVideoSizeChangedListener(this);
        player.setOnBufferingUpdateListener(this);
        Log.e("VideoSurfaceDemoActivity", "Begin:::" + "surfaceDestroyed called");
        String dataPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/20161125_113545.mp4";
        String dataPath1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/123.mp4";
        try {
            player.setDataSource(dataPath1);
            Log.e("VideoSurfaceDemoActivity", "dataPath:::" + url);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Log.e("VideoSurfaceDemoActivity", "Exception:::" + e.toString());
        } catch (IllegalStateException e) {
            e.printStackTrace();
            Log.e("VideoSurfaceDemoActivity", "Exception:::" + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("VideoSurfaceDemoActivity", "Exception:::" + e.toString());
        }
        //然后，我们取得当前Display对象

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        player.setDisplay(holder);
        player.prepareAsync();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // 当Surface尺寸等参数改变时触发
        Logger.v("Surface Change:::" + "surfaceChanged called");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Logger.v("Surface Destory:::" + "surfaceDestroyed called");
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // 当MediaPlayer播放完成后触发
        Logger.v("Play Over:::" + "onComletion called");
        player.start();
        // this.finish();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Logger.v("Play Error:::" + "onError called");
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Logger.v("Play Error:::" + "MEDIA_ERROR_SERVER_DIED");
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Logger.v("Play Error:::" + "MEDIA_ERROR_UNKNOWN");
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        // 当一些特定信息出现或者警告时触发
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                break;
            case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                break;
            case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                break;
            case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                break;
        }
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        // 当prepare完成后，该方法触发，在这里我们播放视频
        Log.e("VideoSurfaceDemoActivity", "onPrepared:::");
        //首先取得video的宽和高
        vWidth = player.getVideoWidth();
        vHeight = player.getVideoHeight();
        Log.e("VideoSurfaceDemoActivity_video", vWidth + "    " + vHeight);
        Log.e("VideoSurfaceDemoActivity_layout", relativeLayout.getWidth() + "    " + relativeLayout.getHeight());
        if (vHeight >= vWidth) {
            //竖的视屏
            if (vWidth > relativeLayout.getWidth() || vHeight > relativeLayout.getHeight()) {
                //如果video的宽或者高超出了当前屏幕的大小，则要进行缩放
                Log.e("VideoSurfaceDemoActivity", relativeLayout.getWidth() + "    " + relativeLayout.getHeight());
                float wRatio = (float) vWidth / (float) relativeLayout.getWidth();
                float hRatio = (float) vHeight / (float) relativeLayout.getHeight();
                Log.e("VideoSurfaceDemoActivity", wRatio + "    " + hRatio);
                //选择大的一个进行缩放
                float ratio = Math.max(wRatio, hRatio);
                vWidth = (int) Math.ceil((float) vWidth / ratio);
                vHeight = (int) Math.ceil((float) vHeight / ratio);
                Log.e("VideoSurfaceDemoActivity", vWidth + "    " + vHeight);
            }
            surfaceView.setLayoutParams(getLayout(vWidth, vHeight));
            //然后开始播放视频
            player.start();
        } else {
            //横屏
            if (vWidth > relativeLayout.getWidth() || vHeight > relativeLayout.getHeight()) {
                //如果video的宽或者高超出了当前屏幕的大小，则要进行缩放
                Log.e("VideoSurfaceDemoActivity", relativeLayout.getWidth() + "    " + relativeLayout.getHeight());
                float wRatio = (float) vWidth / (float) relativeLayout.getWidth();
                float hRatio = (float) vHeight / (float) relativeLayout.getHeight();
                Log.e("VideoSurfaceDemoActivity", wRatio + "    " + hRatio);
                //选择大的一个进行缩放
                float ratio = Math.max(wRatio, hRatio);
                vWidth = (int) Math.ceil((float) vWidth / ratio);
                vHeight = (int) Math.ceil((float) vHeight / ratio);
                Log.e("VideoSurfaceDemoActivity", vWidth + "    " + vHeight);
                //设置surfaceView的布局参数
                //surfaceView.setLayoutParams(getLayout(vWidth, vHeight));
                //然后开始播放视频
                player.start();
                Log.e("VideoSurfaceDemoActivity", "start");
            } else {
                vHeight = (int) Math.ceil(vHeight * relativeLayout.getWidth() / vWidth);
                vWidth = relativeLayout.getWidth();
                surfaceView.setLayoutParams(getLayout(vWidth, vHeight));
                //然后开始播放视频
                player.start();
            }
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        // seek操作完成时触发
        Logger.v("Seek Completion" + "onSeekComplete called");
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        // 当video大小改变时触发
        //这个方法在设置player的source后至少触发一次
        Logger.v("Video Size Change" + "onVideoSizeChanged called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }

    private RelativeLayout.LayoutParams getLayout(int width, int height) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        return layoutParams;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }
}
