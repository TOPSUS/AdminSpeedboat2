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
import com.espeedboat.admin.interfaces.FinishActivity;
import com.espeedboat.admin.interfaces.ToolbarTitle;
import com.espeedboat.admin.utils.Constants;

public class MainActivity extends AppCompatActivity implements FinishActivity, ToolbarTitle {

    private Toolbar toolbar;
    private TextView title;
    private ImageView profileToolbar, notifToolbar, back;

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

    private void init() {
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        profileToolbar = findViewById(R.id.toolbar_profile);
        notifToolbar = findViewById(R.id.toolbar_notification);

        title = findViewById(R.id.toolbar_title);
        title.setText(R.string.menu_dashboard);
        back = findViewById(R.id.toolbar_back);
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