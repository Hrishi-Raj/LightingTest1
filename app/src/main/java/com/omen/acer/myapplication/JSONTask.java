package com.omen.acer.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class JSONTask extends AsyncTask<String,String,String> {

    private final Main2Activity activity;
    private String weather="",place;
    private Bitmap bitmap;
    private double temp,wind_speed,dewPoint,cloud_height,wind_angle,humidity,pressure;

    JSONTask(Main2Activity main2Activity) {
        this.activity = main2Activity;
    }

    @Override
    protected String doInBackground(String... urls)
    {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            URL url= new URL(urls[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder buffer = new StringBuilder();
            String line ;
            while((line= reader.readLine())!=null)
            {
                buffer.append(line);
            }
            String s=buffer.toString();
            JSONObject jo;
            jo = new JSONObject(s);
            place = (String) jo.get("name");
            JSONArray w = (JSONArray) jo.get("weather");

            JSONObject j = (JSONObject) w.get(0);
            weather =  j.getString("main");
            String ic= j.getString("icon");
            JSONObject m = jo.getJSONObject("main");
            temp = (double)Math.round((m.getDouble("temp") - 273.15)*100)/100;
            pressure = (double)Math.round(m.getDouble("pressure"));
            humidity = m.getDouble("humidity");
            JSONObject wind = jo.getJSONObject("wind");
            wind_speed = wind.getDouble("speed");
            wind_angle = wind.getDouble("deg");
            final double a = 6.1121, b = 18.678, c = 257.14, d = 234.5;
            double magnus = Math.log(humidity / 100) + (b - temp / d) * temp / (c + temp);
            dewPoint = c * magnus / (b - magnus);
            dewPoint = (double)Math.round(dewPoint*100)/100;
            cloud_height = Math.abs(dewPoint - temp) * 121.92;
            cloud_height = (double)Math.round(cloud_height*100)/100;
            InputStream st= new java.net.URL("http://openweathermap.org/img/w/"+ ic +".png").openStream();
            bitmap= BitmapFactory.decodeStream(st);
            return s;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
            try{
                if(reader!=null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.setdata(place,weather,humidity,wind_speed,temp,pressure,bitmap);
                }
            });

    }

}
