package com.makhovyk.misteram.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.makhovyk.misteram.R;
import com.makhovyk.misteram.Utils.SessionManager;
import com.makhovyk.misteram.data.ApiClient;

public class MainActivity extends AppCompatActivity {

    private final String USERNAME = "d136@mister.am";
    private final String PASSWORD = "123123123";

    ApiClient apiClient;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (!sessionManager.isLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, TaskListActivity.class);
            startActivity(intent);
            finish();
        }

    }




}
