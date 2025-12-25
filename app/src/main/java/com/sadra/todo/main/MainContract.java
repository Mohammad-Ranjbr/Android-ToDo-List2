package com.sadra.todo.main;

import com.sadra.todo.model.Task;
import com.sadra.todo.util.BasePresenter;
import com.sadra.todo.util.BaseView;

import java.util.List;

public interface MainContract {

    interface View extends BaseView {
        void showTasks(List<Task> tasks);
        void setEmptyStateVisibility(boolean visibility);
        void clearTasks();
        void updateTask(Task task);
    }

    interface Presenter extends BasePresenter<View> {
        void onDeleteAllClick();
        void onSearch(String query);
        void onTaskItemClick(Task task);
    }

}
