package com.makhovyk.misteram.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.makhovyk.misteram.data.model.AuthToken;
import com.makhovyk.misteram.data.model.RegisterToken;
import com.makhovyk.misteram.data.model.Task;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private final String version = "1.0";
    private final String type = "android";
    private final String baseUrl = "https://test.mister.am/";

    private ApiService apiService;

    public ApiClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = builder.build();
        apiService = retrofit.create(ApiService.class);
    }

    public Observable<RegisterToken> getRegisterToken(String deviceId){
        return apiService.getRegisterToken(version,type, deviceId);
    }

    public Observable<AuthToken> getAuthToken(String username, String password, String appToken){
        return apiService.getAuthToken(username,password, appToken);
    }

    public Observable<List<Task>> getActiveTasks(String authToken){
        return apiService.getActiveTasks(authToken);
    }
}
