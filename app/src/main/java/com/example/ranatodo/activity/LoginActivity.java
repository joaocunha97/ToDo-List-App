package com.example.ranatodo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.ranatodo.model.User;
import com.example.ranatodo.utility.UserSessionManager;
import com.example.ranatodo.utility.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername,edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
    }

    public void login(View view) {
        final String username = edtUsername.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Util.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Login_failed")){
                            Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                        }else{
                            try {
                                JSONObject userObject = new JSONObject(response);
                                int userId = userObject.getInt(Util.KEY_USER_ID);
                                String fullname= userObject.getString(Util.KEY_FULLNAME);

                                User user = new User(userId,fullname,username,password);
                                UserSessionManager userSessionManager = new UserSessionManager(LoginActivity.this);
                                userSessionManager.createUserSession(user);

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put(Util.KEY_USERNAME,username);
                map.put(Util.KEY_PASSWORD,password);
                return map;
            }
        };
        queue.add(request);
    }
}
