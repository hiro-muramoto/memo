package com.example.memo;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

// XMLフィードをダウンロードするために使用されるAsyncTaskの実装。
public class DownloadXmlTask extends AsyncTask<String, Void, String> {

    private MainActivity mainActivity;

    private static final String TAG = "RSSread";

    public DownloadXmlTask(MainActivity activity) {
        // 呼び出し元のアクティビティ
        this.mainActivity = activity;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            return loadXmlFromNetwork(urls[0]);
        } catch (IOException e) {
            return "IOException発生";
        } catch (XmlPullParserException e) {
            return "XmlPullParserException発生";
        }
    }

    @Override
    protected void onPostExecute(String result) {

        Log.d(TAG, result);

        // WebViewを介してUIにHTML文字列を表示。
        WebView myWebView = (WebView) mainActivity.findViewById(R.id.webview);
        myWebView.loadData(result, "text/html", null);
    }

    // XMLを解析し、それをHTMLマークアップと組み合わせる。
    // HTML文字列を返す。
    private String loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
        InputStream stream = null;

        // インスタンス化
        RssXmlParser rssXmlParser = new RssXmlParser();
        List<Entry> entries = null;
        String title = null;
        String url = null;
        String summary = null;
        String published = null;

        StringBuilder htmlString = new StringBuilder();

        try {
            stream = downloadUrl(urlString);
            entries = rssXmlParser.parse(stream);
            // 終わったらInputStreamを閉じる
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        // RssXmlParserは、EntryオブジェクトのList(entries)を返す。
        // 各EntryオブジェクトはXMLフィード内の単一の投稿を表す。
        // 各EntryをHTMLマークアップと組み合わせるためにEntryリストを処理。
        // 各Entryは、オプションでテキストサマリーを含むリンクとしてUIに表示。
        for (Entry entry : entries) {
            htmlString.append("<p><a href='");
            htmlString.append(entry.link);
            htmlString.append("'>" + entry.title + "</a></p>");
            htmlString.append("<p>" + entry.summary + "<p>");
            htmlString.append("<p>" + entry.published + "<p>");
        }

        return htmlString.toString();
    }

    // URLの文字列表現が与えられると、接続を設定して入力ストリームを取得
    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();

        int code = conn.getResponseCode();

        return conn.getInputStream();
    }


}
