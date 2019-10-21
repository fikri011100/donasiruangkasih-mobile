package com.titi.mj.model.locale;

import android.os.Parcel;
import android.os.Parcelable;

public class PrefModel implements Parcelable {
    private String fullname;
    private String email;
    private String password;
    private String phoneno;
    private boolean isLoggedin;

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedin() {
        return isLoggedin;
    }

    public void setLoggedin(boolean loggedin) {
        isLoggedin = loggedin;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fullname);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.phoneno);
        dest.writeByte(this.isLoggedin ? (byte) 1 : (byte) 0);
    }

    public PrefModel() {
    }

    private PrefModel(Parcel in) {
        this.fullname = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.phoneno = in.readString();
        this.isLoggedin = in.readByte() != 0;
    }

    public static final Parcelable.Creator<PrefModel> CREATOR = new Parcelable.Creator<PrefModel>() {
        @Override
        public PrefModel createFromParcel(Parcel source) {
            return new PrefModel(source);
        }

        @Override
        public PrefModel[] newArray(int size) {
            return new PrefModel[size];
        }
    };
}
