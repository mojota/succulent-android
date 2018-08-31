package com.mojota.succulent.model;

/**
 * Created by mojota on 18-8-24.
 */
public class UserInfoResponseInfo extends ResponseInfo {
    UserInfo data;

    public UserInfo getData() {
        return data;
    }

    public void setData(UserInfo data) {
        this.data = data;
    }
}
