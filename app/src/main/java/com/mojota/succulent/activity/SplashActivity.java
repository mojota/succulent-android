package com.mojota.succulent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.mojota.succulent.R;


/**
 * 启动页
 * Created by mojota on 18-10-2
 **/
public class SplashActivity extends AppCompatActivity {

    private static final long DELAY_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity
                        .class));
                finish();
            }
        }, DELAY_TIME);
    }
}
