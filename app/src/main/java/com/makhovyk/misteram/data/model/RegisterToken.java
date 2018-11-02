package com.makhovyk.misteram.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterToken {

    @SerializedName("token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }
}
