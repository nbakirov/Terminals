package com.example.nurlan.terminals;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nurlan on 13.06.2016.
 */
public class TerminalsDatabase extends SQLiteOpenHelper {

    String sQuery = "CREATE TABLE terminals(id integer primary key, point_name text, lat text, longt text);";

    private static final String DATABASE_NAME = "terminals";
    final String LOG_TAG = "nb_log";
    private static final int DATABASE_VERSION = 1;

    public TerminalsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sQuery = "CREATE TABLE terminals(id integer primary key, point_name text, lat text, longt text);";
        db.execSQL(sQuery);
    }

    public void deleteDatabase() {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE terminals;");
        onCreate(db);
    }

    public void addPoint(Point point) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues cv = new ContentValues();
        cv.put("point_name", point.getPoint_name());
        cv.put("lat", point.getPoint_lat());
        cv.put("longt", point.getPoint_longt());
        db.insert("terminals", null, cv);
        Log.d("Add point", point.toString());
        db.close();
    }

    public List<Point> getAllPoints() {
        Log.d(LOG_TAG, "getAllPoint вхождение");

        List<Point> pointList = new ArrayList<Point>();

        String selectQuery = "SELECT * FROM terminals";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Point point = new Point();
                point.setPoint_id(Integer.valueOf(cursor.getString(0)));
                Log.d(LOG_TAG, "column 0 " + cursor.getString(0));
                point.setPoint_name(cursor.getString(1));
                Log.d(LOG_TAG, "column 1 " + cursor.getString(1));
                point.setPoint_lat(Double.valueOf(cursor.getString(2)));
                Log.d(LOG_TAG, "column 2 " + cursor.getString(2));
                point.setPoint_longt(Double.valueOf(cursor.getString(3)));
                Log.d(LOG_TAG, "column 3 " + cursor.getString(3));
                pointList.add(point);
            } while (cursor.moveToNext());
        }
        Log.d(LOG_TAG, "Point List сформировался");
        db.close();
        return pointList;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
