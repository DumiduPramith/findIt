package Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create User table
        String qr1 = "CREATE TABLE User(\n" +
                "    ID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    USERNAME TEXT,\n" +
                "EMAIL TEXT,\n" +
                "    PASSWORD TEXT\n" +
                ")";
        sqLiteDatabase.execSQL(qr1);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public int login(String username, String password) {
        // Implement login logic here
        int result = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE USERNAME = ? AND PASSWORD = ?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            result = 1;
        }
        cursor.close();
        return result;
    }

    public void register(String username, String email, String password) {
        // Implement register logic here
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO User(USERNAME, EMAIL, PASSWORD) VALUES(?, ?, ?)", new String[]{username, email, password});
    }

    public boolean checkRegistered() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM User", null);
        if (c.getCount() > 0) {
            return true;
        }
        return false;
    }
}
