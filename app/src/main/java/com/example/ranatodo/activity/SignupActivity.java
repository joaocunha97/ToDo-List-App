package com.example.ranatodo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ranatodo.R;
import com.example.ranatodo.utility.Util;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText edtFullname,edtUsername,edtPassword,edtRepassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtFullname = findViewById(R.id.edtFullname);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtRepassword = findViewById(R.id.edtRepassword);
    }

    public void signup(View view) {
        final String fullname = edtFullname.getText().toString().trim();
        final String username = edtUsername.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();
        String repass = edtRepassword.getText().toString().trim();

        if(fullname.isEmpty()){
            edtFullname.setError("Fullname is Empty");
            return;
        }
        if(password.length()<6){
            edtPassword.setError("Password too short, at leat 6 characters");
            return;
        }
        if(!password.equals(repass)){
            Toast.makeText(this,"passwords do not match",Toast.LENGTH_SHORT).show();
            return;
        }
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL
        StringRequest request = new StringRequest(Request.Method.POST, Util.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    if(response.equals("success")){
                        Toast.makeText(SignupActivity.this, "Registration Success, please login", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(SignupActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignupActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put(Util.KEY_FULLNAME,fullname);
                map.put(Util.KEY_USERNAME,username);
                map.put(Util.KEY_PASSWORD,password);
                return map;
            }
        };
        queue.add(request);
    }
}
