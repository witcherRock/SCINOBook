package com.ru.witcher_rock.scinobook.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ru.witcher_rock.scinobook.Interface.OnListActionListener;
import com.ru.witcher_rock.scinobook.R;
import com.ru.witcher_rock.scinobook.db.ListTable;

public class ListAdapter extends CursorAdapter {

    private OnListActionListener mListener;

    public void setmListener(OnListActionListener mListener) {
        this.mListener = mListener;
    }

    public ListAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView ListName = (TextView) view.findViewById(R.id.TitleListTv);
        ImageButton deleteBtn = (ImageButton)view.findViewById(R.id.btnDeleteList);

        final String name = cursor.getString(cursor.getColumnIndex(ListTable.COLUMN_NAME));
        final int list_id = cursor.getInt(cursor.getColumnIndex(ListTable.COLUMN_ID));

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onActionDeleteList(list_id, name);
                    }
                });

        ListName.setText(name);
    }
}
