package com.ru.witcher_rock.scinobook.activity;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ru.witcher_rock.scinobook.R;
import com.ru.witcher_rock.scinobook.fragment.ButtonFirst;
import com.ru.witcher_rock.scinobook.fragment.ButtonSecond;

public class Main extends AppCompatActivity {

    private ButtonFirst mFragment1;
    private ButtonSecond mFragment2;
    private FragmentTransaction fTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null)
        {
            mFragment1 = new ButtonFirst();
            mFragment2 = new ButtonSecond();
            fTrans = getFragmentManager().beginTransaction();

            fTrans.add(R.id.frameFirstLayoutMainActivity, mFragment1);
            fTrans.add(R.id.frameSecondLayoutMainActivity, mFragment2);
            fTrans.commit();
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_book_main);

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_exit)
                .setMessage(R.string.text_exit)
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Main.super.onBackPressed();
                    }
                }).create().show();
    }
}
