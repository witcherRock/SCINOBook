package com.ru.witcher_rock.scinobook.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ru.witcher_rock.scinobook.db.BookTable;
import com.ru.witcher_rock.scinobook.db.SCSQLiteHelper;
import com.ru.witcher_rock.scinobook.enum_name.TypeRead;
import com.ru.witcher_rock.scinobook.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookDao {

    private SQLiteDatabase mDatabase;
    private SCSQLiteHelper mHelper;

    public BookDao(Context context) {
        mHelper = new SCSQLiteHelper(context);
    }

    public void open() throws SQLException {
        mDatabase = mHelper.getWritableDatabase();
    }

    public void close() {
        mHelper.close();
    }

    public Book createBook(String name, String author, TypeRead readble, int list_id) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BookTable.COLUMN_NAME, name);
        contentValues.put(BookTable.COLUMN_AUTHOR, author);
        contentValues.put(BookTable.COLUMN_LIST, list_id);
        contentValues.put(BookTable.COLUMN_READBLE, readble.ordinal());

        long bookId = mDatabase.insert(BookTable.TABLE_BOOK, null, contentValues);

        Cursor cursor = mDatabase.query(BookTable.TABLE_BOOK,
                new String[]{BookTable.COLUMN_ID, BookTable.COLUMN_NAME, BookTable.COLUMN_AUTHOR,
                        BookTable.COLUMN_LIST, BookTable.COLUMN_READBLE},
                BookTable.COLUMN_ID + " = ?",
                new String[]{String.valueOf(bookId)}, null, null, null);
        cursor.moveToFirst();
        Book book = cursorToBook(cursor);
        cursor.close();

        return book;
    }

    public void SetReadble(boolean checked, int id)
    {
        String sql;
        if(checked)
            sql = "update " +  BookTable.TABLE_BOOK
            + " set " +  BookTable.COLUMN_READBLE  + " = "
            + Integer.toString(TypeRead.FINISH_READ.ordinal()) + " where "
            + BookTable.COLUMN_ID + " = " + id + ";" ;
        else
            sql = "update " +  BookTable.TABLE_BOOK
                    + " set " +  BookTable.COLUMN_READBLE  + " = "
            + Integer.toString(TypeRead.NO_FINISH_READ.ordinal()) + " where "
                    + BookTable.COLUMN_ID + " = " + id + ";" ;
        mDatabase.execSQL(sql);
    }

    public void deleteBookById(long id) {
        mDatabase.delete(BookTable.TABLE_BOOK, BookTable.COLUMN_ID + " = " + id, null);
    }

    public void deleteAllBookByListId(int list_id)
    {
        mDatabase.delete(BookTable.TABLE_BOOK, BookTable.COLUMN_LIST + " = " + list_id, null);
    }

    public Cursor getBooksCursorInList(int list_id)
    {
        return mDatabase.query(BookTable.TABLE_BOOK,
                new String[]{BookTable.COLUMN_ID, BookTable.COLUMN_NAME, BookTable.COLUMN_AUTHOR,
                        BookTable.COLUMN_LIST, BookTable.COLUMN_READBLE}, BookTable.COLUMN_LIST +
                        " = " + list_id  ,null, null, null, null, null);
    }

    public Cursor getBooksCursorInListFinishRead(int list_id)
    {
        return mDatabase.query(BookTable.TABLE_BOOK,
                new String[]{BookTable.COLUMN_ID, BookTable.COLUMN_NAME, BookTable.COLUMN_AUTHOR,
                        BookTable.COLUMN_LIST, BookTable.COLUMN_READBLE}, BookTable.COLUMN_LIST +
                        " = " + list_id
                        + " AND " + BookTable.COLUMN_READBLE + " = "
                        + TypeRead.FINISH_READ.ordinal(),null, null, null, null, null);
    }

    public Cursor getBooksCursorInListNotFinishRead(int list_id)
    {
        return mDatabase.query(BookTable.TABLE_BOOK,
                new String[]{BookTable.COLUMN_ID, BookTable.COLUMN_NAME, BookTable.COLUMN_AUTHOR,
                        BookTable.COLUMN_LIST, BookTable.COLUMN_READBLE}, BookTable.COLUMN_LIST +
                        " = " + list_id
                        + " AND " + BookTable.COLUMN_READBLE + " = "
                        + TypeRead.NO_FINISH_READ.ordinal(),null, null, null, null, null);
    }

    public Cursor getBooksCursorInListSortByAuthor(int list_id)
    {
        return mDatabase.query(BookTable.TABLE_BOOK,
                new String[]{BookTable.COLUMN_ID, BookTable.COLUMN_NAME, BookTable.COLUMN_AUTHOR,
                        BookTable.COLUMN_LIST, BookTable.COLUMN_READBLE}, BookTable.COLUMN_LIST +
                        " = " + list_id, null, null, null, BookTable.COLUMN_AUTHOR, null);
    }

    public Cursor getBooksCursorInListSortByTitle(int list_id)
    {
        return mDatabase.query(BookTable.TABLE_BOOK,
                new String[]{BookTable.COLUMN_ID, BookTable.COLUMN_NAME, BookTable.COLUMN_AUTHOR,
                        BookTable.COLUMN_LIST, BookTable.COLUMN_READBLE}, BookTable.COLUMN_LIST +
                        " = " + list_id, null, null, null, BookTable.COLUMN_NAME, null);
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        Cursor cursor = mDatabase.query(BookTable.TABLE_BOOK,
                new String[]{BookTable.COLUMN_ID, BookTable.COLUMN_NAME, BookTable.COLUMN_AUTHOR,
                        BookTable.COLUMN_LIST, BookTable.COLUMN_READBLE}, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Book book = cursorToBook(cursor);
            books.add(book);
            cursor.moveToNext();
        }

        cursor.close();

        return books;
    }

    public List<Book> getAllBooksInList(int list_id) {
        List<Book> books = new ArrayList<>();

        Cursor cursor = mDatabase.query(BookTable.TABLE_BOOK,
                new String[]{BookTable.COLUMN_ID, BookTable.COLUMN_NAME, BookTable.COLUMN_AUTHOR,
                        BookTable.COLUMN_LIST, BookTable.COLUMN_READBLE}, BookTable.COLUMN_LIST +
                        " = " + list_id ,null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Book book = cursorToBook(cursor);
            books.add(book);
            cursor.moveToNext();
        }

        cursor.close();

        return books;
    }

    public Cursor getAllBooksCursor() {
        return mDatabase.query(BookTable.TABLE_BOOK,
                    new String[]{BookTable.COLUMN_ID, BookTable.COLUMN_NAME, BookTable.COLUMN_AUTHOR,
                            BookTable.COLUMN_LIST, BookTable.COLUMN_READBLE}, null, null, null, null, null);
    }

    private Book cursorToBook(Cursor cursor) {
        Book book = new Book();
        book.setId(cursor.getLong(cursor.getColumnIndex(BookTable.COLUMN_ID)));
        book.setName(cursor.getString(cursor.getColumnIndex(BookTable.COLUMN_NAME)));
        book.setAuthor(cursor.getString(cursor.getColumnIndex(BookTable.COLUMN_AUTHOR)));
        book.setList(Integer.parseInt(cursor.getString(cursor.getColumnIndex(BookTable.COLUMN_LIST))));

        int temp = Integer.parseInt(cursor.getString(cursor.getColumnIndex(BookTable.COLUMN_READBLE)));
        if(temp == TypeRead.FINISH_READ.ordinal())
            book.setReadble(TypeRead.FINISH_READ);
        else
            book.setReadble(TypeRead.NO_FINISH_READ);

        return book;
    }
}
