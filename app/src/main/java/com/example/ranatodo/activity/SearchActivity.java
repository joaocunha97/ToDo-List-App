package com.example.ranatodo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    EditText edtTaskName;
    TextView tvTaskId,tvName,tvUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        edtTaskName=findViewById(R.id.edtTaskName);
        tvTaskId=findViewById(R.id.tvTaskId);
        tvName=findViewById(R.id.tvTaskName);
        tvUserId=findViewById(R.id.tvUserId);
    }

    public void searchTask(View view) {
        final String taskName = edtTaskName.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Util.URL_SEARCH_TASK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("not_found")){
                            Toast.makeText(SearchActivity.this, "Task not found", Toast.LENGTH_SHORT).show();
                        }else{
                            try {
                                JSONObject task = new JSONObject(response);

                                tvTaskId.setText(""+ task.getInt(Util.KEY_TASK_ID));
                                tvName.setText(task.getString(Util.KEY_TASK_NAME));
                                tvUserId.setText(""+ task.getInt(Util.KEY_USER_ID));

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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map = new HashMap<>();
                map.put(Util.KEY_TASK_NAME,taskName);
                return map;
            }
        };
        queue.add(request);

    }
}
