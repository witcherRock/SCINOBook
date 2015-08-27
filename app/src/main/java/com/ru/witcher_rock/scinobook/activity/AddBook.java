package com.ru.witcher_rock.scinobook.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.ru.witcher_rock.scinobook.R;
import com.ru.witcher_rock.scinobook.dao.BookDao;
import com.ru.witcher_rock.scinobook.enum_name.TypeRead;
import com.ru.witcher_rock.scinobook.fragment.EditTexts;
import com.ru.witcher_rock.scinobook.fragment.SpinnerAndButton;

public class AddBook extends AppCompatActivity implements SpinnerAndButton.EventListenerPushButton{

    private static final String TAG = "AddBook";
    private EditTexts mFragment1;
    private SpinnerAndButton mFragment2;
    private FragmentTransaction fTrans;
    private BookDao mBookDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.custom_back_button);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mBookDao = new BookDao(this);
        mBookDao.open();
        Log.d(TAG, "onCreate - mBookDao.open();");

        if(savedInstanceState == null)
        {
            mFragment1 = new EditTexts();
            mFragment2 = new SpinnerAndButton();
            fTrans = getFragmentManager().beginTransaction();

            fTrans.add(R.id.frameFirstLayoutAddBook, mFragment1);
            fTrans.add(R.id.frameSecondLayoutAddBook, mFragment2);
            fTrans.commit();
        }
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
    public void pushButton(int list_id) {
        Fragment frag1 = getFragmentManager().findFragmentById(R.id.frameFirstLayoutAddBook);

        TextView tV1 = ((TextView) frag1.getView().findViewById(R.id.editTextTitleBook));
        TextView tV2 = ((TextView) frag1.getView().findViewById(R.id.editTextNameAuthor));

        final String titleBook = tV1.getText().toString();
        String nameAuthor = tV2.getText().toString();

        if(titleBook.isEmpty() || nameAuthor.isEmpty())
        {
            if(titleBook.isEmpty())
                tV1.setError(getString(R.string.text_error_input));
            if(nameAuthor.isEmpty())
                tV2.setError(getString(R.string.text_error_input));
            return;
        }

        tV1.setText("");
        tV2.setText("");
        tV1.requestFocus();

        mBookDao.createBook(titleBook, nameAuthor, TypeRead.NO_FINISH_READ, list_id);
        Snackbar
                .make(findViewById(R.id.frameSecondLayoutAddBook),
                        titleBook + " " + getString(R.string.text_save_book), Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mBookDao.open();
        Log.d(TAG, "onResume -  mBookDao.open();");
    }

    @Override
    protected void onPause() {
        super.onPause();

        mBookDao.close();
        Log.d(TAG, "onPause - mBookDao.close();");
    }
}
