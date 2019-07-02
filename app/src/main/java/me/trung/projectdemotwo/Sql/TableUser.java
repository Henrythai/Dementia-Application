package me.trung.projectdemotwo.Sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

import me.trung.projectdemotwo.Model.Task;
import me.trung.projectdemotwo.Model.User;

public class TableUser {
    public static final String TABLE_NAME = "User";

    public static final String COL_ID = "UserId";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";
    public static final String COL_LOCATION = "location";
    public static final String COL_AGE = "age";
    public static final String COL_GENDER = "gender";
    public static final String COL_IS_DEMENTIA = "isDementia";
    public static final String COL_IS_ACTIVE = "isActive";
    public static final String COL_IMAGE_NAME = "imageName";
    public static final String COL_EDUCATION_LEVEL = "education_level";


    private DatabaseHelper databaseHelper;

    public TableUser(Context context) {
        try {
            databaseHelper = new DatabaseHelper(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getuser(String email) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where " + COL_EMAIL + "=?", new String[]{email});
        User user = null;
        if (cursor.moveToNext()) {
            int Id = cursor.getInt(cursor.getColumnIndex(COL_ID));
            String Email = cursor.getString(cursor.getColumnIndex(COL_EMAIL));
            String Password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD));
            String Location = cursor.getString(cursor.getColumnIndex(COL_LOCATION));
            int Age = cursor.getInt(cursor.getColumnIndex(COL_AGE));
            String Gender = cursor.getString(cursor.getColumnIndex(COL_GENDER));
            int isDementia = cursor.getInt(cursor.getColumnIndex(COL_IS_DEMENTIA));
            String education_level = cursor.getString(cursor.getColumnIndex(COL_EDUCATION_LEVEL));
            byte[] imageName = cursor.getBlob(cursor.getColumnIndex(COL_IMAGE_NAME));

            user = new User(Email, Password, Location, Age, Gender, isDementia, imageName, education_level);
            user.setId(Id);
        }
        cursor.close();
        db.close();
        return user;
    }

    public void addUser(User user) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.putNull(COL_ID);
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_PASSWORD, user.getPassword());
        values.put(COL_LOCATION, user.getLocation());
        values.put(COL_AGE, user.getAge());
        values.put(COL_GENDER, user.getGender());
        values.put(COL_IS_DEMENTIA, user.getIsDementia());
        values.put(COL_IS_ACTIVE, 1);
        values.put(COL_IMAGE_NAME, user.getImageName());
        values.put(COL_EDUCATION_LEVEL, user.getEducation_level());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    public boolean checkUser(String email) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {COL_ID};
        String selection = COL_EMAIL + "=?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    public int getUserIdbyEmail(String email) {
        int id = -1;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {COL_ID, COL_EMAIL};
        String selection = COL_EMAIL + "=?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex(COL_ID));
        }
        cursor.close();
        db.close();
        return id;
    }


    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {COL_ID};
        String selection = COL_EMAIL + "=?" + " AND " + COL_PASSWORD + "=?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }

    public boolean updatePassword(String email, String password) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_PASSWORD, password);

        int rows = db.update(TABLE_NAME, values, COL_EMAIL + "=?", new String[]{email});
        db.close();
        return rows > 0;

    }

    public boolean updateUserInfo(String email, User user) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_LOCATION, user.getLocation());
        values.put(COL_AGE, user.getAge());
        values.put(COL_GENDER, user.getGender());
        values.put(COL_IS_DEMENTIA, user.getIsDementia());
        values.put(COL_IMAGE_NAME, user.getImageName());
        values.put(COL_EDUCATION_LEVEL, user.getEducation_level());

        int rows = db.update(TABLE_NAME, values, COL_EMAIL + "=?", new String[]{email});
        db.close();
        if (rows > 0) {
            return true;
        }
        return false;
    }

}
