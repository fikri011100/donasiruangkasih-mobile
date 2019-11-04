package com.titi.mj.model.locale;

import android.os.Parcel;
import android.os.Parcelable;

public class PrefModel implements Parcelable {
    private boolean isLoggedin;
    private int id;
    private String fullname;
    private String email;
    private String password;
    private String phoneno;
    private String photo;
    private String status;

    public PrefModel(){

    }

    protected PrefModel(Parcel in) {
        isLoggedin = in.readByte() != 0;
        id = in.readInt();
        fullname = in.readString();
        email = in.readString();
        password = in.readString();
        phoneno = in.readString();
        photo = in.readString();
        status = in.readString();
    }

    public static final Creator<PrefModel> CREATOR = new Creator<PrefModel>() {
        @Override
        public PrefModel createFromParcel(Parcel in) {
            return new PrefModel(in);
        }

        @Override
        public PrefModel[] newArray(int size) {
            return new PrefModel[size];
        }
    };

    public boolean isLoggedin() {
        return isLoggedin;
    }

    public void setLoggedin(boolean loggedin) {
        isLoggedin = loggedin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isLoggedin ? 1 : 0));
        dest.writeInt(id);
        dest.writeString(fullname);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(phoneno);
        dest.writeString(photo);
        dest.writeString(status);
    }
}
