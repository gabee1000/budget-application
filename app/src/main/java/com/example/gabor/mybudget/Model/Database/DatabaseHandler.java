package com.example.gabor.mybudget.Model.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabor on 2017. 05. 20..
 */

public abstract class DatabaseHandler extends SQLiteOpenHelper {
    protected static final String DB_NAME = "budgets_db";
    protected static final int DB_VERSION = 1;

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    protected Cursor getCursorForAll(String tableName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(tableName,null,null,null,null,null,null,null);
        db.close();
        return cursor;
    }
}
