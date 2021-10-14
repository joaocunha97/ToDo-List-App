package com.example.ranatodo.activity;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.ranatodo.model.Task;
import com.example.ranatodo.utility.Util;

import java.util.HashMap;
import java.util.Map;

public class UpdateTaskActivity extends AppCompatActivity {

    EditText edtTaskName;
    int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        edtTaskName = findViewById(R.id.edtTaskName);
        Intent intent=getIntent();
        Bundle bundle = intent.getExtras();
        Task task = (Task) bundle.getSerializable("TASK");

        taskId = task.getTask_id();
        edtTaskName.setText(task.getTask_name());
    }

    public void UpdateTask(View view) {
        final String taskName = edtTaskName.getText().toString();
        if(taskName.isEmpty()){
            edtTaskName.setError("Please enter task name");
            return;
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Util.URL_UPDATE_TASK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("updated")){
                            Toast.makeText(UpdateTaskActivity.this, "Task Updated", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(UpdateTaskActivity.this, "Couldn't update", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String>map= new HashMap<>();
                map.put(Util.KEY_TASK_NAME,taskName);
                map.put(Util.KEY_TASK_ID,String.valueOf(taskId));
                return map;
            }
        };
        queue.add(request);
    }
}
