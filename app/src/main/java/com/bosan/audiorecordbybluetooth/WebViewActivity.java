package com.bosan.audiorecordbybluetooth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {

    public static void startActivity(Context context,String url){
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        WebView webView = (WebView) findViewById(R.id.web);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.setWebChromeClient(new WebChromeClient());

//        String url = "http://ar.gstai.com/xuniren.html";
//        String url = "https://fanyi.baidu.com/translate?aldtype=16047&query=&keyfrom=baidu&smartresult=dict&lang=auto2zh#auto/zh/";
//        String url = "https://www.bilibili.com/";
//        String url = "https://www.baidu.com/";
//        String url = "http://ar.gstai.com/stb/#/home";
        String url = "http://ar.gstai.com/stb/#/";
//        String url = "http://toptopv.com/";

        String homePageUrl = getIntent().getStringExtra("url");
        webView.loadUrl(homePageUrl);

    }
}