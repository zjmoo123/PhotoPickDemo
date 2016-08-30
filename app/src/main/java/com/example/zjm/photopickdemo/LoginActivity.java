package com.example.zjm.photopickdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.zjm.photopickdemo.login.*;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends AppCompatActivity {
    private Button sina;
    private Button weixin;
    private Button qq;
    private LoginApi api;
    private Button unregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ShareSDK.initSDK(this);
        api=new LoginApi();
        sina=(Button)findViewById(R.id.sina);
        sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Platform platform=ShareSDK.getPlatform(SinaWeibo.NAME);
                api.setPlatform(SinaWeibo.NAME);
//                api.setOnLoginListener(new OnLoginListener() {
//                    public boolean onLogin(String platform, HashMap<String, Object> res) {
//                        // 在这个方法填写尝试的代码，返回true表示还不能登录，需要注册
//                        // 此处全部给回需要注册
//                        return true;
//                    }
//
//                    public boolean onRegister(UserInfo info) {
//                        // 填写处理注册信息的代码，返回true表示数据合法，注册页面可以关闭
//                        return true;
//                    }
//                });
                api.login(LoginActivity.this);
            }

        });
        weixin=(Button)findViewById(R.id.weixin);
            weixin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // Platform platform=ShareSDK.getPlatform(Wechat.NAME);
                    api.setPlatform(Wechat.NAME);
//                    api.setOnLoginListener(new OnLoginListener() {
//                        public boolean onLogin(String platform, HashMap<String, Object> res) {
//                            // 在这个方法填写尝试的代码，返回true表示还不能登录，需要注册
//                            // 此处全部给回需要注册
//                            return true;
//                        }
//
//                        public boolean onRegister(UserInfo info) {
//                            // 填写处理注册信息的代码，返回true表示数据合法，注册页面可以关闭
//                            return true;
//                        }
//                    });
                    api.login(LoginActivity.this);
                }
            });
        qq=(Button)findViewById(R.id.qq);
            qq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // Platform platform=ShareSDK.getPlatform(QQ.NAME);
                    api.setPlatform(QQ.NAME);
//                    api.setOnLoginListener(new OnLoginListener() {
//                        public boolean onLogin(String platform, HashMap<String, Object> res) {
//                            // 在这个方法填写尝试的代码，返回true表示还不能登录，需要注册
//                            // 此处全部给回需要注册
//                            return true;
//                        }
//
//                        public boolean onRegister(UserInfo info) {
//                            // 填写处理注册信息的代码，返回true表示数据合法，注册页面可以关闭
//                            return true;
//                        }
//                    });
                    api.login(LoginActivity.this);
                }
            });
        unregister=(Button)findViewById(R.id.unregister);
        unregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Platform sina=ShareSDK.getPlatform(LoginActivity.this, SinaWeibo.NAME);
                if (sina.isValid()) {
                    Log.i("Register_token","sina");
                    sina.removeAccount();
                    ShareSDK.removeCookieOnAuthorize(true);
                }
                Platform qq=ShareSDK.getPlatform(LoginActivity.this,QQ.NAME);
                if (qq.isValid()){
                    Log.i("Register_token","qq");
                    qq.removeAccount();
                }
                Log.i("Register_token","quxiaochengg");
            }
        });

    }
}
