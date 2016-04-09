package com.example.timur.organizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Timur on 4/8/2016.
 */
public class LoginActivity extends Activity{
    EditText edtLogin, edtPassword;
    Button btnLogin, btnReg;
    DBService dbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        dbService = new DBService(this);
        dbService = dbService.open();

        edtLogin = (EditText)findViewById(R.id.edtLoginL);
        edtPassword = (EditText)findViewById(R.id.edtPasswordL);
        btnReg = (Button)findViewById(R.id.btnGoReg);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = edtLogin.getText().toString();
                String password = edtPassword.getText().toString();

                switch (v.getId()){
                    case R.id.btnGoReg:
                        startActivity(new Intent(LoginActivity.this, Registration.class));
                        finish();
                        break;
                    case R.id.btnLogin:
                        if(dbService.login(login, password)){
                            Toast.makeText(getApplicationContext(), "Добро пажаловать"+login, Toast.LENGTH_SHORT).show();
                            CalendarActivity.IS_REGISTERED=true;
                            CalendarActivity.USERNAME = login;
                            startActivity(new Intent(LoginActivity.this, CalendarActivity.class));
                            finish();
                            break;
                        }else{
                            Toast.makeText(getApplicationContext(), "Неверный логин или пароль ", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        };

        btnLogin.setOnClickListener(onClickListener);
        btnReg.setOnClickListener(onClickListener);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbService.close();
    }
}
