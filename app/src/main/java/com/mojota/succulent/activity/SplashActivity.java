package com.mojota.succulent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mojota.succulent.R;
import com.mojota.succulent.network.OssUtil;
import com.mojota.succulent.utils.GlobalUtil;
import com.mojota.succulent.utils.UserUtil;


/**
 * 启动页
 * Created by mojota on 18-10-2
 **/
public class SplashActivity extends AppCompatActivity {

    private static final long DELAY_TIME = 800;
    private ImageView mIvSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        mIvSplash = findViewById(R.id.iv_splash);
//        String coverUrl = UserUtil.getCover();
//        if (!TextUtils.isEmpty(coverUrl)){
//            Glide.with(this).load(OssUtil.getWholeImageUrl(coverUrl)).apply(GlobalUtil
//                    .getDefaultOptions().error(R.mipmap.ic_splash_default).centerCrop()).into
//                    (mIvSplash);
//        }

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
