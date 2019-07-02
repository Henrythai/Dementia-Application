package me.trung.projectdemotwo.Sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import me.trung.projectdemotwo.Class.Helpers;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DB_PATH = null;
    public static final String DB_NAME = "project.db";
    public static final int DB_VERSION = 1;
    private SQLiteDatabase myDatabase;
    private Context context;

    public DatabaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        DB_PATH = Helpers.getDatabasePath(DB_NAME, context);
        if (!checkIfDBExists()) {
            createDatabase();
        }
        openDatabase();
    }

    public void createDatabase() throws IOException {
        Boolean dbExist = checkIfDBExists();
        if (dbExist) {

        } else {
            getReadableDatabase();
            this.close();
            try {
                copyDatabase();
            } catch (SQLiteException ex) {
                throw new Error("Error Copying Database");
            }
        }
    }

    private boolean checkIfDBExists() {
        File dbFile = new File(DB_PATH);
        return dbFile.exists();
    }


    private void copyDatabase() throws IOException {
        InputStream inputStream = context.getAssets().open(DB_NAME);

        String outFileName = DB_PATH;
        Log.e("Check Path", outFileName);
        OutputStream outputStream = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public void openDatabase() throws SQLiteException {
        myDatabase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (myDatabase != null) {
            myDatabase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion > oldVersion) {
            try {
                copyDatabase();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
