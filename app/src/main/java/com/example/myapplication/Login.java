package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnlogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if (username.isEmpty()) {
                    Toast.makeText(Login.this, "Tài khoản không được trống!", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(Login.this, "Mật khẩu không được trống!", Toast.LENGTH_SHORT).show();
                } else {
                    authenticate(username, password);
                }
            }
        });
    }

    public void authenticate(String username, String password) {
        Boolean checkInternet = isNetworkConnected();
        if (checkInternet) {
            AuthenService authenService = AuthenController.authenticate();
            Call<BaseResponse<Data>> call = authenService.authenticate(new LoginRequest(username, password));
            call.enqueue(new Callback<BaseResponse<Data>>() {
                @Override
                public void onResponse(Call<BaseResponse<Data>> call, Response<BaseResponse<Data>> response) {
                    if (response.body().getStatus().equals("200")) {
                        Toast.makeText(Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        if (response.body().getData().getToken() != null) {
                            SharedPreferences preferences = getSharedPreferences("login_data", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("token", response.body().getData().getToken());
                            editor.apply();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Login.this, "Lỗi Api không trả về token!", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(Login.this, "Tài khoản hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse<Data>> call, Throwable t) {
                    Toast.makeText(Login.this, "Lỗi chưa xác định!", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(Login.this, "Điện thoại chưa kết nối mạng!", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}