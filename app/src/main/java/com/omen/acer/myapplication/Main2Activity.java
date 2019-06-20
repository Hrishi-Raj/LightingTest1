package com.omen.acer.myapplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    TextView city,temp,detail,weather;
    ImageView icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle bundle= getIntent().getExtras();
        assert bundle != null;
        double lat= bundle.getDouble("lat");
        double log= bundle.getDouble("log");
        city = findViewById(R.id.city_field);
        temp = findViewById(R.id.current_temperature_field);
        detail = findViewById(R.id.details_field);
        weather = findViewById(R.id.type);
        icon= findViewById(R.id.icon);

        JSONTask task= new JSONTask(Main2Activity.this);
        task.execute("https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+log+"&appid=72211993449697fef818ed78213ff2c1");

    }
    void setdata(String c,String w,double humid,double wind,double t,double p,Bitmap bitmap)
    {
        city.setText(c);
        weather.setText(w);
        detail.setText("Pressure: "+p+"hPa\nHumidity: "+humid+"%\nWind:"+wind+"m/s");
        temp.setText(t+"'C");
        icon.setImageBitmap(bitmap);
    }


}
