package com.omen.acer.myapplication;

import android.os.AsyncTask;
import android.widget.TextView;

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

    private String weather="";

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

            return buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
        JSONObject jo;
        try {
            jo = new JSONObject(s);
            String place = (String) jo.get("name");
            JSONArray w = (JSONArray) jo.get("weather");
            for (int i = 0; i < w.length(); i++) {
                JSONObject j = (JSONObject) w.get(i);
                weather += j.get("main") + ", ";
            }
            JSONObject m = jo.getJSONObject("main");
            Double temp = m.getDouble("temp") - 273.15;
            Double pressure = m.getDouble("pressure");
            Double humidity = m.getDouble("humidity");
            JSONObject wind = jo.getJSONObject("wind");
            Double wind_speed = wind.getDouble("speed");
            Double wind_angle = wind.getDouble("deg");
            final double a = 6.1121, b = 18.678, c = 257.14, d = 234.5;
            double magnus = Math.log(humidity / 100) + (b - temp / d) * temp / (c + temp);
            Double dewPoint = c * magnus / (b - magnus);
            Double cloud_height = Math.abs(dewPoint - temp) * 121.92;
            try {
                Main3Activity.text.setText("DewPoint:" + dewPoint + "'C\nCloudHeight:" + cloud_height + "m");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
               Main2Activity.text.setText("Place:" + place + "\nTemprature:" + temp + "'c\nWeather:" + weather + "\nPressure:" + pressure + "hPa\nHumidity:" + humidity + "%\nWind:" + wind_speed + "m/s at <" + wind_angle);
            }
            catch (Exception e)
            {e.printStackTrace();}
        }catch (JSONException e) {
            e.printStackTrace();
        }



    }
}
