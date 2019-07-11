package com.example.memo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MemoActivity extends AppCompatActivity implements View.OnClickListener {
    // Loggerのタグ
    private static final String TAG = "memo_appli";

    private SharedPreferences mPrefs;

    // パラメータで受け取った日付
    private long mDate = 0;

    private Button baseball_news;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        // SharedPreferencesのインスタンスを取得
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Intentから日付を取得する
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Defines.KEY_DATE)) {
                mDate = intent.getLongExtra(Defines.KEY_DATE, 0);
            }
        }

        baseball_news = (Button) findViewById(R.id.baseball_news);
        baseball_news.setOnClickListener(this);

        // SharedPreferencesから"content"の値を取得する
        String content = mPrefs.getString(getKey(mDate), "");

        // カレンダーを取得
        // Calendar cal = Calendar.getInstance();

        // 今日の日付を文字列に変換する
        String date = Defines.sFmt.format(mDate);

        TextView textSubject = findViewById(R.id.textSubject);
        EditText textContent = findViewById(R.id.textContent);

        textSubject.setText(date);
        textContent.setText(content);
    }

    //ボタンが押された時の処理
    public void onClick(View view){
        //ここに遷移するための処理を追加する
        // MainActivityを呼び出すIntentを生成

        if (view == baseball_news ) {
            Intent intent = new Intent(this, BaseballRssActivity.class);
            startActivity(intent);
        }
    }

    /***
     * 設定ファイルから値を取得するKEYを生成
     *
     * @param value
     * @return
     */
    private String getKey(long value) {
        return "key." + value;
    }

    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.save: {
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setTitle(getString(R.string.confirmation));
                dlg.setPositiveButton(getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 保存処理
                                EditText textcontent = findViewById(R.id.textContent);
                                String content = textcontent.getText().toString();

                                // 保存されたデータを確認する
                                Log.d(TAG, "content=" + content);

                                // Editorのインスタンスを取得
                                SharedPreferences.Editor editor = mPrefs.edit();

                                // "content"に入力された文字列を設定する
                                editor.putString(getKey(mDate), content);

                                // 設定を反映する
                                editor.commit();

                                finish();

                            }
                        }
                );
                dlg.setNegativeButton(getString(R.string.no),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // キャンセル
                            }
                        }
                );
                dlg.show();
            }
            break;

            case R.id.back: {
                finish();
            }
            break;
        }
    }
}