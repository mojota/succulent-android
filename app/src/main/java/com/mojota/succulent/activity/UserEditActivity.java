package com.mojota.succulent.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

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

public class UserEditActivity extends BaseActivity implements View.OnClickListener {

    public static final String KEY_USER = "key_user";
    private Toolbar mToolbar;
    private EditText mEtNickname;
    private EditText mEtRegion;
//    private PasswordView mEtPassword;
    private Button mBtSubmit;
    private UserInfo mUser;
    private InputMethodManager mInputManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        mEtNickname = findViewById(R.id.et_nickname);
        mEtRegion = findViewById(R.id.et_region);
//        mEtPassword = findViewById(R.id.et_password);
//        mEtPassword.setHint(R.string.str_edit_password);
        mBtSubmit = findViewById(R.id.bt_submit);
        mBtSubmit.setOnClickListener(this);

        mUser = (UserInfo) getIntent().getSerializableExtra(KEY_USER);

        setView();
    }

    private void setView() {
        if (mUser != null) {
            mEtNickname.setText(mUser.getNickname());
            mEtRegion.setText(mUser.getRegion());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                requestModify();
                break;
        }
    }

    private void requestModify() {
        closeKeyboard();
        String url = UrlConstants.USER_EDIT_URL;
        showProgress(true);
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId", mUser.getUserId());
        map.put("nickname", mEtNickname.getText().toString());
        map.put("region", mEtRegion.getText().toString());
//        if (!TextUtils.isEmpty(mEtPassword.getText())) {
//            map.put("password", mEtPassword.getText());
//        }
        GsonPostRequest request = new GsonPostRequest(url, null, map, UserInfoResponseInfo
                .class, new Response.Listener<UserInfoResponseInfo>() {

            @Override
            public void onResponse(UserInfoResponseInfo responseInfo) {
                showProgress(false);
                if (responseInfo == null || !"0".equals(responseInfo.getCode()) ||
                        responseInfo.getData() == null) {
                    if (responseInfo != null && !TextUtils.isEmpty(responseInfo.getMsg
                            ())) {
                        GlobalUtil.makeToast(getString(R.string.str_user_edit_failed) +
                                ":" + responseInfo.getMsg());
                    } else {
                        GlobalUtil.makeToast(R.string.str_user_edit_failed);
                    }
                } else {
                    GlobalUtil.makeToast(R.string.str_user_edit_successful);
                    UserInfo userInfo = responseInfo.getData();
                    UserUtil.saveUser(userInfo);
                    setResult(CodeConstants.RESULT_USER_CHANGE);
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
        mInputManager.hideSoftInputFromWindow(mEtNickname.getWindowToken(), 0);
        mInputManager.hideSoftInputFromWindow(mEtRegion.getWindowToken(), 0);
//        mInputManager.hideSoftInputFromWindow(mEtPassword.getWindowToken(), 0);
    }
}
