package com.coolweather.app.util;

/**
 * Created by yjh on 2015/1/18.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
