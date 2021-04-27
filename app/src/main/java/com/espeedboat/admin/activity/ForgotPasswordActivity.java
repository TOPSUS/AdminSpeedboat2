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
import com.espeedboat.admin.model.Data;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.service.AuthService;
import com.espeedboat.admin.service.ForgotPassService;
import com.espeedboat.admin.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText txtEmail;
    private TextView masuk;
    private ImageButton submitreset;
    private SessionManager sessionManager;
    private ForgotPassService forgotpass;
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

        submitreset.setOnClickListener(v -> {
            forgotPass();
        });
    }

    private void init() {
        masuk = findViewById(R.id.masuk);
        txtEmail = findViewById(R.id.email);
        submitreset = findViewById(R.id.resetbutton);
        sessionManager = new SessionManager(this);
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        forgotpass = RetrofitClient.getClient().create(ForgotPassService.class);
    }

    private void forgotPass(){
        progress.setMessage("Submitting");
        progress.show();

        Call<Response> forgot = forgotpass.postCode(txtEmail.getText().toString());

        forgot.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        Toast.makeText(getApplicationContext(), "Cek Email Anda", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ForgotPasswordActivity.this, VerifCodeForgotPassActivity.class));
                        finish();
                        progress.dismiss();
                    }else{
                        Toast.makeText(getApplicationContext(), "Email Salah!", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e("Forgot Pass Error", t.getMessage().toString());
                Toast.makeText(getApplicationContext(), "Gagal Mengirim Kode", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
    }
}