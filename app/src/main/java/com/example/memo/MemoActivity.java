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
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MemoActivity extends AppCompatActivity {
    // Loggerのタグ
    private static final String TAG = "hogehoge";

    private SharedPreferences mPrefs;

    // パラメータで受け取った日付
    private long mDate = 0;

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