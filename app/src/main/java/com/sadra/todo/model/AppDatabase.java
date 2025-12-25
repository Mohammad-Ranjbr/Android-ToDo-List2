package com.sadra.todo.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sadra.todo.util.AppConstant;

@Database(version = 1, exportSchema = false, entities = {Task.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    public static AppDatabase getAppDatabase(Context context) {
        if (appDatabase == null)
            appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, AppConstant.APP_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        return appDatabase;
    }

    public abstract TaskDao getTaskDao();

}
