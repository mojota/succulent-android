package com.mojota.succulent.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mojota.succulent.R;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.GlobalUtil;
import com.mojota.succulent.utils.UrlConstants;
import com.mojota.succulent.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加造景后花园
 * created by mojota on 18-8-3
 */
public class LandscapingAddActivity extends PhotoChooseSupportActivity implements View
        .OnClickListener {
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
                showPicDialog(mIbtPic1, null);
                break;
            case R.id.ibt_pic2:
                showPicDialog(mIbtPic2, null);
                break;
            case R.id.ibt_pic3:
                showPicDialog(mIbtPic3, null);
                break;
            case R.id.ibt_pic4:
                showPicDialog(mIbtPic4, null);
                break;
            case R.id.bt_commit:
                submit();
                break;
        }

    }

    /**
     * 提交请求
     */
    private void submit() {
        String title = mEtTitle.getText().toString();
        int permission = mCbPermission.isChecked() ? 1 : 0;
        String picUrls = "";
        if (TextUtils.isEmpty(title) && TextUtils.isEmpty(picUrls)) {
            GlobalUtil.makeToast("提交内容为空");
        } else {
            Map<String, String> map = new HashMap<String, String>();
            map.put("userId", UserUtil.getCurrentUserId());
            map.put("noteType", "2");
            map.put("permission", String.valueOf(permission));
            map.put("noteTitle", title);
            map.put("picUrls", picUrls);
            loadingRequestSubmit(UrlConstants.NOTE_ADD_URL, map, CodeConstants.REQUEST_NOTE_ADD);
        }
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        setResult(CodeConstants.RESULT_REFRESH);
        finish();
    }
}
