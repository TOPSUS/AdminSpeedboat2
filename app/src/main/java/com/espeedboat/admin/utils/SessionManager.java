package com.espeedboat.admin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.espeedboat.admin.R;

public class SessionManager {
    private Context context;
    private String USER_TOKEN = "user_token";
    private String USER_NAME = "user_name";
    private String USER_ROLE = "user_role";
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

    public void setUserName(String string) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME, string);
        editor.apply();
    }

    public String getUserName() {
        return sharedPreferences.getString(USER_NAME, null);
    }

    public void setUserRole(String string) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ROLE, string);
        editor.apply();
    }

    public String getUserRole() {
        return sharedPreferences.getString(USER_ROLE, null);
    }

    public String getToken() {
        return sharedPreferences.getString(USER_TOKEN, null);
    }

    public void clearSession() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
