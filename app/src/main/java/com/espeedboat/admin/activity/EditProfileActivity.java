package com.espeedboat.admin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.espeedboat.admin.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EditProfileActivity extends AppCompatActivity {
    private RelativeLayout editprofile;
    private AutoCompleteTextView autoCompleteJK;
    private TextView username, role, title;
    private Toolbar toolbar;
    private ImageView  profileToolbar, notifToolbar, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setToolbar();
        setJKValue(null);
    }
    private void setToolbar() {
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        profileToolbar = findViewById(R.id.toolbar_profile);
        notifToolbar = findViewById(R.id.toolbar_notification);
        back = findViewById(R.id.toolbar_back);

        title = findViewById(R.id.toolbar_title);
        title.setText("Edit Profile");

        profileToolbar.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(v -> onBackPressed());
    }
    private void setJKValue(String tipe) {
        String[] items = {"Laki-laki", "Perempuan"};
        autoCompleteJK = findViewById(R.id.autoCompleteJeniskelamin);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_dropdown, items);

        if (tipe != null) {
            if (tipe.equals("Laki-laki")) {
                autoCompleteJK.setText(adapter.getItem(0).toString());
            } else if (tipe.equals("Perempuan")) {
                autoCompleteJK.setText(adapter.getItem(1).toString());
            }
        }

        autoCompleteJK.setAdapter(adapter);
    }
}