package com.example.andrei.mateatodo.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Andrei on 1/15/2018.
 */

@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Insert()
    void insertUser(User user);

    @Query("DELETE FROM user WHERE id=:id")
    void deleteUser(int id);

    @Query("SELECT * FROM user WHERE id=:id")
    User getUserById(int id);

    @Query("UPDATE user SET password=:newPassword WHERE id=:id")
    void updatePassword(String newPassword, int id);

    @Query("UPDATE user SET username=:newUsername WHERE id=:id")
    void updateUsername(String newUsername, int id);

    @Query("UPDATE user SET email =:newEmail WHERE id=:id")
    void updateEmail(String newEmail, int id);

    @Query("SELECT id FROM user WHERE username=:userNameIntroduced AND password=:passwordIntroduced")
    int login(String userNameIntroduced, String passwordIntroduced);

    @Query("SELECT * FROM user WHERE username=:usernameIntroduced")
    User getUserByUsername(String usernameIntroduced);
}
