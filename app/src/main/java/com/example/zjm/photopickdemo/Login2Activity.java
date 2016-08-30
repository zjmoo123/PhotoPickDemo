package com.example.zjm.photopickdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.zjm.photopickdemo.login.LoginApi;
import com.example.zjm.photopickdemo.login.OnLoginListener;
import com.example.zjm.photopickdemo.login.RegActivity;
import com.example.zjm.photopickdemo.login.UserInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class Login2Activity extends AppCompatActivity {
    private Button sina;
    private Button weixin;
    private Button qq;
    private LoginApi api;
    private Button unregister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ShareSDK.initSDK(this);
        sina=(Button)findViewById(R.id.sina2);
        sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                weibo.SSOSetting(false);  //设置false表示使用SSO授权方式
                weibo.setPlatformActionListener(new PlatActionListener()); // 设置分享事件回调
                weibo.authorize();
                weibo.showUser(null);
            }

        });
        weixin=(Button)findViewById(R.id.weixin2);
        weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        qq=(Button)findViewById(R.id.qq2);
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        unregister=(Button)findViewById(R.id.unregister2);
        unregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Platform sina=ShareSDK.getPlatform(Login2Activity.this, SinaWeibo.NAME);
                if (sina.isAuthValid()) {
                    Log.i("quxiao","quxiao");
                    sina.SSOSetting(true);
                    sina.removeAccount();
                    ShareSDK.removeCookieOnAuthorize(true);

                }
            }
        });
    }

    /**
     * 使用目标平台客户端授权的回调
     */
    private class PlatActionListener implements PlatformActionListener {

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            Iterator ite =hashMap.entrySet().iterator();
            while (ite.hasNext()) {
                Map.Entry entry = (Map.Entry)ite.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                System.out.println(key+"： "+value);
            }
            Intent intent=new Intent(Login2Activity.this,RegActivity.class);



        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(Platform platform, int i) {

        }
    }
}
