package com.example.mystore;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreferences {
    private Context context;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private final String ACCOUNT_PREF = "ACCOUNT_PREF";
    public static final String IS_LOGIN = "IS_LOGIN";
    public static final String USERNAME = "USERNAME";

    public MyPreferences(Context context) {
        this.context = context;
        this.sharedPreferences = this.context
                .getSharedPreferences(ACCOUNT_PREF, Context.MODE_PRIVATE);
        this.editor = this.sharedPreferences.edit();
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public static SharedPreferences.Editor getEditorPreferences() {
        return editor;
    }

}
