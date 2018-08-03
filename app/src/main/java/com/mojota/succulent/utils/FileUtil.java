package com.mojota.succulent.utils;

import android.os.Environment;

import com.mojota.succulent.SucculentApplication;

import java.io.File;
import java.io.IOException;

/**
 * Created by wangjing on 18-8-3.
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

    public static File createPicFile(String fileName) {
        File file = new File(getPublicPicFileFolder(), fileName);
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return file;
    }

}
