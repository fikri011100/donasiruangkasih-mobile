package com.titi.mj.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DonationResponse {
    @Expose
    @SerializedName("message")
    private String message;


//    @Expose
//    @SerializedName("message")
//    public String message;
    @Expose
    @SerializedName("data")
    public List<Data> data;
    @Expose
    @SerializedName("success")
    public boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class Data {
        @Expose
        @SerializedName("categories")
        public Categories categories;
        @Expose
        @SerializedName("donation_end")
        public String donationEnd;
        @Expose
        @SerializedName("users")
        public Users users;
        @Expose
        @SerializedName("donation_received")
        public int donationReceived;
        @Expose
        @SerializedName("donation_image")
        public String donationImage;
        @Expose
        @SerializedName("donation_title")
        public String donationTitle;
        @Expose
        @SerializedName("id")
        public int id;
    }

    public static class Categories {
        @Expose
        @SerializedName("name_category")
        public String nameCategory;
        @Expose
        @SerializedName("id")
        public int id;

        public String getNameCategory() {
            return nameCategory;
        }

        public void setNameCategory(String nameCategory) {
            this.nameCategory = nameCategory;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class Users {
        @Expose
        @SerializedName("email")
        public String email;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("id")
        public int id;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}