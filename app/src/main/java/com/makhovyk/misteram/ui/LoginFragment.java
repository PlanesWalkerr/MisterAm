package com.makhovyk.misteram.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.makhovyk.misteram.R;
import com.makhovyk.misteram.Utils.SessionManager;
import com.makhovyk.misteram.data.ApiClient;
import com.makhovyk.misteram.data.model.AuthToken;
import com.makhovyk.misteram.data.model.RegisterToken;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginFragment extends Fragment {

    private final String TAG = "MisterAm";

    @BindView(R.id.il_login)
    TextInputLayout ilLogin;
    @BindView(R.id.il_password)
    TextInputLayout ilPassword;
    @BindView(R.id.il_auth_error)
    TextInputLayout ilAuthError;
    @BindView(R.id.et_login)
    EditText etLogin;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_sign_in)
    LinearLayout btSignIn;

    ApiClient apiClient;
    SessionManager sessionManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getActivity());
        apiClient = new ApiClient();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, v);

        etLogin.addTextChangedListener(new MyTextWatcher(etLogin));
        etPassword.addTextChangedListener(new MyTextWatcher(etPassword));

        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    authenticate();
                }
            }
        });
        return v;
    }

    private void authenticate() {
        if (!sessionManager.isAppRegistered()) {
            getRegisterToken();
        } else {
            getAuthToken(sessionManager.getAppToken());
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validate() {
        if (!validateLogin()) {
            return false;
        }
        if (!validatePassword()) {
            return false;
        }
        return true;
    }

    private boolean validateLogin() {
        ilAuthError.setErrorEnabled(false);
        if (etLogin.getText().toString().trim().isEmpty()) {
            ilLogin.setError(getActivity().getString(R.string.error_login));
            requestFocus(etLogin);
            return false;
        } else {
            ilLogin.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword() {
        ilAuthError.setErrorEnabled(false);
        if (etPassword.getText().toString().trim().isEmpty()) {
            ilPassword.setError(getActivity().getString(R.string.error_login));
            requestFocus(etPassword);
            return false;
        } else {
            ilPassword.setErrorEnabled(false);
        }
        return true;
    }

    private void getRegisterToken() {
        String deviceId = getDeviceId();
        apiClient.getRegisterToken(deviceId)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<RegisterToken>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RegisterToken registerToken) {
                        String appToken = registerToken.getToken();
                        sessionManager.setAppToken(appToken);
                        getAuthToken(appToken);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(),
                                       getActivity().getString(R.string.error_register),
                                       Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void getAuthToken(final String appToken) {
        String login = etLogin.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        apiClient.getAuthToken(login, password, appToken)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<AuthToken>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AuthToken authToken) {
                        Log.v(TAG, "authToken: " + authToken.getAuthToken());
                        sessionManager.setAuthToken(authToken.getAuthToken());
                        navigateToTaskList();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v(TAG, e.getMessage());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ilAuthError.setError(getActivity().getString(R.string.error_authentication));
                            }
                        });

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private String getDeviceId() {
        return Settings.Secure.getString(getActivity().getContentResolver(),Settings.Secure.ANDROID_ID);
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.et_login:
                    validateLogin();
                    break;
                case R.id.et_password:
                    validatePassword();
                    break;
            }
        }
    }

    private void navigateToTaskList() {
        Intent intent = new Intent(getActivity(), TaskListActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
