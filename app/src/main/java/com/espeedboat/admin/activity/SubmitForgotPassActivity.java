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

import com.espeedboat.admin.R;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.model.Data;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.service.ForgotPassService;
import com.espeedboat.admin.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;

public class SubmitForgotPassActivity extends AppCompatActivity {
    private EditText txtConfirmpass, txtNewpass;
    private TextView masuk;
    private ImageButton submitpass;
    private SessionManager sessionManager;
    private ForgotPassService forgotpass;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_forgot_pass);

        init();
        masuk.setOnClickListener(v -> {
            startActivity(new Intent(SubmitForgotPassActivity.this, LoginActivity.class));
            finish();
        });

        submitpass.setOnClickListener(v -> {
            addpass();
        });
    }

    private void init() {
        masuk = findViewById(R.id.masuk);
        txtConfirmpass = findViewById(R.id.confirmpass);
        txtNewpass = findViewById(R.id.newpass);
        submitpass = findViewById(R.id.passbutton);
        sessionManager = new SessionManager(this);
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        forgotpass = RetrofitClient.getClient().create(ForgotPassService.class);
        masuk = findViewById(R.id.masuk);
    }

    private void addpass(){
        progress.setMessage("Submitting");
        progress.show();

        Call<Response> forgot = forgotpass.postPass(txtConfirmpass.getText().toString(),txtNewpass.getText().toString(),sessionManager.getUserEmail());

        forgot.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        Toast.makeText(getApplicationContext(), "Password Berhasil Direset, Silahkan Login", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SubmitForgotPassActivity.this, LoginActivity.class));
                        finish();
                        progress.dismiss();
                    }else{
                        Toast.makeText(getApplicationContext(), "User Not Found", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e("Error", t.getMessage().toString());
                Toast.makeText(getApplicationContext(), "Gagal Mereset Password", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
    }
}