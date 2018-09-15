package lf.com.android.blackfishdemo.util;

import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebSettingUtil {
    public static void setSetting(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持jsp脚本

        webView.canGoBack();
        //自适应屏幕
        webSettings.setUseWideViewPort(true);//将图片调整到合适的webView的大小
        webSettings.setLoadWithOverviewMode(true);//缩放至屏幕大小

        //缩放操作
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);//设置内置缩放控件
        webSettings.setDisplayZoomControls(false);//隐藏原生的缩放控件

        //优先使用缓存
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置可访问文件
        webSettings.setAllowFileAccess(true);
        //支持通过JS打开新的窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");

        //设置网页还是在WebView中显示
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.toString());
                return true;
            }
        });

    }
}
