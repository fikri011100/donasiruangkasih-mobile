package com.titi.mj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {

    @Expose
    @SerializedName("message")
    public String message;
    @Expose
    @SerializedName("data")
    public List<Data> data;
    @Expose
    @SerializedName("success")
    public boolean success;

    public static class Data {
        @Expose
        @SerializedName("updated_at")
        public String updatedAt;
        @Expose
        @SerializedName("created_at")
        public String createdAt;
        @Expose
        @SerializedName("image_category")
        public String imageCategory;
        @Expose
        @SerializedName("name_category")
        public String nameCategory;
        @Expose
        @SerializedName("id")
        public int id;
    }
}
