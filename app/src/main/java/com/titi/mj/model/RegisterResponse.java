package com.titi.mj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    @Expose
    @SerializedName("token")
    private String token;
    @Expose
    @SerializedName("user")
    private User user;

    public static class User {
        @Expose
        @SerializedName("id")
        private int id;
        @Expose
        @SerializedName("created_at")
        private String createdAt;
        @Expose
        @SerializedName("updated_at")
        private String updatedAt;
        @Expose
        @SerializedName("api_token")
        private String apiToken;
        @Expose
        @SerializedName("email")
        private String email;
        @Expose
        @SerializedName("name")
        private String name;
    }
}
