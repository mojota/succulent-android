package com.mojota.succulent.model;

/**
 * 升级信息
 * Created by wangjing on 18-12-14.
 */
public class UpdateInfo extends BaseBean {
    private static final long serialVersionUID = 1L;

    int versionCode;
    String versionName;
    String versionDesc;
    String downloadUrl;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
