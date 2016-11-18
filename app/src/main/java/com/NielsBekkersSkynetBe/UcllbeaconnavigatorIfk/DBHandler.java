package com.NielsBekkersSkynetBe.UcllbeaconnavigatorIfk;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by r0579260 on 18-11-2016.
 */

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "beaconInfo";
    // Contacts table name
    private static final String TABLE_BEACONS = "beacons";
    // Shops Table Columns names
    private static final String KEY_UUID = "uuid ";
    private static final String KEY_NAME = "name";
    private static final String KEY_MAJOR = "major";
    private static final String KEY_MINOR = "minor";
    private static final String KEY_LOCATION_TITLE = "location_title";
    private static final String KEY_LOCATION_DESCRIPTION = "location_description";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_BEACONS + "(" + KEY_MAJOR + " INTEGER PRIMARY KEY," + KEY_UUID + " TEXT," + KEY_NAME + "TEXT," + KEY_MINOR + "INTEGER," +KEY_LOCATION_TITLE + "TEXT," +KEY_LOCATION_DESCRIPTION + "TEXT," +")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        System.out.print("DATABASE IS AANGEMAAKT");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEACONS);
        onCreate(db);
    }

    public void addDevice(BeaconDevice bd){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(KEY_MAJOR,BeaconDevice.getMajor());
        values.put(KEY_UUID,BeaconDevice.getUUID());
        values.put(KEY_MINOR,BeaconDevice.getMinor());
        values.put(KEY_NAME,BeaconDevice.getName());
        values.put(KEY_LOCATION_TITLE,BeaconDevice.getKeyLocationTitle());
        values.put(KEY_LOCATION_DESCRIPTION,BeaconDevice.getLocationDescription());

        db.insert(TABLE_BEACONS,null,values);
        db.close();
    }
}

