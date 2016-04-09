package com.example.timur.organizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Timur on 4/8/2016.
 */
public class Registration extends Activity {
    EditText edtUsername, edtLogin, edtPassword, edtRepeatPassword;
    Button btnReg;
    DBService dbService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);

        dbService = new DBService(this);
        dbService = dbService.open();

        edtUsername = (EditText)findViewById(R.id.edtUsername);
        edtLogin = (EditText)findViewById(R.id.edtLoginR);
        edtPassword = (EditText)findViewById(R.id.edtPasswordR);
        edtRepeatPassword = (EditText)findViewById(R.id.edtRepeatPassword);

        btnReg = (Button)findViewById(R.id.btnReg);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String login = edtLogin.getText().toString();
                String password = edtPassword.getText().toString();
                String repeatPassword = edtRepeatPassword.getText().toString();

                if(username.equals("")||login.equals("")||password.equals("")||repeatPassword.equals("")){
                    Toast.makeText(getApplicationContext(), "Заполните все поля", Toast.LENGTH_LONG).show();
                }else if(!(password.equals(repeatPassword))){
                    Toast.makeText(getApplicationContext(), "Пароли не совпадают", Toast.LENGTH_LONG).show();
                }else {
                    dbService.register(username, login, password);
                    Toast.makeText(getApplicationContext(), "Вы зарегистрированы, пожалуйста войдите в систему", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Registration.this, LoginActivity.class));
                    finish();
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbService.close();
    }
}
