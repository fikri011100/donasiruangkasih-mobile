package com.titi.mj.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.titi.mj.model.locale.PrefModel;

public class SharedPref {
    private static final String PREF_NAME = "user_pref";

    private static final String FULLNAME = "fullname";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String PHONENO = "phoneno";
    private static final String LOGGEDIN = "isLogin";

    private static SharedPreferences preferences = null;


    public SharedPref(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setPreferences(PrefModel model){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FULLNAME, model.getFullname());
        editor.putString(EMAIL, model.getEmail());
        editor.putString(PASSWORD, model.getPassword());
        editor.putString(PHONENO, model.getPhoneno());
        editor.putBoolean(LOGGEDIN, model.isLoggedin());
        editor.apply();

    }

    public PrefModel getPreferences(){
        PrefModel model = new PrefModel();
        model.setFullname(preferences.getString(FULLNAME, ""));
        model.setEmail(preferences.getString(EMAIL, ""));
        model.setPassword(preferences.getString(PASSWORD, ""));
        model.setPhoneno(preferences.getString(PHONENO, ""));
        model.setLoggedin(preferences.getBoolean(LOGGEDIN, false));

        return model;
    }

    public static void clearPreferences(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(EMAIL);
        editor.remove(FULLNAME);
        editor.remove(PASSWORD);
        editor.remove(PHONENO);
        editor.remove(LOGGEDIN);
        editor.apply();
    }
}
