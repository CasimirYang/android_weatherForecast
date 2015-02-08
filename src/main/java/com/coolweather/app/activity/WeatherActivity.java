package com.coolweather.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;
import com.mymodule.weatherforecast.R;


public class WeatherActivity extends Activity implements View.OnClickListener {

    private LinearLayout weatherInfoLayout;
    private TextView cityNameText;
    private TextView publishText;
    private TextView weatherDespText;
    private TextView temp1Text;
    private TextView temp2Text;
    private TextView currentDateText;
    private Button swithCity;
    private Button refreshWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);

        //init widget
        weatherInfoLayout = (LinearLayout)findViewById(R.id.weather_info_layout);
        cityNameText = (TextView)findViewById(R.id.city_name);
        currentDateText = (TextView)findViewById(R.id.current_date);
        publishText =  (TextView)findViewById(R.id.publish_text);
        temp1Text =  (TextView)findViewById(R.id.temp1);
        temp2Text =  (TextView)findViewById(R.id.temp3);
        weatherDespText = (TextView)findViewById(R.id.weather_desp);
        swithCity = (Button)findViewById(R.id.swith_city);
        refreshWeather = (Button)findViewById(R.id.refresh_weather);
        swithCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);

        final String  countyCode = getIntent().getStringExtra("county_code");
        if(!TextUtils.isEmpty(countyCode)){
           // weatherInfoLayout.setVisibility(View.INVISIBLE);
           // cityNameText.setVisibility(View.INVISIBLE);
            Log.d("同步中","同步中...");
            queryWeatherCode(countyCode);
        }else
        {
           showWeather();
           Log.d("直接取本地数据","countyCode is ull");
           //   Toast.makeText(getApplicationContext(), "Update My UI",Toast.LENGTH_LONG).show();
        }
    }

    private void queryWeatherCode(String countyCode){
            String address = "http://www.weather.com.cn/data/list3/city"+countyCode+".xml";
        queryFromServer(address,"countyCode");
    }
    private void queryWeatherInfo(String weatherCode){
        String address = "http://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
        queryFromServer(address,"weatherCode");
    }
    private void queryFromServer(final String address,final String type){
        HttpUtil.sendHttpRequest(address,new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if("countyCode".equals(type)){
                    Log.d("queryFromServer","queryFromServer countyCode");
                    if(!TextUtils.isEmpty(response)){
                        String[] array = response.split("\\|");
                        if(array !=null && array.length == 2){
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                }
                else if("weatherCode".equals(type)){
                    //this 是这个内部类的对象，所以是WeatherActivity.this
                    Utility.handleWeatherResponse(WeatherActivity.this,response);
                    Log.d("queryFromServer","queryFromServer weatherCode");
                    showWeather();
                }
            }
            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishText.setText("同步失败");
                        Log.d("同步失败","queryFromServer同步失败");
                    }
                });
            }
        });
    }

    private void showWeather(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                cityNameText.setText(prefs.getString("cityName",""));
                temp1Text.setText(prefs.getString("temp1",""));
                temp2Text.setText(prefs.getString("temp2",""));
                weatherDespText.setText(prefs.getString("weatherDesp",""));
                publishText.setText("今天"+prefs.getString("ptime","")+"发布");
                currentDateText.setText(prefs.getString("current_date",""));
                weatherInfoLayout.setVisibility(View.VISIBLE);
                cityNameText.setVisibility(View.VISIBLE);
            }
         });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.swith_city:
                Intent intent = new Intent(this, ChooseAreaActivity.class);
                intent.putExtra("from_weatehr_activity", true);
                startActivity(intent);
                finish();
                break;
            case R.id.refresh_weather:
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                String weatherCode = prefs.getString("WeatherCode", "");
                if (!TextUtils.isEmpty(weatherCode)) {
                    Log.d("refreshing", "refresh...");
                    queryWeatherCode(weatherCode);
                    break;
                }
        }
    }
}
