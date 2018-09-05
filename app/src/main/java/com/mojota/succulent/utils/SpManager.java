package com.mojota.succulent.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.mojota.succulent.SucculentApplication;

/**
 * Created by mojota on 18-9-3.
 */
public class SpManager {

    private final SharedPreferences mSp;
    private final SharedPreferences.Editor mSpEdit;

    public SpManager(String fileName) {
        mSp = SucculentApplication.getInstance().getSharedPreferences(fileName, Context
                .MODE_PRIVATE);
        mSpEdit = mSp.edit();
    }

    public String getString(String key) {
        return mSp.getString(key, "");
    }

    public int getInt(String key) {
        return mSp.getInt(key, 0);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mSp.getBoolean(key, defValue);
    }

    public boolean contains(String key) {
        return mSp.contains(key);
    }

    public void putString(String key, String value) {
        mSpEdit.putString(key, value);
        mSpEdit.commit();
    }

    public void putInt(String key, int value) {
        mSpEdit.putInt(key, value);
        mSpEdit.commit();
    }

    public void putBoolean(String key, boolean value) {
        mSpEdit.putBoolean(key, value);
        mSpEdit.commit();
    }

    public void remove(String key) {
        mSpEdit.remove(key);
        mSpEdit.commit();
    }

    public void clear() {
        mSpEdit.clear();
        mSpEdit.commit();
    }

}
