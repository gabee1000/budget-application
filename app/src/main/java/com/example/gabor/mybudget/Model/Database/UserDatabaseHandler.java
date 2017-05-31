package com.example.gabor.mybudget.Model.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gabor.mybudget.Model.Entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserDatabaseHandler extends DatabaseHandler {
    private static final String TABLE_NAME = "user_table";
    private static final String ID_KEY = "id_key";
    private static final String USER_NAME = "user";
    private static final String PASSWORD = "password";

    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                    + ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + USER_NAME + " TEXT NOT NULL, "
                    + PASSWORD + " TEXT NOT NULL,"
                    + "UNIQUE(" + USER_NAME + ")"
                    + ");";

    public UserDatabaseHandler(Context context) {
        super(context, TABLE_NAME, CREATE_TABLE);
    }

    /**
     * @param user to insert into table.
     * @return the row ID of the newly inserted row, or -1 if an error occurred (user already exists).
     */
    public long addUser(User user) {
//        if (userNameExists(user.getName())) {
//            return false;
//        }
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_NAME, user.getName());
        contentValues.put(PASSWORD, user.getPassword());
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        if (result == -1) {
            return -1;
        }
        return result;
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

//    public List<User> getAllUser() {
//        List<User> userList = new ArrayList<>();
//        Cursor cursor = getCursorForAll(TABLE_NAME);
//        do {
//            String name = cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME));
//            String password = cursor.getString(cursor.getColumnIndexOrThrow(PASSWORD));
//            User user = new User(name, password);
//            userList.add(user);
//        } while (cursor.moveToNext());
//        return userList;
//    }

    /**
     * <p>Authenticate the given username and password.</p>
     *
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

    @Override
    List<?> getAllEntitiesList(Cursor cursor) {
        return null;
    }

    /**
     * <p>Get the user by name.</p>
     * @param userName of the user
     * @return The User object, or null if it does not exist.
     */
    public User getUser(String userName) {
        String where = USER_NAME + " = '" + userName + "'";
        return getUserWhere(where);
    }

    /**
     * <p>Get the user by ID.</p>
     *
     * @param id of the user
     * @return the User object from the table, or null if it does not exist.
     */
    public User getUser(int id) {
        String where = ID_KEY + " = " + id;
        return getUserWhere(where);
    }

    /**
     * <p>Get User object from the table.</p>
     *
     * @param where clause for querying the table.
     * @return the User object from the table or null if it does not exist.
     */
    private User getUserWhere(String where) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, where, null, null, null, null);
        User user = null;
        if (cursor.moveToFirst()) {
            user = getSingleUserFromCursor(cursor);
        }
        cursor.close();
        db.close();
        return user;
    }

    private User getSingleUserFromCursor(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(ID_KEY));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME));
        String password = cursor.getString(cursor.getColumnIndexOrThrow(PASSWORD));
        return new User(id, name, password);
    }
}
