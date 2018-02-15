package com.example.susheel.androidstorageapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class SqliteDb {
    private static final String DATABASE_NAME = "MyDatabase.db";
    private static final String TABLE_NAME = "library";
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "BOOK_NAME" + " TEXT, " + "BOOK_AUTHOR" + " TEXT, " + "BOOK_DESC" + " TEXT)";

    DataBaseHelper dbHelper;
    Context context;
    SQLiteDatabase db;
    ContentValues contentValues;

    public SqliteDb(Context context) {
        this.context = context;
        dbHelper=new DataBaseHelper(context);
        contentValues = new ContentValues();
    }

    public SqliteDb open() {
        db=dbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        dbHelper.close();
    }

    public long insertRecord (String book, String author, String desc) {
        Log.d("book -> ", book);
        Log.d("author -> ", author);
        Log.d("desc -> ", desc);

        contentValues.put("BOOK_NAME", book);
        contentValues.put("BOOK_AUTHOR", author);
        contentValues.put("BOOK_DESC", desc);
        Log.d("contentValues -> ", contentValues.toString());
        this.open();
        long ret = db.insert(TABLE_NAME, "BOOK_DESC", contentValues);
        this.close();
        return ret;
    }

    private static class DataBaseHelper extends SQLiteOpenHelper
    {

        public DataBaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            try
            {
                db.execSQL(TABLE_CREATE);
            }
            catch(SQLiteException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

    }
}
