package com.mojota.succulent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.android.volley.Response;
import com.mojota.succulent.R;
import com.mojota.succulent.model.UserInfo;
import com.mojota.succulent.model.UserInfoResponseInfo;
import com.mojota.succulent.network.GsonPostRequest;
import com.mojota.succulent.network.VolleyErrorListener;
import com.mojota.succulent.network.VolleyUtil;
import com.mojota.succulent.utils.GlobalUtil;
import com.mojota.succulent.utils.UrlConstants;
import com.mojota.succulent.utils.UserUtil;
import com.mojota.succulent.view.PasswordView;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录和注册
 * Created by mojota on 18-8-27
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

    public static final String ACTION_LOGIN = "com.mojota.succulent.LOGIN";
    // UI references.
    private AutoCompleteTextView mEmailView;
    private PasswordView mEtPassword;
    private PasswordView mEtPasswordAgain;
    private Toolbar mToolbar;
    private Button mBtLogin;
    private Button mBtRegister;
    private MenuItem mActionRegister;
    private InputMethodManager mInputManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.str_login);

        mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.tv_email);
        mEtPassword = findViewById(R.id.et_password);
        mEtPasswordAgain = findViewById(R.id.et_password_again);
        mEtPasswordAgain.setHint(R.string.prompt_password_again);
        mEtPasswordAgain.setVisibility(View.GONE);

        mBtLogin = findViewById(R.id.bt_login);
        mBtLogin.setOnClickListener(this);

        mBtRegister = findViewById(R.id.bt_register);
        mBtRegister.setOnClickListener(this);

        mEmailView.setText(UserUtil.getLastUserName());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu_bar, menu);
        mActionRegister = menu.findItem(R.id.action_register);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_register:
                if (getString(R.string.str_register).equals(mActionRegister.getTitle())) {
                    mEtPasswordAgain.setVisibility(View.VISIBLE);
                    mBtRegister.setVisibility(View.VISIBLE);
                    mBtLogin.setVisibility(View.GONE);
                    mActionRegister.setTitle(R.string.str_login);
                    getSupportActionBar().setTitle(R.string.str_register);
                } else if (getString(R.string.str_login).equals(mActionRegister.getTitle())) {
                    mEtPasswordAgain.setVisibility(View.GONE);
                    mBtRegister.setVisibility(View.GONE);
                    mBtLogin.setVisibility(View.VISIBLE);
                    mActionRegister.setTitle(R.string.str_register);
                    getSupportActionBar().setTitle(R.string.str_login);
                }

                break;
            case R.id.action_forget_pw:
                Intent intent = new Intent(LoginActivity.this, FindPwdActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * type 0-登录 1-注册
     * 登录或注册校验
     */
    private void attemptRegisterOrLogin(final int type) {
        // Reset errors.
        mEmailView.setError(null);
        mEtPassword.setError(null);
        mEtPasswordAgain.setError(null);

        String email = mEmailView.getText().toString();
        String password = mEtPassword.getText();
        String passwordAgain = mEtPasswordAgain.getText();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!UserUtil.isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (TextUtils.isEmpty(password)){
            mEtPassword.setError(getString(R.string.prompt_password));
            focusView = mEtPassword;
            cancel = true;
        }
        if (!TextUtils.isEmpty(password) && !UserUtil.isPasswordValid(password)) {
            mEtPassword.setError(getString(R.string.error_invalid_password));
            focusView = mEtPassword;
            cancel = true;
        }
        if (type == 1) {
            if (!TextUtils.isEmpty(password) && !password.equals(passwordAgain)) {
                mEtPasswordAgain.setError(getString(R.string.error_password_not_same));
                focusView = mEtPasswordAgain;
                cancel = true;
            }
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            requestLoginOrRegister(type, email, password);
        }
    }

    /**
     * type 0-登录 1-注册
     * 登录或注册请求
     */
    private void requestLoginOrRegister(final int type, String email, String password) {
        closeKeyboard();
        String url = "";
        if (type == 0) {
            url = UrlConstants.LOGIN_URL;
        } else if (type == 1) {
            url = UrlConstants.REGISTER_URL;
        }
        showProgress(true);
        Map<String, String> map = new HashMap<String, String>();
        map.put("userName", email);
        map.put("password", password);
        GsonPostRequest request = new GsonPostRequest(url, null, map, UserInfoResponseInfo
                .class, new Response.Listener<UserInfoResponseInfo>() {

            @Override
            public void onResponse(UserInfoResponseInfo responseInfo) {
                showProgress(false);
                if (responseInfo == null || !"0".equals(responseInfo.getCode()) ||
                        responseInfo.getData() == null) {
                    if (type == 1) {
                        if (responseInfo != null && !TextUtils.isEmpty(responseInfo.getMsg()
                        )) {
                            GlobalUtil.makeToast("注册失败:" + responseInfo.getMsg());
                        } else {
                            GlobalUtil.makeToast("注册失败");
                        }
                    } else if (type == 0) {
                        if (responseInfo != null && !TextUtils.isEmpty(responseInfo.getMsg()
                        )) {
                            GlobalUtil.makeToast("登录失败:" + responseInfo.getMsg());
                        } else {
                            GlobalUtil.makeToast("登录失败");
                        }
                    }
                } else {
                    if (type == 1) {
                        GlobalUtil.makeToast("注册成功");
                    } else if (type == 0) {
                        GlobalUtil.makeToast("登录成功");
                    }
                    UserUtil.clearUser();
                    UserInfo userInfo = responseInfo.getData();
                    UserUtil.saveUser(userInfo);
                    sendBroadcast(new Intent(ACTION_LOGIN));
                    finish();
                }
            }
        }, new VolleyErrorListener(new VolleyErrorListener.RequestErrorListener() {
            @Override
            public void onError(String error) {
                showProgress(false);
                GlobalUtil.makeToast(R.string.str_network_error);
            }
        }));
        VolleyUtil.execute(request);
    }


    /**
     * 关闭软键盘
     */
    private void closeKeyboard() {
        mInputManager.hideSoftInputFromWindow(mEmailView.getWindowToken(), 0);
        mInputManager.hideSoftInputFromWindow(mEtPassword.getWindowToken(), 0);
        mInputManager.hideSoftInputFromWindow(mEtPasswordAgain.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                attemptRegisterOrLogin(0);
                break;
            case R.id.bt_register:
                attemptRegisterOrLogin(1);
                break;
        }
    }
}

