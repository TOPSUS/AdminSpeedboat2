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

public class VerifCodeForgotPassActivity extends AppCompatActivity {
    private EditText txtCode;
    private TextView masuk;
    private ImageButton submitcode;
    private SessionManager sessionManager;
    private ForgotPassService forgotpass;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verif_code_forgot_pass);
        init();
        masuk.setOnClickListener(v -> {
            startActivity(new Intent(VerifCodeForgotPassActivity.this, LoginActivity.class));
            finish();
        });

        submitcode.setOnClickListener(v -> {
            cekCode();
        });
    }
    private void init() {
        masuk = findViewById(R.id.masuk);
        txtCode = findViewById(R.id.code);
        submitcode = findViewById(R.id.subbutton);
        sessionManager = new SessionManager(this);
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        forgotpass = RetrofitClient.getClient().create(ForgotPassService.class);
        masuk = findViewById(R.id.masuk);
    }

    private void cekCode(){
        progress.setMessage("Submitting");
        progress.show();

        Call<Response> forgot = forgotpass.postCekCode(txtCode.getText().toString());

        forgot.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        Data data = response.body().getData();
                        sessionManager.setUserEmail(data.getUser().getEmail());
                        Log.e("test", sessionManager.getUserEmail());
                        Toast.makeText(getApplicationContext(), "Kode Benar, Silahkan Ganti Password", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(VerifCodeForgotPassActivity.this, SubmitForgotPassActivity.class));
                        finish();
                        progress.dismiss();
                    }else{
                        Toast.makeText(getApplicationContext(), "Kode Salah!", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e("Code Error", t.getMessage().toString());
                Toast.makeText(getApplicationContext(), "Gagal Mengirim Kode", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
    }

}