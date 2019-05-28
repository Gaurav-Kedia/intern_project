package com.gaurav.intern_project.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.gaurav.intern_project.data.Contract.Entry;

import static com.gaurav.intern_project.data.Contract.Entry.TABLE_NAME;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "workshop.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_PETS_TABLE =  "CREATE TABLE " + TABLE_NAME + " ("
                + Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Entry.WORKSHOP_NAME + " TEXT NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PETS_TABLE);

        ContentValues values = new ContentValues();
        values.put(Contract.Entry.WORKSHOP_NAME, "Web Development");
        db.insert(TABLE_NAME, null, values);

        values.put(Contract.Entry.WORKSHOP_NAME, "Android Development");
        db.insert(TABLE_NAME, null, values);

        values.put(Contract.Entry.WORKSHOP_NAME, "Programming with python");
        db.insert(TABLE_NAME, null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
