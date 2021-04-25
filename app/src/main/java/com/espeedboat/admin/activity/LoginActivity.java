package com.espeedboat.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.espeedboat.admin.MainActivity;
import com.espeedboat.admin.R;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.model.Auth;
import com.espeedboat.admin.model.Data;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.service.AuthService;
import com.espeedboat.admin.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    private EditText txtEmail, txtPassword;
    private TextView forgotPassword;
    private ImageButton submitlogin;
    private SessionManager sessionManager;
    private AuthService authService;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        submitlogin.setOnClickListener(v -> {
            login();
        });

        forgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            finish();
        });
    }

    private void init() {
        txtEmail = findViewById(R.id.emailadmin);
        txtPassword = findViewById(R.id.passwordadmin);
        submitlogin = findViewById(R.id.loginbutton);
        sessionManager = new SessionManager(this);
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        authService = RetrofitClient.getClient().create(AuthService.class);
        forgotPassword = findViewById(R.id.forgot_password);
    }

    private void login() {
        progress.setMessage("Logging In");
        progress.show();

        Call<Response> login = authService.postLogin(txtEmail.getText().toString(),
                                                    txtPassword.getText().toString());

        login.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        Data data = response.body().getData();
                        sessionManager.setAuthToken(data.getAuth().getToken());
                        sessionManager.setUserName(data.getAuth().getUser().getNama());
                        sessionManager.setUserRole(data.getAuth().getUser().getRole());
                        sessionManager.setUserId(data.getAuth().getUser().getId());
                        Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                        progress.dismiss();
                    }else{
                        Toast.makeText(getApplicationContext(), "Email/Password Salah!", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e("Login Error", t.getMessage().toString());
                Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
    }
}