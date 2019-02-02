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
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mojota.succulent.R;
import com.mojota.succulent.model.UserInfo;
import com.mojota.succulent.model.UserInfoResponseInfo;
import com.mojota.succulent.network.GsonPostRequest;
import com.mojota.succulent.network.VolleyErrorListener;
import com.mojota.succulent.network.VolleyUtil;
import com.mojota.succulent.utils.AppLog;
import com.mojota.succulent.utils.GlobalUtil;
import com.mojota.succulent.utils.UrlConstants;
import com.mojota.succulent.utils.UserUtil;
import com.mojota.succulent.view.PasswordView;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录和注册
 * Created by mojota on 18-8-27
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

    private static final String TAG = "LoginActivity";
    public static final String ACTION_LOGIN = "com.mojota.succulent.LOGIN";
    private static final String QQ_APP_ID = "101551502";
    // UI references.
    private AutoCompleteTextView mEmailView;
    private PasswordView mEtPassword;
    private PasswordView mEtPasswordAgain;
    private Toolbar mToolbar;
    private Button mBtLogin;
    private Button mBtRegister;
    private MenuItem mActionRegister;
    private InputMethodManager mInputManager;
    private TextView mTvTips;
    private ImageButton mBtQQLogin;
    private Tencent mTencent;

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

        mBtQQLogin = findViewById(R.id.bt_qq_login);
        mBtQQLogin.setOnClickListener(this);

        mBtRegister = findViewById(R.id.bt_register);
        mBtRegister.setOnClickListener(this);

        mTvTips = findViewById(R.id.tv_tips);

        mEmailView.setText(UserUtil.getLastEmail());

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
                    mTvTips.setVisibility(View.VISIBLE);
                    mBtLogin.setVisibility(View.GONE);
                    mActionRegister.setTitle(R.string.str_login);
                    getSupportActionBar().setTitle(R.string.str_register);
                } else if (getString(R.string.str_login).equals(mActionRegister.getTitle())) {
                    mEtPasswordAgain.setVisibility(View.GONE);
                    mBtRegister.setVisibility(View.GONE);
                    mTvTips.setVisibility(View.GONE);
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
                            GlobalUtil.makeToast(getString(R.string
                                    .str_register_failed) + ":" + responseInfo.getMsg());
                        } else {
                            GlobalUtil.makeToast(R.string.str_register_failed);
                        }
                    } else if (type == 0) {
                        if (responseInfo != null && !TextUtils.isEmpty(responseInfo.getMsg()
                        )) {
                            GlobalUtil.makeToast(getString(R.string.str_login_failed) +
                                    ":" + responseInfo.getMsg());
                        } else {
                            GlobalUtil.makeToast(R.string.str_login_failed);
                        }
                    }
                } else {
                    if (type == 1) {
                        GlobalUtil.makeToast(R.string.str_register_successful);
                    } else if (type == 0) {
                        GlobalUtil.makeToast(R.string.str_login_successful);
                    }
                    loginSuccessful(responseInfo.getData());
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
            case R.id.bt_qq_login:
                qqLogin();
                break;
            case R.id.bt_login:
                attemptRegisterOrLogin(0);
                break;
            case R.id.bt_register:
                attemptRegisterOrLogin(1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN || requestCode == Constants.REQUEST_APPBAR) {
            if (mTencent != null) {
                mTencent.onActivityResultData(requestCode, resultCode, data, null);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void qqLogin() {
        mTencent = Tencent.createInstance(QQ_APP_ID, this.getApplicationContext());
        if (mTencent != null) {
            mTencent.login(this, "get_user_info", mLoginListener);
        }
    }

    IUiListener mLoginListener = new IUiListener() {
        @Override
        public void onComplete(Object response) {
            JSONObject jsonResponse = (JSONObject) response;
            if (null == jsonResponse || jsonResponse.length() == 0) {
                AppLog.d(TAG, "返回为空,qq登录失败");
                return;
            }
            String openId = jsonResponse.optString("openid");
            String accessToken = jsonResponse.optString("access_token");
            AppLog.d(TAG, "qq登录成功" + ",openId:" + openId + ",accessToken:" + accessToken);
            // 登录后获取用户信息
            getQQUserInfo(openId, accessToken);
        }
        @Override
        public void onError(UiError uiError) {
            AppLog.d(TAG,
                    "onError:" + "code:" + uiError.errorCode + ", msg:" + uiError.errorMessage + ", detail:" + uiError.errorDetail);
            GlobalUtil.makeToastShort(R.string.str_qq_login_failed);
        }
        @Override
        public void onCancel() {
            AppLog.d(TAG, "onCancel");
        }
    };

    /**
     * 获取qq用户信息
     * sdk中的方法不好用，故用url获取
     */
    private void getQQUserInfo(final String openId, String accessToken) {
        String url =
                "https://graph.qq.com/user/get_user_info?access_token=" + accessToken + "&oauth_consumer_key=" + QQ_APP_ID + "&openid=" + openId;
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject responseInfo) {
                String nickname = "";
                String avatarUrl = "";
                if (responseInfo != null) {
                    nickname = responseInfo.optString("nickname");
                    avatarUrl = responseInfo.optString("figureurl_qq_2");
                }
                // 请求使用qq登录
                requestQQLogin(openId, nickname, avatarUrl);
            }
        }, new VolleyErrorListener(new VolleyErrorListener.RequestErrorListener() {
            @Override
            public void onError(String error) {
                // 请求使用qq登录
                requestQQLogin(openId, "", "");
                GlobalUtil.makeToast(R.string.str_network_error);
            }
        }));
        VolleyUtil.execute(request);
    }


    /**
     * 使用qq登录
     */
    private void requestQQLogin(String userName, String nickname, String avatarUrl) {
        String url = UrlConstants.QQ_LOGIN_URL;
        showProgress(true);
        Map<String, String> map = new HashMap<String, String>();
        map.put("userName", userName);
        map.put("password", "qq");//使用qq登录无需密码，统一置为qq。
        map.put("nickname", nickname);
        map.put("avatarUrl", avatarUrl);
        GsonPostRequest request = new GsonPostRequest(url, null, map, UserInfoResponseInfo
                .class, new Response.Listener<UserInfoResponseInfo>() {

            @Override
            public void onResponse(UserInfoResponseInfo responseInfo) {
                showProgress(false);
                if (responseInfo == null || !"0".equals(responseInfo.getCode()) || responseInfo.getData() == null) {
                    if (responseInfo != null && !TextUtils.isEmpty(responseInfo.getMsg())) {
                        GlobalUtil.makeToast(getString(R.string.str_login_failed) + ":" + responseInfo.getMsg());
                    } else {
                        GlobalUtil.makeToast(R.string.str_login_failed);
                    }
                } else {
                    GlobalUtil.makeToast(R.string.str_login_successful);
                    loginSuccessful(responseInfo.getData());
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

    private void loginSuccessful(UserInfo userInfo) {
        UserUtil.clearUser();
        UserUtil.saveUser(userInfo);
        sendBroadcast(new Intent(ACTION_LOGIN));
        finish();
    }


}

