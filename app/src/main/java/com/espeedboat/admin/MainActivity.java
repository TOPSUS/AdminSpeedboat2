package com.espeedboat.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.espeedboat.admin.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView title;
    private ImageView profileToolbar, notifToolbar, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new DashboardFragment());

        init();
        leftToolbarListener();
    }

    private void init() {
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        profileToolbar = findViewById(R.id.toolbar_profile);
        notifToolbar = findViewById(R.id.toolbar_notification);

        title = findViewById(R.id.toolbar_title);
    }

    private void leftToolbarListener() {
        profileToolbar.setOnClickListener(v -> {
            profileToolbar.setVisibility(View.INVISIBLE);
            title.setText(R.string.menu_profile);
            back = findViewById(R.id.toolbar_back);
            back.setVisibility(View.VISIBLE);
            loadFragment(new ProfileFragment());
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content, fragment);
        ft.commit();
    }
}