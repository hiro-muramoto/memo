package com.example.memo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class BaseballRssActivity extends AppCompatActivity {

    private static final String URL = "https://www.nikkansports.com/baseball/professional/atom.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baseball_rss);

        loadPage();
    }

    // AsyncTaskを使用してURLからXMLフィードをダウンロード。
    public void loadPage() {
        new DownloadXmlTask(this).execute(URL);
    }

    public void onTaskFinished() {
        Log.d("taskfinished", "タスク終了");
        Toast.makeText(this, "通信に成功しました", Toast.LENGTH_LONG).show();
    }

    public void onTaskCancelled() {
        Log.d("taskcancelled", "通信失敗");
        Toast.makeText(this, "通信に失敗しました", Toast.LENGTH_LONG).show();
    }

}
