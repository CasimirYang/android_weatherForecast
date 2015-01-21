package com.coolweather.app.util;


import android.text.TextUtils;

import com.coolweather.app.db.WeatherForecastDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

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


}
