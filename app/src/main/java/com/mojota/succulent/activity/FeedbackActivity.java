package com.mojota.succulent.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.mojota.succulent.R;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.GlobalUtil;
import com.mojota.succulent.utils.UrlConstants;
import com.mojota.succulent.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends BaseActivity {

    private Toolbar mToolbar;
    private EditText mEtFeedback;
    private InputMethodManager mInputManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEtFeedback = findViewById(R.id.et_feedback);
        mInputManager = (InputMethodManager) getSystemService(Context
                .INPUT_METHOD_SERVICE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.commit_menu_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_commit:
                if (TextUtils.isEmpty(mEtFeedback.getText())) {
                    GlobalUtil.makeToast(R.string.str_content_empty);
                } else {
                    commitFeedback();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 关闭软键盘
     */
    private void closeKeyboard() {
        mInputManager.hideSoftInputFromWindow(mEtFeedback.getWindowToken(), 0);
    }

    private void commitFeedback() {
        closeKeyboard();
        String content = mEtFeedback.getText().toString();
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("content", content);
        paramMap.put("userId", UserUtil.getCurrentUserId());
        loadingRequestSubmit(UrlConstants.ADD_FEEDBACK_URL, paramMap, CodeConstants
                .REQUEST_ADD_FEEDBACK);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        switch (requestCode) {
            case CodeConstants.REQUEST_ADD_FEEDBACK:
                GlobalUtil.makeToast(R.string.str_finish_feedback);
                finish();
                break;
        }
    }
}
