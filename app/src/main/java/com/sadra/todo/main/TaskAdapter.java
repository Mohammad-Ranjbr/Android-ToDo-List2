package com.sadra.todo.main;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sadra.todo.R;
import com.sadra.todo.listener.TaskItemEventListener;
import com.sadra.todo.model.Task;
import com.sadra.todo.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    private final Drawable highImportanceDrawable;
    private final Drawable normalImportanceDrawable;
    private final Drawable lowImportanceDrawable;
    private final TaskItemEventListener taskItemEventListener;

    public TaskAdapter(Context context, TaskItemEventListener taskItemEventListener) {
        this.tasks = new ArrayList<>();
        this.taskItemEventListener = taskItemEventListener;
        this.highImportanceDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.importance_high_rect_shape, null);
        this.normalImportanceDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.importance_normal_rect_shape, null);
        this.lowImportanceDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.importance_low_rect_shape, null);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bindTask(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void addItems(List<Task> tasks) {
        this.tasks.clear();
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public void addItem(Task task) {
        this.tasks.add(0, task);
        notifyItemInserted(0);
    }

    public void updateItem(Task task) {
        for (int i = 0; i <tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                this.tasks.set(i, task);
                notifyItemChanged(i, task);
                break;
            }
        }
    }

    public void deleteItem(Task task) {
        for (int i = 0; i <tasks.size(); i++) {
            if (tasks.get(i).getId() == task.getId()) {
                this.tasks.remove(task);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public void clearItems() {
        this.tasks.clear();
        notifyDataSetChanged();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        private final ImageView checkBoxIv;
        private final TextView titleTextView;
        private final View importanceView;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.checkBoxIv = itemView.findViewById(R.id.iv_task_checkBox);
            this.titleTextView = itemView.findViewById(R.id.tv_task_checkBox);
            this.importanceView = itemView.findViewById(R.id.view_task_importance);
        }

        public void bindTask(Task task) {
            titleTextView.setText(task.getTitle());
            if (task.isCompleted()) {
                checkBoxIv.setBackgroundResource(R.drawable.checkbox_checked_shape);
                checkBoxIv.setImageResource(R.drawable.ic_check_white_24dp);
                titleTextView.setPaintFlags(titleTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                checkBoxIv.setBackgroundResource(R.drawable.checkbox_default_shape);
                checkBoxIv.setImageResource(0);
                titleTextView.setPaintFlags(titleTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }

            switch (task.getImportance()) {
                case AppConstant.IMPORTANCE_LOW :
                    importanceView.setBackground(lowImportanceDrawable);
                    break;
                case AppConstant.IMPORTANCE_NORMAL:
                    importanceView.setBackground(normalImportanceDrawable);
                    break;
                case AppConstant.IMPORTANCE_HIGH:
                    importanceView.setBackground(highImportanceDrawable);
                    break;
            }

            itemView.setOnClickListener(v -> taskItemEventListener.onClick(task));
            itemView.setOnLongClickListener(v -> {
                taskItemEventListener.onLongClick(task);
                return true;
            });
        }

    }

}
