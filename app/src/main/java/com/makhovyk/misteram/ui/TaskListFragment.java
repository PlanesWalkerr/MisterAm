package com.makhovyk.misteram.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makhovyk.misteram.R;
import com.makhovyk.misteram.Utils.SessionManager;
import com.makhovyk.misteram.data.ApiClient;
import com.makhovyk.misteram.data.model.Order;
import com.makhovyk.misteram.data.model.Task;
import com.makhovyk.misteram.ui.adapters.ListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TaskListFragment extends Fragment {

    private final String TAG = "MisterAm";

    SessionManager sessionManager;
    List<Task> tasks = new ArrayList<Task>();

    @BindView(R.id.tasks_recycler_view)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_active_tasks_list, container, false);
        ButterKnife.bind(this, v);

        sessionManager = new SessionManager(getActivity());
        if (sessionManager.isLoggedIn()) {
            loadActiveTasks();

        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ListAdapter(tasks, getActivity()));

        return v;
    }

    private void loadActiveTasks() {
        ApiClient apiClient = new ApiClient();
        apiClient.getActiveTasks(sessionManager.getAuthToken())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<List<Task>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Task> tasksList) {

                        tasks.clear();
                        tasks.addAll(tasksList);
                        tasks.addAll(tasksList);
                        Log.v(TAG, String.valueOf(tasks.size()));

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.getAdapter().notifyDataSetChanged();
                                Log.v(TAG, String.valueOf(tasks.size()));
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
