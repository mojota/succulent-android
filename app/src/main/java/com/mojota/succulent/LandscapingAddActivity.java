package com.mojota.succulent;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mojota.succulent.utils.CodeConstants;

/**
 * 添加造
 * Created by mojota on 18-8-3
 */
public class LandscapingAddActivity extends BaseActivity implements View.OnClickListener {
    private Button mBtClose;
    private TextInputLayout mTiTitle;
    private EditText mEtTitle;
    private ImageButton mIbtPic1;
    private ImageButton mIbtPic2;
    private ImageButton mIbtPic3;
    private ImageButton mIbtPic4;
    private CheckBox mCbPermission;
    private Button mBtCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landscaping_add);


        mBtClose = findViewById(R.id.bt_close);
        mBtClose.setOnClickListener(this);
        mTiTitle = findViewById(R.id.ti_title);
        mEtTitle = findViewById(R.id.et_title);
        mIbtPic1 = findViewById(R.id.ibt_pic1);
        mIbtPic1.setOnClickListener(this);
        mIbtPic2 = findViewById(R.id.ibt_pic2);
        mIbtPic2.setOnClickListener(this);
        mIbtPic3 = findViewById(R.id.ibt_pic3);
        mIbtPic3.setOnClickListener(this);
        mIbtPic4 = findViewById(R.id.ibt_pic4);
        mIbtPic4.setOnClickListener(this);
        mCbPermission = findViewById(R.id.cb_permission);
        mBtCommit = findViewById(R.id.bt_commit);
        mBtCommit.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {
        // 禁用返回键
        // super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_close:
                finish();
                break;
            case R.id.ibt_pic1:
                showPicDialog(mIbtPic1);
                break;
            case R.id.ibt_pic2:
                showPicDialog(mIbtPic2);
                break;
            case R.id.ibt_pic3:
                showPicDialog(mIbtPic3);
                break;
            case R.id.ibt_pic4:
                showPicDialog(mIbtPic4);
                break;
            case R.id.bt_commit:
                setResult(CodeConstants.RESULT_ADD);
                finish();
                break;
        }

    }
}
