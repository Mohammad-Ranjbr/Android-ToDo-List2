package com.sadra.todo.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    long insert(Task task);

    @Delete
    int delete(Task task);

    @Update
    int update(Task task);

    @Query("SELECT * FROM tasks ORDER BY completed ASC, create_date DESC")
    List<Task> getAll();

    @Query("SELECT * FROM tasks WHERE title LIKE '%' || :query || '%' ORDER BY completed ASC, create_date DESC")
    List<Task> search(String query);

    @Query("DELETE FROM tasks")
    void deleteAll();

}
