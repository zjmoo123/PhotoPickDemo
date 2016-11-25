package com.example.zjm.photopickdemo.vedioplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;

import com.example.zjm.photopickdemo.R;
import com.example.zjm.photopickdemo.base.BaseActivity;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class IJKPlayerActivity extends BaseActivity implements SurfaceHolder.Callback, IMediaPlayer.OnCompletionListener, IMediaPlayer.OnErrorListener, IMediaPlayer.OnInfoListener, IMediaPlayer.OnPreparedListener, IMediaPlayer.OnSeekCompleteListener, IMediaPlayer.OnVideoSizeChangedListener {
    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private IjkMediaPlayer player;
    private Display currDisplay;
    private int vWidth, vHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ijkplayer);
        surfaceView = (SurfaceView) findViewById(R.id.ijkplayer_view);
        holder = surfaceView.getHolder();
        holder.addCallback(this);//sufface添加callback监听
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//为了可以播放视频或者使用Camera预览，我们需要指定其Buffer类型
        String url = "http://9890.vod.myqcloud.com/9890_9c1fa3e2aea011e59fc841df10c92278.f20.mp4";
        player = new IjkMediaPlayer();
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
        player.setOnInfoListener(this);
        player.setOnPreparedListener(this);
        player.setOnSeekCompleteListener(this);
        player.setOnVideoSizeChangedListener(this);
        Log.e("VideoSurfaceDemoActivity", "Begin:::" + "surfaceDestroyed called");
//        String dataPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/20161125_113545.mp4";
        try {
            player.setDataSource(this, Uri.parse(url));
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
        currDisplay = this.getWindowManager().getDefaultDisplay();
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
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }

    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        // 当MediaPlayer播放完成后触发
        Logger.v("Play Over:::" + "onComletion called");
        // this.finish();
    }

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
        Logger.v("Play Error:::" + "onError called");
        switch (i) {
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
    public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
        // 当一些特定信息出现或者警告时触发
        switch (i) {
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
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        // 当prepare完成后，该方法触发，在这里我们播放视频
        Log.e("VideoSurfaceDemoActivity", "onPrepared:::");
        //首先取得video的宽和高
        vWidth = player.getVideoWidth();
        vHeight = player.getVideoHeight();
        Log.e("VideoSurfaceDemoActivity", vWidth + "    " + vHeight);

        if (vWidth > currDisplay.getWidth() || vHeight > currDisplay.getHeight()) {
            //如果video的宽或者高超出了当前屏幕的大小，则要进行缩放
            Log.e("VideoSurfaceDemoActivity", currDisplay.getWidth() + "    " + currDisplay.getHeight());
            float wRatio = (float) vWidth / (float) currDisplay.getWidth();
            float hRatio = (float) vHeight / (float) currDisplay.getHeight();
            Log.e("VideoSurfaceDemoActivity", wRatio + "    " + hRatio);
            //选择大的一个进行缩放
            float ratio = Math.max(wRatio, hRatio);

            vWidth = (int) Math.ceil((float) vWidth / ratio);
            vHeight = (int) Math.ceil((float) vHeight / ratio);
            Log.e("VideoSurfaceDemoActivity", vWidth + "    " + vHeight);

            //设置surfaceView的布局参数
            surfaceView.setLayoutParams(new LinearLayout.LayoutParams(vWidth, vHeight));

            //然后开始播放视频

            player.start();

            Log.e("VideoSurfaceDemoActivity", "start");
        }

    }

    @Override
    public void onSeekComplete(IMediaPlayer iMediaPlayer) {
        // seek操作完成时触发
        Logger.v("Seek Completion" + "onSeekComplete called");
    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {
        // 当video大小改变时触发
        //这个方法在设置player的source后至少触发一次
        Logger.v("Video Size Change" + "onVideoSizeChanged called");
    }
}
