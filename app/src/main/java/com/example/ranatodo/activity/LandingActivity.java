package com.example.ranatodo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ranatodo.R;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
    }

    public void process(View view) {
        switch(view.getId()){
            case R.id.btnSignup:
                startActivity(new Intent(this,SignupActivity.class));
                break;
            case R.id.btnLogin:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }
}
