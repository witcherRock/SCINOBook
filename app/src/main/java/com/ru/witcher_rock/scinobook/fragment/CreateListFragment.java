package com.ru.witcher_rock.scinobook.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ru.witcher_rock.scinobook.Interface.OnListAddedListener;
import com.ru.witcher_rock.scinobook.R;

public class CreateListFragment extends DialogFragment {

    private static final String TAG = CreateListFragment.class.toString();

    private EditText mListName;
    private Button mButtonOk;
    private Button mButtonCancel;

    private OnListAddedListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnListAddedListener) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_list, container, false);

        mListName = (EditText) view.findViewById(R.id.book_name_edit_text);

        mButtonOk = (Button) view.findViewById(R.id.button_ok);
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String listName = mListName.getText().toString();

                if (isValidListName(listName)) {
                    if (mListener != null) {
                        mListener.onListAdded(listName);
                        CreateListFragment.this.dismiss();
                    }
                } else {
                    mListName.setError(getString(R.string.text_error_input));

                }
            }
        });

        mButtonCancel = (Button) view.findViewById(R.id.button_cancel);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateListFragment.this.dismiss();
            }
        });

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.setTitle(getString(R.string.text_create_list));

        return dialog;
    }

    private boolean isValidListName(String listName) {
        return !TextUtils.isEmpty(listName);
    }
}
