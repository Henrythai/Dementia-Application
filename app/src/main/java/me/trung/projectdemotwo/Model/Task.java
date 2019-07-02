package me.trung.projectdemotwo.Model;

import java.io.Serializable;
import java.util.Calendar;

public class Task implements Serializable {
    private int id;
    private int taskID;
    private String date;
    private String time;
    private String note;
    private String repeatType;
    private String isActive; //0 is false and 1 is true
    private String soundName;
    private String isDelete;
    private int userId;
    private String repeatId;

    public Task(int taskID, String date, String time, String note, String repeatType, String isActive, String soundName, String isDelete, int userId, String repeatId) {
        this.taskID = taskID;
        this.date = date;
        this.time = time;
        this.note = note;
        this.repeatType = repeatType;
        this.isActive = isActive;
        this.soundName = soundName;
        this.isDelete = isDelete;
        this.userId = userId;
        this.repeatId = repeatId;
    }

    public Task(int id, int taskID, String date, String time, String note, String repeatType, String isActive, String soundName, String isDelete, int userId, String repeatId) {
        this.id = id;
        this.taskID = taskID;
        this.date = date;
        this.time = time;
        this.note = note;
        this.repeatType = repeatType;
        this.isActive = isActive;
        this.soundName = soundName;
        this.isDelete = isDelete;
        this.userId = userId;
        this.repeatId = repeatId;
    }

    public Task(int taskID, String date, String time, String note, String repeatType, String isActive, String soundName, String isDelete, int userId) {
        this.taskID = taskID;
        this.date = date;
        this.time = time;
        this.note = note;
        this.repeatType = repeatType;
        this.isActive = isActive;
        this.soundName = soundName;
        this.isDelete = isDelete;
        this.userId = userId;
    }

    public Task() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRepeatType() {
        return repeatType;
    }

    public void setRepeatType(String repeatType) {
        this.repeatType = repeatType;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getSoundName() {
        return soundName;
    }

    public void setSoundName(String soundName) {
        this.soundName = soundName;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRepeatId() {
        return repeatId;
    }

    public void setRepeatId(String repeatId) {
        this.repeatId = repeatId;
    }
}
