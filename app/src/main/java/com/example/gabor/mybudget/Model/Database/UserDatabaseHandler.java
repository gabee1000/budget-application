package com.example.gabor.mybudget.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gabor.mybudget.Model.Entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabor on 2017. 05. 20..
 */

public class UserDatabaseHandler extends DatabaseHandler {
    private static final String USER_NAME = "user";
    private static final String TABLE_NAME = "user_table";
    private static final String ID_KEY = "id_key";
    private static final String PASSWORD = "password";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_NAME + " TEXT, " + PASSWORD + " TEXT);";

    public UserDatabaseHandler(Context context) {
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    /**
     * @param user to insert into table.
     * @return true if user was added successfully to the db, false otherwise (user already exists).
     */
    public boolean addUser(User user) {
        if (userNameExists(user.getName())) {
            return false;
        }
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, user.getName());
        contentValues.put(PASSWORD, user.getPassword());
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    /**
     * <p>Checks whether the username exists in the database or not.</p>
     *
     * @param userName name of the user
     * @return True if username exists, false otherwise.
     */
    public boolean userNameExists(String userName) {
        boolean exists = false;
        SQLiteDatabase db = getReadableDatabase();
        String where = USER_NAME + " = '" + userName + "'";
        Cursor cursor = db.query(TABLE_NAME, new String[]{USER_NAME}, where, null, null, null, null);
        if (cursor.moveToFirst()) {
            exists = true;
        }
        db.close();
        return exists;
    }

    public List<User> getAllUser() {
        List<User> userList = new ArrayList<>();
        Cursor cursor = getCursorForAll(TABLE_NAME);
        do {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(PASSWORD));
            User user = new User(name, password);
            userList.add(user);
        } while (cursor.moveToNext());
        return userList;
    }

    /**
     * <p>Authenticate the given username and password.</p>
     * @param name
     * @param password
     * @return True if authentication successful, false otherwise.
     */
    public boolean authenticate(String name, String password) {
        SQLiteDatabase db = getReadableDatabase();
        String where = USER_NAME + " = '" + name + "' AND " + PASSWORD + " = '" + password + "'";
        String[] columns = new String[]{USER_NAME, PASSWORD};
        Cursor cursor = db.query(TABLE_NAME, columns, where, null, null, null, null);
        boolean authentic = false;
        if (cursor.moveToFirst()) {
            authentic = true;
        }
        db.close();
        return authentic;
    }
}
