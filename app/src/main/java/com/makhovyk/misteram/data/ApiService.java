package com.makhovyk.misteram.data;

import com.makhovyk.misteram.data.model.AuthToken;
import com.makhovyk.misteram.data.model.RegisterToken;
import com.makhovyk.misteram.data.model.Task;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @POST("business-app/api/couriers/app")
    Observable<RegisterToken> getRegisterToken(@Query("v") String version,
                                               @Query("type") String type,
                                               @Query("deviceId") String deviceId);


    @POST("business-app/api/couriers/account/sign-in")
    Observable<AuthToken> getAuthToken(@Query("username") String username,
                                       @Query("password") String password,
                                       @Header("App-Token") String appToken);

    @GET("business-app/api/couriers/tasks/active")
    Observable<List<Task>> getActiveTasks(@Header("App-Auth-Token") String authToken);
}
