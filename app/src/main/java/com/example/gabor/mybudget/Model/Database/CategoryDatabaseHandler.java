package com.example.gabor.mybudget.Model.Database;

import android.content.Context;

/**
 * Created by Gabor on 2017. 05. 22..
 */

public class CategoryDatabaseHandler extends DatabaseHandler {
    private static final String TABLE_NAME = "category_table";
    private static final String ID_KEY = "id_key";
    private static final String CATEGORY_NAME = "category_name";

    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CATEGORY_NAME + " TEXT NOT NULL,"
                    + "UNIQUE(" + CATEGORY_NAME + ")"
                    + ");";

    public CategoryDatabaseHandler(Context context) {
        super(context, TABLE_NAME, CREATE_TABLE);
    }
}
