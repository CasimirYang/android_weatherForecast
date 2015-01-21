package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yjh on 2015/1/18.
 */
public class HttpUtil {
    public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connecton = null;
                try{
                    URL url = new URL(address);
                    connecton = (HttpURLConnection)url.openConnection();
                    connecton.setRequestMethod("GET");
                    connecton.setReadTimeout(8000);
                    InputStream in = connecton.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    if( listener != null){
                        listener.onFinish(response.toString());
                    }
                }
                catch (Exception e){
                    if(listener != null){
                        listener.onError(e);
                    }
                }
                finally {
                    if(connecton != null){
                        connecton.disconnect();
                    }
                }
            }
        }).start();
    }

}
