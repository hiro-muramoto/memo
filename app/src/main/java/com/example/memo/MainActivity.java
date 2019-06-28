package com.example.memo;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Loggerのタグ
    private static final String TAG = "memo";

    private SharedPreferences mPrefs;

    // ここから


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = (RecyclerView) findViewById(R.id.memoRecyclerView);
        MemoRecycleViewAdapter adapter = new MemoRecycleViewAdapter(this.createDataset());

        LinearLayoutManager llm = new LinearLayoutManager(this);

        rv.setHasFixedSize(true);

        rv.setLayoutManager(llm);

        rv.setAdapter(adapter);
    }

    private List<RowMemoUnit> createDataset() {

        List<RowMemoUnit> dataset = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            RowMemoUnit data = new RowMemoUnit();
            data.setTitle("カサレアル　太郎" + i + "号");
            data.setDetail("カサレアル　太郎は" + i + "個の唐揚げが好き");

            dataset.add(data);
        }
        return dataset;
    }
}