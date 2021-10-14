package com.example.ranatodo.utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.ranatodo.activity.LandingActivity;
import com.example.ranatodo.model.User;

public class UserSessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Context context;

    private final String USER_LOGGED_IN = "userLoggedIn";

    public UserSessionManager(Context context){
        this.context= context;
        sharedPreferences = context.getSharedPreferences("UserPrefs",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createUserSession(User user) {

        editor.putBoolean(USER_LOGGED_IN,true);

        editor.putInt(Util.KEY_USER_ID,user.getUser_id());
        editor.putString(Util.KEY_FULLNAME,user.getFullname());
        editor.putString(Util.KEY_USERNAME,user.getUsername());
        editor.putString(Util.KEY_PASSWORD,user.getPassword());

        editor.commit();
    }
    public User getLoggedInUser(){
        int id =sharedPreferences.getInt(Util.KEY_USER_ID,0);
        String fullname =sharedPreferences.getString(Util.KEY_FULLNAME,null);
        String username =sharedPreferences.getString(Util.KEY_USERNAME,null);
        String password =sharedPreferences.getString(Util.KEY_PASSWORD,null);

        User user = new User(id,fullname,username,password);
        return user;
    }
    public boolean isUserLoggedIn(){
        boolean status = sharedPreferences.getBoolean(USER_LOGGED_IN,false);
        return status;
    }
    public void logoutUser(){
        editor.clear();// deletes all key value pairs from sharedPrefs
        editor.commit();

        context.startActivity(new Intent(context, LandingActivity.class));
        
    }
}
