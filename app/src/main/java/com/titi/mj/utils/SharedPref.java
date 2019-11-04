package com.titi.mj.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.titi.mj.model.locale.PrefModel;

public class SharedPref {
    private static final String PREF_NAME = "user_pref";

    private static final String ID = "id";
    private static final String FULLNAME = "fullname";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String PHOTO = "photo";
    private static final String PHONENO = "phoneno";
    private static final String STATUS = "status";
    private static final String LOGGEDIN = "isLogin";

    private static SharedPreferences preferences = null;


    public SharedPref(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setPreferences(PrefModel model){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(ID, model.getId());
        editor.putString(FULLNAME, model.getFullname());
        editor.putString(EMAIL, model.getEmail());
        editor.putString(PASSWORD, model.getPassword());
        editor.putString(PHOTO, model.getPhoto());
        editor.putString(PHONENO, model.getPhoneno());
        editor.putString(STATUS, model.getStatus());
        editor.putBoolean(LOGGEDIN, model.isLoggedin());
        editor.apply();

    }

    public PrefModel getPreferences(){
        PrefModel model = new PrefModel();
        model.setId(preferences.getInt(ID, 0));
        model.setFullname(preferences.getString(FULLNAME, ""));
        model.setEmail(preferences.getString(EMAIL, ""));
        model.setPassword(preferences.getString(PASSWORD, ""));
        model.setPhoto(preferences.getString(PHOTO, ""));
        model.setPhoneno(preferences.getString(PHONENO, ""));
        model.setStatus(preferences.getString(STATUS, ""));
        model.setLoggedin(preferences.getBoolean(LOGGEDIN, false));

        return model;
    }

    public static void clearPreferences(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(ID);
        editor.remove(EMAIL);
        editor.remove(FULLNAME);
        editor.remove(PASSWORD);
        editor.remove(PHOTO);
        editor.remove(PHONENO);
        editor.remove(STATUS);
        editor.remove(LOGGEDIN);
        editor.apply();
    }
}
