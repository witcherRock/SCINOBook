package com.ru.witcher_rock.scinobook.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ru.witcher_rock.scinobook.Interface.OnBookActionListener;
import com.ru.witcher_rock.scinobook.R;
import com.ru.witcher_rock.scinobook.dao.BookDao;
import com.ru.witcher_rock.scinobook.db.BookTable;
import com.ru.witcher_rock.scinobook.enum_name.TypeRead;

public class BookAdapter extends CursorAdapter {

    private BookDao mDao;
    private OnBookActionListener mListener;

    public void setmListener(OnBookActionListener mListener) {
        this.mListener = mListener;
    }

    public BookAdapter(Context context, Cursor c, boolean autoRequery, BookDao dao) {
        super(context, c, autoRequery);
        mDao = dao;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
       return LayoutInflater.from(context).inflate(R.layout.item_list_book, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView bookName = (TextView) view.findViewById(R.id.tvTitleBook);
        TextView bookAuthor = (TextView) view.findViewById(R.id.tvNameAuthor);
        final CheckBox readBox = (CheckBox)view.findViewById(R.id.cbBox);
        ImageButton button = (ImageButton)view.findViewById(R.id.btnDeleteBook);

        final String name = cursor.getString(cursor.getColumnIndex(BookTable.COLUMN_NAME));
        String author = cursor.getString(cursor.getColumnIndex(BookTable.COLUMN_AUTHOR));
        int read = cursor.getInt(cursor.getColumnIndex(BookTable.COLUMN_READBLE));

        final int id = cursor.getInt(cursor.getColumnIndex(BookTable.COLUMN_ID));

        readBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onActionMarkedBook(id, name, readBox.isChecked());
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          mListener.onActionDeleteBook(id, name);
                                      }
                                  }
        );

        bookName.setText(name);
        bookAuthor.setText(author);

        if(read == TypeRead.FINISH_READ.ordinal())
            readBox.setChecked(true);
        else
            readBox.setChecked(false);
    }
}