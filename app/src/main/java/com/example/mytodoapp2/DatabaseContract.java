package com.example.mytodoapp2;

import android.provider.BaseColumns;

public class DatabaseContract {

    /* Inner class that defines the table contents */
    public static abstract class PageList implements BaseColumns {
        public static final String TABLE_NAME = "pagememos";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
    }
}
