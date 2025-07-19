package com.example.LuxeVista.Data_Base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.LuxeVista.Models.UserModel;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    private static final String DATABASE_NAME = "AlokaApp.db";
    private static final int DATABASE_VERSION = 3;

    // Users Table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_NIC = "nic";
    private static final String COLUMN_PASSWORD = "password";

    // Cart Table
    private static final String TABLE_CART = "cart";
    private static final String COLUMN_CART_ID = "id";
    private static final String COLUMN_CART_USER_ID = "user_id";
    private static final String COLUMN_ROOM_TYPE = "room_type";
    private static final String COLUMN_ROOM_PRICE = "room_price";
    private static final String COLUMN_QUANTITY = "quantity";

    // Confirmed Bookings Table
    private static final String TABLE_BOOKINGS = "confirmed_bookings";
    private static final String COLUMN_BOOKING_ID = "id";
    private static final String COLUMN_BOOKING_USER_ID = "user_id";
    private static final String COLUMN_CHECK_IN = "check_in";
    private static final String COLUMN_CHECK_OUT = "check_out";
    private static final String COLUMN_SPA = "spa";
    private static final String COLUMN_TOUR = "tour";
    private static final String COLUMN_DINNER = "dinner";
    private static final String COLUMN_TOTAL = "total_amount";

    // Rented Rooms Table
    private static final String TABLE_RENTED_ROOMS = "rented_rooms";
    private static final String COLUMN_RENTED_ID = "id";
    private static final String COLUMN_RENTED_USER_ID = "user_id";
    private static final String COLUMN_RENTED_ROOM_NAME = "room_name";
    private static final String COLUMN_RENTED_PRICE = "price";
    private static final String COLUMN_RENTED_DATE = "rented_date";
    // New columns for check-in/out dates
    private static final String COLUMN_RENTED_CHECK_IN = "check_in_date";
    private static final String COLUMN_RENTED_CHECK_OUT = "check_out_date";
    private static final String COLUMN_RENTED_NIGHTS = "nights";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // Force database initialization
        getWritableDatabase().close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Creating database tables");
        try {

            db.execSQL("CREATE TABLE " + TABLE_USERS + "(" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_FIRST_NAME + " TEXT," +
                    COLUMN_LAST_NAME + " TEXT," +
                    COLUMN_EMAIL + " TEXT UNIQUE," +
                    COLUMN_PHONE + " TEXT," +
                    COLUMN_NIC + " TEXT," +
                    COLUMN_PASSWORD + " TEXT)");


            db.execSQL("CREATE TABLE " + TABLE_CART + "(" +
                    COLUMN_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_CART_USER_ID + " INTEGER," +
                    COLUMN_ROOM_TYPE + " TEXT," +
                    COLUMN_ROOM_PRICE + " REAL," +
                    COLUMN_QUANTITY + " INTEGER)");


            db.execSQL("CREATE TABLE " + TABLE_BOOKINGS + "(" +
                    COLUMN_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_BOOKING_USER_ID + " INTEGER," +
                    COLUMN_CHECK_IN + " TEXT," +
                    COLUMN_CHECK_OUT + " TEXT," +
                    COLUMN_SPA + " INTEGER," +
                    COLUMN_TOUR + " INTEGER," +
                    COLUMN_DINNER + " INTEGER," +
                    COLUMN_TOTAL + " REAL)");


            db.execSQL("CREATE TABLE " + TABLE_RENTED_ROOMS + "(" +
                    COLUMN_RENTED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_RENTED_USER_ID + " INTEGER, " +
                    COLUMN_RENTED_ROOM_NAME + " TEXT, " +
                    COLUMN_RENTED_PRICE + " REAL, " +
                    COLUMN_RENTED_DATE + " INTEGER, " +
                    COLUMN_RENTED_CHECK_IN + " TEXT, " +
                    COLUMN_RENTED_CHECK_OUT + " TEXT, " +
                    COLUMN_RENTED_NIGHTS + " INTEGER)");

            Log.d(TAG, "All tables created successfully");
        } catch (SQLException e) {
            Log.e(TAG, "Error creating tables: " + e.getMessage());
            throw e; // Re-throw to ensure we know if table creation fails
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
        try {
            if (oldVersion < 3) {

                if (isTableExists(db, TABLE_RENTED_ROOMS)) {

                    db.execSQL("ALTER TABLE " + TABLE_RENTED_ROOMS +
                            " ADD COLUMN " + COLUMN_RENTED_CHECK_IN + " TEXT DEFAULT 'Not specified'");
                    db.execSQL("ALTER TABLE " + TABLE_RENTED_ROOMS +
                            " ADD COLUMN " + COLUMN_RENTED_CHECK_OUT + " TEXT DEFAULT 'Not specified'");
                    db.execSQL("ALTER TABLE " + TABLE_RENTED_ROOMS +
                            " ADD COLUMN " + COLUMN_RENTED_NIGHTS + " INTEGER DEFAULT 1");

                    Log.d(TAG, "Added new columns to rented_rooms table");
                }
            } else {
                // Complete recreation for other version changes
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_RENTED_ROOMS);
                onCreate(db);
            }
        } catch (SQLException e) {
            Log.e(TAG, "Error upgrading database: " + e.getMessage());
            throw e;
        }
    }


    private boolean isTableExists(SQLiteDatabase db, String tableName) {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(
                    "SELECT name FROM sqlite_master WHERE type='table' AND name=?",
                    new String[]{tableName}
            );
            return cursor != null && cursor.getCount() > 0;
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public long addUser(String firstName, String lastName, String email, String phone, String nic, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_FIRST_NAME, firstName);
            values.put(COLUMN_LAST_NAME, lastName);
            values.put(COLUMN_EMAIL, email);
            values.put(COLUMN_PHONE, phone);
            values.put(COLUMN_NIC, nic);
            values.put(COLUMN_PASSWORD, password);

            return db.insert(TABLE_USERS, null, values);
        } finally {
            db.close();
        }
    }

    public UserModel loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_USERS,
                    null,
                    COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?",
                    new String[]{email, password},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                return new UserModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIC))
                );
            }
            return null;
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
    }

    public boolean addBooking(int userId, String roomType, double price, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CART_USER_ID, userId);
            values.put(COLUMN_ROOM_TYPE, roomType);
            values.put(COLUMN_ROOM_PRICE, price);
            values.put(COLUMN_QUANTITY, quantity);

            long result = db.insert(TABLE_CART, null, values);
            return result != -1;
        } finally {
            db.close();
        }
    }

    public boolean updateUser(int userId, String firstName, String lastName, String email,
                              String phone, String nic, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_FIRST_NAME, firstName);
            values.put(COLUMN_LAST_NAME, lastName);
            values.put(COLUMN_EMAIL, email);
            values.put(COLUMN_PHONE, phone);
            values.put(COLUMN_NIC, nic);

            if (password != null && !password.isEmpty()) {
                values.put(COLUMN_PASSWORD, password);
            }

            int rowsAffected = db.update(TABLE_USERS, values,
                    COLUMN_USER_ID + "=?",
                    new String[]{String.valueOf(userId)});
            return rowsAffected > 0;
        } finally {
            db.close();
        }
    }


    public boolean addToRentedRooms(int userId, String roomName, double price) {
        return addToRentedRooms(userId, roomName, price, "Not specified", "Not specified", 1);
    }


    public boolean addToRentedRooms(int userId, String roomName, double price,
                                    String checkInDate, String checkOutDate, int nights) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_RENTED_USER_ID, userId);
            values.put(COLUMN_RENTED_ROOM_NAME, roomName);
            values.put(COLUMN_RENTED_PRICE, price);
            values.put(COLUMN_RENTED_DATE, System.currentTimeMillis());
            values.put(COLUMN_RENTED_CHECK_IN, checkInDate);
            values.put(COLUMN_RENTED_CHECK_OUT, checkOutDate);
            values.put(COLUMN_RENTED_NIGHTS, nights);

            long result = db.insert(TABLE_RENTED_ROOMS, null, values);
            return result != -1;
        } catch (SQLException e) {
            Log.e(TAG, "Error adding to rented rooms: " + e.getMessage());
            return false;
        } finally {
            db.close();
        }
    }

    public boolean isTableExists(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(
                    "SELECT name FROM sqlite_master WHERE type='table' AND name=?",
                    new String[]{tableName}
            );
            return cursor != null && cursor.getCount() > 0;
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
    }

    public boolean isRentedRoomsTableExists() {
        return isTableExists(TABLE_RENTED_ROOMS);
    }

    public boolean verifyDatabase() {
        SQLiteDatabase db = this.getReadableDatabase();
        try {

            return isTableExists(TABLE_USERS) &&
                    isTableExists(TABLE_CART) &&
                    isTableExists(TABLE_BOOKINGS) &&
                    isTableExists(TABLE_RENTED_ROOMS);
        } finally {
            db.close();
        }
    }
}