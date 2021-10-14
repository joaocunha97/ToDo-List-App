package com.example.ranatodo.model;

import java.io.Serializable;

public class Task implements Serializable {
    int task_id;
    String task_name;
    int user_id;

    public Task(int task_id, String task_name, int user_id) {
        this.task_id = task_id;
        this.task_name = task_name;
        this.user_id = user_id;
    }

    public int getTask_id() {
        return task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public int getUser_id() {
        return user_id;
    }

    @Override
    public String toString() {
        return task_name;
    }
}
