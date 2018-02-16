package com.example.andrei.mateatodo.model;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Andrei on 1/15/2018.
 */

@Database(entities = {User.class, Task.class}, version = 2)
public abstract class AppDatabaseRoom extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract TaskDao taskDao();

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
