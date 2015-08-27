package com.ru.witcher_rock.scinobook.fragment;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.ru.witcher_rock.scinobook.R;
import com.ru.witcher_rock.scinobook.adapters.SpinnerAdapter;
import com.ru.witcher_rock.scinobook.dao.ListDao;
import com.ru.witcher_rock.scinobook.loader.SpinnerCursorLoader;
import com.ru.witcher_rock.scinobook.db.ListTable;

public class SpinnerAndButton extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int SPINNER_LOADER_ID = 1;
    private Spinner mSpinnerView;
    private SpinnerAdapter mAdapter;
    private ListDao mListDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        mListDao.open();
    }

    @Override
    public void onPause() {
        super.onPause();

        mListDao.close();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (SPINNER_LOADER_ID == id) {
            return new SpinnerCursorLoader(getActivity(), mListDao, true);
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

    public interface EventListenerPushButton {
         void pushButton(int index_list);
    }
    EventListenerPushButton EventListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_spinner_and_button, null);
        Button button = (Button) v.findViewById(R.id.buttonAddBookInList);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Cursor cursor = (Cursor)mSpinnerView.getSelectedItem();
                EventListener.pushButton(cursor.getInt(cursor.getColumnIndex(ListTable.COLUMN_ID)));
            }
        });


        mListDao = new ListDao(getActivity());
        mListDao.open();

        mSpinnerView = (Spinner) v.findViewById(R.id.spinnerListBook);
        mAdapter = new SpinnerAdapter(getActivity(), null, true);
        mSpinnerView.setAdapter(mAdapter);

        getLoaderManager().initLoader(SPINNER_LOADER_ID, null, this);

        return v;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            EventListener = (EventListenerPushButton) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement EventListenerPushButton");
        }
    }
}
