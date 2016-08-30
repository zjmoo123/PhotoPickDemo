package com.example.zjm.photopickdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;
    private Button b8;
    private Button b9;
    /** Notification构造器 */
    NotificationCompat.Builder mBuilder;
    /** Notification的ID */
    int notifyId = 100;

    /** Notification管理 */
    public NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initService();
        b1=(Button)findViewById(R.id.b1);
        b2=(Button)findViewById(R.id.b2);
        b3=(Button)findViewById(R.id.b3);
        b4=(Button)findViewById(R.id.b4);
        b5=(Button)findViewById(R.id.b5);
        b6=(Button)findViewById(R.id.b6);
        b7=(Button)findViewById(R.id.b7);
        b8=(Button)findViewById(R.id.b8);
        b9=(Button)findViewById(R.id.b9);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        initNotify();
    }
    private void initNotify(){
        Log.i("Notification","显示通知栏");
        mBuilder=new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("测试百题")
                .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
                //.setNumber(number)//显示输了
                .setTicker("测试通知来啦")
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(false)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.ic_launcher);
    }
    private void ShowNotification(){
        mBuilder.setContentTitle("测试标题")
                .setContentText("测试被内容")
                .setTicker("测试通知来啦");
        mNotificationManager.notify(notifyId,mBuilder.build());
    }

    private void showBigStyleNotify(){
        NotificationCompat.InboxStyle inboxStyle=new NotificationCompat.InboxStyle();
        String[] events=new String[3];
        inboxStyle.setBigContentTitle("大视图内容");
        for (int i=0;i<events.length;i++){
            inboxStyle.addLine(events[i]);
        }
        mBuilder.setContentTitle("测试标题")
                .setContentText("测试内容")
                .setStyle(inboxStyle)
                .setTicker("测试通知来啦");
        mNotificationManager.notify(notifyId,mBuilder.build());
    }

    private void showCzNotify(){
        NotificationCompat.Builder mBuilder= new NotificationCompat.Builder(this);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0, getIntent(),0);
        mBuilder.setSmallIcon(R.drawable.ic_launcher)
                .setTicker("常驻通知来啦")
                .setContentTitle("常驻测试")
                .setContentText("使用cancel（）方法才可以把握去掉哦")
                .setContentIntent(pendingIntent);
        Notification mNotification =mBuilder.build();
        mNotification.icon=R.drawable.ic_launcher;
        mNotification.flags=Notification.FLAG_ONGOING_EVENT;
        mNotification.defaults=Notification.DEFAULT_VIBRATE;
        mNotification.tickerText="通知来啦";
        mNotification.when=System.currentTimeMillis();
        mNotificationManager.notify(notifyId,mNotification);
    }
//跳转activity
    public void showIntentActivityNotify(){
        mBuilder.setAutoCancel(true)
                .setContentTitle("测试白头")
                .setContentText("点击跳转")
                .setTicker("点位");
        Intent resultIntent=new Intent(this,LoginActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        mNotificationManager.notify(notifyId,mBuilder.build());
    }
    //跳转apk
    public void showIntentApkNotify(){
        mBuilder.setAutoCancel(true)
                .setContentTitle("打开apk")
                .setContentText("点击打开")
                .setTicker("dakai ");
        Intent intent = getPackageManager().getLaunchIntentForPackage(

                //这个是另外一个应用程序的包名
                "com.sina.weibo");
        PendingIntent contextIntent=PendingIntent.getActivity(this,0,intent,0);
        mBuilder.setContentIntent(contextIntent);
        mNotificationManager.notify(notifyId,mBuilder.build());
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.b1:
                ShowNotification();
                break;
            case R.id.b2:
                showBigStyleNotify();
                break;
            case R.id.b3:
                showCzNotify();
                break;
            case R.id.b4:
                showIntentActivityNotify();
                break;
            case R.id.b5:
                showIntentApkNotify();
                break;
            case R.id.b6:
                clearNotify(notifyId);
                break;
            case R.id.b7:
                clearAllNotify();
                break;

            default:
                break;

        }
    }
    /**
     * 初始化要用到的系统服务
     */
    private void initService() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    /**
     * 清除当前创建的通知栏
     */
    public void clearNotify(int notifyId){
        mNotificationManager.cancel(notifyId);//删除一个特定的通知ID对应的通知
//		mNotification.cancel(getResources().getString(R.string.app_name));
    }

    /**
     * 清除所有通知栏
     * */
    public void clearAllNotify() {
        mNotificationManager.cancelAll();// 删除你发的所有通知
    }

    /**
     * @获取默认的pendingIntent,为了防止2.3及以下版本报错
     * @flags属性:
     * 在顶部常驻:Notification.FLAG_ONGOING_EVENT
     * 点击去除： Notification.FLAG_AUTO_CANCEL
     */
    public PendingIntent getDefalutIntent(int flags){
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }
}
