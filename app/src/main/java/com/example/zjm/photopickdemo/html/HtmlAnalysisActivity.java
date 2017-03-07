package com.example.zjm.photopickdemo.html;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.zjm.photopickdemo.R;
import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HtmlAnalysisActivity extends AppCompatActivity {
    static final private String TAG= HtmlAnalysisActivity.class.getSimpleName();
    private TextView mHtmlText;
    private String mHtmlString;
    private WebView mWebView;
    private TextView mImgUrlTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_analysis);
        mHtmlText = (TextView) findViewById(R.id.html_text);
        mWebView = (WebView) findViewById(R.id.web_view);
        mImgUrlTxt=(TextView)findViewById(R.id.img_text);
        mHtmlText.setMovementMethod(ScrollingMovementMethod.getInstance());
        mHtmlString = "<p><img alt=\"\" src=\"http://7xi8d6.com1.z0.glb.clouddn.com/2017-02-27-tumblr_om1aowIoKa1qbw5qso1_540.jpg\" /></p>\r\n\r\n<h3>iOS</h3>\r\n\r\n<ul>\r\n\t<li><a href=\"https://github.com/saoudrizwan/CardSlider\" target=\"_blank\">\u5f88\u6709\u5fc3\u610f\u7684\u4e00\u4e2a\u5361\u7247\u6ed1\u52a8\uff0c\u4ee5\u53ca\u8bc4\u4ef7\u5e93\u3002</a>&nbsp;(\u4ee3\u7801\u5bb6)\r\n\r\n\t<ul>\r\n\t\t<li><a href=\"https://github.com/saoudrizwan/CardSlider\" target=\"_blank\"><img src=\"http://img.gank.io/2a3fdc59-a660-4d39-8a34-416833e4b822\" title=\"\u5f88\u6709\u5fc3\u610f\u7684\u4e00\u4e2a\u5361\u7247\u6ed1\u52a8\uff0c\u4ee5\u53ca\u8bc4\u4ef7\u5e93\u3002\" /></a></li>\r\n\t</ul>\r\n\t</li>\r\n</ul>\r\n\r\n<h3>Android</h3>\r\n\r\n<ul>\r\n\t<li><a href=\"http://skyseraph.com/2017/01/26/Android/Appuim%E6%BA%90%E7%A0%81%E5%89%96%E6%9E%90(Boottrap)/\" target=\"_blank\">Appuim\u6e90\u7801\u5256\u6790(Bootstrap)</a>&nbsp;(SkySeraph)\r\n\r\n\t<ul>\r\n\t\t<li><a href=\"http://skyseraph.com/2017/01/26/Android/Appuim%E6%BA%90%E7%A0%81%E5%89%96%E6%9E%90(Boottrap)/\" target=\"_blank\"><img src=\"http://img.gank.io/cb1f4963-e04b-4427-aeb5-e6b3f0fd1f4f\" title=\"Appuim\u6e90\u7801\u5256\u6790(Bootstrap)\" /></a></li>\r\n\t</ul>\r\n\t</li>\r\n\t<li><a href=\"https://miaoyongjun.github.io/2017/02/26/2017-2-26-mvideo/\" target=\"_blank\">\u4eff\u5fae\u4fe1\u670b\u53cb\u5708\u89c6\u9891\u64ad\u653e\u7279\u6548</a>&nbsp;(miaoyj)\r\n\t<ul>\r\n\t\t<li><a href=\"https://miaoyongjun.github.io/2017/02/26/2017-2-26-mvideo/\" target=\"_blank\"><img src=\"http://img.gank.io/5b913719-2113-4c93-aeb4-eca03b831755\" title=\"\u4eff\u5fae\u4fe1\u670b\u53cb\u5708\u89c6\u9891\u64ad\u653e\u7279\u6548\" /></a></li>\r\n\t</ul>\r\n\t</li>\r\n\t<li><a href=\"https://github.com/TedaLIEz/ParsingPlayer\" target=\"_blank\">A media player playing videos from video sites.</a>&nbsp;(CoXier)\r\n\t<ul>\r\n\t\t<li><a href=\"https://github.com/TedaLIEz/ParsingPlayer\" target=\"_blank\"><img src=\"http://img.gank.io/27a19529-4ef4-43fc-9e72-9e80e0083197\" title=\"A media player playing videos from video sites.\" /></a></li>\r\n\t</ul>\r\n\t</li>\r\n\t<li><a href=\"https://github.com/ImangazalievM/CircleMenu\" target=\"_blank\">\u6f02\u4eae\u7684 Android \u5706\u5f62\u83dc\u5355\u3002</a>&nbsp;(\u4ee3\u7801\u5bb6)\r\n\t<ul>\r\n\t\t<li><a href=\"https://github.com/ImangazalievM/CircleMenu\" target=\"_blank\"><img src=\"http://img.gank.io/48615451-b5f0-44a7-a250-d706a23c1eae\" title=\"\u6f02\u4eae\u7684 Android \u5706\u5f62\u83dc\u5355\u3002\" /></a></li>\r\n\t</ul>\r\n\t</li>\r\n</ul>\r\n\r\n<h3>\u524d\u7aef</h3>\r\n\r\n<ul>\r\n\t<li><a href=\"http://www.jianshu.com/p/40a41bdbe054\" target=\"_blank\">\u8bb0\u5f55\u4e86\u5728\u5f00\u53d1\u817e\u8baf2016\u516c\u53f8\u4ee3\u7801\u62a5\u544a\u65f6\u9047\u5230\u7684\u4e00\u4e9b\u95ee\u9898\uff0c\u4ee5\u53ca\u89e3\u51b3\u65b9\u6848\u3002</a>&nbsp;(None)\r\n\r\n\t<ul>\r\n\t</ul>\r\n\t</li>\r\n\t<li><a href=\"https://mobi-css.github.io/mobi-plugin-flexbox/\" target=\"_blank\">A flexbox grid system - the first Mobi.css plugin</a>&nbsp;(xcatliu)\r\n\t<ul>\r\n\t</ul>\r\n\t</li>\r\n</ul>\r\n\r\n<h3>\u778e\u63a8\u8350</h3>\r\n\r\n<ul>\r\n\t<li><a href=\"http://www.imlifengfeng.com/blog/?p=15\" target=\"_blank\">\u6559\u4f60\u7834\u89e3\u9694\u58c1\u59b9\u5b50\u7684wifi\u5bc6\u7801\uff0c\u6210\u529f\u7387\u9ad8\u8fbe90%</a>&nbsp;(sw)\r\n\r\n\t<ul>\r\n\t</ul>\r\n\t</li>\r\n\t<li><a href=\"https://mpv.io/\" target=\"_blank\">\u4e00\u6b3e\u597d\u7528\u7684Geek\u4e13\u7528\u64ad\u653e\u5668\uff0c\u5168\u5feb\u6377\u952e\u64cd\u4f5c\uff0c\u5168\u5e73\u53f0\u652f\u6301\uff0c\u6700\u91cd\u8981\u7684\uff0c\u514d\u8d39\u5f00\u6e90\u3002</a>&nbsp;(audiebant)\r\n\t<ul>\r\n\t</ul>\r\n\t</li>\r\n</ul>\r\n\r\n<h3>\u62d3\u5c55\u8d44\u6e90</h3>\r\n\r\n<ul>\r\n\t<li><a href=\"https://github.com/LPZilva/Google-Material-Icons-for-Sketch\" target=\"_blank\">Google Material Icons for Sketch</a>&nbsp;(drakeet)\r\n\r\n\t<ul>\r\n\t\t<li><a href=\"https://github.com/LPZilva/Google-Material-Icons-for-Sketch\" target=\"_blank\"><img src=\"http://img.gank.io/3f40f806-d534-4926-93d5-bd1116dda612\" title=\"Google Material Icons for Sketch\" /></a></li>\r\n\t</ul>\r\n\t</li>\r\n</ul>\r\n\r\n<h3>App</h3>\r\n\r\n<ul>\r\n\t<li><a href=\"https://github.com/otale/tale\" target=\"_blank\">Tale \u4e00\u6b3e\u7b80\u6d01\u7f8e\u89c2\u7684Java\u535a\u5ba2\u7ba1\u7406\u53d1\u5e03\u7cfb\u7edf</a>&nbsp;(\u5495\u549a)\r\n\r\n\t<ul>\r\n\t\t<li><a href=\"https://github.com/otale/tale\" target=\"_blank\"><img src=\"http://img.gank.io/ebc695dd-6006-4a7b-8239-2069f07b1735\" title=\"Tale \u4e00\u6b3e\u7b80\u6d01\u7f8e\u89c2\u7684Java\u535a\u5ba2\u7ba1\u7406\u53d1\u5e03\u7cfb\u7edf\" /></a></li>\r\n\t\t<li><a href=\"https://github.com/otale/tale\" target=\"_blank\"><img src=\"http://img.gank.io/0bb2346a-4b75-4936-8f08-1c7dbcfb6d33\" title=\"Tale \u4e00\u6b3e\u7b80\u6d01\u7f8e\u89c2\u7684Java\u535a\u5ba2\u7ba1\u7406\u53d1\u5e03\u7cfb\u7edf\" /></a></li>\r\n\t</ul>\r\n\t</li>\r\n</ul>\r\n\r\n<h3>\u4f11\u606f\u89c6\u9891</h3>\r\n\r\n<ul>\r\n\t<li><a href=\"http://weibo.com/tv/v/8de638e154edd1afb0d8bf37251413b1?fid=1034:8de638e154edd1afb0d8bf37251413b1\" target=\"_blank\">\u8fd9\u662f\u6211\u89c1\u8fc7\u6700\u5e72\u51c0\u5229\u843d\u7684\u5206\u624b\uff0c\u5367\u69fd\uff0c\u8fd9\u4e2a\u5e7f\u544a\u6211\u7ed9\u6ee1\u5206\uff01\uff01\uff01</a>&nbsp;(lxxself)\r\n\r\n\t<ul>\r\n\t</ul>\r\n\t</li>\r\n</ul>\r\n\r\n<p>\u611f\u8c22\u6240\u6709\u9ed8\u9ed8\u4ed8\u51fa\u7684\u7f16\u8f91\u4eec\uff0c\u613f\u5927\u5bb6\u6709\u7f8e\u597d\u4e00\u5929\u3002</p>\r\n";
        Document doc = Jsoup.parse(mHtmlString);
        for (Element x:doc.body().children()){
            Log.d("Tag",x.html());
        }
        mHtmlText.setText(doc.toString());
  //      Logger.e(doc.toString());
        mWebView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        Element content = doc.body();
        Elements img = content.getAllElements();
        List<String> x=new ArrayList<>();


        Elements links = doc.select("li[img]");
        Elements resultLinks = doc.select("ul+ h3");
        Elements h3=doc.select("h3");

        for (Element link : links) {
            //Element xx= (Element) link.nextSibling();
            String linkHref = link.attr("href");
            String linkText = link.text();
        }
//        Logger.d(links.toString());
        Logger.d(resultLinks.toString());
        mImgUrlTxt.setText(img.text());
        mWebView.loadData(mHtmlString, "text/html; charset=UTF-8", null);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
