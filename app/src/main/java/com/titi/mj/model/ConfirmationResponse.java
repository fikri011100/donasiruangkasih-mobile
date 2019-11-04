package com.titi.mj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfirmationResponse {

    @Expose
    @SerializedName("message")
    public String message;
    @Expose
    @SerializedName("data")
    public Data data;
    @Expose
    @SerializedName("success")
    public boolean success;

    public static class Data {
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
        @SerializedName("photo_struk")
        public String photoStruk;
        @Expose
        @SerializedName("id_user")
        public String idUser;
        @Expose
        @SerializedName("id_donator")
        public String idDonator;
    }
}
