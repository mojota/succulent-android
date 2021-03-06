package com.mojota.succulent.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mojota.succulent.R;
import com.mojota.succulent.SucculentApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by mojota on 18-8-3.
 */
public class FileUtil {

    /**
     * getExternalFilesDir()应用卸载后文件被自动删除
     */
    public static File getFileFolder() {
        File fileFolder = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdcard = SucculentApplication.getInstance().getExternalFilesDir(null);
            fileFolder = new File(sdcard, "succulentmu");
            if (!fileFolder.exists()) {
                fileFolder.mkdirs();
            }
        }
        return fileFolder;
    }

    /**
     * getExternalStoragePublicDirectory()外部存储空间,所有应用可以访问
     */
    public static File getPublicPicFileFolder() {
        File fileFolder = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdcard = Environment.getExternalStoragePublicDirectory(Environment
                    .DIRECTORY_PICTURES);
            fileFolder = new File(sdcard, "succulentmu");
            if (!fileFolder.exists()) {
                fileFolder.mkdirs();
            }
        }
        return fileFolder;
    }

    /**
     * 拍照图片的文件夹
     */
    public static File getPhotoFileFolder() {
        File fileFolder = new File(getPublicPicFileFolder(), "photo");
        if (!fileFolder.exists()) {
            fileFolder.mkdirs();
        }
        return fileFolder;
    }

    /**
     * 保存图片的文件夹
     */
    public static File getDownloadFileFolder() {
        File fileFolder = new File(getPublicPicFileFolder(), "download");
        if (!fileFolder.exists()) {
            fileFolder.mkdirs();
        }
        return fileFolder;
    }

    /**
     * 保存图片到手机
     */
    public static void saveImage(String imageUrl) {
        final String fileName = imageUrl.substring(imageUrl.lastIndexOf("/"));
        Glide.with(SucculentApplication.getInstance()).asFile().load(imageUrl).into(new SimpleTarget<File>() {

            @Override
            public void onResourceReady(File resource, Transition<? super File> transition) {
                FileOutputStream fos = null;
                FileInputStream fis = null;
                try {
                    File file = new File(getDownloadFileFolder(), fileName);
                    if (file.exists()) {
                        file.delete();
                    }
                    fos = new FileOutputStream(file);
                    fis = new FileInputStream(resource);
                    byte[] buffer = new byte[1024];
                    int count;
                    while ((count = fis.read(buffer)) != -1) {
                        fos.write(buffer, 0, count);
                    }
                    GlobalUtil.makeToast(R.string.str_save_success);
                    scanPhoto(file);
                } catch (Exception e) {
                    GlobalUtil.makeToast(R.string.str_save_failed);
                    e.printStackTrace();
                } finally {
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                        if (fis != null) {
                            fis.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 扫描相册以便更新
     */
    public static void scanPhoto(File file) {
        scanPhoto(Uri.fromFile(file));
    }

    /**
     * 扫描相册以便更新
     */
    private static void scanPhoto(Uri uri) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(uri);
        SucculentApplication.getInstance().sendBroadcast(scanIntent);
    }

    /**
     * 用于保存下载的apk全路径
     */
    public static File obtainAppFile(String versionName) {
        File file = new File(getFileFolder(), "succulentMU" + versionName + ".apk");
        return file;
    }


}
