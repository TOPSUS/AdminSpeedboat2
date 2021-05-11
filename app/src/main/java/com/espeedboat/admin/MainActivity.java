package com.espeedboat.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.espeedboat.admin.fragment.DashboardFragment;
import com.espeedboat.admin.fragment.JadwalFragment;
import com.espeedboat.admin.fragment.KapalFragment;
import com.espeedboat.admin.fragment.ListTransaksiFragment;
import com.espeedboat.admin.fragment.ProfileFragment;
import com.espeedboat.admin.fragment.QrFragment;
import com.espeedboat.admin.interfaces.ChangeBottomNav;
import com.espeedboat.admin.interfaces.FinishActivity;
import com.espeedboat.admin.interfaces.ShowBackButton;
import com.espeedboat.admin.interfaces.ToolbarTitle;
import com.espeedboat.admin.utils.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements FinishActivity, ToolbarTitle, ShowBackButton, ChangeBottomNav {

    private Toolbar toolbar;
    private TextView title;
    private ImageView profileToolbar, notifToolbar, back;
    private BottomNavigationView navigation;
    private FloatingActionButton fabBottom;

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

        fabBottom = findViewById(R.id.fabBottom);
        fabBottom.setOnClickListener(v -> {
            profileToolbar.setVisibility(View.INVISIBLE);
            back.setVisibility(View.VISIBLE);
            title.setText(R.string.sb_qr);
            loadFragment(new QrFragment());
        });
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
            case R.id.transaksi:
                loadFragment(new ListTransaksiFragment("all"));
                title.setText(R.string.menu_transaksi);
                return true;
        }

        return false;
    };

    // Back Button Click
    private void backButtonClick() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            try {
                if (fragmentManager.findFragmentByTag(Constants.FRAG_MOVE).isVisible()) {
                    BottomNavigationView mBottomNavigationView = findViewById(R.id.bottomnav);
                    mBottomNavigationView.setSelectedItemId(R.id.nav_dashboard);
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

    @Override
    public void showBackButton(Boolean status) {
        if (status) {
            back.setVisibility(View.VISIBLE);
            profileToolbar.setVisibility(View.GONE);
        } else {
            back.setVisibility(View.GONE);
            profileToolbar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setBottomNav(int id) {
        MenuItem item = navigation.getMenu().findItem(id);
        item.setChecked(true);
    }
}