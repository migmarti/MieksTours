package com.example.miek.miekstours.Classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MMART on 11/15/2017.
 */

//DatabaseHandler DBHelper = new DatabaseHandler(getApplicationContext());
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MiekToursLocalDB";
    private static final String TABLE_USERS = "Users";
    public final String KEY_USERID = "UserId";
    public final String KEY_FIRSTNAME = "FirstName";
    public final String KEY_LASTNAME = "LastName";
    public final String KEY_DOB = "DoB";
    public final String KEY_PASSWORD = "password";
    public final String KEY_EMAIL = "email";
    public final String KEY_DESCRIPTION = "Description";
    public final String KEY_HOSTING = "HostingStatus";
    public final String KEY_RATE = "Rate";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_USERID + " TEXT PRIMARY KEY,"
                + KEY_FIRSTNAME + " TEXT,"
                + KEY_LASTNAME + " TEXT"
                + KEY_DOB + " TEXT"
                + KEY_PASSWORD + " TEXT"
                + KEY_EMAIL + " TEXT"
                + KEY_DESCRIPTION + " TEXT"
                + KEY_HOSTING + " INTEGER"
                + KEY_RATE + " REAL"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
    }

//    void addContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_NAME, contact.getName()); // Contact Name
//        values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone
//
//        // Inserting Row
//        db.insert(TABLE_CONTACTS, null, values);
//        db.close(); // Closing database connection
//    }
//
//    Contact getContact(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
//                        KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
//                cursor.getString(1), cursor.getString(2));
//        return contact;
//    }
}
