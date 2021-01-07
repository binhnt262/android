package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHandle extends SQLiteOpenHelper {
    private static final String TAG = "SQLiteHandle";
    private static final String DB_NAME = "math_game";
    private static final String TABLE_NAME = "scores";

    private static final String S_ID = "id";
    private static final String S_SCORE = "score";

    public SQLiteHandle(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                S_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                S_SCORE + " INTEGER)";
        Log.d(TAG, "Tạo databse thành công");
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        Log.d(TAG, "Xóa database thành công");
        onCreate(db);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = " SELECT score FROM " + TABLE_NAME + " ORDER BY "+S_SCORE + " DESC ";
//        String sql = " SELECT " + " MAX(score) " + " FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    User user = new User();
//                    user.setId(cursor.getInt(1));
                    user.setScore(cursor.getInt(0));
                    users.add(user);
                } while (cursor.moveToNext());
            }
        }
        db.close();
        if (users.size() > 0) {
            Log.d(TAG, "Get All user thành công. Điểm cao nhất là: " + users.get(0).getScore());
        }
        return users;
    }

    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(S_SCORE, user.getScore());
        long value = db.insert(TABLE_NAME, null, values);
        db.close();
        if (value > 0) {
            Log.d(TAG, "addUser: Add thành công người dùng với điểm là "+ user.getScore());
        } else {
            Log.d(TAG, "addUser: Add người dùng thất bại");
        }
        return value;
    }
}
