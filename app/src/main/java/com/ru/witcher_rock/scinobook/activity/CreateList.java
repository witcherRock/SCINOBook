package com.ru.witcher_rock.scinobook.activity;

import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.ru.witcher_rock.scinobook.Interface.OnListActionListener;
import com.ru.witcher_rock.scinobook.Interface.OnListAddedListener;
import com.ru.witcher_rock.scinobook.R;
import com.ru.witcher_rock.scinobook.adapters.ListAdapter;
import com.ru.witcher_rock.scinobook.dao.BookDao;
import com.ru.witcher_rock.scinobook.dao.ListDao;
import com.ru.witcher_rock.scinobook.fragment.CreateListFragment;
import com.ru.witcher_rock.scinobook.loader.ListCursorLoader;


public class CreateList extends AppCompatActivity implements OnListAddedListener,
        LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener, OnListActionListener {

    private static final String TAG = "CreateList";
    private static final int LIST_LOADER_ID = 1;
    private ListView mListView;
    private Button createBookButton;
    private ListAdapter mAdapter;
    private ListDao mListDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        mListDao = new ListDao(this);
        mListDao.open();
        Log.d(TAG, "onCreate -  mListDao.open();");

        mListView = (ListView) findViewById(R.id.lvData);
        mAdapter = new ListAdapter(this, null, true);
        mAdapter.setmListener(this);
        mListView.setAdapter(mAdapter);

        createBookButton = (Button) findViewById(R.id.buttonAddList);
        createBookButton.setOnClickListener(this);

        if(savedInstanceState == null)
            getLoaderManager().initLoader(LIST_LOADER_ID, null, this);
        else
            getLoaderManager().restartLoader(LIST_LOADER_ID, null, this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.custom_back_button);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mListDao.open();
        Log.d(TAG, "onResume -  mListDao.open();");
    }

    @Override
    protected void onPause() {
        super.onPause();

        mListDao.close();
        Log.d(TAG, "onPause -  mListDao.close();");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListAdded(final String name) {
        if(mListDao.createList(name) == null)
        {
            Snackbar
                    .make(findViewById(R.id.buttonAddList), R.string.text_exist_list, Snackbar.LENGTH_SHORT)
                    .show();
            return;
        }
        mAdapter.changeCursor(mListDao.getAllListsCursor());
        getLoaderManager().getLoader(LIST_LOADER_ID).onContentChanged();

        Snackbar
                .make(findViewById(R.id.buttonAddList),
                        name + " " + getString(R.string.text_save_list), Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (LIST_LOADER_ID == id) {
            return new ListCursorLoader(this, mListDao);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Override
    public void onClick(View v) {
        CreateListFragment fragment = new CreateListFragment();
        fragment.show(getFragmentManager(), fragment.getClass().toString());
    }

    @Override
    public void onActionDeleteList(final int listID, final String listName) {
        new AlertDialog.Builder(this)
                    .setTitle(R.string.text_delet_list)
                    .setMessage(R.string.text_ask_delete_list)
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            // извлекаем id записи и удаляем соответствующую запись в БД
                            mListDao.deleteListById(listID);
                            BookDao mBookDao = new BookDao(getApplication());
                            mBookDao.open();
                            mBookDao.deleteAllBookByListId(listID);
                            mBookDao.close();
                            // получаем новый курсор с данными
                            mAdapter.changeCursor(mListDao.getAllListsCursor());
                            getLoaderManager().getLoader(LIST_LOADER_ID).onContentChanged();

                            Snackbar
                                    .make(findViewById(R.id.buttonAddList),
                                            listName + " " + getString(R.string.text_delete_list), Snackbar.LENGTH_SHORT)
                                    .show(); // Don’t forget to show!
                        }
                    }).create().show();

        }
}
