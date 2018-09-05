package com.mojota.succulent.view;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.mojota.succulent.R;

/**
 * Created by mojota on 18-9-3.
 */
public class PasswordView extends FrameLayout {
    private EditText mEtPw;
    private CheckBox mCbShowPw;
    private TextInputLayout mTiPw;

    public PasswordView(Context context) {
        super(context);
        initView();
    }

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout
                .layout_password, this);
        mTiPw = contentView.findViewById(R.id.ti_pw);
        mEtPw = contentView.findViewById(R.id.et_pw);
        mCbShowPw = contentView.findViewById(R.id.cb_show_pw);

        mCbShowPw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mEtPw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    mEtPw.setInputType(InputType.TYPE_CLASS_TEXT | InputType
                            .TYPE_TEXT_VARIATION_PASSWORD);
                }
                mEtPw.setSelection(mEtPw.length());
            }
        });
    }

    public String getText() {
        if (mEtPw.getText() !=null) {
            return mEtPw.getText().toString();
        } else {
            return "";
        }
    }

    public void setError(CharSequence error) {
        mEtPw.setError(error);
    }

    public void setHint(int resId){
        mEtPw.setHint("");
        mTiPw.setHint(getResources().getString(resId));
    }
}
