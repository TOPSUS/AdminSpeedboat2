package com.espeedboat.admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.espeedboat.admin.R;

public class WebViewActivity extends AppCompatActivity {
    private WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        String newString;
        Bundle extras = getIntent().getExtras();
        newString = extras.getString("URL");
        webview = findViewById(R.id.beritaPelabuhan);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl(newString);

        WebSettings webset = webview.getSettings();
        webset.setJavaScriptEnabled(true);
        webset.setBuiltInZoomControls(true);
    }
}