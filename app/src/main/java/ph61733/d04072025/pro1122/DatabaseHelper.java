package ph61733.d04072025.pro1122;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    
    // Database Info
    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final int DATABASE_VERSION = 1;
    
    // Table Names
    private static final String TABLE_USERS = "users";
    
    // User Table Columns
    private static final String KEY_ID = "id";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_FULL_NAME + " TEXT,"
                + KEY_USERNAME + " TEXT UNIQUE,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_PASSWORD + " TEXT"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
    
    /**
     * Add a new user to the database
     * @return true if user added successfully, false otherwise
     */
    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(KEY_FULL_NAME, user.getFullName());
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_PASSWORD, user.getPassword());
        
        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        
        return result != -1;
    }
    
    /**
     * Check if username already exists
     */
    public boolean checkUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_ID},
                KEY_USERNAME + "=?",
                new String[]{username},
                null, null, null);
        
        int count = cursor.getCount();
        cursor.close();
        db.close();
        
        return count > 0;
    }
    
    /**
     * Check if email already exists
     */
    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_ID},
                KEY_EMAIL + "=?",
                new String[]{email},
                null, null, null);
        
        int count = cursor.getCount();
        cursor.close();
        db.close();
        
        return count > 0;
    }
    
    /**
     * Check user login credentials
     * @return User object if credentials are valid, null otherwise
     */
    public User checkUserLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_ID, KEY_FULL_NAME, KEY_USERNAME, KEY_EMAIL},
                KEY_USERNAME + "=? AND " + KEY_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);
        
        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(0));
            user.setFullName(cursor.getString(1));
            user.setUsername(cursor.getString(2));
            user.setEmail(cursor.getString(3));
        }
        
        cursor.close();
        db.close();
        
        return user;
    }
    
    /**
     * Get user by username
     */
    public User getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_ID, KEY_FULL_NAME, KEY_USERNAME, KEY_EMAIL},
                KEY_USERNAME + "=?",
                new String[]{username},
                null, null, null);
        
        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(0));
            user.setFullName(cursor.getString(1));
            user.setUsername(cursor.getString(2));
            user.setEmail(cursor.getString(3));
        }
        
        cursor.close();
        db.close();
        
        return user;
    }
    
    /**
     * Update user password
     */
    public boolean updatePassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD, newPassword);
        
        int rowsAffected = db.update(TABLE_USERS,
                values,
                KEY_USERNAME + "=?",
                new String[]{username});
        
        db.close();
        
        return rowsAffected > 0;
    }
    
    /**
     * Verify current password for a user
     */
    public boolean verifyPassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{KEY_ID},
                KEY_USERNAME + "=? AND " + KEY_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);
        
        int count = cursor.getCount();
        cursor.close();
        db.close();
        
        return count > 0;
    }
}

