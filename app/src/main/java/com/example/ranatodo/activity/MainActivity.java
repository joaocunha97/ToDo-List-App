package com.example.ranatodo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ranatodo.R;
import com.example.ranatodo.adapter.TaskAdapter;
import com.example.ranatodo.model.Task;
import com.example.ranatodo.model.User;
import com.example.ranatodo.utility.UserSessionManager;
import com.example.ranatodo.utility.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText edtTaskName;
    User user;
    UserSessionManager userSessionManager;
    int userId;

    ListView lvTasks;
    ArrayList<Task> taskArrayList;
    //ArrayAdapter<Task> taskArrayAdapter;
    TaskAdapter taskArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTasks=findViewById(R.id.lvTask);
        taskArrayList = new ArrayList<>();
        taskArrayAdapter= new TaskAdapter(this,taskArrayList);
        lvTasks.setAdapter(taskArrayAdapter);

        edtTaskName = findViewById(R.id.edtTaskName);
        userSessionManager = new UserSessionManager(this);
        user = userSessionManager.getLoggedInUser();
        userId = user.getUser_id();
        String fullname = user.getFullname();

        getSupportActionBar().setTitle("Welcome "+ fullname);

        //getAllTasksFromDB();
    }

    private void getAllTasksFromDB() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Util.URL_GET_ALL_TASK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //JSON parsing

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray= jsonObject.getJSONArray("tasks");

                            int totalTasks =  jsonArray.length();

                            if ( totalTasks > 0){

                                taskArrayList.clear();

                                for (int i = 0; i < totalTasks; i++){
                                    JSONObject taskJSONObject = jsonArray.getJSONObject(i);

                                    int task_id =taskJSONObject.getInt(Util.KEY_TASK_ID);
                                    String task_name = taskJSONObject.getString(Util.KEY_TASK_NAME);
                                    int user_id = taskJSONObject.getInt(Util.KEY_USER_ID);

                                    Task task = new Task ( task_id, task_name, user_id);
                                    taskArrayList.add(task);

                                }
                                taskArrayAdapter.notifyDataSetChanged();
                                //taskArrayAdapter= new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,taskArrayList);
                                //lvTasks.setAdapter(taskArrayAdapter);

                            }else{
                                Toast.makeText(MainActivity.this, "No Tasks" , Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //Toast.makeText(MainActivity.this, response , Toast.LENGTH_SHORT).show();
                        Log.d("Tasks",response);
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
                Map <String,String>map= new HashMap<>();
                map.put(Util.KEY_USER_ID,String.valueOf(userId));
                return map;
            }
        };
        queue.add(request);
    }

    public void addTask(View view) {
        final String taskName= edtTaskName.getText().toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Util.URL_ADD_TASK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("failed")){
                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Task Added "+ response, Toast.LENGTH_SHORT).show();

                            int taskId = Integer.parseInt(response);
                            Task task = new Task (taskId, taskName,userId);
                            taskArrayList.add(task);
                            taskArrayAdapter.notifyDataSetChanged();
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
                Map<String,String> map = new HashMap<>();
                map.put(Util.KEY_TASK_NAME,taskName);
                map.put(Util.KEY_USER_ID,String.valueOf(userId));

                return map;
            }
        };
        queue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuSignout:
                userSessionManager.logoutUser();
                finish();
                break;
            case R.id.menuSearch:
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
                break;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAllTasksFromDB();
    }
}
