package com.mojota.succulent.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mojota.succulent.R;
import com.mojota.succulent.model.UserInfo;

public class UserEditActivity extends AppCompatActivity {

    public static final String KEY_USER = "key_user";
    private Toolbar mToolbar;
    private EditText mEtNickname;
    private EditText mEtRegion;
    private EditText mEtPassword;
    private Button mBtSubmit;
    private UserInfo mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mEtNickname = findViewById(R.id.et_nickname);
        mEtRegion = findViewById(R.id.et_region);
        mEtPassword = findViewById(R.id.et_password);
        mBtSubmit = findViewById(R.id.bt_submit);

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
}
