package com.example.android.sunshine.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Peter on 2016/5/24.
 */
public class PurchaseDbHelper extends SQLiteOpenHelper{
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;

    static final String DATABASE_NAME = "weather.db";

    public PurchaseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude
        final String SQL_CREATE_CUSTOMER_TABLE = "CREATE TABLE " + PurchaseContract.CustomerEntry.TABLE_NAME + " (" +
                PurchaseContract.CustomerEntry.COLUMN_ID_KEY + " INTEGER PRIMARY KEY," +
                PurchaseContract.CustomerEntry.COLUMN_CUS_NAME + " TEXT UNIQUE NOT NULL " +
                " );";

        final String SQL_CREATE_PRODUCT_TABLE = "CREATE TABLE " + PurchaseContract.ProductEntry.TABLE_NAME + " (" +
                PurchaseContract.ProductEntry.COLUMN_LOC_KEY + " INTEGER PRIMARY KEY," +
                PurchaseContract.ProductEntry.COLUMN_NAME + " TEXT UNIQUE NOT NULL, " +
                PurchaseContract.ProductEntry.COLUMN_price + " INTEGER NOT NULL " +
                " );";

        final String SQL_CREATE_RELATION_TABLE = "CREATE TABLE " + PurchaseContract.RelationEntry.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                PurchaseContract.RelationEntry.COLUMN_CUSID + " INTEGER NOT NULL, " +
                PurchaseContract.RelationEntry.COLUMN_PROID + " INTEGER NOT NULL, " +


                // Set up the location column as a foreign key to location table.
                " FOREIGN KEY (" + PurchaseContract.RelationEntry.COLUMN_CUSID + ") REFERENCES " +
                PurchaseContract.CustomerEntry.TABLE_NAME +" (" + PurchaseContract.CustomerEntry.COLUMN_ID_KEY + "), " +

                " FOREIGN KEY (" + PurchaseContract.RelationEntry.COLUMN_PROID  + ") REFERENCES " +
                PurchaseContract.ProductEntry.TABLE_NAME +" (" + PurchaseContract.ProductEntry.COLUMN_LOC_KEY + ") "
                + " );";


        sqLiteDatabase.execSQL(SQL_CREATE_CUSTOMER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_PRODUCT_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_RELATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PurchaseContract.CustomerEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PurchaseContract.ProductEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PurchaseContract.RelationEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
