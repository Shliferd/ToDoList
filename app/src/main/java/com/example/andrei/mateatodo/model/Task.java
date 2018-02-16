package com.example.andrei.mateatodo.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Andrei on 1/15/2018.
 */

//@Entity(foreignKeys = @ForeignKey(value = (entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = CASCADE)));

@Entity(tableName = "task")
public class Task {

    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("userId")
    @Expose
    @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId")
    @ColumnInfo(name = "userId")
    private int userId;

    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("dateTime")
    @Expose
    @ColumnInfo(name = "dateTime")
    private long dateTime;

    @SerializedName("checked")
    @Expose
    @ColumnInfo(name = "checked")
    private int checked;

    public Task() {
    }

    public Task(int userId, String name, long dateTime, boolean checked) {
        this.userId = userId;
        this.name = name;
        this.dateTime = dateTime;
        this.checked = checked ? 1 : 0;
    }

    protected Task(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        name = in.readString();
        dateTime = in.readLong();
        checked = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isChecked() {
        return this.checked == 1;
    }

    public void setChecked(boolean checked) {
        this.checked = checked ? 1 : 0;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }
}
