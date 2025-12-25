package com.sadra.todo.detail;

import com.sadra.todo.model.Task;
import com.sadra.todo.model.TaskDao;
import com.sadra.todo.util.AppConstant;

public class TaskDetailPresenter implements TaskDetailContract.Presenter{

    private Task task;
    private TaskDao taskDao;
    private TaskDetailContract.View view;

    public TaskDetailPresenter(TaskDao taskDao, Task task) {
        this.taskDao = taskDao;
        this.task = task;
    }

    @Override
    public void onAttach(TaskDetailContract.View view) {
        this.view = view;
        if (view != null && task != null) {
            view.showTask(task);
        }
    }

    @Override
    public void saveChange(String title, int importance) {
        if (title == null && view != null) {
            view.showError("Enter task title");
        }

        if (view != null) {
            if (task == null) {
                Task task = new Task();
                task.setTitle(title);
                task.setImportance(importance);
                task.setCompleted(false);
                task.setCreateDate(String.valueOf(System.currentTimeMillis()));
                long result = taskDao.insert(task);
                task.setId(result);
                view.returnResult(AppConstant.RESULT_CODE_ADD_TASK, task);
            } else {
                task.setTitle(title);
                task.setImportance(importance);
                int result = taskDao.update(task);
                if (result > 0) {
                    view.returnResult(AppConstant.RESULT_CODE_UPDATE_TASK, task);
                }
            }
        }
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

}
