package com.espeedboat.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.espeedboat.admin.activity.LoginActivity;
import com.espeedboat.admin.fragment.DashboardFragment;
import com.espeedboat.admin.fragment.JadwalFragment;
import com.espeedboat.admin.fragment.KapalFragment;
import com.espeedboat.admin.fragment.ProfileFragment;
import com.espeedboat.admin.interfaces.FinishActivity;
import com.espeedboat.admin.interfaces.ToolbarTitle;
import com.espeedboat.admin.model.Auth;
import com.espeedboat.admin.model.User;
import com.espeedboat.admin.utils.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements FinishActivity, ToolbarTitle {

    private Toolbar toolbar;
    private TextView title;
    private ImageView profileToolbar, notifToolbar, back;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        leftToolbarListener();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backButtonClick();
            }
        });

        loadFragment(new DashboardFragment());

    }



    @Override
    protected void onStart() {
        super.onStart();
    }

    private void init() {
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        profileToolbar = findViewById(R.id.toolbar_profile);
        notifToolbar = findViewById(R.id.toolbar_notification);

        title = findViewById(R.id.toolbar_title);
        title.setText(R.string.menu_dashboard);
        back = findViewById(R.id.toolbar_back);

        navigation = findViewById(R.id.bottomnav);
        navigation.setOnNavigationItemSelectedListener(bottomNavItemSelected);
    }

    private void leftToolbarListener() {
        profileToolbar.setOnClickListener(v -> {
            profileToolbar.setVisibility(View.INVISIBLE);
            title.setText(R.string.menu_profile);
            back.setVisibility(View.VISIBLE);
            loadFragment(new ProfileFragment());
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content, fragment, Constants.FRAG_MOVE);
        ft.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavItemSelected = item -> {
        back.setVisibility(View.INVISIBLE);

        switch (item.getItemId()) {
            case R.id.nav_kapal:
                loadFragment(new KapalFragment());
                title.setText(R.string.menu_speedboat);
                return true;
            case R.id.nav_dashboard:
                loadFragment(new DashboardFragment());
                title.setText(R.string.menu_dashboard);
                return true;
            case R.id.jadwal:
                loadFragment(new JadwalFragment());
                title.setText(R.string.menu_jadwal);
                return true;
        }

        return false;
    };

    // Back Button Click
    private void backButtonClick() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        try {
            if (fragmentManager.findFragmentByTag(Constants.FRAG_MOVE).isVisible()) {
                loadFragment(new DashboardFragment());
                profileToolbar.setVisibility(View.VISIBLE);
                title.setText(R.string.menu_dashboard);
                back = findViewById(R.id.toolbar_back);
                back.setVisibility(View.INVISIBLE);
            }
        } catch (NullPointerException e) {
            super.onBackPressed();
        }
    }


    @Override
    public void onBackPressed() {
        backButtonClick();
    }


    // Finish Activity From Fragment
    @Override
    public void finishActivity() {
        overridePendingTransition(0,0);
        finish();
    }

    @Override
    public void setToolbarTitle(String toolbarTitle) {
        title.setText(toolbarTitle);
    }
}