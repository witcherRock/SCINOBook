package com.ru.witcher_rock.scinobook.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.ru.witcher_rock.scinobook.R;
import com.ru.witcher_rock.scinobook.db.ListTable;

public class SpinnerAdapter extends CursorAdapter {

    public SpinnerAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView ListName = (TextView) view.findViewById(R.id.TitleList);

        String name = cursor.getString(cursor.getColumnIndex(ListTable.COLUMN_NAME));

        ListName.setText(name);
    }
}
