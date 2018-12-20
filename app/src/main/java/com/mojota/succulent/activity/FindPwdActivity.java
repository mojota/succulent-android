package com.mojota.succulent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.mojota.succulent.R;
import com.mojota.succulent.model.UserInfo;
import com.mojota.succulent.model.UserInfoResponseInfo;
import com.mojota.succulent.network.GsonPostRequest;
import com.mojota.succulent.network.VolleyErrorListener;
import com.mojota.succulent.network.VolleyUtil;
import com.mojota.succulent.utils.CodeConstants;
import com.mojota.succulent.utils.GlobalUtil;
import com.mojota.succulent.utils.UrlConstants;
import com.mojota.succulent.utils.UserUtil;
import com.mojota.succulent.view.PasswordView;

import java.util.HashMap;
import java.util.Map;

/**
 * 找回密码
 * Created by mojota on 18-12-20
 */
public class FindPwdActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private InputMethodManager mInputManager;
    private AutoCompleteTextView mEtEmail;
    private TextInputEditText mEtCode;
    private PasswordView mEtPassword;
    private PasswordView mEtPasswordAgain;
    private Button mBtSendcode;
    private Button mBtCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.str_forget_pw);

        mInputManager = (InputMethodManager) getSystemService(Context
                .INPUT_METHOD_SERVICE);

        mEtEmail = findViewById(R.id.tv_email);
        mEtCode = findViewById(R.id.tv_code);
        mEtPassword = findViewById(R.id.et_password);
        mEtPassword.setHint(R.string.prompt_new_password);
        mEtPasswordAgain = findViewById(R.id.et_password_again);
        mEtPasswordAgain.setHint(R.string.prompt_new_password_again);

        mBtSendcode = findViewById(R.id.bt_send_code);
        mBtSendcode.setOnClickListener(this);

        mBtCommit = findViewById(R.id.bt_commit);
        mBtCommit.setOnClickListener(this);

        mEtEmail.setText(UserUtil.getLastUserName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 关闭软键盘
     */
    private void closeKeyboard() {
        mInputManager.hideSoftInputFromWindow(mEtEmail.getWindowToken(), 0);
        mInputManager.hideSoftInputFromWindow(mEtCode.getWindowToken(), 0);
        mInputManager.hideSoftInputFromWindow(mEtPassword.getWindowToken(), 0);
        mInputManager.hideSoftInputFromWindow(mEtPasswordAgain.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_send_code:
                String email = mEtEmail.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    mEtEmail.setError(getString(R.string.error_field_required));
                    mEtEmail.requestFocus();
                    return;
                } else if (!UserUtil.isEmailValid(email)) {
                    mEtEmail.setError(getString(R.string.error_invalid_email));
                    mEtEmail.requestFocus();
                    return;
                }
                mBtSendcode.setEnabled(false);
                new CountDownTimer(GlobalUtil.ONE_MINUTE, GlobalUtil.ONE_SECOND) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        mBtSendcode.setText(millisUntilFinished / 1000 + "秒");
                    }

                    @Override
                    public void onFinish() {
                        mBtSendcode.setText(R.string.str_send_code);
                        mBtSendcode.setEnabled(true);
                    }
                }.start();
                break;
            case R.id.bt_commit:
                attemptModifyPwd();
                break;
        }
    }

    /**
     * 请求前校验
     */
    private void attemptModifyPwd() {
        boolean cancel = false;
        View focusView = null;
        String email = mEtEmail.getText().toString();
        String tempCode = mEtCode.getText().toString();
        String password = mEtPassword.getText();
        String passwordAgain = mEtPasswordAgain.getText();
        if (TextUtils.isEmpty(email)) {
            mEtEmail.setError(getString(R.string.error_field_required));
            focusView = mEtEmail;
            cancel = true;
        } else if (!UserUtil.isEmailValid(email)) {
            mEtEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEtEmail;
            cancel = true;
        }
        if (TextUtils.isEmpty(tempCode)) {
            mEtCode.setError(getString(R.string.error_code_empty));
            focusView = mEtCode;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            mEtPassword.setError(getString(R.string.prompt_new_password));
            focusView = mEtPassword;
            cancel = true;
        }

        if (!TextUtils.isEmpty(password) && !UserUtil.isPasswordValid(password)) {
            mEtPassword.setError(getString(R.string.error_invalid_password));
            focusView = mEtPassword;
            cancel = true;
        }
        if (!TextUtils.isEmpty(password) && !password.equals(passwordAgain)) {
            mEtPasswordAgain.setError(getString(R.string.error_password_not_same));
            focusView = mEtPasswordAgain;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            requestModifyPwd(tempCode, email, password);
        }
    }

    /**
     * 请求重置密码
     */
    private void requestModifyPwd(String tempCode, String email, String password) {
        closeKeyboard();
        Map<String, String> map = new HashMap<String, String>();
        map.put("userName", email);
        map.put("tempCode", tempCode);
        map.put("password", password);
        loadingRequestSubmit(UrlConstants.RESET_PWD_URL, map, CodeConstants
                .REQUEST_RESET_PWD);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        GlobalUtil.makeToast("重置密码成功");
        finish();
    }
}
