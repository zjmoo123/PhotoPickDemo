package com.example.zjm.photopickdemo.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zjm on 16-10-9.
 */

public class TcpSocketServer {
    //端口号
    private final static int servicePort = 9999;
    //tcp套接字列表
    private List<Socket> mList = new ArrayList<Socket>();
    //套接字服务
    private ServerSocket server = null;
    //线程池
    private ExecutorService mExecutorService = null;

    public static void main(String[] args) {
        new TcpSocketServer();//启动tcp套接字服务
    }


    public TcpSocketServer() {
        try {
            server = new ServerSocket(servicePort);
            System.out.print("server start ...");
            Socket client = null;
            //创建线程池
            mExecutorService = Executors.newCachedThreadPool();
            while (true) {
                client = server.accept();
                mList.add(client);
                //开始一个新线程处理连接
                mExecutorService.execute(new TcpSocketService(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class TcpSocketService implements Runnable {
        private Socket socket;
        //缓冲区读取
        private BufferedReader in = null;
        //消息
        private String msg = "";

        public TcpSocketService(Socket socket) {
            this.socket = socket;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                msg = "tips: user" + this.socket.getInetAddress() + " come";
                this.sendmsg();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if ((msg = in.readLine()) != null) {
                        if (msg.equals("exit")) {
                            mList.remove(socket);
                            in.close();
                            msg = "tips: users" + this.socket.getInetAddress() + " exit";
                            socket.close();
                            this.sendmsg();
                            break;
                        } else {
                            msg = socket.getInetAddress() + "receive :" + msg;
                            this.sendmsg();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        /**
         * 发送消息
         */
        public void sendmsg() {
            System.out.println(msg);
            int num = mList.size();
            for (int index = 0; index < num; index++) {
                Socket mSocket = mList.get(index);
                PrintWriter pout = null;
                try {
                    pout = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(mSocket.getOutputStream())), true);
                    pout.println(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


