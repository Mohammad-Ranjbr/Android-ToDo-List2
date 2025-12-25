package com.sadra.todo.main;

import com.sadra.todo.model.Task;
import com.sadra.todo.util.BasePresenter;
import com.sadra.todo.util.BaseView;

import java.util.List;

public interface MainContract {

    interface View extends BaseView {
        void showTasks(List<Task> tasks);
        void setEmptyStateVisibility(boolean visibility);
    }

    interface Presenter extends BasePresenter<View> {

    }

}
