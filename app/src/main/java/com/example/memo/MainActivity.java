package com.example.memo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://www.nikkansports.com/baseball/professional/atom.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadPage();
    }

    // AsyncTaskを使用してURLからXMLフィードをダウンロード。
    public void loadPage() {
            new DownloadXmlTask(this).execute(URL);
    }

}
