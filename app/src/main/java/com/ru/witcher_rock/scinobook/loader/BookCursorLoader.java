package com.ru.witcher_rock.scinobook.loader;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

import com.ru.witcher_rock.scinobook.dao.BookDao;
import com.ru.witcher_rock.scinobook.enum_name.TypeSort;

public class BookCursorLoader extends CursorLoader {

    private BookDao mBookDao;
    private int mlistId = 1;
    private TypeSort mSortType;

    public BookCursorLoader(Context context, BookDao dao, int list_id, TypeSort sortType) {
        super(context);
        this.mBookDao = dao;
        this.mlistId = list_id;
        this.mSortType = sortType;
    }

    @Override
    public Cursor loadInBackground() {
        if( this.mSortType == TypeSort.NOT_SORT )
            return mBookDao.getBooksCursorInList(mlistId);

        if( this.mSortType == TypeSort.SORT_BY_AUTHOR )
            return mBookDao.getBooksCursorInListSortByAuthor(mlistId);

        if( this.mSortType == TypeSort.SORT_BY_TITLE )
            return mBookDao.getBooksCursorInListSortByTitle(mlistId);

        if( this.mSortType == TypeSort.FILTER_FIHISH_READ )
            return mBookDao.getBooksCursorInListFinishRead(mlistId);

        if( this.mSortType == TypeSort.FILTER_NOT_FINISH_READ )
            return mBookDao.getBooksCursorInListNotFinishRead(mlistId);

        return mBookDao.getBooksCursorInList(mlistId);
    }
}
