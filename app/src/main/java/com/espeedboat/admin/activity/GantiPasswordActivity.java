package com.espeedboat.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.espeedboat.admin.MainActivity;
import com.espeedboat.admin.R;
import com.espeedboat.admin.client.RetrofitClient;
import com.espeedboat.admin.model.Response;
import com.espeedboat.admin.service.EditProfileService;
import com.espeedboat.admin.service.ForgotPassService;
import com.espeedboat.admin.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;

public class GantiPasswordActivity extends AppCompatActivity {
    private EditText txtConfirmpass, txtNewpass, txtOldpass;
    private TextView username, role, title;
    private Button submitpass;
    private SessionManager sessionManager;
    private Toolbar toolbar;
    private ImageView profileToolbar, notifToolbar, back, profile;
    private EditProfileService gantipass;
    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_password);
        init();
        setToolbar();

        submitpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addpass();
            }
        });
    }

    private void init() {
        txtConfirmpass = findViewById(R.id.confirmpassForm);
        txtNewpass = findViewById(R.id.newpassForm);
        txtOldpass = findViewById(R.id.oldpassForm);
        sessionManager = new SessionManager(this);
        submitpass = findViewById(R.id.btn_submit_pass);
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        gantipass = RetrofitClient.getClient().create(EditProfileService.class);
    }
    private void setToolbar() {
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        profileToolbar = findViewById(R.id.toolbar_profile);
        notifToolbar = findViewById(R.id.toolbar_notification);
        back = findViewById(R.id.toolbar_back);

        title = findViewById(R.id.toolbar_title);
        title.setText("Edit Password");

        profileToolbar.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(v -> onBackPressed());
    }

    private void addpass(){
        progress.setMessage("Submitting");
        progress.show();

        Call<Response> ganti = gantipass.postGantiPass(sessionManager.getAuthToken(),txtOldpass.getText().toString(), txtConfirmpass.getText().toString(),txtNewpass.getText().toString(), sessionManager.getUserId());

        ganti.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        Toast.makeText(getApplicationContext(), "Password Berhasil Direset", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        progress.dismiss();
                    }else{
                        Log.e("Response not 200", response.message().toString());
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