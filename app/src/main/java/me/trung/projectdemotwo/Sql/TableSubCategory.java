package me.trung.projectdemotwo.Sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import me.trung.projectdemotwo.Model.SubCategory;

public class TableSubCategory {
    public static final String TABLE_NAME = "SubCategory";
    public static final String COL_ID = "SubCategoryId";
    public static final String COL_CATEGORY_ID = "CategoryId";
    public static final String COL_NAME = "SubCategoryName";
    public static final String COL_ICON_NAME = "SubCategoryIcon";
    private DatabaseHelper databaseHelper;

    public TableSubCategory(Context context) {
        try {
            databaseHelper = new DatabaseHelper(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SubCategory> listSubCategoryByCatId(int CategoryId) {
        ArrayList<SubCategory> items = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(" select * from " + TABLE_NAME + " where " + COL_CATEGORY_ID + "=?", new String[]{String.valueOf(CategoryId)});
        while (cursor.moveToNext()) {
            int subCategoryId = cursor.getInt(cursor.getColumnIndex(COL_ID));
            int CategoryId1 = cursor.getInt(cursor.getColumnIndex(COL_CATEGORY_ID));
            String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
            String icon = cursor.getString(cursor.getColumnIndex(COL_ICON_NAME));
            SubCategory subCategory = new SubCategory(subCategoryId, CategoryId1, name, icon);
            items.add(subCategory);
        }
        return items;
    }

    public SubCategory getSubCategoryById(int SubId) {
        SubCategory item = null;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select " + COL_ID + "," + COL_NAME + "," + COL_CATEGORY_ID + "," + COL_ICON_NAME + " from " +
                TABLE_NAME + " where " + COL_ID + "=?", new String[]{String.valueOf(SubId)});
        if (cursor.moveToNext()) {
            int subCategoryId = cursor.getInt(cursor.getColumnIndex(COL_ID));
            int CategoryId1 = cursor.getInt(cursor.getColumnIndex(COL_CATEGORY_ID));
            String name = cursor.getString(cursor.getColumnIndex(COL_NAME));
            String icon = cursor.getString(cursor.getColumnIndex(COL_ICON_NAME));
            item = new SubCategory(subCategoryId, CategoryId1, name, icon);
        }
        cursor.close();
        db.close();
        return item;
    }

    public Hashtable<Integer, String> getImageSubCategoryIcon() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Hashtable<Integer, String> images = new Hashtable<>();
        boolean distinct = true;
        String table = TABLE_NAME;
        String[] columns = new String[]{COL_ID, COL_ICON_NAME};

        Cursor cursor = db.query(distinct, table, columns, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int task_id = cursor.getInt(cursor.getColumnIndex(COL_ID));
                String icon = cursor.getString(cursor.getColumnIndex(COL_ICON_NAME));
                images.put(task_id, icon);
            }
        }
        cursor.close();
        db.close();
        return images;
    }
}
