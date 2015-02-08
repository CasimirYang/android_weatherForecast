package com.coolweather.app.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.coolweather.app.db.WeatherForecastDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.jar.JarException;
import java.util.logging.SimpleFormatter;

/**
 * Created by yjh on 2015/1/18.
 */
public class Utility {
    public synchronized static boolean handleProvincesResponse(WeatherForecastDB weatherForecastDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if ( allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setCode(array[0]);
                    province.setName(array[1]);
                    weatherForecastDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean handleCitiesResponse(WeatherForecastDB weatherForecastDB, String response,int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if ( allCities.length > 0) {
                for (String c : allCities) {
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCode(array[0]);
                    city.setName(array[1]);
                    city.setProvince_id(provinceId);
                    weatherForecastDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }
    public synchronized static boolean handleCountiesResponse(WeatherForecastDB weatherForecastDB, String response,int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCountied = response.split(",");
            if ( allCountied.length > 0) {
                for (String c : allCountied) {
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCode(array[0]);
                    county.setName(array[1]);
                    county.setCity_id(cityId);
                    weatherForecastDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
    public static void handleWeatherResponse(Context context,String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String city = weatherInfo.getString("city");
            String cityid = weatherInfo.getString("cityid");
            String temp1 =  weatherInfo.getString("temp1");
            String temp2 =  weatherInfo.getString("temp2");
            String weather =  weatherInfo.getString("weather");
            String ptime =  weatherInfo.getString("ptime");
            saveWeahterInfo(context,city,cityid,temp1,temp2,weather,ptime);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static void saveWeahterInfo(Context context,String cityName,String WeatherCode,String temp1,String temp2,String weatherDesp,String publishTime){
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy年M月D日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("cityName",cityName);
        editor.putString("WeatherCode",WeatherCode);
        editor.putString("temp1",temp1);
        editor.putString("temp2",temp2);
        editor.putString("weatherDesp",weatherDesp);
        editor.putString("ptime",publishTime);
        editor.putString("current_date",sdf.format(new Date()));
        editor.commit();
    }
}
