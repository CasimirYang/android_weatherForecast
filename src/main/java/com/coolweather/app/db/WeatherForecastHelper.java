package com.coolweather.app.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yjh on 2015/1/12.
 */
public class WeatherForecastHelper extends SQLiteOpenHelper {

    public WeatherForecastHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public WeatherForecastHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CTEATE_PROVINCE);
        sqLiteDatabase.execSQL(CTEATE_CITY);
        sqLiteDatabase.execSQL(CTEATE_COUNTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    public static final String CTEATE_PROVINCE="CREATE TABLE if not exists \"City\" (\"id\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ON CONFLICT ROLLBACK,\"name\"  TEXT(10),\"code\"  TEXT(10),\"province_id\"  INTEGER,CONSTRAINT \"province_city_foreign\" FOREIGN KEY (\"province_id\") REFERENCES \"Province\" (\"id\") ON DELETE SET NULL ON UPDATE CASCADE);";
    public static final String CTEATE_CITY="CREATE TABLE if not exists \"County\" (\"id\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ON CONFLICT ROLLBACK,\"name\"  TEXT,\"code\"  TEXT,\"city_id\"  INTEGER,CONSTRAINT \"county_city_foreign\" FOREIGN KEY (\"city_id\") REFERENCES \"City\" (\"id\") ON DELETE SET NULL ON UPDATE CASCADE);";
    public static final String CTEATE_COUNTY="CREATE TABLE if not exists \"Province\" (\"id\"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ON CONFLICT ROLLBACK,\"name\"  TEXT(10),\"code\"  TEXT(10));";

}
