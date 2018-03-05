package com.yingying.calculator;

import android.provider.BaseColumns;

/**
 * Created by Yingying Xia on 2016/6/1.
 */
public class TableData {
    public TableData(){

    }
    public static abstract class TableInfo implements BaseColumns{
        public static final String DATE = "DATE";
        public static final String CONTENT = "CONTENT";
        public static final String TABLENAME = "TABLEINFO";
    }
}
