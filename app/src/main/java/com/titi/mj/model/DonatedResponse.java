package com.titi.mj.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DonatedResponse {

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
        @SerializedName("created_at")
        public String createdAt;
        @Expose
        @SerializedName("donator_status")
        public int donatorStatus;
        @Expose
        @SerializedName("paymentmethod")
        public Paymentmethod paymentmethod;
        @Expose
        @SerializedName("donation_amount")
        public int donationAmount;
        @Expose
        @SerializedName("donation")
        public Donation donation;
        @Expose
        @SerializedName("user")
        public User user;
        @Expose
        @SerializedName("id_donator")
        public int idDonator;

        protected Data(Parcel in) {
            createdAt = in.readString();
            donatorStatus = in.readInt();
            donationAmount = in.readInt();
            idDonator = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(createdAt);
            dest.writeInt(donatorStatus);
            dest.writeInt(donationAmount);
            dest.writeInt(idDonator);
        }

        @Override
        public int describeContents() {
            return 0;
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
    }

    public static class Paymentmethod implements Parcelable{
        @Expose
        @SerializedName("paymentmethod_no_rek")
        public String paymentmethodNoRek;
        @Expose
        @SerializedName("paymentmethod_name")
        public String paymentmethodName;
        @Expose
        @SerializedName("paymentmethod_image")
        public String paymentmethodImage;

        protected Paymentmethod(Parcel in) {
            paymentmethodNoRek = in.readString();
            paymentmethodName = in.readString();
            paymentmethodImage = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(paymentmethodNoRek);
            dest.writeString(paymentmethodName);
            dest.writeString(paymentmethodImage);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Paymentmethod> CREATOR = new Creator<Paymentmethod>() {
            @Override
            public Paymentmethod createFromParcel(Parcel in) {
                return new Paymentmethod(in);
            }

            @Override
            public Paymentmethod[] newArray(int size) {
                return new Paymentmethod[size];
            }
        };
    }

    public static class Donation implements Parcelable{
        @Expose
        @SerializedName("donation_image")
        public String donationImage;
        @Expose
        @SerializedName("donation_title")
        public String donationTitle;

        protected Donation(Parcel in) {
            donationImage = in.readString();
            donationTitle = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(donationImage);
            dest.writeString(donationTitle);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Donation> CREATOR = new Creator<Donation>() {
            @Override
            public Donation createFromParcel(Parcel in) {
                return new Donation(in);
            }

            @Override
            public Donation[] newArray(int size) {
                return new Donation[size];
            }
        };
    }

    public static class User implements Parcelable{
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

        protected User(Parcel in) {
            profilePhoto = in.readString();
            email = in.readString();
            name = in.readString();
            id = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(profilePhoto);
            dest.writeString(email);
            dest.writeString(name);
            dest.writeInt(id);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<User> CREATOR = new Creator<User>() {
            @Override
            public User createFromParcel(Parcel in) {
                return new User(in);
            }

            @Override
            public User[] newArray(int size) {
                return new User[size];
            }
        };
    }
}
