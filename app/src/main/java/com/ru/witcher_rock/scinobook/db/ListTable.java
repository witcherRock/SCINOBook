package com.ru.witcher_rock.scinobook.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ListTable {

    private static final String TAG = ListTable.class.toString();

    public static final String TABLE_LIST = "list";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_LIST
            + "("
            + COLUMN_ID + " integer primary key autoincrement,"
            + COLUMN_NAME + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        ContentValues contentValues = new ContentValues();
        contentValues.put(ListTable.COLUMN_NAME, "Without category");
        database.insert(ListTable.TABLE_LIST, null, contentValues);
    }

    public static void onUpgrate(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade database to newVersion =" + newVersion + " from oldVersion =" + oldVersion);

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);
        onCreate(database);
    }
}
