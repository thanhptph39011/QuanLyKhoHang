package com.example.quanlykhohang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quanlykhohang.Dao.UserDao;
import com.example.quanlykhohang.Model.NguoiDung;

public class LoginActivity extends AppCompatActivity {
    EditText edtUserName, edtPassWord;
    CheckBox chkLuuMk;
    Button btnLogin, btnHuy;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUserName = findViewById(R.id.edtUsername);
        edtPassWord = findViewById(R.id.edtPassword);
        chkLuuMk = findViewById(R.id.chkLuuMatKhau);
        btnHuy = findViewById(R.id.btnHuy);
        btnLogin = findViewById(R.id.btnLogin);
        userDao = new UserDao(this);
//Đọc User,Pass
        SharedPreferences preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        edtUserName.setText(preferences.getString("userName", ""));
        edtPassWord.setText((preferences.getString("passWord", "")));
        chkLuuMk.setChecked(preferences.getBoolean("remember", false));
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = edtUserName.getText().toString();
                String passWord = edtPassWord.getText().toString();
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(passWord)) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean checkLogin = false;
                NguoiDung nd = userDao.getUserName(userName);
                if (nd != null) {
                    if (passWord.equals(nd.getPassWord())) {
                        checkLogin = true;
                    }else{
                        Toast.makeText(LoginActivity.this, "SAi PassWord", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }else{
                    if( userName.equals("admin") && passWord.equals("admin")){
                        checkLogin=true;
                    }else{
                        Toast.makeText(LoginActivity.this, "SAi user name hoặc pass", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if(checkLogin=true){

                    Toast.makeText(LoginActivity.this, "Đăng nhập Succ", Toast.LENGTH_SHORT).show();
                    rememberUser(userName, passWord, chkLuuMk.isChecked());
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("user", userName);
                    startActivity(intent);
                    finish();
                }

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtUserName.setText("");
                edtPassWord.setText("");
            }
        });
    }

    public void rememberUser(String user, String pass, boolean satus) {
        SharedPreferences preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (!satus) {
            editor.clear();
        } else {
            editor.putString("userName", user);
            editor.putString("passWord", pass);
            editor.putBoolean("remember", satus);
        }
        editor.commit();
    }

}