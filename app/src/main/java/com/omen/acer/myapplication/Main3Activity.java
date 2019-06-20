package com.omen.acer.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    TextView city,type,detail,chance;
    ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Bundle bundle= getIntent().getExtras();
        double lat= bundle.getDouble("lat");
        double log= bundle.getDouble("log");
        city =  findViewById(R.id.city_field);
        type = findViewById(R.id.type);
        detail = findViewById(R.id.details_field);
        icon = findViewById(R.id.icon);
        chance = findViewById(R.id.probability);
        new JSONTask2(Main3Activity.this).execute("https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+log+"&appid=72211993449697fef818ed78213ff2c1");

    }

    public void setdata(String place, String weather, double dewPoint, double cloud_height, Bitmap bitmap) {
        city.setText(place);
        icon.setImageBitmap(bitmap);
        city.setText(place);
        type.setText(weather);
        chance.setText("__%");
        detail.setText("DewPoint: "+dewPoint+"'C\nCloud Height: "+cloud_height+"m");
    }
}
