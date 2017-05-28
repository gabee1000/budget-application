package com.example.gabor.mybudget.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gabor.mybudget.Model.Entities.Transaction;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * <p>Add a new transaction to the DB.</p>
     * @param newTransaction to add to the DB.
     * @return The newly created row ID of the transaction, or -1 if it does not exist.
     */
    public long addTransaction(Transaction newTransaction) {
        long row = 0;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_ID, newTransaction.getUserId());
        contentValues.put(ITEM_ID, newTransaction.getItemId());
        contentValues.put(VALUE, newTransaction.getValue());
//        contentValues.put(CREATED_TIME, DateFormat.getDateTimeInstance().format(new Date(System.currentTimeMillis())));
        contentValues.put(CREATED_TIME, newTransaction.getCreatedTime());
        int isIncome = newTransaction.isIncome() ? 1 : 0;
        contentValues.put(IS_INCOME, isIncome);
        row = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return row;
    }

    @Override
    List<?> getAllEntitiesList(Cursor cursor) {
        return null;
    }
}
