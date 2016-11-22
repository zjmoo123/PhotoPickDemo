package com.example.zjm.photopickdemo.socket;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by zjm on 16-10-9.
 */

public class TcpSocketClient {
    //192.168.100.249
    private String serverIp = "192.168.100.249";
    private int serverPort = 9999;
    private Socket mSocket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private TcpSocketListener mTcpSocketListener;
    private String content = "";

    public TcpSocketClient(TcpSocketListener mTcpSocketListener) {
        this.mTcpSocketListener = mTcpSocketListener;
    }

    public TcpSocketClient(String serverIp, int serverPort, TcpSocketListener mTcpSocketListener) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.mTcpSocketListener = mTcpSocketListener;
    }

    public void startTcpSocketConnect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket = new Socket(serverIp, serverPort);
                    in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream())), true);
                    while (true) {
                        if (mSocket.isConnected()) {
                            if (!mSocket.isInputShutdown()) {
                                if ((content = in.readLine()) != null) {
                                    content += "\n";
                                    if (mTcpSocketListener != null) {
                                        mTcpSocketListener.callBackContent(content);
                                    }
                                }
                            }
                        }
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendMessageByTcpSocket(String msg) {
        if (mSocket != null && mSocket.isConnected()) {
            if (!mSocket.isOutputShutdown() && out != null) {
                out.println(msg);
                if (mTcpSocketListener != null) {
                    mTcpSocketListener.clearInputContent();
                }
            }
        }
    }

    public interface TcpSocketListener {
        // 回调内容
        void callBackContent(String content);

        // 清除输入框内容
        void clearInputContent();
    }

}
