package com.example.miek.miekstours.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by MMART on 11/15/2017.
 */

//DatabaseHandler DBHelper = new DatabaseHandler(getApplicationContext());
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "MiekToursLocalDB2";
    private static final String TABLE_USERS = "Users";
    public final String KEY_USERID = "UserId";
    public final String KEY_FIRSTNAME = "FirstName";
    public final String KEY_LASTNAME = "LastName";
    public final String KEY_DOB = "DoB";
    public final String KEY_PASSWORD = "password";
    public final String KEY_EMAIL = "email";
    public final String KEY_LOCATION = "Location";
    public final String KEY_DESCRIPTION = "Description";
    public final String KEY_HOSTING = "HostingStatus";
    public final String KEY_RATE = "Rate";
    public final String KEY_LAT = "Latitud";
    public final String KEY_LONG = "Longitud";
    public String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
            + KEY_USERID + " TEXT PRIMARY KEY, "
            + KEY_FIRSTNAME + " TEXT, "
            + KEY_LASTNAME + " TEXT, "
            + KEY_DOB + " TEXT, "
            + KEY_PASSWORD + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_LOCATION + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_HOSTING + " INTEGER, "
            + KEY_LAT + " REAL, "
            + KEY_LONG + " REAL, "
            + KEY_RATE + " REAL "
            + ")";
    private String[] USER_TABLE_COLUMNS =
            {
                    KEY_USERID,
                    KEY_FIRSTNAME,
                    KEY_LASTNAME,
                    KEY_DOB,
                    KEY_PASSWORD,
                    KEY_EMAIL,
                    KEY_LOCATION,
                    KEY_DESCRIPTION,
                    KEY_HOSTING,
                    KEY_LAT,
                    KEY_LONG,
                    KEY_RATE
            };

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void addUser(UserAccount account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERID, account.getId());
        values.put(KEY_FIRSTNAME, account.getFirstName());
        values.put(KEY_LASTNAME, account.getLastName());
        values.put(KEY_DOB, account.getDob());
        values.put(KEY_PASSWORD, account.getPassword());
        values.put(KEY_EMAIL, account.getEmail());
        values.put(KEY_LOCATION, account.getLocation());
        values.put(KEY_DESCRIPTION, account.getDescription());
        values.put(KEY_HOSTING, account.getHostingStatus());
        values.put(KEY_RATE, account.getRate());
        values.put(KEY_LONG, account.getLongitude());
        values.put(KEY_LAT, account.getLatitude());
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public UserAccount getCurrentUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<UserAccount> accounts = new ArrayList<UserAccount>();
        Cursor cursor = db.query(TABLE_USERS, USER_TABLE_COLUMNS, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            accounts.add(parseAccount(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        if (accounts.size() > 0){
            return accounts.get(0);
        }
        else {
            return new UserAccount();
        }
    }

    public void deleteAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_USERS);
        db.close();
    }

    private UserAccount parseAccount(Cursor cursor) {
        UserAccount user = new UserAccount();
        user.setId(cursor.getString(cursor.getColumnIndex(KEY_USERID)));
        user.setFirstName(cursor.getString(cursor.getColumnIndex(KEY_FIRSTNAME)));
        user.setLastName(cursor.getString(cursor.getColumnIndex(KEY_LASTNAME)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
        user.setDob(cursor.getString(cursor.getColumnIndex(KEY_DOB)));
        user.setHostingStatus(cursor.getInt(cursor.getColumnIndex(KEY_HOSTING)));
        user.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
        user.setRate(cursor.getDouble(cursor.getColumnIndex(KEY_RATE)));
        user.setLocation(cursor.getString(cursor.getColumnIndex(KEY_LOCATION)));
        user.setLatitude(cursor.getDouble(cursor.getColumnIndex(KEY_LAT)));
        user.setLongitude(cursor.getDouble(cursor.getColumnIndex(KEY_LONG)));
        return user;
    }

    public void updateUser(String id, String firstName, String lastName, String dob, String password, String email,
                      String location, String description, int hosting) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, firstName);
        values.put(KEY_LASTNAME, lastName);
        values.put(KEY_DOB, dob);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_EMAIL, email);
        values.put(KEY_LOCATION, location);
        values.put(KEY_DESCRIPTION, description);
        values.put(KEY_HOSTING, hosting);
        db.update(TABLE_USERS, values, KEY_USERID +"=\""+id+"\"", null);
        db.close();
    }

    public void updateHost(String id, int hostStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_HOSTING, hostStatus);
        db.update(TABLE_USERS, values, KEY_USERID +"=\""+id+"\"", null);
        db.close();
    }

    public void updateLatLong(String id, Double lat, Double lng) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LAT, lat);
        values.put(KEY_LONG, lng);
        db.update(TABLE_USERS, values, KEY_USERID +"=\""+id+"\"", null);
        db.close();
    }

}
