package com.example.ranatodo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.ranatodo.R;
import com.example.ranatodo.utility.UserSessionManager;

public class SplashActivity extends AppCompatActivity {

    UserSessionManager userSessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userSessionManager = new UserSessionManager(this);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (userSessionManager.isUserLoggedIn()){

                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashActivity.this, LandingActivity.class));
                    finish();
                }

        }
        });
        t.start();
    }
}
