package me.trung.projectdemotwo.Sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.io.IOException;
import java.util.ArrayList;

import me.trung.projectdemotwo.Model.Category;


public class TableCategory {
    public static final String TABLE_NAME = "Category";    // id name
    public static final String COL_ID = "cat_id";
    public static final String COL_NAME = "cat_name";
    public static final String COL_DESCRIPTION = "cat_description";
    public static final String COL_ICON_NAME = "cat_icon";


    private DatabaseHelper databaseHelper;

    public TableCategory(Context context) {
        try {
            databaseHelper = new DatabaseHelper(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Category> listCategories() throws SQLiteException {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ArrayList<Category> items = new ArrayList<>();
        boolean distinct = true;
        String table = TABLE_NAME;
        String[] columns = new String[]{COL_ID, COL_NAME, COL_ICON_NAME, COL_DESCRIPTION};
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        Cursor cursor = db.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int cat_id = cursor.getInt(cursor.getColumnIndex(COL_ID));
                String description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION));
                String cat_name = cursor.getString(cursor.getColumnIndex(COL_NAME));
                String cat_icon = cursor.getString(cursor.getColumnIndex(COL_ICON_NAME));
                Category category = new Category(cat_id, cat_name, description, cat_icon);
                items.add(category);
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

    public String getCategoryNamebyId(int cat_id) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String items = null;
        String table = TABLE_NAME;
        String[] columns = new String[]{COL_ID, COL_NAME};
        String selection = COL_ID + "=?";
        String[] selectionArgs = new String[]{cat_id + ""};
        Cursor cursor = db.query(true, table, columns, selection, selectionArgs, null, null, null, null);

        if (cursor.moveToNext()) {
            items = cursor.getString(cursor.getColumnIndex(COL_NAME));
        }
        cursor.close();
        db.close();
        return items;
    }

    public String getCategoryIconbyUd(int cat_id) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String cat_icon = null;
        Cursor cursor = db.rawQuery("Select " + COL_ID + "," + COL_ICON_NAME + " FROM " +
                TABLE_NAME + " WHERE " + COL_ID + "=?", new String[]{cat_id + ""});

        if (cursor.moveToNext()) {
            cat_icon = cursor.getString(cursor.getColumnIndex(COL_ICON_NAME));
        }
        cursor.close();
        db.close();
        return cat_icon;

    }


}
