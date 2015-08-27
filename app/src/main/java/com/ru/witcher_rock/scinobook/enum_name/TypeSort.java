package com.ru.witcher_rock.scinobook.enum_name;

public enum TypeSort {
    FILTER_FIHISH_READ,
    FILTER_NOT_FINISH_READ,
    SORT_BY_TITLE,
    SORT_BY_AUTHOR,
    NOT_SORT;

    public TypeSort GetTypeSort(int original)
    {
        if(original == NOT_SORT.ordinal())
            return NOT_SORT;

        if(original == SORT_BY_AUTHOR.ordinal())
            return SORT_BY_AUTHOR;

        if(original == SORT_BY_TITLE.ordinal())
            return SORT_BY_TITLE;

        if(original == FILTER_NOT_FINISH_READ.ordinal())
            return FILTER_NOT_FINISH_READ;

        if(original == FILTER_FIHISH_READ.ordinal())
            return FILTER_FIHISH_READ;

        return null;
    }
}
