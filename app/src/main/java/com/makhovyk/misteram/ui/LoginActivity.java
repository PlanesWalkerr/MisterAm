package com.makhovyk.misteram.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.makhovyk.misteram.R;
import com.makhovyk.misteram.Utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentManager fragmentManager = getSupportFragmentManager();
            LoginFragment loginFragment = new LoginFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_container, loginFragment)
                    .commit();
    }
}
