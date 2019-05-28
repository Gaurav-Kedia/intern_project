package com.gaurav.intern_project.data;

import android.provider.BaseColumns;

public final class Contract {

    private Contract() {
    }

    public static final class Entry implements BaseColumns {

        public final static String TABLE_NAME = "Workshops";
        public final static String _ID = BaseColumns._ID;
        public final static String WORKSHOP_NAME = "Workshop";
    }
}
