package me.trung.projectdemotwo.Model;

public class TaskDisplayItem {
    //remember get id for fetching value
    private int Id;
    private String mTaskName;
    private String mDate;
    private String mTime;
    private String mRepeatype;
    private String mActive;
    private String mRepeatID;
    private String mIconImage;


    public TaskDisplayItem(int id, String mTaskName, String mDate, String mTime, String mRepeatype, String mActive, String mRepeatID, String ImageIcon) {
        Id = id;
        this.mTaskName = mTaskName;
        this.mDate = mDate;
        this.mTime = mTime;
        this.mRepeatype = mRepeatype;
        this.mActive = mActive;
        this.mRepeatID = mRepeatID;
        this.mIconImage = ImageIcon;
    }

    public String getmRepeatID() {
        return mRepeatID;
    }

    public void setmRepeatID(String mRepeatID) {
        this.mRepeatID = mRepeatID;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmTaskName() {
        return mTaskName;
    }

    public void setmTaskName(String mTaskName) {
        this.mTaskName = mTaskName;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmRepeatype() {
        return mRepeatype;
    }

    public void setmRepeatype(String mRepeatype) {
        this.mRepeatype = mRepeatype;
    }

    public String getmActive() {
        return mActive;
    }

    public void setmActive(String mActive) {
        this.mActive = mActive;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getmIconImage() {
        return mIconImage;
    }

    public void setmIconImage(String mIconImage) {
        this.mIconImage = mIconImage;
    }
}
