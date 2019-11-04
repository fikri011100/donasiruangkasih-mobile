package com.titi.mj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @Expose
    @SerializedName("token")
    public String token;
    @Expose
    @SerializedName("user")
    public User user;

    public static class User {
        @Expose
        @SerializedName("id")
        public int id;
        @Expose
        @SerializedName("created_at")
        public String createdAt;
        @Expose
        @SerializedName("updated_at")
        public String updatedAt;
        @Expose
        @SerializedName("user_status")
        public String userStatus;
        @Expose
        @SerializedName("profile_photo")
        public String profilePhoto;
        @Expose
        @SerializedName("api_token")
        public String apiToken;
        @Expose
        @SerializedName("phone")
        public String phone;
        @Expose
        @SerializedName("email")
        public String email;
        @Expose
        @SerializedName("name")
        public String name;
    }
}
