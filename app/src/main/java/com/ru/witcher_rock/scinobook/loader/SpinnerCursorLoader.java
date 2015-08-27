package com.ru.witcher_rock.scinobook.loader;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

import com.ru.witcher_rock.scinobook.dao.ListDao;

public class SpinnerCursorLoader extends CursorLoader {

    private ListDao mListDao;
    private boolean mFlagCategory = true;
    public SpinnerCursorLoader(Context context, ListDao dao, boolean flagCategory) {
        super(context);
        mListDao = dao;
        mFlagCategory = flagCategory;
    }

    @Override
    public Cursor loadInBackground() {
        if(mFlagCategory)
            return mListDao.getAllListsCursorWithoutCategory();
        else
            return mListDao.getAllListsCursor();
    }
}
