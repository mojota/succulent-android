package com.mojota.succulent.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.mojota.succulent.BuildConfig;
import com.mojota.succulent.R;
import com.mojota.succulent.SucculentApplication;
import com.mojota.succulent.model.UpdateInfo;
import com.mojota.succulent.model.UpdateInfoResponseInfo;
import com.mojota.succulent.network.DownloadRequest;
import com.mojota.succulent.network.GsonPostRequest;
import com.mojota.succulent.network.VolleyErrorListener;
import com.mojota.succulent.network.VolleyUtil;

import java.io.File;

/**
 * 检测升级
 * Created by mojota on 18-12-18
 */
public class CheckUpdateUtil {

    public static void checkUpdate(final Activity activity) {
        String url = UrlConstants.LATEST_APP_URL;
        GsonPostRequest request = new GsonPostRequest(url, null, null,
                UpdateInfoResponseInfo.class, new Response
                .Listener<UpdateInfoResponseInfo>() {

            @Override
            public void onResponse(UpdateInfoResponseInfo responseInfo) {
                if (responseInfo != null && "0".equals(responseInfo.getCode())) {
                    UpdateInfo updateInfo = responseInfo.getData();
                    if (updateInfo != null) {
                        if (updateInfo.getVersionCode() > GlobalUtil.getVersionCode()) {
                            showUpdateDialog(updateInfo, activity);
                        } else {
                            GlobalUtil.makeToast(R.string.str_latest_ver);
                        }
                    }
                }
            }
        }, new VolleyErrorListener());
        VolleyUtil.execute(request);
    }

    /**
     * 显示升级对话框
     */
    private static void showUpdateDialog(final UpdateInfo updateInfo, final Activity
            activity) {
        AlertDialog dialog = new AlertDialog.Builder(activity).setTitle("发现新版本:" +
                updateInfo.getVersionName()).setMessage(updateInfo.getVersionDesc())
                .setPositiveButton("现在升级", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                File file = FileUtil.obtainAppFile(updateInfo.getVersionName());
                if (fileExists(file, updateInfo.getVersionCode())) {
                    installApk(file, activity);
                } else {
                    downloadApp(updateInfo.getDownloadUrl(), file, activity);
                }
            }
        }).setNegativeButton("取消", null).create();
        dialog.show();
    }

    /**
     * 验证要安装的apk是否已下载过
     */
    private static boolean fileExists(File file, int versionCode) {
        if (file.exists()) {
            PackageManager pm = SucculentApplication.getInstance().getPackageManager();
            PackageInfo newPgkInfo = pm.getPackageArchiveInfo(file.getAbsolutePath(),
                    PackageManager.GET_ACTIVITIES);
            if (newPgkInfo != null && versionCode == newPgkInfo.versionCode) {
                return true;
            }
        }
        return false;
    }

    /**
     * 下载apk后安装
     */
    private static void downloadApp(String downloadUrl, final File file, final Activity
            activity) {
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_progress, null);
        final AlertDialog progressDialog = new AlertDialog.Builder(activity).setTitle
                ("正在下载").setView(view).setNegativeButton("关闭", null).create();
        final ProgressBar progressBar = view.findViewById(R.id.pb_progress);
        final TextView tvComplete = view.findViewById(R.id.tv_complete);
        final TextView tvProgress = view.findViewById(R.id.tv_progress);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        DownloadRequest request = new DownloadRequest();
        request.setStateListener(new DownloadRequest.DownloadStateListener() {
            @Override
            public void onProgress(int progress) {
                progressBar.setProgress(progress);
                tvProgress.setText("" + progress + "%");
            }

            @Override
            public void onComplete(boolean success) {
                if (success) {
                    tvComplete.setText(R.string.str_download_complete);
                    installApk(file, activity);
                    progressDialog.dismiss();
                } else {
                    tvComplete.setText(R.string.str_download_failed);
                }
                tvComplete.setVisibility(View.VISIBLE);
            }
        });
        request.startDownload(downloadUrl, file);
    }

    /**
     * 安装app
     */
    private static void installApk(File file, Activity activity) {
        if (activity == null || activity.isDestroyed()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri apkUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            apkUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID +
                    ".provider", file);
        } else {
            apkUri = Uri.fromFile(file);
        }
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }
}
