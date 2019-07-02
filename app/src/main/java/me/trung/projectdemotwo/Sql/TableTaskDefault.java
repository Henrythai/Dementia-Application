package me.trung.projectdemotwo.Sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import me.trung.projectdemotwo.Model.ModelTask;

public class TableTaskDefault {
    public static final String TABLE_NAME = "Task";

    public static final String COL_ID = "task_id";
    public static final String COL_NAME = "task_name";
    public static final String COL_SUBCAT_ID = "subcategory_id";


    public static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NAME + " TEXT, " +
            COL_SUBCAT_ID + " INTEGER) ";


    private DatabaseHelper databaseHelper;

    public TableTaskDefault(Context context) {
        try {
            databaseHelper = new DatabaseHelper(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ModelTask getMTaskById(int id) {
        ModelTask task = null;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * From " + TABLE_NAME + " WHERE " + COL_ID + " =?", new String[]{String.valueOf(id)});
        //int id, int taskID, String date, String time, String note, String repeatType, String isActive, String soundName, String isDelete, int userId
        if (cursor.moveToNext()) {
            task = new ModelTask(id, cursor.getString(cursor.getColumnIndex(COL_NAME)), cursor.getInt(cursor.getColumnIndex(COL_SUBCAT_ID)));
        }
        cursor.close();
        db.close();
        return task;
    }

    public Boolean InsertTemplateTask(String taskName, int SubCategoryId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.putNull(COL_ID);
        values.put(COL_NAME, taskName);
        values.put(COL_SUBCAT_ID, SubCategoryId);
        long rows = db.insert(TABLE_NAME, null, values);
        db.close();
        if (rows > 0) return true;
        return false;
    }

    public ArrayList<ModelTask> listTasksByCatId(int Subcat_id) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ArrayList<ModelTask> items = new ArrayList<>();
        boolean distinct = true;
        String table = TABLE_NAME;
        String[] columns = new String[]{COL_ID, COL_NAME, COL_SUBCAT_ID};
        String selection = COL_SUBCAT_ID + "=?";
        String[] selectionArgs = new String[]{Subcat_id + ""};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        Cursor cursor = db.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int task_id = cursor.getInt(cursor.getColumnIndex(COL_ID));
                String task_name = cursor.getString(cursor.getColumnIndex(COL_NAME));
                int category_id = cursor.getInt(cursor.getColumnIndex(COL_SUBCAT_ID));
                ModelTask modelTask = new ModelTask(task_id, task_name, category_id);
                items.add(modelTask);
            }
        } else {
            cursor.close();
            db.close();
            return null;
        }
        cursor.close();
        db.close();
        return items;
    }



}
