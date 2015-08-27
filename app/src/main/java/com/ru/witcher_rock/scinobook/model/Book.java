package com.ru.witcher_rock.scinobook.model;

import com.ru.witcher_rock.scinobook.enum_name.TypeRead;

public class Book {

    private long mId;
    private String mName;
    private String mAuthor;
    private int mListId;
    private TypeRead mReadble;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String name) {
        this.mAuthor = name;
    }

    public int getList() {
        return mListId;
    }

    public void setList(int lst) {
        this.mListId = lst;
    }

    public TypeRead getReadble() {
        return mReadble;
    }

    public void setReadble(TypeRead rdbl) {
        this.mReadble = rdbl;
    }
}