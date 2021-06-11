package com.example.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private String TAG = SplashScreenActivity.class.getSimpleName();

    private MyPreferences myPreferences;

    private int TIME_OUT = 3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        myPreferences = new MyPreferences(SplashScreenActivity.this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (MyPreferences.getSharedPreferences().
                        getBoolean(MyPreferences.IS_LOGIN, false)) {
                    Intent intent = new Intent(SplashScreenActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashScreenActivity.this,
                            SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, TIME_OUT);
    }
}
