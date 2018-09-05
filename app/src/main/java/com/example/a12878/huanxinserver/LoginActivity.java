package com.example.a12878.huanxinserver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a12878.huanxinserver.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.btn_login)
    Button login;
    @BindView(R.id.et_account)
    EditText account;
    @BindView(R.id.et_pwd)
    EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMainActivity();
            }
        });
    }

    private void toMainActivity() {
        if ("root".equals(account.getText().toString().trim())
                && "123456".equals(pwd.getText().toString().trim()) ){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}
