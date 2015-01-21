package com.coolweather.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjh on 2015/1/13.
 */
public class WeatherForecastDB {

    public static final String DB_NAME = "Weather Forecast";

    public static final int VERSION =1;

    private static WeatherForecastDB weatherForecastDB;
    private SQLiteDatabase db;

    private WeatherForecastDB(Context context) {
        WeatherForecastHelper dbHelper = new WeatherForecastHelper(context,DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }
    public synchronized  static WeatherForecastDB getInstance(Context context){
        if(weatherForecastDB == null){
            weatherForecastDB =  new WeatherForecastDB(context);
        }
        return weatherForecastDB;
    }


    public void saveProvince(Province province){
        if(province != null){
            ContentValues values = new ContentValues();
            values.put("name",province.getName());
            values.put("code",province.getCode());
             db.insert("Province",null,values);
        }
    }
    public List<Province> loadProvinces(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setName(cursor.getString(cursor.getColumnIndex("name")));
                province.setCode(cursor.getString(cursor.getColumnIndex("code")));
                list.add(province);
            }
            while(cursor.moveToNext());
        }
        return list;
    }

    public void saveCity(City city){
        if(city != null){
            ContentValues values = new ContentValues();
            values.put("name",city.getName());
            values.put("code",city.getCode());
            values.put("province_id",city.getProvince_id());
            db.insert("City",null,values);
        }
    }
    public List<City> loadCities(int provinceId){
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City",null,"province_id = ?",new String[]{String.valueOf(provinceId)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setName(cursor.getString(cursor.getColumnIndex("name")));
                city.setCode(cursor.getString(cursor.getColumnIndex("code")));
                city.setProvince_id(cursor.getInt(cursor.getColumnIndex("province_id")));
                list.add(city);
            }
            while(cursor.moveToNext());
        }
        return list;
    }
    public void saveCounty(County county){
        if(county != null){
            ContentValues values = new ContentValues();
            values.put("name",county.getName());
            values.put("code",county.getCode());
            values.put("city_id",county.getCity_id());
            db.insert("County",null,values);
        }
    }
    public List<County> loadCounties(int cityId){
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County",null,"city_id =?",new String[]{String.valueOf(cityId)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setName(cursor.getString(cursor.getColumnIndex("name")));
                county.setCode(cursor.getString(cursor.getColumnIndex("code")));
                county.setCity_id(cursor.getInt(cursor.getColumnIndex("city_id")));
                list.add(county);
            }
            while(cursor.moveToNext());
        }
        return list;
    }
}
