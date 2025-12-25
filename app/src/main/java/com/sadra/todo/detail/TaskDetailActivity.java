package com.sadra.todo.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.sadra.todo.R;
import com.sadra.todo.model.AppDatabase;
import com.sadra.todo.model.Task;
import com.sadra.todo.util.AppConstant;

public class TaskDetailActivity extends AppCompatActivity implements TaskDetailContract.View {

    private int selectedImportance = AppConstant.IMPORTANCE_NORMAL;
    private ImageView lastSelectedImportanceIv;
    private TaskDetailContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_detail);
        getWindow().setStatusBarColor(getColor(R.color.colorPrimary));

        this.presenter = new TaskDetailPresenter(AppDatabase.getAppDatabase(this).getTaskDao());

        View normalImportanceBtn = findViewById(R.id.normalImportanceBtn);
        lastSelectedImportanceIv = normalImportanceBtn.findViewById(R.id.normalImportanceCheckIv);

        EditText editText = findViewById(R.id.et_taskDetail_title);
        View saveChangeButton = findViewById(R.id.btn_taskDetail_saveChanges);
        saveChangeButton.setOnClickListener(v -> presenter.saveChange(editText.getText().toString(), selectedImportance));

        View highImportanceBtn = findViewById(R.id.highImportanceBtn);
        highImportanceBtn.setOnClickListener(v -> {
            if (selectedImportance != AppConstant.IMPORTANCE_HIGH) {
                this.lastSelectedImportanceIv.setImageResource(0);
                ImageView imageView = v.findViewById(R.id.highImportanceCheckIv);
                imageView.setImageResource(R.drawable.ic_check_white_24dp);
                this.selectedImportance = AppConstant.IMPORTANCE_HIGH;
                this.lastSelectedImportanceIv = imageView;
            }
        });

        View lowImportanceBtn = findViewById(R.id.lowImportanceBtn);
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
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}