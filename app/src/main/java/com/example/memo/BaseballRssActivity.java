package com.example.memo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class BaseballRssActivity extends AppCompatActivity {

    private static final String TAG = "RSSread";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baseball_rss);

        Intent i = getIntent();
        String result = i.getStringExtra("RSS");
        displayRss(result);
    }

    public void displayRss(String result) {
        // WebViewを介してUIにHTML文字列を表示。
        WebView myWebView = (WebView) this.findViewById(R.id.webview);
        myWebView.loadData(result, "text/html", null);

    }


}
