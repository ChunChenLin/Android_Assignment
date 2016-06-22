package com.example.android.sunshine.app.data;

import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by Peter on 2016/5/24.
 */
public class PurchaseContract {
    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.example.android.sunshine.app";

    // To make it easy to query for the exact date, we normalize all dates that go into
    // the database to the start of the the Julian day at UTC.
    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    /* Inner class that defines the table contents of the location table */
    public static final class CustomerEntry implements BaseColumns {
        // Table name
        public static final String TABLE_NAME = "customer";

        public static final String COLUMN_ID_KEY = "customer_id";
        // Human readable location string, provided by the API.  Because for styling,
        // "Mountain View" is more recognizable than 94043.
        public static final String COLUMN_CUS_NAME = "name";

    }

    /* Inner class that defines the table contents of the weather table */
    public static final class ProductEntry implements BaseColumns {

        public static final String TABLE_NAME = "product";

        // Column with the foreign key into the location table.
        public static final String COLUMN_LOC_KEY = "product_id";
        // Date, stored as long in milliseconds since the epoch

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_price = "price";

    }

    /* Inner class that defines the table contents of the weather table */
    public static final class RelationEntry implements BaseColumns {

        public static final String TABLE_NAME = "relation";

        // Column with the foreign key into the location table.
        public static final String COLUMN_CUSID = "customer_id";
        // Date, stored as long in milliseconds since the epoch

        public static final String COLUMN_PROID = "product_id";

    }
}
