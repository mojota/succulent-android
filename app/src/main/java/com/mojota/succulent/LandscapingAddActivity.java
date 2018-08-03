package com.mojota.succulent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 添加造
 * Created by mojota on 18-8-3
*/
public class LandscapingAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landscaping_add);
    }


    @Override
    public void onBackPressed() {
        // 禁用返回键
        // super.onBackPressed();
    }
}
