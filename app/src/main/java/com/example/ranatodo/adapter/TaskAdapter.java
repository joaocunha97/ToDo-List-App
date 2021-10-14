package com.example.ranatodo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ranatodo.R;
import com.example.ranatodo.activity.UpdateTaskActivity;
import com.example.ranatodo.model.Task;
import com.example.ranatodo.utility.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaskAdapter extends ArrayAdapter<Task> {
    Context context;
    ArrayList<Task> taskArrayList;
    public TaskAdapter(@NonNull Context context, ArrayList<Task> taskArrayList) {
        super(context, 0,taskArrayList);

        this.context=context;
        this.taskArrayList=taskArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Task task = taskArrayList.get(position);

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);

            convertView = inflater.inflate(R.layout.row_task,parent,false);
        }
        TextView tvTaskName = convertView.findViewById(R.id.tvTaskName);

        tvTaskName.setText(task.getTask_name());

        ImageButton btnDelete = convertView.findViewById(R.id.btnDelete);
        ImageButton btnUpdate = convertView.findViewById(R.id.btnUpdate);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure to delete?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final int taskid = task.getTask_id();

                        RequestQueue queue = Volley.newRequestQueue(context);
                        StringRequest request = new StringRequest(Request.Method.POST, Util.URL_DELETE_TASK,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(context,response, Toast.LENGTH_SHORT).show();

                                        if(response.equals("deleted")){
                                            taskArrayList.remove(task);
                                            notifyDataSetChanged();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                        ){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String>map = new HashMap<>();
                                map.put(Util.KEY_TASK_ID,String.valueOf(taskid));
                                return map;
                            }
                        };
                        queue.add(request);
                    }
                });
                builder.setNegativeButton("No",null);
                builder.show();



            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateTaskActivity.class);

                intent.putExtra("TASK",task);

                context.startActivity(intent);
            }
        });

        return convertView;

    }
}
