package com.example.zjm.photopickdemo.broadcast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.zjm.photopickdemo.R;

public class BroadcastActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mSendNoOrder, mSendOrder;
    private static final String TAG = BroadcastActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        mSendNoOrder = (Button) findViewById(R.id.send_noOrder);
        mSendOrder = (Button) findViewById(R.id.send_order);
        mSendNoOrder.setOnClickListener(this);
        mSendOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_noOrder:
                Log.i(TAG, "开始发送无序广播");
                /**
                 * 无序广播的广播接收者不可以使用setResultData()方法和abortBroadcast()方法，
                 * 如果使用了会报错。
                 * 另外虽然使用getResultData()方法不报错，但是一般没什么意义，结果应该是null，但是在有序广播的第三个参数对应的广播接收者收到的无序
                 * 广播仍然可以使用该方法获取到数据，请看下面的发送有序广播的注释。
                 * 也就是说无序广播不可以被拦截，不可以被终止，不可以被修改.
                 * 无序广播任何接收者都可以接收到，无优先级问题。如果想通过无序广播传递数据，则可以调用intent.putExtra方法传递，
                 * 接收者可通过intent.get...接收，不可通过getResultData接收。
                 */
                Intent nonOrderIntent = new Intent();
                nonOrderIntent.setAction("com.yin.test1broadcast");
                nonOrderIntent.putExtra("info", "中了5000块钱");
                //nonOrderIntent.setPackage(packageName);该广播仅对指定包名的应用有效
                sendBroadcast(nonOrderIntent);
                //sendBroadcast(intent,permission);带权限发送广播
                break;
            case R.id.send_order:
                Log.i(TAG, "开始发送有序广播");
                /**
                 * 发送有序广播：可以被拦截、被终止、被修改，在
                 * sendOrderedBroadcast(intent, null, new Receiver2(), null, 0, "给五保户10000块", null)
                 * 方法中第三个参数是resultReceiver,该广播的各个接收者(包括resultReceiver)接收到广播之
                 * 后(中途被setResultData了，则广播数据是set后的)，resultReceiver会最终再次接收一次广播(前面可能接
                 * 收一次了)，且接收到的广播数据是最后一次被setResultData的数据。即使该广播被某个接收者终止了，resultReceiver
                 * 仍然可以最终再次(同样，前面可能接到一次了)接收广播(最后收到的这个广播其实已经变成了无序广播)。另外，广播接收者中的setResultData,
                 * getResultData,abortBroadCast方法只适用于有序广播。
                 *
                 * sendOrderedBroadcast()的第三个参数需要特别注意，第三个参数是指当该有序广播的所有广播接收者都接收到了该广播后或者某个广播接收者终止了该
                 * 广播后，第三个参数对应的广播接收者仍然可以再收到一次广播，但是，此时收到的广播已经是无序广播了，可以通过调用setResultData()或者abortBroadcast()
                 * 看日志是否报错来验证。也可以根据BroadcastReceiver的isOrderedBroadcast()方法来验证，若其返回值是true，说明是接收到的是有序广播，若为false说明
                 * 接收到的是无序广播
                 */
                Intent intent = new Intent();
                intent.setAction("com.yin.testbroadcast2");
                sendOrderedBroadcast(intent, null, null, null, 0, "youzhongle了10000块", null);
                //sendOrderedBroadcast(intent,permission);给广播指定权限
                break;
            default:
                break;
        }
    }
}
