package com.sadra.todo.listener;

import com.sadra.todo.model.Task;

public interface TaskItemEventListener {
    void onClick(Task task);
    void onLongClick(Task task);
}
