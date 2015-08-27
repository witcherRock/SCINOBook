package com.ru.witcher_rock.scinobook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SCSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "scino_book.db";
    private static final int DATABASE_VERSION = 1;

    public SCSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        BookTable.onCreate(db);
        ListTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        BookTable.onUpgrate(db, oldVersion, newVersion);
        ListTable.onUpgrate(db, oldVersion, newVersion);
    }
}
