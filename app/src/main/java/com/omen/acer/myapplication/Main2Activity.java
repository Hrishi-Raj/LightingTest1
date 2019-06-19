package com.omen.acer.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    static TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle bundle= getIntent().getExtras();
        double lat= bundle.getDouble("lat");
        double log= bundle.getDouble("log");
        text = (TextView) findViewById(R.id.text);
        new JSONTask().execute("https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+log+"&appid=72211993449697fef818ed78213ff2c1");

    }
}
