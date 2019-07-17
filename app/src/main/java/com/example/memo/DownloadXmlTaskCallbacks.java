package com.example.memo;

public interface DownloadXmlTaskCallbacks {

    // ボタンタップしたときのグルグルを表示
    public void onPreExecute();

    // タスクが正常に終了
    public void onTaskFinished();

    // 通信に失敗
    public void onTaskCancelled();
}
