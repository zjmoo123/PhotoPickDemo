package com.example.zjm.photopickdemo.socket;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zjm.photopickdemo.R;

import java.io.File;

public class SocketActivity extends AppCompatActivity {
    private static String serverIp = "192.168.100.249";
    private final static int serverPort = 9999;
    private TextView showTv;
    private EditText contentEdit;
    private Button sendBtn;
    private Button mService;
    private Button mClient;

    private TcpSocketClient mTcpSocketClient;
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        showTv = (TextView) findViewById(R.id.tv_show);
        contentEdit = (EditText) findViewById(R.id.edt_content);
        mService=(Button)findViewById(R.id.service);
        mClient=(Button)findViewById(R.id.client);
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        //设置服务器ip
        serverIp=intToIp(wifiInfo.getIpAddress());
        //int ipAddress = wifiInfo.getIpAddress();
        mService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Runnable() {

                    @Override
                    public void run() {
                        new TcpSocketServer().startService();
                    }
                };
            }
        });
        mTcpSocketClient = new TcpSocketClient(serverIp, serverPort,
                new TcpSocketClient.TcpSocketListener() {
                    @Override
                    public void callBackContent(final String content) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (showTv != null)
                                    showTv.setText(showTv.getText().toString() + content);
                            }
                        });
                    }

                    @Override
                    public void clearInputContent() {
                        if (contentEdit != null){
                            contentEdit.setText("");
                        }
                    }
                });
        mClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTcpSocketClient.startTcpSocketConnect();
            }
        });

        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg=contentEdit.getText().toString().trim();
                mTcpSocketClient.sendMessageByTcpSocket(msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mTcpSocketClient!=null){
            mTcpSocketClient.sendMessageByTcpSocket("exit");
        }
        super.onDestroy();
    }
    private String intToIp(int i) {

        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }
}
