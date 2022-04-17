package com.example.gameapplication3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "memorygame";
    public static final int DATABASE_VERSION = 10;

    public static final String TABLE_SCORE = "scores";
    public static final String ROW_ID = "id";
    public static final String ROW_NAME = "name";
    public static final String ROW_SCORE = "score";
    Context context;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_SCORE + "(" + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ROW_NAME + " TEXT NOT NULL, " + ROW_SCORE + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);
        onCreate(db);
    }

    public void AddScore(Score score) {
        SQLiteDatabase db = this.getWritableDatabase();

        DeleteMinScore();

        ContentValues cv = new ContentValues();
        cv.put(ROW_NAME, score.getName());
        cv.put(ROW_SCORE, score.getScore());

        db.insert(TABLE_SCORE, null, cv);
        db.close();
    }

    public ArrayList<Score> ListScore() {
        int number = 1;
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Score> data = new ArrayList<>();
        String[] columns = {ROW_ID, ROW_NAME, ROW_SCORE};
        Cursor cursor = db.query(TABLE_SCORE, columns, null, null,
                null, null, ROW_SCORE + " desc");
        while (cursor.moveToNext()) {
            data.add(new Score(String.valueOf(number), cursor.getString(1), cursor.getString(2)));
            number++;
        }
        cursor.close();
        return data;
    }

    public void DeleteMinScore() {
        int number = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + TABLE_SCORE, null);
        while (cursor.moveToNext()) number++;

        if (number > 6) {
            Cursor mCursor = db.rawQuery("delete from " + TABLE_SCORE + " where " + ROW_SCORE
                    + " = " + "(select min(" + ROW_SCORE + ") from " + TABLE_SCORE + ")", null);
            mCursor.moveToFirst();
            mCursor.close();
        }
        cursor.close();
    }

    public int GetHighScore() {
        int highScore;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select max(" + ROW_SCORE + ") from " +
                TABLE_SCORE, null);
        cursor.moveToFirst();

        if (cursor.isNull(0)) highScore = 0;
        else highScore = Integer.parseInt(cursor.getString(0));

        cursor.close();
        return highScore;
    }
}
