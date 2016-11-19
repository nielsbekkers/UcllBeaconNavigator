package com.NielsBekkersSkynetBe.UcllbeaconnavigatorIfk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEACONS);
        onCreate(db);
    }

    public void addDevice(BeaconDevice bd){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(KEY_MAJOR,bd.getMajor());
        values.put(KEY_UUID,bd.getUUID());
        values.put(KEY_MINOR,bd.getMinor());
        values.put(KEY_NAME,bd.getName());
        values.put(KEY_LOCATION_TITLE,bd.getKeyLocationTitle());
        values.put(KEY_LOCATION_DESCRIPTION,bd.getLocationDescription());

        db.insertWithOnConflict(TABLE_BEACONS,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }



    public void printTableData(String table_name){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cur = db.rawQuery("SELECT * FROM " + table_name, null);

        if(cur.getCount() != 0){
            cur.moveToFirst();

            do{
                String row_values = "";

                for(int i = 0 ; i < cur.getColumnCount(); i++){
                    row_values = row_values + " || " + cur.getString(i);
                }

                Log.d("DATABASE PRINT", row_values);

            }while (cur.moveToNext());
        }
    }


}

