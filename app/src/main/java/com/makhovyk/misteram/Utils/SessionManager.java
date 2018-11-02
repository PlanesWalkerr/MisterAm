package com.makhovyk.misteram.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREFER_NAME = "UserSession";
    private static final String APP_TOKEN = "appToken";
    private static final String AUTH_TOKEN = "authToken";

    private SharedPreferences sharedPreferences;
    private Context context;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setAppToken(String appToken) {
        editor.putString(APP_TOKEN, appToken);
        editor.commit();
    }

    public void setAuthToken(String authToken) {
        editor.putString(AUTH_TOKEN, authToken);
        editor.commit();
    }

    public String getAppToken() {
        return sharedPreferences.getString(APP_TOKEN, null);
    }

    public String getAuthToken() {
        return sharedPreferences.getString(AUTH_TOKEN, null);
    }

    public boolean isLoggedIn() {
        return getAuthToken() != null;
    }

    public boolean isAppRegistered() {
        return getAppToken() != null;
    }

    public void logOut() {
        editor.remove(AUTH_TOKEN);
        editor.commit();
    }
}
