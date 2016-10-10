package com.example.zjm.photopickdemo.socket;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zjm.photopickdemo.R;

public class SocketActivity extends AppCompatActivity {
    private final static String serverIp = "192.168.100.249";
    private final static int serverPort = 9999;
    private TextView showTv;
    private EditText contentEdit;
    private Button sendBtn;

    private TcpSocketClient mTcpSocketClient;
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        showTv = (TextView) findViewById(R.id.tv_show);
        contentEdit = (EditText) findViewById(R.id.edt_content);
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
        mTcpSocketClient.startTcpSocketConnect();
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
}
