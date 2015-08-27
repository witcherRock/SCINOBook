package com.ru.witcher_rock.scinobook.loader;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

import com.ru.witcher_rock.scinobook.dao.ListDao;

public class ListCursorLoader extends CursorLoader {

    private ListDao mListDao;

    public ListCursorLoader(Context context, ListDao dao) {
        super(context);
        mListDao = dao;
    }

    @Override
    public Cursor loadInBackground() {
        return mListDao.getAllListsCursor();
    }
}
