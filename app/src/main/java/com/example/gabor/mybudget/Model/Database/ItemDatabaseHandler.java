package com.example.gabor.mybudget.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gabor.mybudget.Model.Entities.Item;

import java.util.ArrayList;
import java.util.List;

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
                    + "UNIQUE(" + ITEM_NAME + ")"
                    + ");";

    public ItemDatabaseHandler(Context context) {
        super(context, TABLE_NAME, CREATE_TABLE);
    }

    /**
     * @param item
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long addItem(Item item) {
        long row = 0;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME, item.getName());
        contentValues.put(CATEGORY_ID, item.getCategoryId());
        contentValues.put(LAST_VALUE, item.getLastValue());
        int isIncome = item.isIncome() ? 1 : 0;
        contentValues.put(IS_INCOME, isIncome);
        row = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return row;
    }

    /**
     * <p>Remove an item row from item_table by name.</p>
     *
     * @param name of the item to delete from table.
     */
    public void removeItem(String name) {
        SQLiteDatabase db = getWritableDatabase();
        String statement = "DELETE FROM " + TABLE_NAME + " WHERE " + ITEM_NAME + " = '" + name + "'";
        db.execSQL(statement);
    }

    /**
     * <p>Get Item object from item_table by name.</p>
     *
     * @param itemName
     * @return the existing Item object from the table, or null if it does not exist.
     */
    public Item getItem(String itemName) {
        String where = ITEM_NAME + " = '" + itemName + "'";
        return getItemWhere(where);
    }

    /**
     * Get item object from item_table by ID.
     *
     * @param id
     * @return the Item object from the table, or null if it does not exist.
     */
    public Item getItem(long id) {
        String where = ID_KEY + " = " + id;
        return getItemWhere(where);
    }

    /**
     * <p>Get item object from the table.</p>
     *
     * @param where clause for querying the table.
     * @return the item object from the table or null if it does not exist.
     */
    private Item getItemWhere(String where) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, where, null, null, null, null);
        Item item = null;
        if (cursor.moveToFirst()) {
            item = getSingleItemFromCursor(cursor);
        }
        cursor.close();
        db.close();
        return item;
    }

    private Item getSingleItemFromCursor(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(ID_KEY));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(ITEM_NAME));
        int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(CATEGORY_ID));
        int lastValue = cursor.getInt(cursor.getColumnIndexOrThrow(LAST_VALUE));
        boolean isIncome;
        if (cursor.getInt(cursor.getColumnIndexOrThrow(IS_INCOME)) == 0) {
            isIncome = false;
        } else isIncome = true;
        return new Item(id, name, categoryId, lastValue, isIncome);
    }

    /**
     * Update an item in item_table by its id.
     *
     * @param newItem the item which will replace the old item.
     * @param id      of the item which will be replaced.
     * @return the number of rows affected.
     */
    public int updateItemById(Item newItem, long id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM_NAME, newItem.getName());
        contentValues.put(CATEGORY_ID, newItem.getCategoryId());
        contentValues.put(LAST_VALUE, newItem.getLastValue());
        int isIncome = newItem.isIncome() ? 1 : 0;
        contentValues.put(IS_INCOME, isIncome);
        String where = ID_KEY + " = " + id;
        int numberOfRowsAffected = db.update(TABLE_NAME, contentValues, where, null);
        db.close();
        return numberOfRowsAffected;
    }

    /**
     * <p>Get a new Item list containing all of the existing items from the DB.</p>
     * @return a list containing all of the Item objects.
     */
    @SuppressWarnings("unchecked")
    public List<Item> getAllItems() {
        return (List<Item>) getAll();
    }

    @Override
    List<Item> getAllEntitiesList(Cursor cursor) {
        List<Item> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Item item = getSingleItemFromCursor(cursor);
                list.add(item);
            } while (cursor.moveToNext());
        }
        return list;
    }
}
