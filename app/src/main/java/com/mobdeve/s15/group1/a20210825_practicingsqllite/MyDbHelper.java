package com.mobdeve.s15.group1.a20210825_practicingsqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDbHelper extends SQLiteOpenHelper {

    public static MyDbHelper instance = null;

    public MyDbHelper(Context context) {
        super(context, DbReferences.DATABASE_NAME, null, DbReferences.DATABASE_VERSION);
    }

    public static MyDbHelper getInstance(Context context) {

        if(instance == null) {
            instance = new MyDbHelper(context.getApplicationContext());
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbReferences.CREATE_TABLE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DbReferences.DROP_TABLE_STATEMENT);
        onCreate(db);
    }

    public synchronized void insertContact(ContactModel c) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DbReferences.COLUMN_NAME_NAME, c.getName());
        values.put(DbReferences.COLUMN_NAME_DATE, c.getDate());

        db.insert(DbReferences.TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<ContactModel> getAllContactsDefault() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(
                DbReferences.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                DbReferences.COLUMN_NAME_NAME + " ASC, "+DbReferences.COLUMN_NAME_DATE + " ASC",
                null
        );

        ArrayList<ContactModel> contacts = new ArrayList<>();
        while(c.moveToNext()) {
            contacts.add(new ContactModel(
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_NAME_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_NAME_DATE))
            ));
        }
        c.close();
        db.close();
        return contacts;
    }

    private final class DbReferences {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "my_database.db";

        private static final String
                TABLE_NAME = "contacts",
                _ID = "id",
                COLUMN_NAME_NAME = "name",
                COLUMN_NAME_DATE = "date";

        private static final String CREATE_TABLE_STATEMENT =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME_NAME + " TEXT, " +
                        COLUMN_NAME_DATE + " TEXT)";

        private static final String DROP_TABLE_STATEMENT =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

    }
}
