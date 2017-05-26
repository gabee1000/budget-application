package com.example.gabor.mybudget.Model.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by Gabor on 2017. 05. 20..
 */

public abstract class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "budgets_db";
    private static final int DB_VERSION = 1;
    private String mTableName;
    private String mTableCreation;

    public DatabaseHandler(Context context, String tableName, String tableCreation) {
        super(context, DB_NAME, null, DB_VERSION);
        setTableName(tableName);
        setTableCreation(tableCreation);
        if (!doesExist(mTableName)) {
            onCreate(getWritableDatabase());
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(mTableCreation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + mTableName);
    }


    protected Cursor getCursorForAll(String tableName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        cursor = db.query(tableName, null, null, null, null, null, null, null);
        return cursor;
    }

    private boolean doesExist(String tableName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        return false;
    }

    protected List<?> getAll() {
        Cursor cursor = getCursorForAll(mTableName);
        return getAllEntitiesList(cursor);
    }

    abstract List<?> getAllEntitiesList(Cursor cursor);

    private void setTableName(String tableName) {
        this.mTableName = tableName;
    }

    private void setTableCreation(String tableCreation) {
        this.mTableCreation = tableCreation;
    }
}
