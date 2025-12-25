package com.sadra.todo.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sadra.todo.R;
import com.sadra.todo.detail.TaskDetailActivity;
import com.sadra.todo.listener.TaskItemEventListener;
import com.sadra.todo.model.AppDatabase;
import com.sadra.todo.model.Task;
import com.sadra.todo.util.AppConstant;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskItemEventListener, MainContract.View {

    private TaskAdapter taskAdapter;
    private RecyclerView recyclerView;
    private View emptyState;
    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getColor(R.color.colorPrimary));

        this.presenter = new MainPresenter(AppDatabase.getAppDatabase(this).getTaskDao());

        emptyState = findViewById(R.id.main_emptyState_container);

        taskAdapter = new TaskAdapter(this, this);
        recyclerView = findViewById(R.id.rv_main_taskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(taskAdapter);

        View addNewTaskBtn = findViewById(R.id.btn_main_addNewTask);
        addNewTaskBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
            startActivityForResult(intent, AppConstant.REQUEST_CODE);
        });

        View deleteAllButton = findViewById(R.id.btn_main_deleteAll);
        deleteAllButton.setOnClickListener(v -> presenter.onDeleteAllClick());

        EditText searchEditText = findViewById(R.id.et_main_search);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onSearch(s.toString());
            }
        });

        presenter.onAttach(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.REQUEST_CODE && data != null) {
            Task task = data.getParcelableExtra(AppConstant.EXTRA_KEY_TASK);
            if (task != null) {
                switch (resultCode) {
                    case AppConstant.RESULT_CODE_ADD_TASK:
                        taskAdapter.addItem(task);
                        recyclerView.scrollToPosition(0);
                        setEmptyStateVisibility(false);
                        break;
                    case AppConstant.RESULT_CODE_UPDATE_TASK:
                        taskAdapter.updateItem(task);
                        break;
                    case AppConstant.RESULT_CODE_DELETE_TASK:
                        taskAdapter.deleteItem(task);
                        break;
                }
            }
        }
    }

    @Override
    public void onClick(Task task) {
        presenter.onTaskItemClick(task);
    }

    @Override
    public void onLongClick(Task task) {
        Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
        intent.putExtra(AppConstant.EXTRA_KEY_TASK, task);
        startActivityForResult(intent, AppConstant.REQUEST_CODE);
    }

    @Override
    public void showTasks(List<Task> tasks) {
        taskAdapter.addItems(tasks);
    }

    @Override
    public void setEmptyStateVisibility(boolean visibility) {
        emptyState.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    @Override
    public void clearTasks() {
        taskAdapter.clearItems();
    }

    @Override
    public void updateTask(Task task) {
        taskAdapter.updateItem(task);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }
}