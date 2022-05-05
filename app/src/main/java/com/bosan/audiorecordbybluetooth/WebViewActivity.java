package com.bosan.audiorecordbybluetooth;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        WebView webView = (WebView) findViewById(R.id.web);

        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("utf-8") ;
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){//不写的话自动跳到默认浏览器了 跳出APP
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.startsWith("intent")||url.startsWith("youku")){
                    return true;
                }else{
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }
        });
//        String url = "http://ar.gstai.com/xuniren.html";
        String url = "http://ar.gstai.com/stb/#/home";
        webView.loadUrl(url);


//        //该方法解决的问题是打开浏览器不调用系统浏览器，直接用webview打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}