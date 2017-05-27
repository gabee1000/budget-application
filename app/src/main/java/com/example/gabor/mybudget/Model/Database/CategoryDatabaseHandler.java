package com.example.gabor.mybudget.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

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

    /**
     * @param categoryName to insert into table.
     * @return the row ID of the newly inserted row, or -1 if an error occurred (category already exists).
     */
    public long addCategory(String categoryName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_NAME, categoryName);
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        if (result == -1) {
            return -1;
        }
        return result;
    }

    /**
     * <p>Get the specified category name's unique ID.</p>
     * @param categoryName
     * @return The ID of the category, or -1 if this category does not exist.
     */
    public long getCategoryId(String categoryName) {
        SQLiteDatabase db = getReadableDatabase();
        long id = -1;
        Cursor cursor = db.rawQuery(
                "SELECT " + ID_KEY +
                " FROM " + TABLE_NAME +
                " WHERE " + CATEGORY_NAME + " = '" + categoryName + "'", null);
        if (cursor.moveToFirst()) {
            id = cursor.getLong(cursor.getColumnIndexOrThrow(ID_KEY));
        }
        cursor.close();
        db.close();
        return id;
    }

    @Override
    List<?> getAllEntitiesList(Cursor cursor) {
        return null;
    }

    /**
     * <p>Get the category name by ID.</p>
     * @param categoryId ID of the category to query
     * @return Name of the category, or null if the category does not exist.
     */
    public String getCategory(long categoryId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + CATEGORY_NAME
                + " FROM " + TABLE_NAME
                + " WHERE " + ID_KEY + " = " + categoryId, null);
        String categoryName = null;
        if (cursor.moveToFirst()) {
            categoryName = cursor.getString(cursor.getColumnIndexOrThrow(CATEGORY_NAME));
        }
        cursor.close();
        db.close();
        return categoryName;
    }

    public int updateCategoryById(String newCategoryName, long id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CATEGORY_NAME, newCategoryName);
        String where = ID_KEY + " = " + id;
        int numberOfRowsAffected = db.update(TABLE_NAME, contentValues, where, null);
        db.close();
        return numberOfRowsAffected;
    }
}
