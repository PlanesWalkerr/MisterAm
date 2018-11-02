package com.makhovyk.misteram.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthToken {

    @SerializedName("authToken")
    @Expose
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }
}
