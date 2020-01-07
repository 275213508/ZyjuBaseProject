package com.example.zyjulib.utile;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import static com.example.zyjulib.resource.Contect.htmlStyle;


public class RichTextUtile {
    public static String getNewContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (org.jsoup.nodes.Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }

    public static void setHtml(WebView web_comment_meinfo_body, String st) {
        web_comment_meinfo_body.setHorizontalScrollBarEnabled(false);//水平不显示
        web_comment_meinfo_body.setVerticalScrollBarEnabled(false); //垂直不显示

        WebSettings webSettings = web_comment_meinfo_body.getSettings();
        webSettings.setDisplayZoomControls(false); //隐藏webview缩放按钮
        webSettings.setJavaScriptEnabled(true);//支持js
        web_comment_meinfo_body.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 得到 URL 可以传给应用中的某个 WebView 页面加载显示
                return true;
            }
        });

        web_comment_meinfo_body.loadDataWithBaseURL(null, getNewContent(htmlStyle+st), "text/html", "UTF-8", null);
    }
}
