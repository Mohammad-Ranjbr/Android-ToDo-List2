package com.sadra.todo.detail;

import com.sadra.todo.model.Task;
import com.sadra.todo.util.BasePresenter;
import com.sadra.todo.util.BaseView;

public interface TaskDetailContract {

    interface View extends BaseView {
        void showTask(Task task);
        void showError(String error);
        void returnResult(int resultCode, Task task);
        void setDeleteButtonVisibility(boolean visibility);
    }

    interface Presenter extends BasePresenter<View> {
        void saveChange(String title, int importance);
        void deleteTask();
    }

}
