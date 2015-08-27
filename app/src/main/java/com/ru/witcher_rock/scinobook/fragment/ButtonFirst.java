package com.ru.witcher_rock.scinobook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ru.witcher_rock.scinobook.R;
import com.ru.witcher_rock.scinobook.activity.AddBook;
import com.ru.witcher_rock.scinobook.activity.CreateList;

public class ButtonFirst extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_button_first, null);
        Button buttonAddBook = (Button)v.findViewById(R.id.buttonAddBook);
        Button buttonCreateList = (Button)v.findViewById(R.id.buttonCreateList);
        buttonAddBook.setOnClickListener(this);
        buttonCreateList.setOnClickListener(this);

        return v;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonAddBook:
                startActivity(new Intent(getActivity(), AddBook.class));
                break;
            case R.id.buttonCreateList:
                startActivity(new Intent(getActivity(), CreateList.class));
                break;
            default:
                break;
        }
    }
}
