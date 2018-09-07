package com.mojota.succulent.utils;

import android.text.TextUtils;

import com.mojota.succulent.model.UserInfo;

/**
 * Created by mojota on 18-9-3.
 */
public class UserUtil {


    private static SpManager mSp = new SpManager("user");

    /**
     * 保存登录信息到sp
     */
    public static void saveUser(UserInfo userInfo) {
        mSp.clear();
        mSp.putString(KeyConstants.KEY_USER_ID, userInfo.getUserId());
        mSp.putString(KeyConstants.KEY_USER_NAME, userInfo.getUserName());
        mSp.putString(KeyConstants.KEY_NICKNAME, userInfo.getNickname());
        mSp.putString(KeyConstants.KEY_AVATAR_URL, userInfo.getAvatarUrl());
        mSp.putString(KeyConstants.KEY_REGION, userInfo.getRegion());
        mSp.putString(KeyConstants.KEY_EMAIL, userInfo.getEmail());
        mSp.putString(KeyConstants.KEY_PHONE, userInfo.getPhone());
        mSp.putString(KeyConstants.KEY_COVER_URL, userInfo.getCoverUrl());
    }


    public static UserInfo getUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(mSp.getString(KeyConstants.KEY_USER_ID));
        userInfo.setUserName(mSp.getString(KeyConstants.KEY_USER_NAME));
        userInfo.setNickname(mSp.getString(KeyConstants.KEY_NICKNAME));
        userInfo.setAvatarUrl(mSp.getString(KeyConstants.KEY_AVATAR_URL));
        userInfo.setRegion(mSp.getString(KeyConstants.KEY_REGION));
        userInfo.setEmail(mSp.getString(KeyConstants.KEY_EMAIL));
        userInfo.setPhone(mSp.getString(KeyConstants.KEY_PHONE));
        userInfo.setCoverUrl(mSp.getString(KeyConstants.KEY_COVER_URL));
        return userInfo;
    }

    /**
     * 清除用户信息
     */
    public static void clearUser() {
        mSp.clear();
    }

    /**
     * 获取当前用户id
     */
    public static String getCurrentUserId() {
        return mSp.getString(KeyConstants.KEY_USER_ID);
    }

    public static boolean isLogin() {
        if (!TextUtils.isEmpty(getCurrentUserId())) {
            return true;
        }
        return false;
    }
}
