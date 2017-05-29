package com.example.gabor.mybudget.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.gabor.mybudget.Model.Entities.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
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
                    + CREATED_TIME + " INTEGER DEFAULT 0, "
                    + IS_INCOME + " INTEGER DEFAULT 0 "
                    + ");";

    public TransactionDatabaseHandler(Context context) {
        super(context, TABLE_NAME, CREATE_TABLE);
    }

    /**
     * <p>Add a new transaction to the DB.</p>
     *
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

    /**
     * <p>Get a new Transaction list containing all of the existing items from the DB.</p>
     *
     * @return a list containing all of the Transaction objects.
     */
    @SuppressWarnings("unchecked")
    public List<Transaction> getAllItems() {
        return (List<Transaction>) getAll();
    }

    @Override
    List<Transaction> getAllEntitiesList(Cursor cursor) {
        List<Transaction> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = getSingleTransactionFromCursor(cursor);
                list.add(transaction);
            } while (cursor.moveToNext());
        }
        return list;
    }

    private Transaction getSingleTransactionFromCursor(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(ID_KEY));
        long userId = cursor.getLong(cursor.getColumnIndexOrThrow(USER_ID));
        long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(ITEM_ID));
        long value = cursor.getLong(cursor.getColumnIndexOrThrow(VALUE));
        long createdTime = cursor.getLong(cursor.getColumnIndexOrThrow(CREATED_TIME));
        boolean isIncome;
        if (cursor.getInt(cursor.getColumnIndexOrThrow(IS_INCOME)) == 0) {
            isIncome = false;
        } else isIncome = true;
        return new Transaction(id, userId, itemId, value, createdTime, isIncome);
    }

    /**
     * <p>Get transaction list from DB by date time (year and month).</p>
     * <p>When <i>givenYear</i> <b>AND</b> <i>givenMonth</i> are supplied in the parameters, the method will ignore the parameter <i>time</i>, so plan accordingly.</p>
     *
     * @param time       in milliseconds format.
     * @param givenYear  Year in 'YYYY' format.
     * @param givenMonth Month in 'MM' or 'M' format.
     * @return List of Transaction objects, or an empty list if no transaction exists at the given time.
     */
    public List<Transaction> getAllTransactionByDate(Long time, Integer givenYear, Integer givenMonth) {
        if (givenYear <= 0 || givenMonth <= 0) {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(time);
            givenYear = c.get(Calendar.YEAR);
            givenMonth = c.get(Calendar.MONTH) + 1;
        }

        String year = String.valueOf(givenYear);
        String month;
        if (givenMonth < 10) {
            month = "0" + givenMonth;
        } else {
            month = String.valueOf(givenMonth);
        }

        SQLiteDatabase db = getReadableDatabase();
        List<Transaction> list = new ArrayList<>();
        String query = "SELECT *" +
                " FROM " + TABLE_NAME +
                " WHERE strftime('%Y', " + CREATED_TIME + " / 1000, 'unixepoch') = '" + year + "' AND " +
                "strftime('%m', " + CREATED_TIME + " / 1000, 'unixepoch') = '" + month + "'";
        Log.e("QUERY = ", query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = getSingleTransactionFromCursor(cursor);
                Log.e("transaction= ", transaction.toString());
                list.add(transaction);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    /**
     * <p>Get all the transactions from the DB by year.</p>
     * @param givenYear Year in 'YYYY' format.
     * @return Transaction list matching the given year.
     */
    public List<Transaction> getAllTransactionByYear(int givenYear) {
        String year = String.valueOf(givenYear);

        SQLiteDatabase db = getReadableDatabase();
        List<Transaction> list = new ArrayList<>();
        String query = "SELECT *" +
                " FROM " + TABLE_NAME +
                " WHERE strftime('%Y', " + CREATED_TIME + " / 1000, 'unixepoch') = '" + year + "'";
        Log.e("QUERY = ", query);
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = getSingleTransactionFromCursor(cursor);
                Log.e("transaction= ", transaction.toString());
                list.add(transaction);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }
}
