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

        setdata(bundle.getString("city"),bundle.getString("weather"),bundle.getString("detail"),bundle.getDouble("temp"),(Bitmap)bundle.get("bitmap"));

    }
    void setdata(String c,String w,String d,double t,Bitmap bitmap)
    {
        city.setText(c);
        weather.setText(w);
        detail.setText(d);
        temp.setText(t+"'C");
        icon.setImageBitmap(bitmap);
    }


}
