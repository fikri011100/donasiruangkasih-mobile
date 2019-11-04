package com.titi.mj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @Expose
    @SerializedName("user_status")
    public Integer userStatus;
    @Expose
    @SerializedName("profile_photo")
    public String profilePhoto;
    @Expose
    @SerializedName("phone")
    public String phone;
    @Expose
    @SerializedName("api_token")
    public String apiToken;
    @Expose
    @SerializedName("email")
    public String email;
    @Expose
    @SerializedName("name")
    public String name;
    @Expose
    @SerializedName("id")
    public int id;
}
