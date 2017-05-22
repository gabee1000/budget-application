package com.example.gabor.mybudget.Model.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Gabor on 2017. 05. 21..
 */

public class ItemDatabaseHandler extends DatabaseHandler {

    private static final String TABLE_NAME = "item_table";
    private static final String ID_KEY = "id_key";
    private static final String ITEM_NAME = "item_name";
    private static final String CATEGORY_ID = "category_id";
    private static final String LAST_VALUE = "last_value";
    private static final String IS_INCOME = "is_income";

    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ITEM_NAME + " TEXT NOT NULL, "
                    + CATEGORY_ID + " INTEGER NOT NULL, "
                    + LAST_VALUE + " INTEGER DEFAULT 0, "
                    + IS_INCOME + " INTEGER DEFAULT 0,"
                    + "UNIQUE(" + ID_KEY + ", " + ITEM_NAME + ")"
                    + ");";

    public ItemDatabaseHandler(Context context) {
        super(context, TABLE_NAME, CREATE_TABLE);
    }


}
