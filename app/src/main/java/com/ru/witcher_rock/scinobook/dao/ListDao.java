package com.ru.witcher_rock.scinobook.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.ru.witcher_rock.scinobook.db.ListTable;
import com.ru.witcher_rock.scinobook.db.SCSQLiteHelper;
import com.ru.witcher_rock.scinobook.model.ListBook;

public class ListDao {

    private SQLiteDatabase mDatabase;
    private SCSQLiteHelper mHelper;

    public ListDao(Context context) {
        mHelper = new SCSQLiteHelper(context);
    }

    public void open() throws SQLException {
        mDatabase = mHelper.getWritableDatabase();
    }

    public void close() {
        mHelper.close();
    }

    public ListBook createList(String name) {
        ////////////////////////////////////////////////
        List<ListBook> temp = this.getAllLists();

        for(ListBook lst : temp)                    //////!!!!!!!!!!!!!!!!
        {
            if(lst.getName().equals(name))
                return null;
        }
        temp.clear();
        //////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put(ListTable.COLUMN_NAME, name);
        long listId = mDatabase.insert(ListTable.TABLE_LIST, null, contentValues);

        Cursor cursor = mDatabase.query(ListTable.TABLE_LIST,
                new String[]{ListTable.COLUMN_ID, ListTable.COLUMN_NAME},
                ListTable.COLUMN_ID + " = ?",
                new String[]{String.valueOf(listId)}, null, null, null);
        cursor.moveToFirst();
        ListBook list = cursorToList(cursor);
        cursor.close();

        return list;
    }

    public void deleteListById(long id) {
        mDatabase.delete(ListTable.TABLE_LIST, ListTable.COLUMN_ID + " = " + id, null);
    }

    public void deleteListByName(String name) {
        mDatabase.delete(ListTable.TABLE_LIST, ListTable.COLUMN_NAME + " = " + name, null);
    }

    public List<ListBook> getAllLists() {
        List<ListBook> lists = new ArrayList<>();

        Cursor cursor = mDatabase.query(ListTable.TABLE_LIST,
                new String[]{ListTable.COLUMN_ID, ListTable.COLUMN_NAME}, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ListBook list = cursorToList(cursor);
            lists.add(list);
            cursor.moveToNext();
        }

        cursor.close();

        return lists;
    }

    public Cursor getAllListsCursor() {
            return mDatabase.query(ListTable.TABLE_LIST,
                    new String[]{ListTable.COLUMN_ID, ListTable.COLUMN_NAME},
                    ListTable.COLUMN_NAME + " NOT LIKE " + "'Without category'", null, null, null, null);

    }

    public Cursor getAllListsCursorWithoutCategory() {
        return mDatabase.query(ListTable.TABLE_LIST,
                new String[]{ListTable.COLUMN_ID, ListTable.COLUMN_NAME}, null, null, null, null, null);
    }

    public long getIdListWithoutCaregory() {
//        Cursor cursor = mDatabase.query(ListTable.TABLE_LIST,
//                new String[]{ListTable.COLUMN_ID, ListTable.COLUMN_NAME},
//                ListTable.COLUMN_NAME + " = " + "'Without category'",
//                null, null, null, null, null);
//
//
//        int temp = cursor.getInt(cursor.getColumnIndex(ListTable.COLUMN_ID));

        return 1;
    }

    private ListBook cursorToList(Cursor cursor) {
        ListBook list = new ListBook();
        list.setId(cursor.getLong(cursor.getColumnIndex(ListTable.COLUMN_ID)));
        list.setName(cursor.getString(cursor.getColumnIndex(ListTable.COLUMN_NAME)));

        return list;
    }
}
