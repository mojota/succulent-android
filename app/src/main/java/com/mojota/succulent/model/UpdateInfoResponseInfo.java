package com.mojota.succulent.model;

/**
 * Created by wangjing on 18-12-14.
 */
public class UpdateInfoResponseInfo extends ResponseInfo {

    UpdateInfo data;

    public UpdateInfo getData() {
        return data;
    }

    public void setData(UpdateInfo data) {
        this.data = data;
    }
}
