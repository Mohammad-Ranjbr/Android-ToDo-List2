package com.sadra.todo.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.sadra.todo.R;
import com.sadra.todo.confirm.ConfirmDialog;
import com.sadra.todo.model.AppDatabase;
import com.sadra.todo.model.Task;
import com.sadra.todo.util.AppConstant;

public class TaskDetailActivity extends AppCompatActivity implements TaskDetailContract.View {

    private int selectedImportance = AppConstant.IMPORTANCE_NORMAL;
    private ImageView lastSelectedImportanceIv;
    private TaskDetailContract.Presenter presenter;
    private EditText taskTitleEditText;
    private View highImportanceBtn;
    private View normalImportanceBtn;
    private View lowImportanceBtn;
    private View deleteTaskButton;
    private TextView toolBarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_detail);

        this.presenter = new TaskDetailPresenter(AppDatabase.getAppDatabase(this).getTaskDao(),
                getIntent().getParcelableExtra(AppConstant.EXTRA_KEY_TASK));

        normalImportanceBtn = findViewById(R.id.normalImportanceBtn);
        lastSelectedImportanceIv = normalImportanceBtn.findViewById(R.id.normalImportanceCheckIv);

        taskTitleEditText = findViewById(R.id.et_taskDetail_title);
        View saveChangeButton = findViewById(R.id.btn_taskDetail_saveChanges);
        saveChangeButton.setOnClickListener(v -> presenter.saveChange(taskTitleEditText.getText().toString(), selectedImportance));

        deleteTaskButton = findViewById(R.id.iv_taskDetail_delete);
        deleteTaskButton.setOnClickListener(v -> {
            ConfirmDialog confirmDialog = new ConfirmDialog(false);
            confirmDialog.setListener(() -> presenter.deleteTask());
            confirmDialog.show(getSupportFragmentManager(), null);
        });

        toolBarTitle = findViewById(R.id.tv_taskDetail_toolbarTitle);

        highImportanceBtn = findViewById(R.id.highImportanceBtn);
        highImportanceBtn.setOnClickListener(v -> {
            if (selectedImportance != AppConstant.IMPORTANCE_HIGH) {
                this.lastSelectedImportanceIv.setImageResource(0);
                ImageView imageView = v.findViewById(R.id.highImportanceCheckIv);
                imageView.setImageResource(R.drawable.ic_check_white_24dp);
                this.selectedImportance = AppConstant.IMPORTANCE_HIGH;
                this.lastSelectedImportanceIv = imageView;
            }
        });

        lowImportanceBtn = findViewById(R.id.lowImportanceBtn);
        lowImportanceBtn.setOnClickListener(v -> {
            if (selectedImportance != AppConstant.IMPORTANCE_LOW) {
                this.lastSelectedImportanceIv.setImageResource(0);
                ImageView imageView = v.findViewById(R.id.lowImportanceCheckIv);
                imageView.setImageResource(R.drawable.ic_check_white_24dp);
                this.selectedImportance = AppConstant.IMPORTANCE_LOW;
                this.lastSelectedImportanceIv = imageView;
            }
        });

        normalImportanceBtn.setOnClickListener(v -> {
            if (selectedImportance != AppConstant.IMPORTANCE_NORMAL) {
                this.lastSelectedImportanceIv.setImageResource(0);
                ImageView imageView = v.findViewById(R.id.normalImportanceCheckIv);
                imageView.setImageResource(R.drawable.ic_check_white_24dp);
                this.selectedImportance = AppConstant.IMPORTANCE_NORMAL;
                this.lastSelectedImportanceIv = imageView;
            }
        });

        presenter.onAttach(this);

    }

    @Override
    public void showTask(Task task) {
        taskTitleEditText.setText(task.getTitle());
        switch (task.getImportance()) {
            case AppConstant.IMPORTANCE_HIGH:
                highImportanceBtn.performClick();
                break;
            case AppConstant.IMPORTANCE_NORMAL:
                normalImportanceBtn.performClick();
                break;
            case AppConstant.IMPORTANCE_LOW:
                lowImportanceBtn.performClick();
                break;
        }
    }

    @Override
    public void showError(String error) {
        Snackbar.make(findViewById(R.id.rootTaskDetail), error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void returnResult(int resultCode, Task task) {
        Intent intent = new Intent();
        intent.putExtra(AppConstant.EXTRA_KEY_TASK, task);
        setResult(resultCode, intent);
        finish();
    }

    @Override
    public void setEditTaskScreen(boolean visibility) {
        deleteTaskButton.setVisibility(visibility ? View.VISIBLE : View.GONE);
        toolBarTitle.setText(visibility ? getString(R.string.taskDetail_toolbarTitle_edit) : getString(R.string.taskDetail_toolbarTitle_add));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}