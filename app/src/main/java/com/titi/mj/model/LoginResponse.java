package com.titi.mj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @Expose
    @SerializedName("banks")
    public Banks banks;
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

    public static class Banks {
        @Expose
        @SerializedName("no_rekening")
        public String noRekening;
        @Expose
        @SerializedName("bank_name")
        public String bankName;
    }
}
