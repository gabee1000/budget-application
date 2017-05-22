package com.example.gabor.mybudget.Model.Database;

import android.content.Context;

/**
 * Created by Gabor on 2017. 05. 22..
 */

public class TransactionDatabaseHandler extends DatabaseHandler {
    private static final String TABLE_NAME = "transaction_table";
    private static final String ID_KEY = "id_key";
    private static final String USER_ID = "user_id";
    private static final String ITEM_ID = "item_id";
    private static final String VALUE = "value";
    private static final String CREATED_TIME = "created_time";
    private static final String IS_INCOME = "is_income";

    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + USER_ID + " INTEGER NOT NULL, "
                    + ITEM_ID + " INTEGER NOT NULL, "
                    + VALUE + " INTEGER DEFAULT 0, "
                    + CREATED_TIME + " TEXT NOT NULL, "
                    + IS_INCOME + " INTEGER DEFAULT 0 "
                    + ");";

    public TransactionDatabaseHandler(Context context) {
        super(context, TABLE_NAME, CREATE_TABLE);
    }

}
