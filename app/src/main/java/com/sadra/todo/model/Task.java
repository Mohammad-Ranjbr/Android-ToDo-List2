package com.sadra.todo.model;

import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.sadra.todo.util.AppConstant;

@Entity(tableName = "tasks")
public class Task implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    @ColumnInfo(name = "completed")
    private boolean isCompleted;
    @ColumnInfo(name = "create_date")
    private String createDate;
    private int importance = AppConstant.IMPORTANCE_NORMAL;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public Task() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeByte(this.isCompleted ? (byte) 1 : (byte) 0);
        dest.writeString(this.createDate);
        dest.writeInt(this.importance);
    }

    protected Task(android.os.Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.isCompleted = in.readByte() != 0;
        this.createDate = in.readString();
        this.importance = in.readInt();
    }

    public static final Creator<Task> CREATOR = new Creator<>() {
        @Override
        public Task createFromParcel(android.os.Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

}
