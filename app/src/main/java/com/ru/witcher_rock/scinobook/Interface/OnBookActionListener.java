package com.ru.witcher_rock.scinobook.Interface;

/**
 * Created by Администратор on 18.08.2015.
 */
public interface OnBookActionListener {
    void onActionDeleteBook(int idBook, String nameBook);
    void onActionMarkedBook(int idBook, String nameBook, boolean read);
}
