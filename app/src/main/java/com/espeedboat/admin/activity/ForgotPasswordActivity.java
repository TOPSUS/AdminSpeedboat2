package com.espeedboat.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.espeedboat.admin.R;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.service.AuthService;
import com.espeedboat.admin.utils.SessionManager;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText txtEmail, txtPassword;
    private TextView masuk;
    private ImageButton submitlogin;
    private SessionManager sessionManager;
    private AuthService authService;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        init();

        masuk.setOnClickListener(v -> {
            startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void init() {
        masuk = findViewById(R.id.masuk);
    }
}