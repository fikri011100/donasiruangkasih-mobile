package com.titi.mj.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DonationResponse {

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
        @SerializedName("donation_total")
        public int donationTotal;
        @Expose
        @SerializedName("donation_received")
        public int donationReceived;
        @Expose
        @SerializedName("donation_description")
        public String donationDescription;
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
            donationTotal = in.readInt();
            donationReceived = in.readInt();
            donationDescription = in.readString();
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
            dest.writeInt(donationTotal);
            dest.writeInt(donationReceived);
            dest.writeString(donationDescription);
            dest.writeString(donationImage);
            dest.writeString(donationTitle);
            dest.writeInt(id);
        }
    }

    public static class Categories implements Parcelable{
        @Expose
        @SerializedName("name_category")
        public String nameCategory;
        @Expose
        @SerializedName("id")
        public int id;

        protected Categories(Parcel in) {
            nameCategory = in.readString();
            id = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(nameCategory);
            dest.writeInt(id);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Categories> CREATOR = new Creator<Categories>() {
            @Override
            public Categories createFromParcel(Parcel in) {
                return new Categories(in);
            }

            @Override
            public Categories[] newArray(int size) {
                return new Categories[size];
            }
        };
    }

    public static class Users implements Parcelable{
        @Expose
        @SerializedName("profile_photo")
        public String profilePhoto;
        @Expose
        @SerializedName("email")
        public String email;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("id")
        public int id;

        protected Users(Parcel in) {
            profilePhoto = in.readString();
            email = in.readString();
            name = in.readString();
            id = in.readInt();
        }

        public static final Creator<Users> CREATOR = new Creator<Users>() {
            @Override
            public Users createFromParcel(Parcel in) {
                return new Users(in);
            }

            @Override
            public Users[] newArray(int size) {
                return new Users[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(profilePhoto);
            dest.writeString(email);
            dest.writeString(name);
            dest.writeInt(id);
        }
    }
}