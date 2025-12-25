package com.sadra.todo.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.sadra.todo.R;
import com.sadra.todo.util.AppConstant;

public class TaskDetailActivity extends AppCompatActivity {

    private int selectedImportance = AppConstant.IMPORTANCE_NORMAL;
    private ImageView lastSelectedImportanceIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_detail);
        getWindow().setStatusBarColor(getColor(R.color.colorPrimary));

        View normalImportanceBtn = findViewById(R.id.normalImportanceBtn);
        lastSelectedImportanceIv = normalImportanceBtn.findViewById(R.id.normalImportanceCheckIv);

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

    }
}