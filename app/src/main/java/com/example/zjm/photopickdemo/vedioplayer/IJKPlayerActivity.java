package com.example.zjm.photopickdemo.vedioplayer;

import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.zjm.photopickdemo.R;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Formatter;
import java.util.Locale;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class IJKPlayerActivity extends AppCompatActivity implements SurfaceHolder.Callback, IMediaPlayer.OnCompletionListener, IMediaPlayer.OnErrorListener, IMediaPlayer.OnInfoListener, IMediaPlayer.OnPreparedListener, IMediaPlayer.OnSeekCompleteListener, IMediaPlayer.OnVideoSizeChangedListener, IMediaPlayer.OnBufferingUpdateListener, View.OnClickListener {
    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private IjkMediaPlayer player;
    private Display currDisplay;
    private int vWidth, vHeight;
    private int mDuration, mCurrentTime, mBufferPer;
    private SeekBar mProgressBar;
    private TextView mCurrentText, mTotalText;
    private static final int FADE_OUT = 1;
    private static final int SHOW_PROGRESS = 2;
    private Boolean isVisible = false;
    private Boolean isStart = false;
    StringBuilder mFormatBuilder;
    Formatter mFormatter;
    private ImageButton mMediaControlBtn, mRestartBtn;
    private RelativeLayout relativeLayout, mIjkPlayerLayout;
    private ImageView mFengMian;
    private Boolean isPause = false;
    private int mCurrentBar;
    private ProgressBar mLoadingProgressBar;
    /**
     * 动画控件
     */
    private ImageView mLoading;
    private AnimationDrawable mLoadingAinm;
    private static final String TAG = IJKPlayerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向

        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            final WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_ijkplayer);
        surfaceView = (SurfaceView) findViewById(R.id.ijkplayer_view);
        mProgressBar = (SeekBar) findViewById(R.id.media_bar);
        mCurrentText = (TextView) findViewById(R.id.time_current);
        mTotalText = (TextView) findViewById(R.id.time);
        mMediaControlBtn = (ImageButton) findViewById(R.id.media_control);
        mRestartBtn = (ImageButton) findViewById(R.id.restart_button);
        relativeLayout = (RelativeLayout) findViewById(R.id.show_layout);
        mIjkPlayerLayout = (RelativeLayout) findViewById(R.id.activity_ijkplayer);
        mFengMian = (ImageView) findViewById(R.id.fengmian);
        mLoadingProgressBar = (ProgressBar) findViewById(R.id.progressBar3);
        mMediaControlBtn.setOnClickListener(this);
        mRestartBtn.setOnClickListener(this);
        mIjkPlayerLayout.setOnClickListener(this);
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

        mProgressBar.setMax(1000);
        holder = surfaceView.getHolder();
        holder.addCallback(this);//sufface添加callback监听
        //   holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//为了可以播放视频或者使用Camera预览，我们需要指定其Buffer类型
        String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
        String dataPath1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/123.mp4";
        String dataPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/20161125_113545.mp4";
        player = new IjkMediaPlayer();
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
        player.setOnInfoListener(this);
        player.setOnPreparedListener(this);
        player.setOnSeekCompleteListener(this);
        player.setOnVideoSizeChangedListener(this);
        player.setOnBufferingUpdateListener(this);
        Log.e(TAG, "Begin:::" + "surfaceDestroyed called");
//        String dataPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/20161125_113545.mp4";
//        try {
//            player.setDataSource(this, Uri.parse(url));
//            Log.e(TAG, "dataPath:::" + url);
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            Log.e(TAG, "Exception:::" + e.toString());
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//            Log.e(TAG, "Exception:::" + e.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e(TAG, "Exception:::" + e.toString());
//        }
        //然后，我们取得当前Display对象
        mProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //  txt_cur.setText("当前进度值:" + progress + "  / 100 ");
                Log.d(TAG, "当前进度值:" + progress);
                mCurrentBar = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Toast.makeText(mContext, "触碰SeekBar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(mConte0xt, "放开SeekBar", Toast.LENGTH_SHORT).show();
                long c = (long) mCurrentBar;
                Log.e(TAG, "-----MEIDIA duration" + player.getDuration());
                Log.e(TAG, "---seekBar" + c);
                Log.i(TAG, "----seekto" + player.getDuration() * c / 1000);
                player.seekTo(player.getDuration() * c / 1000);
            }
        });
        currDisplay = this.getWindowManager().getDefaultDisplay();
    }
    @Override
    public void onClick(View v) {
        String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
        String dataPath1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/123.mp4";
        String dataPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/20161125_113545.mp4";
        switch (v.getId()) {
            case R.id.media_control:
                if (!isPause) {
                    player.pause();
                    mMediaControlBtn.setImageDrawable(getDrawable(R.mipmap.start1));
                    isPause = true;
                } else {
                    player.start();
                    mMediaControlBtn.setImageDrawable(getDrawable(R.mipmap.pause1));
                    mRestartBtn.setVisibility(View.GONE);
                    isPause = false;
                    mHandler.sendEmptyMessage(SHOW_PROGRESS);
                }
                break;
            case R.id.restart_button:
                if (!isStart) {
                    Log.d(TAG, "----media---start");
                    try {
                        player.setDataSource(this, Uri.parse(url));
                        Log.e(TAG, "dataPath:::" + url);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Exception:::" + e.toString());
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Exception:::" + e.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Exception:::" + e.toString());
                    }
                    mMediaControlBtn.setImageDrawable(getDrawable(R.mipmap.pause1));
                    player.prepareAsync();
                    isStart = true;
                    mLoadingProgressBar.setVisibility(View.VISIBLE);
                } else {
                    player.seekTo(0);
                    Log.e(TAG, "---seekTo---0");
                    player.start();
                    mMediaControlBtn.setImageDrawable(getDrawable(R.mipmap.pause1));
                    mHandler.sendEmptyMessage(SHOW_PROGRESS);
                }
                // isStart=true;
                mRestartBtn.setVisibility(View.GONE);
                mFengMian.setVisibility(View.GONE);
                //relativeLayout.setVisibility(View.VISIBLE);
                mHandler.sendEmptyMessageDelayed(FADE_OUT, 3000);
                break;
            case R.id.activity_ijkplayer:
                if (!isVisible) {
                    relativeLayout.setVisibility(View.VISIBLE);
                    isVisible = true;
                    mHandler.sendEmptyMessageDelayed(FADE_OUT, 3000);
                } else {
                    relativeLayout.setVisibility(View.GONE);
                    mHandler.removeMessages(FADE_OUT);
                    isVisible = false;
                }
                break;

        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int pos;
            switch (msg.what) {
                case SHOW_PROGRESS:
                    pos = setProgressBar();
                    if (player.isPlaying()) {
                        msg = obtainMessage(SHOW_PROGRESS);
                        sendMessageDelayed(msg, 1000 - (pos % 1000));
                    }
                    break;
                case FADE_OUT:
                    relativeLayout.setVisibility(View.GONE);
                    isVisible = false;
                    break;
            }
        }
    };

    private int setProgressBar() {
        if (player == null) {
            return 0;
        }
        int position = (int) player.getCurrentPosition();
        int duration = (int) player.getDuration();
        if (mProgressBar != null) {
            if (duration > 0) {
                // use long to avoid overflow
                long pos = 1000L * position / duration;
                mProgressBar.setProgress((int) pos);
            }
            int percent = mBufferPer;
            mProgressBar.setSecondaryProgress(percent * 10);
        }

        if (mTotalText != null)
            mTotalText.setText(stringForTime(duration));
        if (mCurrentText != null)
            mCurrentText.setText(stringForTime(position));

        return position;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        player.setDisplay(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // 当Surface尺寸等参数改变时触发
        Logger.t(TAG).v("Surface Change:::" + "surfaceChanged called");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Logger.t(TAG).v("Surface Destory:::" + "surfaceDestroyed called");
    }


    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        // 当MediaPlayer播放完成后触发
        Logger.t(TAG).v("Play Over:::" + "onComletion called");
        mRestartBtn.setVisibility(View.VISIBLE);
        mMediaControlBtn.setImageDrawable(getDrawable(R.mipmap.start1));
        // this.finish();
    }

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
        Logger.t(TAG).v("Play Error:::" + "onError called" + i);
        switch (i) {
            case IjkMediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Logger.t(TAG).v("Play Error:::" + "MEDIA_ERROR_SERVER_DIED");
                break;
            case IjkMediaPlayer.MEDIA_ERROR_UNKNOWN:
                Logger.t(TAG).v("Play Error:::" + "MEDIA_ERROR_UNKNOWN");
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
            case IjkMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                Log.e(TAG, "MEDIA_INFO_BAD_INTERLEAVING" + i1);
                break;
            case IjkMediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                Log.e(TAG, "MEDIA_INFO_METADATA_UPDATE" + i1);
                break;
            case IjkMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                Log.e(TAG, "MEDIA_INFO_VIDEO_TRACK_LAGGING" + i1);
                break;
            case IjkMediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                Log.e(TAG, "MEDIA_INFO_NOT_SEEKABLE" + i1);
                break;
        }
        return false;
    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        // 当prepare完成后，该方法触发，在这里我们播放视频
        Log.e(TAG, "onPrepared:::");
        //首先取得video的宽和高
        vWidth = player.getVideoWidth();
        vHeight = player.getVideoHeight();
        Log.e(TAG, "duration" + player.getDuration());
        Log.e(TAG, vWidth + "    " + vHeight);
        Log.e(TAG, currDisplay.getWidth() + "    " + currDisplay.getHeight());
        if (vHeight >= vWidth) {
            //竖的视屏
            if (vWidth > currDisplay.getWidth() || vHeight > currDisplay.getHeight()) {
                //如果video的宽或者高超出了当前屏幕的大小，则要进行缩放
                Log.e(TAG, currDisplay.getWidth() + "    " + currDisplay.getHeight());
                float wRatio = (float) vWidth / (float) currDisplay.getWidth();
                float hRatio = (float) vHeight / (float) currDisplay.getHeight();
                Log.e(TAG, wRatio + "    " + hRatio);
                //选择大的一个进行缩放
                float ratio = Math.max(wRatio, hRatio);
                vWidth = (int) Math.ceil((float) vWidth / ratio);
                vHeight = (int) Math.ceil((float) vHeight / ratio);
                Log.e(TAG, vWidth + "    " + vHeight);
            }
            surfaceView.setLayoutParams(getLayout(vWidth, vHeight));
            //然后开始播放视频
            player.start();
        } else {
            //横屏
            if (vWidth > currDisplay.getWidth() || vHeight > currDisplay.getHeight()) {
                //如果video的宽或者高超出了当前屏幕的大小，则要进行缩放
                Log.e(TAG, currDisplay.getWidth() + "    " + currDisplay.getHeight());
                float wRatio = (float) vWidth / (float) currDisplay.getWidth();
                float hRatio = (float) vHeight / (float) currDisplay.getHeight();
                Log.e(TAG, wRatio + "    " + hRatio);
                //选择大的一个进行缩放
                float ratio = Math.max(wRatio, hRatio);
                vWidth = (int) Math.ceil((float) vWidth / ratio);
                vHeight = (int) Math.ceil((float) vHeight / ratio);
                Log.e(TAG, vWidth + "    " + vHeight);
                //设置surfaceView的布局参数
                surfaceView.setLayoutParams(getLayout(vWidth, vHeight));
                //然后开始播放视频
                player.start();
                Log.e(TAG, "start");
            } else {
                vHeight = (int) Math.ceil(vHeight * currDisplay.getWidth() / vWidth);
                vWidth = currDisplay.getWidth();
                surfaceView.setLayoutParams(getLayout(vWidth, vHeight));
                //然后开始播放视频
                player.start();
            }
        }
        relativeLayout.setVisibility(View.VISIBLE);
        mLoadingProgressBar.setVisibility(View.GONE);
        mHandler.sendEmptyMessage(SHOW_PROGRESS);

    }

    @Override
    public void onSeekComplete(IMediaPlayer iMediaPlayer) {
        // seek操作完成时触发
        Logger.t(TAG).v("Seek Completion" + "onSeekComplete called");
    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {
        // 当video大小改变时触发
        //这个方法在设置player的source后至少触发一次
        Logger.t(TAG).v("Video Size Change" + "onVideoSizeChanged called");
    }

    private RelativeLayout.LayoutParams getLayout(int width, int height) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        return layoutParams;
    }

    //缓存进度
    @Override
    public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
        Log.d(TAG, "PERCENT" + i);
        mBufferPer = i;
    }

    private String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }


    /**
     * 配制发生变化，这里处理屏幕旋转的事件
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        vWidth = player.getVideoWidth();
        vHeight = player.getVideoHeight();
        Log.e(TAG, "duration" + player.getDuration());
        Log.e(TAG, vWidth + "    " + vHeight);
        Log.e(TAG, currDisplay.getWidth() + "    " + currDisplay.getHeight());
        if (vHeight >= vWidth) {
            //竖的视屏
            if (vWidth > currDisplay.getWidth() || vHeight > currDisplay.getHeight()) {
                //如果video的宽或者高超出了当前屏幕的大小，则要进行缩放
                Log.e(TAG, currDisplay.getWidth() + "    " + currDisplay.getHeight());
                float wRatio = (float) vWidth / (float) currDisplay.getWidth();
                float hRatio = (float) vHeight / (float) currDisplay.getHeight();
                Log.e(TAG, wRatio + "    " + hRatio);
                //选择大的一个进行缩放
                float ratio = Math.max(wRatio, hRatio);
                vWidth = (int) Math.ceil((float) vWidth / ratio);
                vHeight = (int) Math.ceil((float) vHeight / ratio);
                Log.e(TAG, vWidth + "    " + vHeight);
            }
            surfaceView.setLayoutParams(getLayout(vWidth, vHeight));
            //然后开始播放视频
            //  player.start();
        } else {
            //横屏
            if (vWidth > currDisplay.getWidth() || vHeight > currDisplay.getHeight()) {
                //如果video的宽或者高超出了当前屏幕的大小，则要进行缩放
                Log.e(TAG, currDisplay.getWidth() + "    " + currDisplay.getHeight());
                float wRatio = (float) vWidth / (float) currDisplay.getWidth();
                float hRatio = (float) vHeight / (float) currDisplay.getHeight();
                Log.e(TAG, wRatio + "    " + hRatio);
                //选择大的一个进行缩放
                float ratio = Math.max(wRatio, hRatio);
                vWidth = (int) Math.ceil((float) vWidth / ratio);
                vHeight = (int) Math.ceil((float) vHeight / ratio);
                Log.e(TAG, vWidth + "    " + vHeight);
                //设置surfaceView的布局参数
                surfaceView.setLayoutParams(getLayout(vWidth, vHeight));
                //然后开始播放视频
                // player.start();
                Log.e(TAG, "start");
            } else {
                vHeight = (int) Math.ceil(vHeight * currDisplay.getWidth() / vWidth);
                vWidth = currDisplay.getWidth();
                surfaceView.setLayoutParams(getLayout(vWidth, vHeight));
                //然后开始播放视频
                // player.start();
            }
        }
        //根据app内的设置，来设置屏幕方向，并判断是否全屏
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        } else {
            final WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            // screenLandscape();
        }
    }

}
