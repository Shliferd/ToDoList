package com.example.andrei.mateatodo.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Andrei on 1/15/2018.
 */

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    List<Task> getAllTasks();

    @Insert()
    void insertTask(Task task);

    @Query("DELETE FROM task WHERE id=:id")
    void deleteTask(int id);

    @Query("SELECT * FROM task WHERE userId=:userId")
    List<Task> getTasksById(int userId);

    @Query("UPDATE task SET name=:newName WHERE id=:id")
    void updateTaskName(String newName, int id);

    @Query("UPDATE task SET checked=:checked WHERE id=:id")
    void checkTask(int checked, int id);

    @Query("UPDATE task SET userId=:userId WHERE id=:id")
    void updateTaskUserIdById(int userId, int id);
}
