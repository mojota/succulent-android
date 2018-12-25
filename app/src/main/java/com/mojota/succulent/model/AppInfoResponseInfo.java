package com.mojota.succulent.model;

/**
 * Created by wangjing on 18-12-14.
 */
public class AppInfoResponseInfo extends ResponseInfo {

    AppInfo data;

    public AppInfo getData() {
        return data;
    }

    public void setData(AppInfo data) {
        this.data = data;
    }
}
