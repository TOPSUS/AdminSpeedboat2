package com.espeedboat.admin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.espeedboat.admin.R;

public class SessionManager {
    private Context context;
    private String USER_TOKEN = "user_token";
    private SharedPreferences sharedPreferences;

    public SessionManager (Context cont) {
        this.context = cont;
        this.sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public void setAuthToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_TOKEN, token);
        editor.apply();
    }

    public String getAuthToken() {
        return "Bearer "+ sharedPreferences.getString(USER_TOKEN, null);
    }

    public String getToken() {
        return sharedPreferences.getString(USER_TOKEN, null);
    }
}
