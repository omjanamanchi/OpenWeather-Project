package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class SpaceWeather extends AppCompatActivity {

    EditText planet;
    Button search;
    Button earth;
    String line = "";
    String strMars = "";
    String marsURL ="";

    TextView atmospheric_temperatureAVG, atmospheric_temperatureMIN, atmospheric_temperatureMAX, windspeedAVG, windspeedMIN, windspeedMAX, pressureAVG, pressureMIN, pressureMAX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_weather);

        planet = findViewById(R.id.editTextTextPostalAddress2);
        search = findViewById(R.id.search_button);
        earth = findViewById(R.id.earth_button);


        atmospheric_temperatureAVG = findViewById(R.id.atmospheric_temperatureAVG);
        atmospheric_temperatureMIN = findViewById(R.id.atmospheric_temperatureMIN);
        atmospheric_temperatureMAX = findViewById(R.id.atmospheric_temperatureMAX);
        windspeedAVG = findViewById(R.id.windspeedAVG);
        windspeedMIN = findViewById(R.id.windspeedMIN);
        windspeedMAX = findViewById(R.id.windspeedMAX);
        pressureAVG = findViewById(R.id.pressureAVG);
        pressureMIN = findViewById(R.id.pressureMIN);
        pressureMAX = findViewById(R.id.pressureMAX);


        planet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        atmospheric_temperatureAVG.setText("Average: -71.233°");
                        atmospheric_temperatureMIN.setText("MIN: -101.024°");
                        atmospheric_temperatureMAX.setText("MAX: -27.149°");
                        windspeedAVG.setText("Average: 4.35°");
                        windspeedMIN.setText("MIN: 0.156°");
                        windspeedMAX.setText("MIN: 17.617°");
                        pressureAVG.setText("Average: 761.006°");
                        pressureMIN.setText("MIN: 742.1498°");
                        pressureMAX.setText("MAX: 780.3891°");

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        earth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SpaceWeather.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}