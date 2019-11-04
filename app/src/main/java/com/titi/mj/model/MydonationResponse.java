package com.titi.mj.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MydonationResponse implements Parcelable {

    @Expose
    @SerializedName("message")
    public String message;
    @Expose
    @SerializedName("data")
    public List<Data> data;
    @Expose
    @SerializedName("success")
    public boolean success;

    public static class Data implements Parcelable {
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

        protected Data(Parcel in) {
            donationEnd = in.readString();
            donationReceived = in.readInt();
            donationImage = in.readString();
            donationTitle = in.readString();
            id = in.readInt();
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(donationEnd);
            dest.writeInt(donationReceived);
            dest.writeString(donationImage);
            dest.writeString(donationTitle);
            dest.writeInt(id);
        }
    }

    public static class Categories {
        @Expose
        @SerializedName("name_category")
        public String nameCategory;
        @Expose
        @SerializedName("id")
        public int id;
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
    }

    protected MydonationResponse(Parcel in) {
        message = in.readString();
        success = in.readByte() != 0;
    }

    public static final Creator<MydonationResponse> CREATOR = new Creator<MydonationResponse>() {
        @Override
        public MydonationResponse createFromParcel(Parcel in) {
            return new MydonationResponse(in);
        }

        @Override
        public MydonationResponse[] newArray(int size) {
            return new MydonationResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
        dest.writeByte((byte) (success ? 1 : 0));
    }
}