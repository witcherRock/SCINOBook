package com.ru.witcher_rock.scinobook.activity;

import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.ru.witcher_rock.scinobook.Interface.OnBookActionListener;
import com.ru.witcher_rock.scinobook.R;
import com.ru.witcher_rock.scinobook.adapters.BookAdapter;
import com.ru.witcher_rock.scinobook.dao.BookDao;
import com.ru.witcher_rock.scinobook.dao.ListDao;
import com.ru.witcher_rock.scinobook.enum_name.TypeSort;
import com.ru.witcher_rock.scinobook.loader.BookCursorLoader;

public class WithoutCategory extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        OnBookActionListener
{
    private static final int BOOK_LOADER_ID = 3;
    private ListView mListView;
    private BookAdapter mAdapter;
    private BookDao mBookDao;

    private ListDao mListDao;
    private int mIdWithoutCategory;

    private static final String LIST_ID_KEY = "list_id";
    private static final String TYPE_SORT_ID_KEY = "type_sort_id";

    private TypeSort mStateSort = TypeSort.NOT_SORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_without_category);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.custom_back_button);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mBookDao = new BookDao(this);
        mBookDao.open();

        mListDao = new ListDao(this);
        mListDao.open();
        mIdWithoutCategory = (int)mListDao.getIdListWithoutCaregory();
        mListDao.close();

        mListView = (ListView) findViewById(R.id.list_wrt);
        mAdapter = new BookAdapter(this, null, true, mBookDao);
        mAdapter.setmListener(this);
        mListView.setAdapter(mAdapter);

        Bundle bundle = new Bundle();
        bundle.putInt(LIST_ID_KEY, mIdWithoutCategory);
        bundle.putInt(TYPE_SORT_ID_KEY, TypeSort.NOT_SORT.ordinal());

        if(savedInstanceState == null)
            getLoaderManager().initLoader(BOOK_LOADER_ID, bundle, this);
        else
            getLoaderManager().restartLoader(BOOK_LOADER_ID, savedInstanceState, this);
    }

    private void ReloadLoader(int list_id, TypeSort typeSort){
        Bundle bundle = new Bundle();
        bundle.putInt(LIST_ID_KEY, list_id);
        bundle.putInt(TYPE_SORT_ID_KEY, typeSort.ordinal());

        getLoaderManager().restartLoader(BOOK_LOADER_ID, bundle, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mBookDao.open();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mBookDao.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_books, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.itemShowAllFinihsRead:
                this.onShowAllRead();
                return true;
            case R.id.itemShowAllNotFinishRead:
                this.onShowAllNotRead();
                return true;
            case R.id.itemSortByTitleBook:
                this.onSortByTitle();
                return true;
            case R.id.itemSortByAuthorBook:
                this.onSortByAuthor();
                return true;
            case R.id.itemCancelFilter:
                this.onCancelFilter();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (BOOK_LOADER_ID == id) {
            TypeSort sortType = TypeSort.NOT_SORT;
            sortType = sortType.GetTypeSort(args.getInt(TYPE_SORT_ID_KEY));
            return new BookCursorLoader(this, mBookDao, args.getInt(LIST_ID_KEY), sortType);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(loader.getId() == BOOK_LOADER_ID)
            mAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void onShowAllRead() {
        mStateSort = TypeSort.FILTER_FIHISH_READ;
        ReloadLoader(mIdWithoutCategory, TypeSort.FILTER_FIHISH_READ);
    }

    public void onShowAllNotRead() {
        mStateSort = TypeSort.FILTER_NOT_FINISH_READ;
        ReloadLoader(mIdWithoutCategory, TypeSort.FILTER_NOT_FINISH_READ);
    }

    public void onSortByTitle() {
        mStateSort = TypeSort.SORT_BY_TITLE;
        ReloadLoader(mIdWithoutCategory, TypeSort.SORT_BY_TITLE);
    }

    public void onSortByAuthor() {
        mStateSort = TypeSort.SORT_BY_AUTHOR;
        ReloadLoader(mIdWithoutCategory, TypeSort.SORT_BY_AUTHOR);
    }

    public void onCancelFilter() {

        mStateSort = TypeSort.NOT_SORT;
        ReloadLoader(mIdWithoutCategory, TypeSort.NOT_SORT);
    }

    @Override
    public void onActionDeleteBook(final int idBook, final String nameBook) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.text_delet_book)
                .setMessage(R.string.text_ask_delete_book)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                       DeleteBook(idBook, mIdWithoutCategory);
                        Snackbar
                                .make(findViewById(R.id.list_wrt),
                                        nameBook + " " +getString(R.string.text_delete_book),
                                        Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }).create().show();

    }

    @Override
    public void onActionMarkedBook(int idBook, String nameBook, boolean read) {
        if(read)
            Snackbar
                    .make(findViewById(R.id.list_wrt),
                            nameBook + " " + getString(R.string.text_finish_read), Snackbar.LENGTH_SHORT)
                    .show();
        else
            Snackbar
                    .make(findViewById(R.id.list_wrt),
                            nameBook + " " + getString(R.string.text_not_finish_read), Snackbar.LENGTH_SHORT)
                    .show();

        mBookDao.SetReadble(read, idBook);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(LIST_ID_KEY, mIdWithoutCategory);
        outState.putInt(TYPE_SORT_ID_KEY, mStateSort.ordinal());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mStateSort = mStateSort.GetTypeSort(savedInstanceState.getInt(TYPE_SORT_ID_KEY));
    }

    public void DeleteBook(int idBook, int idList) {
        mBookDao.deleteBookById(idBook);
        if(mStateSort == TypeSort.NOT_SORT)
        {
            mAdapter.changeCursor(mBookDao.getBooksCursorInList(idList));
            onContentChanged();
            return;
        }

        if(mStateSort == TypeSort.SORT_BY_AUTHOR)
        {
            mAdapter.changeCursor(mBookDao.getBooksCursorInListSortByAuthor(idList));
            onContentChanged();
            return;
        }

        if(mStateSort == TypeSort.SORT_BY_TITLE)
        {
            mAdapter.changeCursor(mBookDao.getBooksCursorInListSortByTitle(idList));
            onContentChanged();
            return;
        }

        if(mStateSort == TypeSort.FILTER_FIHISH_READ)
        {
            mAdapter.changeCursor(mBookDao.getBooksCursorInListFinishRead(idList));
            onContentChanged();
            return;
        }

        if(mStateSort == TypeSort.FILTER_NOT_FINISH_READ)
        {
            mAdapter.changeCursor(mBookDao.getBooksCursorInListNotFinishRead(idList));
            onContentChanged();
            return;
        }
    }
}
