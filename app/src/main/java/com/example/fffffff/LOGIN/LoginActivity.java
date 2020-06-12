package com.example.fffffff.LOGIN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fffffff.MainActivity;
import com.example.fffffff.R;

public class LoginActivity extends AppCompatActivity {
    Button BtLogin;
    EditText EtxLoginAccount,EtxLoginPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BtLogin = findViewById(R.id.BtLogin);
        EtxLoginAccount = findViewById(R.id.EtxLoginAccount);
        EtxLoginPassword = findViewById(R.id.EtxLoginPassword);

        BtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EtxLoginAccount.getText().toString().equals("")&&EtxLoginPassword.getText().toString().equals("")) {
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);

                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this,"帳號密碼錯誤",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}