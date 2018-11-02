package com.makhovyk.misteram.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.makhovyk.misteram.R;
import com.makhovyk.misteram.Utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskListActivity extends AppCompatActivity {

    @BindView(R.id.navigationView)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        setTitle(R.string.label_active_tasks);

        ButterKnife.bind(this);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_orders:
                        Toast.makeText(getApplicationContext(), getString(R.string.orders), Toast.LENGTH_SHORT)
                                .show();
                        break;
                    case R.id.navigation_notifications:
                        Toast.makeText(getApplicationContext(), getString(R.string.notifications), Toast.LENGTH_SHORT)
                                .show();
                        break;
                    case R.id.navigation_profile:
                        showConfirmDialog();
                        break;
                }
                return true;
            }
        });

        SessionManager sessionManager = new SessionManager(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (sessionManager.isLoggedIn()) {
            TaskListFragment activeTasksFragment = new TaskListFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.main_container, activeTasksFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void showConfirmDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.logout))
                .setMessage(getString(R.string.logout_message))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(getString(R.string.ok_message), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        SessionManager sessionManager = new SessionManager(getApplicationContext());
                        sessionManager.logOut();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.cancel_message), null)
                .create()
                .show();
    }
}
