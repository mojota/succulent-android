package com.mojota.succulent.network;


import com.mojota.succulent.utils.AppLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 支持带进度的下载文件
 * 大文件的下载请使用此类
 * Created by wangjing on 18-2-9.
 */

public class DownloadRequest {
    private static final long INTERVAL = 1000; // 更新进度的间隔时间

    private DownloadStateListener mStateListener;
    private long mInterval = INTERVAL;

    public interface DownloadStateListener {
        void onProgress(int progress);

        void onComplete(boolean success);
    }


    public void setStateListener(DownloadStateListener stateListener) {
        mStateListener = stateListener;
    }

    public void setInterval(long interval) {
        mInterval = interval;
    }

    /**
     * 开始下载
     */
    public void startDownload(String url, File file) {
        startDownload(url, file.getAbsolutePath());
    }

    /**
     * 开始下载
     */
    public void startDownload(final String urlStr, final String filename) {
        AppLog.d("DownloadRequest", "url:" + urlStr + " ,filename:" + filename);
        new Thread((new Runnable() {
            @Override
            public void run() {
                download(urlStr, filename);
            }
        }));
    }

    /**
     * 下载到文件
     */
    private void download(String urlStr, String filename) {
        boolean isSuccess = false;
        InputStream in = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(20000);
            conn.setReadTimeout(20000);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                int length = conn.getContentLength();
                in = conn.getInputStream();
                fos = new FileOutputStream(filename);
                byte[] buffer = new byte[1024];
                long readCount = 0;
                int count;
                long updateTime = 0;
                while ((count = in.read(buffer)) != -1) {
                    fos.write(buffer, 0, count);
                    readCount += count;
                    // 一定时间更新一次进度,默认是1秒
                    if (length > 0 && System.currentTimeMillis() - updateTime >= mInterval) {
                        updateTime = System.currentTimeMillis();
                        updateProgress((int) (readCount * 100 / length));
                    }
                }
                updateProgress(100);
                isSuccess = true;
            } else {
                AppLog.d("DownloadRequest", String.valueOf(conn.getResponseCode()));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            downloadEnd(isSuccess);
        }
    }


    /**
     * 更新下载进度
     */
    private void updateProgress(final int progress) {
//        TaskExecutor.runOnMainThread(new Runnable() {
//            @Override
//            public void run() {
//                AppLog.d("DownloadRequest", "progress:" + progress);
//                if (mStateListener != null) {
//                    mStateListener.onProgress(progress);
//                }
//            }
//        });
    }

    /**
     * 是否下载成功
     */
    private void downloadEnd(final boolean isSuccess) {
//        TaskExecutor.runOnMainThread(new Runnable() {
//            @Override
//            public void run() {
//                AppLog.d("DownloadRequest", "success:" + isSuccess);
//                if (mStateListener != null) {
//                    mStateListener.onComplete(isSuccess);
//                }
//            }
//        });
    }

}
