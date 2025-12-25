package com.sadra.todo.main;

import com.sadra.todo.model.Task;
import com.sadra.todo.model.TaskDao;

import java.util.List;

public class MainPresenter implements MainContract.Presenter{

    private TaskDao taskDao;
    private List<Task> tasks;
    private MainContract.View view;

    public MainPresenter(TaskDao taskDao) {
        this.taskDao = taskDao;
        this.tasks = taskDao.getAll();
    }

    @Override
    public void onAttach(MainContract.View view) {
        this.view = view;
        if (!tasks.isEmpty()) {
            view.showTasks(tasks);
            view.setEmptyStateVisibility(false);
        } else {
            view.setEmptyStateVisibility(true);
        }
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    @Override
    public void onDeleteAllClick() {
        if (view != null) {
            taskDao.deleteAll();
            view.clearTasks();
            view.setEmptyStateVisibility(true);
        }
    }
}
