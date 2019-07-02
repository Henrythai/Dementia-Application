package me.trung.projectdemotwo.Sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;

import me.trung.projectdemotwo.Model.Task;
import me.trung.projectdemotwo.Model.TaskDisplayItem;


public class TableTask {
    public static final String TABLE_NAME = "Task_transaction";
    public static final String COL_ID = "id";
    public static final String COL_TASK_ID = "task_id";
    public static final String COL_TIME = "time";
    public static final String COL_DATE = "date";
    public static final String COL_NOTE = "note";
    public static final String COL_REPEAT_TYPE = "repeatType";
    public static final String COL_REPEAT_ID = "repeatId";
    public static final String COL_IS_ACTIVE = "isActive";
    public static final String COL_SOUND_NAME = "soundName";
    public static final String COL_IS_DELETE = "isDelete";
    public static final String COL_USER_ID = "userId";


    private DatabaseHelper databaseHelper;

    public TableTask(Context context) {
        try {
            databaseHelper = new DatabaseHelper(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Task getTaskById(int id) {
        Task task = null;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * From " + TABLE_NAME + " WHERE " + COL_ID + " =?", new String[]{String.valueOf(id)});

        if (cursor.moveToNext()) {
            int taskID = cursor.getInt(cursor.getColumnIndex(COL_TASK_ID));
            String date = cursor.getString(cursor.getColumnIndex(COL_DATE));
            String time = cursor.getString(cursor.getColumnIndex(COL_TIME));
            String note = cursor.getString(cursor.getColumnIndex(COL_NOTE));
            String repeatType = cursor.getString(cursor.getColumnIndex(COL_REPEAT_TYPE));
            String isActive = cursor.getString(cursor.getColumnIndex(COL_IS_ACTIVE));
            String soundName = cursor.getString(cursor.getColumnIndex(COL_SOUND_NAME));
            String isDelete = cursor.getString(cursor.getColumnIndex(COL_IS_DELETE));
            String repeatId = cursor.getString(cursor.getColumnIndex(COL_REPEAT_ID));
            int userId = cursor.getInt(cursor.getColumnIndex(COL_USER_ID));
            task = new Task(id, taskID, date, time, note, repeatType, isActive, soundName, isDelete, userId, repeatId);
        }
        cursor.close();
        db.close();
        return task;
    }


    public ArrayList<Task> getAllActiveTask() {
        ArrayList<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " Where " + COL_IS_ACTIVE + "=?", new String[]{"true"});
        while (cursor.moveToNext()) {
            int id1 = cursor.getInt(cursor.getColumnIndex(COL_ID));
            int taskID = cursor.getInt(cursor.getColumnIndex(COL_TASK_ID));
            String date = cursor.getString(cursor.getColumnIndex(COL_DATE));
            String time = cursor.getString(cursor.getColumnIndex(COL_TIME));
            String note = cursor.getString(cursor.getColumnIndex(COL_NOTE));
            String repeatType = cursor.getString(cursor.getColumnIndex(COL_REPEAT_TYPE));
            String isActive = cursor.getString(cursor.getColumnIndex(COL_IS_ACTIVE));
            String soundName = cursor.getString(cursor.getColumnIndex(COL_SOUND_NAME));
            String repeatId = cursor.getString(cursor.getColumnIndex(COL_REPEAT_ID));
            String isDelete = cursor.getString(cursor.getColumnIndex(COL_IS_DELETE));
            int userId = cursor.getInt(cursor.getColumnIndex(COL_USER_ID));
            Task task = new Task(id1, taskID, date, time, note, repeatType, isActive, soundName, isDelete, userId, repeatId);
            tasks.add(task);
        }

        cursor.close();
        db.close();
        return tasks;
    }


    public int InsertTask(Task task) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.putNull(COL_ID);
        values.put(COL_TASK_ID, task.getTaskID());
        values.put(COL_NOTE, task.getNote());
        values.put(COL_REPEAT_TYPE, task.getRepeatType());
        values.put(COL_IS_ACTIVE, task.getIsActive());
        values.put(COL_TIME, task.getTime());
        values.put(COL_DATE, task.getDate());
        values.put(COL_SOUND_NAME, task.getSoundName());
        values.put(COL_IS_DELETE, "false");
        values.put(COL_REPEAT_ID, task.getRepeatId());
        values.put(COL_USER_ID, task.getUserId());

        long rows = db.insert(TABLE_NAME, null, values);
        db.close();
        return (int) rows;
    }

    public Boolean setDoneTaskbyID(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_IS_ACTIVE, "false");

        int rows = db.update(TABLE_NAME, values, COL_ID + " = ?", new String[]{id + ""});
        db.close();
        if (rows > 0) return true;
        return false;
    }

    public Boolean ActivateTaskbyID(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_IS_ACTIVE, "true");

        int rows = db.update(TABLE_NAME, values, COL_ID + " = ?", new String[]{id + ""});
        db.close();
        if (rows > 0) return true;
        return false;
    }

    public Boolean DeleteTaskbyId(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_IS_DELETE, "true");
        values.put(COL_IS_ACTIVE, "false");
        int rows = db.update(TABLE_NAME, values, COL_ID + " = ?", new String[]{id + ""});
        db.close();
        if (rows > 0) return true;
        return false;
    }

    public ArrayList<TaskDisplayItem> getUnActiveTask(int userId) {
        ArrayList<TaskDisplayItem> tasks = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String tabletaskId = TableTaskDefault.TABLE_NAME + "." + TableTaskDefault.COL_ID;
        String tabletransactionId = TABLE_NAME + "." + COL_TASK_ID;
        String tableSubCategoryId = TableSubCategory.TABLE_NAME + "." + TableSubCategory.COL_ID;
        String taskSubCategoryId = TableTaskDefault.TABLE_NAME + "." + TableTaskDefault.COL_SUBCAT_ID;
        String SQL_query = "select * from " + TABLE_NAME + "  inner join " + TableTaskDefault.TABLE_NAME
                + " on " + tabletaskId + " = " + tabletransactionId + " " +
                "inner join " + TableSubCategory.TABLE_NAME + " on " + tableSubCategoryId + "=" + taskSubCategoryId + " \n" +
                "inner join " + TableCategory.TABLE_NAME + " on Category.cat_id=SubCategory.CategoryId " + " where " +
                COL_IS_ACTIVE + "=\"false\" " + " and " + COL_USER_ID + "=" + userId;
        //  Log.e("check", SQL_query);
        Cursor cursor = db.rawQuery(SQL_query, null);

        while (cursor.moveToNext()) {
            String mTaskName = cursor.getString(cursor.getColumnIndex(TableTaskDefault.COL_NAME));
            String mDate = cursor.getString(cursor.getColumnIndex(COL_DATE));
            String mTime = cursor.getString(cursor.getColumnIndex(COL_TIME));
            String mRepeatype = cursor.getString(cursor.getColumnIndex(COL_REPEAT_TYPE));
            String mActive = cursor.getString(cursor.getColumnIndex(COL_IS_ACTIVE));
            int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
            String repeatedId = cursor.getString(cursor.getColumnIndex(COL_REPEAT_ID));
            String imgIcon = cursor.getString(cursor.getColumnIndex(TableCategory.COL_ICON_NAME));
            TaskDisplayItem item = new TaskDisplayItem(id, mTaskName, mDate, mTime, mRepeatype, mActive, repeatedId, imgIcon);
            tasks.add(item);
        }
        cursor.close();
        db.close();
        return tasks;
    }

    public ArrayList<TaskDisplayItem> getActiveTask(int CategoryId, String repeatType, int userId) {
        ArrayList<TaskDisplayItem> tasks = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String tabletaskId = TableTaskDefault.TABLE_NAME + "." + TableTaskDefault.COL_ID;
        String tabletransactionId = TABLE_NAME + "." + COL_TASK_ID;
        String tableSubCategoryId = TableSubCategory.TABLE_NAME + "." + TableSubCategory.COL_ID;
        String taskSubCategoryId = TableTaskDefault.TABLE_NAME + "." + TableTaskDefault.COL_SUBCAT_ID;
        String SQL_query = "select * from " + TABLE_NAME + "  inner join " + TableTaskDefault.TABLE_NAME
                + " on " + tabletaskId + " = " + tabletransactionId + " " +
                "inner join " + TableSubCategory.TABLE_NAME + " on " + tableSubCategoryId + "=" + taskSubCategoryId + " \n" +
                "inner join " + TableCategory.TABLE_NAME + " on Category.cat_id=SubCategory.CategoryId " + " where " +
                COL_IS_ACTIVE + "=\"true\" " + " and " + COL_USER_ID + "=" + userId;

        if (CategoryId != -1) {
            SQL_query += " and " + TableSubCategory.COL_CATEGORY_ID + "=" + CategoryId;
        }
        if (!TextUtils.isEmpty(repeatType)) {
            SQL_query += " and " + COL_REPEAT_TYPE + "=" + "\"" + repeatType + "\"";
        }

        //   Log.e("check", SQL_query);
        Cursor cursor = db.rawQuery(SQL_query, null);

        while (cursor.moveToNext()) {
            String mTaskName = cursor.getString(cursor.getColumnIndex(TableTaskDefault.COL_NAME));
            String mDate = cursor.getString(cursor.getColumnIndex(COL_DATE));
            String mTime = cursor.getString(cursor.getColumnIndex(COL_TIME));
            String mRepeatype = cursor.getString(cursor.getColumnIndex(COL_REPEAT_TYPE));
            String mActive = cursor.getString(cursor.getColumnIndex(COL_IS_ACTIVE));
            int id = cursor.getInt(cursor.getColumnIndex(COL_ID));
            String repeatedId = cursor.getString(cursor.getColumnIndex(COL_REPEAT_ID));
            String imgIcon = cursor.getString(cursor.getColumnIndex(TableCategory.COL_ICON_NAME));
            TaskDisplayItem item = new TaskDisplayItem(id, mTaskName, mDate, mTime, mRepeatype, mActive, repeatedId, imgIcon);
            tasks.add(item);
        }
        cursor.close();
        db.close();
        return tasks;
    }


    public boolean updateItembyId(Task task) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TASK_ID, task.getTaskID());
        values.put(COL_DATE, task.getDate());
        values.put(COL_TIME, task.getTime());
        values.put(COL_NOTE, task.getNote());
        values.put(COL_REPEAT_TYPE, task.getRepeatType());
        values.put(COL_SOUND_NAME, task.getSoundName());
        values.put(COL_REPEAT_ID, task.getRepeatId());

        int rows = db.update(TABLE_NAME, values, COL_ID + "=?", new String[]{String.valueOf(task.getId())});
        db.close();
        if (rows > 0) {
            return true;
        }
        return false;
    }

}
