package com.ru.witcher_rock.scinobook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ru.witcher_rock.scinobook.R;
import com.ru.witcher_rock.scinobook.activity.ListBooks;
import com.ru.witcher_rock.scinobook.activity.WithoutCategory;

public class ButtonSecond extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_button_second, null);
        Button buttonAddBook = (Button)v.findViewById(R.id.buttonListBook);
        Button buttonCreateList = (Button)v.findViewById(R.id.buttonWithoutCategory);
        buttonAddBook.setOnClickListener(this);
        buttonCreateList.setOnClickListener(this);

        return v;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonListBook:
                startActivity(new Intent(getActivity(), ListBooks.class));
                break;
            case R.id.buttonWithoutCategory:
                startActivity(new Intent(getActivity(), WithoutCategory.class));
                break;
            default:
                break;
        }
    }
}
