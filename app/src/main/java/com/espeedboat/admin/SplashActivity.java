package com.espeedboat.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.espeedboat.admin.activity.LoginActivity;
import com.espeedboat.admin.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {
    // Initial Variable
    ImageView ivSplash;
    TextView tvSplash;
    Handler handler = new Handler();
    CharSequence charSequence;
    SessionManager sessionManager;
    int index;
    long delay = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        bindView();
        hideSystemUI(getWindow());

        animateText("Admin ESpeedboat");

        Animation splashAnim = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
        ivSplash.setAnimation(splashAnim);

        new Handler().postDelayed(() -> {
            String isLogin = sessionManager.getToken();

            if (isLogin == null) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else {
                startActivity(new Intent(SplashActivity.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
            finish();
        }, 4000);
    }

    private void bindView () {
        ivSplash = findViewById(R.id.splash_logo);
        tvSplash = findViewById(R.id.splash_text);
        sessionManager = new SessionManager(getApplicationContext());
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tvSplash.setText(charSequence.subSequence(0, index++));

            if (index <= charSequence.length()) {
                handler.postDelayed(runnable, delay);
            }
        }
    };

    private void animateText (CharSequence cs) {
        charSequence = cs;
        index = 0;
        tvSplash.setText("");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, delay);
    }

    public static void hideSystemUI(Window window) {
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }
}