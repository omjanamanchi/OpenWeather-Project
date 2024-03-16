package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
{

    //Basic Functionality
    EditText zipcode;
    Button search;
    Button space;
    Spinner options;
    ArrayList<String> optionChoices;

    //TextViews for Data
    TextView city;
    TextView current_temperature;
    TextView longitude_latitude_coordinates;
    TextView date_time;
    TextView quote;

    //Dashboard
    ScrollView trihourly_forecast;
    //TextView interval1, interval2, interval3, interval4;
    TextView interval1Time;
    ImageView interval1Img;
    TextView interval1Low;
    TextView interval1High;

    TextView interval2Time;
    ImageView interval2Img;
    TextView interval2Low;
    TextView interval2High;

    TextView interval3Time;
    ImageView interval3Img;
    TextView interval3Low;
    TextView interval3High;

    TextView interval4Time;
    ImageView interval4Img;
    TextView interval4Low;
    TextView interval4High;

    TextView description;


    //AQI Parameters for "AQI Parameters Tab"
    RelativeLayout max_temperature, feels_like, min_temperature, pressure, visibility, humidity, wind_speed, wind_deg, wind_gust;
    TextView max_temperatureText, feels_likeText, min_temperatureText, pressureText, visibilityText, humidityText, wind_speedText, wind_degText, wind_gustText;


    //Global Variables to set values from API
    String cityName = "";
    double regTemp1 = 0;
    String lon = "";
    String lat = "";


    double max_temperature_value1 = 0;
    double min_temperature_value1 = 0;
    double max_temperature_value2 = 0;
    double min_temperature_value2 = 0;
    double max_temperature_value3 = 0;
    double min_temperature_value3 = 0;
    double max_temperature_value4 = 0;
    double min_temperature_value4 = 0;
    double feels_like_value = 0;
    double pressure_value = 0;
    double visibility_value = 0;
    double humidity_value = 0;
    double wind_speed_value = 0;
    double wind_deg_value = 0;
    double wind_gust_value = 0;

    double regTemp2 = 0;
    double regTemp3 = 0;
    double regTemp4 = 0;
    //String description = "";

    //Strings to read through all the initial complicated stuff
    String line = "";
    String line2 = "";

    //Strings that store API Calls
    String geoURL="";
    String weatherURL ="";
    String strLatLong = "";
    String strWeatherTemperature = "";
    String d = "";

    //ArrayLists
    ArrayList<String> quotes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Declaring Basic Functionality
        zipcode = findViewById(R.id.editTextTextPostalAddress);
        search = findViewById(R.id.search_button);
        space = findViewById(R.id.space_button);

        //Spinner & Spinner Adapter Declarations w/ ArrayList<String>
        options = findViewById(R.id.spinner);
        optionChoices = new ArrayList<>();
        optionChoices.add("Dashboard");
        optionChoices.add("AQI Parameters");

        //TextView Declarations
        city = findViewById(R.id.city);
        current_temperature = findViewById(R.id.current_temperature);
        longitude_latitude_coordinates = findViewById(R.id.longitude_latitutde_cordinates);
        date_time = findViewById(R.id.date_time);
        quote = findViewById(R.id.quote);

        //Dashboard Declarations
        trihourly_forecast = findViewById(R.id.trihourly_forecast);

        interval1Time = findViewById(R.id.interval1Time);
        interval1Img = findViewById(R.id.interval1Img);
        interval1Low = findViewById(R.id.interval1Low);
        interval1High = findViewById(R.id.interval1High);

        interval2Time = findViewById(R.id.interval2Time);
        interval2Img = findViewById(R.id.interval2Img);
        interval2Low = findViewById(R.id.interval2Low);
        interval2High = findViewById(R.id.interval2High);

        interval3Time = findViewById(R.id.interval3Time);
        interval3Img = findViewById(R.id.interval3Img);
        interval3Low = findViewById(R.id.interval3Low);
        interval3High = findViewById(R.id.interval3High);


        interval4Time = findViewById(R.id.interval4Time);
        interval4Img = findViewById(R.id.interval4Img);
        interval4Low = findViewById(R.id.interval4Low);
        interval4High = findViewById(R.id.interval4High);

        description = findViewById(R.id.description);



        //AQI Parameters Declarations
        max_temperature = findViewById(R.id.max_temperature);
        max_temperatureText = findViewById(R.id.max_temperatureText);
        feels_like = findViewById(R.id.feels_like);
        feels_likeText = findViewById(R.id.feels_likeText);
        min_temperature = findViewById(R.id.min_temperature);
        min_temperatureText = findViewById(R.id.min_temperatureText);
        pressure = findViewById(R.id.pressure);
        pressureText = findViewById(R.id.pressureText);
        visibility = findViewById(R.id.visibility);
        visibilityText = findViewById(R.id.visibilityText);
        humidity = findViewById(R.id.humidity);
        humidityText = findViewById(R.id.humidityText);
        wind_speed = findViewById(R.id.wind_speed);
        wind_speedText = findViewById(R.id.wind_speedText);
        wind_deg = findViewById(R.id.wind_deg);
        wind_degText = findViewById(R.id.wind_degText);
        wind_gust = findViewById(R.id.wind_gust);
        wind_gustText = findViewById(R.id.wind_gustText);

        quotes = new ArrayList<>();
        /*1*/ quotes.add("Hey girl, I’m not just going to show you the world, I’ll show you the universe.");
        /*2*/ quotes.add("Do you live on Mars? ‘Cause you look out of this world.");
        /*3*/ quotes.add("Hey girl, are you the sun? Because you’re the center of my universe.");
        /*4*/ quotes.add("Hey beautiful! Your face is like a moon. Always glowing.");
        /*5*/ quotes.add("I think you might be a star, because I can't stop orbiting around you.");
        /*6*/ quotes.add("If I had a star for every time you brightened my day, I'd have a galaxy in my hand.");
        /*7*/ quotes.add("Are you an alien? Because you abducted my heart long ago");
        /*8*/ quotes.add("Who took the stars out of the sky and put them in your eyes?");
        /*9*/ quotes.add("The North Star guided me towards you, and I believe you are destined for me");
        /*10*/ quotes.add("Your smile is like a supernova. Brighter than anything in the universe");


        zipcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                    search.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {

                            try
                            {
                                AsyncThread thread = new AsyncThread();
                                thread.execute();
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SpaceWeather.class);
                startActivity(intent);

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, optionChoices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        options.setAdapter(adapter);

        options.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = options.getSelectedItem().toString();
                //quote.setText("");
                if(selected.equals("Dashboard"))
                {
                    max_temperature.setVisibility(View.GONE);
                    feels_like.setVisibility(View.GONE);
                    min_temperature.setVisibility(View.GONE);
                    pressure.setVisibility(View.GONE);
                    visibility.setVisibility(View.GONE);
                    humidity.setVisibility(View.GONE);
                    wind_speed.setVisibility(View.GONE);
                    wind_deg.setVisibility(View.GONE);
                    wind_gust.setVisibility(View.GONE);
                    trihourly_forecast.setVisibility(View.VISIBLE);
                    quote.setVisibility(View.VISIBLE);
                }


                if(selected.equals("AQI Parameters")) {
                    max_temperature.setVisibility(View.VISIBLE);
                    feels_like.setVisibility(View.VISIBLE);
                    min_temperature.setVisibility(View.VISIBLE);
                    pressure.setVisibility(View.VISIBLE);
                    visibility.setVisibility(View.VISIBLE);
                    humidity.setVisibility(View.VISIBLE);
                    wind_speed.setVisibility(View.VISIBLE);
                    wind_deg.setVisibility(View.VISIBLE);
                    wind_gust.setVisibility(View.VISIBLE);
                    trihourly_forecast.setVisibility(View.GONE);
                    quote.setVisibility(View.INVISIBLE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    public class AsyncThread extends AsyncTask<String, String, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            geoURL = "https://api.openweathermap.org/geo/1.0/zip?zip=" + zipcode.getText() + "&units=imperial&appid=1d46b3c3c66ff51736b1464b4881fd31";

            try
            {
                URL url = new URL(geoURL);
                URLConnection connection = url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                line = bufferedReader.readLine();
                while((bufferedReader.readLine()) != null)
                {
                    //
                    line += bufferedReader.readLine();
                }
                strLatLong = line;
                bufferedReader.close();
                inputStream.close();



            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println(e.getLocalizedMessage());
            }
            line = "";
            weatherURL = "https://api.openweathermap.org/data/2.5/forecast?zip=" + zipcode.getText() + "&units=imperial&appid=1d46b3c3c66ff51736b1464b4881fd31";
            try
            {
                URL url = new URL(weatherURL);
                URLConnection connection = url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                line2 = bufferedReader.readLine();
                while((bufferedReader.readLine()) != null)
                {
                    //
                    line2 += bufferedReader.readLine();
                }

                strWeatherTemperature = line2;
                bufferedReader.close();
                inputStream.close();

            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println(e.getLocalizedMessage());
            }



            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            try
            {

                JSONObject root = new JSONObject(strLatLong);
                cityName = root.get("name").toString();
                lon = root.get("lon").toString();
                lat = root.get("lat").toString();


                JSONObject j = new JSONObject(strWeatherTemperature);

                //Receiving AQI Parameter Values & Setting them
                JSONObject max_tempJSON1 = j.getJSONArray("list").getJSONObject(0).getJSONObject("main");
                max_temperature_value1 = max_tempJSON1.getDouble("temp_max");

                JSONObject min_tempJSON1 = j.getJSONArray("list").getJSONObject(0).getJSONObject("main");
                min_temperature_value1 = min_tempJSON1.getDouble("temp_min");

                JSONObject feels_likeJSON = j.getJSONArray("list").getJSONObject(0).getJSONObject("main");
                feels_like_value = feels_likeJSON.getDouble("feels_like");

                JSONObject pressureJSON = j.getJSONArray("list").getJSONObject(0).getJSONObject("main");
                pressure_value = pressureJSON.getDouble("pressure");

                JSONObject visibilityJSON = j.getJSONArray("list").getJSONObject(0);
                visibility_value = visibilityJSON.getDouble("visibility");

                JSONObject humidityJSON = j.getJSONArray("list").getJSONObject(0).getJSONObject("main");
                humidity_value = humidityJSON.getDouble("humidity");

                JSONObject wind_speedJSON = j.getJSONArray("list").getJSONObject(0).getJSONObject("wind");
                wind_speed_value = wind_speedJSON.getDouble("speed");

                JSONObject wind_degJSON = j.getJSONArray("list").getJSONObject(0).getJSONObject("wind");
                wind_deg_value = wind_degJSON.getDouble("deg");

                JSONObject wind_gustJSON = j.getJSONArray("list").getJSONObject(0).getJSONObject("wind");
                wind_gust_value = wind_gustJSON.getDouble("gust");


                //Date & Time
                String currentDay = "";
                JSONObject day1 = j.getJSONArray("list").getJSONObject(0);
                JSONObject day2 = j.getJSONArray("list").getJSONObject(1);
                JSONObject day3 = j.getJSONArray("list").getJSONObject(2);
                JSONObject day4 = j.getJSONArray("list").getJSONObject(3);
                JSONObject day5 = j.getJSONArray("list").getJSONObject(4);
                currentDay = day1.getString("dt_txt");
                currentDay = currentDay.substring(0, (currentDay.indexOf(" ")) );


                //up1 stores Time
                Long hour1 = day1.getLong("dt");
                String up1 = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(hour1 * 1000));

                Long hour2 = day2.getLong("dt");
                String up2 = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(hour2 * 1000));

                Long hour3 = day3.getLong("dt");
                String up3 = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(hour3 * 1000));

                Long hour4 = day4.getLong("dt");
                String up4 = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(hour4 * 1000));

                Long hour5 = day5.getLong("dt");
                String up5 = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(hour5 * 1000));


                //3 hour based Temperatures
                JSONObject temp1 = j.getJSONArray("list").getJSONObject(0).getJSONObject("main");
                regTemp1 = temp1.getDouble("temp");

                JSONObject temp2 = j.getJSONArray("list").getJSONObject(1).getJSONObject("main");
                regTemp2 = temp2.getDouble("temp");

                JSONObject temp3 = j.getJSONArray("list").getJSONObject(2).getJSONObject("main");
                regTemp3 = temp3.getDouble("temp");

                JSONObject temp4 = j.getJSONArray("list").getJSONObject(3).getJSONObject("main");
                regTemp4 = temp4.getDouble("temp");


                //Weather Description Image
                JSONObject weather1 = j.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0);
                d = weather1.getString("description");
                String des = weather1.getString("description");






                city.setText("City: " + cityName); city.setTextColor(Color.BLACK); city.setTypeface(city.getTypeface(), Typeface.BOLD);  city.setTextSize(16);
                current_temperature.setText("Current Temperature: " + String.valueOf(regTemp1) + "℉"); current_temperature.setTextColor(Color.BLACK); current_temperature.setTypeface(current_temperature.getTypeface(), Typeface.BOLD); current_temperature.setTextSize(16);
                longitude_latitude_coordinates.setText("Long: " + lon + " || Lat: " + lat); longitude_latitude_coordinates.setTextColor(Color.BLACK); longitude_latitude_coordinates.setTypeface(longitude_latitude_coordinates.getTypeface(), Typeface.BOLD); longitude_latitude_coordinates.setTextSize(16);
                date_time.setText("Date: " + currentDay + " || Time: " + up1);
                description.setText("Description: " + des);


                //4 Time Weathers
                JSONObject max_tempJSON2 = j.getJSONArray("list").getJSONObject(1).getJSONObject("main");
                max_temperature_value2 = max_tempJSON2.getDouble("temp_max");
                JSONObject min_tempJSON2 = j.getJSONArray("list").getJSONObject(1).getJSONObject("main");
                min_temperature_value2 = min_tempJSON2.getDouble("temp_min");

                JSONObject max_tempJSON3 = j.getJSONArray("list").getJSONObject(2).getJSONObject("main");
                max_temperature_value3 = max_tempJSON3.getDouble("temp_max");
                JSONObject min_tempJSON3 = j.getJSONArray("list").getJSONObject(2).getJSONObject("main");
                min_temperature_value3 = min_tempJSON3.getDouble("temp_min");

                JSONObject max_tempJSON4 = j.getJSONArray("list").getJSONObject(3).getJSONObject("main");
                max_temperature_value4 = max_tempJSON4.getDouble("temp_max");
                JSONObject min_tempJSON4 = j.getJSONArray("list").getJSONObject(3).getJSONObject("main");
                min_temperature_value4 = min_tempJSON4.getDouble("temp_min");


                interval1Time.setText(up1+"-"+up2);
                //interval1Img.setImageResource();
                interval1Low.setText("L: " + String.valueOf(min_temperature_value1) + "°F");
                interval1High.setText("H: " + String.valueOf(max_temperature_value1) + "°F");
                setImageForDescription(d,0);

                interval2Time.setText(up2+"-"+up3);
                //interval1Img.setImageResource();
                interval2Low.setText("L: " + String.valueOf(min_temperature_value2) + "°F");
                interval2High.setText("H: " + String.valueOf(max_temperature_value2) + "°F");

                weather1 = j.getJSONArray("list").getJSONObject(1).getJSONArray("weather").getJSONObject(0);
                d = weather1.getString("description");
                setImageForDescription(d,1);


                interval3Time.setText(up3+"-"+up4);
                //interval1Img.setImageResource();
                interval3Low.setText("L: " + String.valueOf(min_temperature_value3) + "°F");
                interval3High.setText("H: " + String.valueOf(max_temperature_value3) + "°F");

                weather1 = j.getJSONArray("list").getJSONObject(2).getJSONArray("weather").getJSONObject(0);
                d = weather1.getString("description");
                setImageForDescription(d,2);


                interval4Time.setText(up4+"-"+up5);
                //interval1Img.setImageResource();
                interval4Low.setText("L: " + String.valueOf(min_temperature_value4) + "°F");
                interval4High.setText("H: " + String.valueOf(max_temperature_value4) + "°F");

                weather1 = j.getJSONArray("list").getJSONObject(3).getJSONArray("weather").getJSONObject(0);
                d = weather1.getString("description");
                setImageForDescription(d,3);


                max_temperatureText.setText("L: " + String.valueOf(max_temperature_value1));
                feels_likeText.setText("Feels: " + String.valueOf(feels_like_value));
                min_temperatureText.setText("H: " + String.valueOf(min_temperature_value1));
                pressureText.setText("P: " + String.valueOf(pressure_value));
                visibilityText.setText("V: " + String.valueOf(visibility_value));
                humidityText.setText("Humidity: " + String.valueOf(humidity_value));
                wind_speedText.setText("Speed: " + String.valueOf(wind_speed_value));
                wind_degText.setText("Deg: " + String.valueOf(wind_deg_value));
                wind_gustText.setText("Gust: " + String.valueOf(wind_gust_value));

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }



        }

        public void setImageForDescription(String descriptionCheck, int intDay)
        {

            if(descriptionCheck.contains("clouds"))
            {
                switch (intDay)
                {
                    case 0: interval1Img.setImageResource(R.drawable.cloud); break;
                    case 1: interval2Img.setImageResource((R.drawable.cloud)); break;
                    case 2: interval3Img.setImageResource(R.drawable.cloud); break;
                    case 3: interval4Img.setImageResource((R.drawable.cloud)); break;
                }
                quote.setText(quotes.get(0));
            }
            else if(descriptionCheck.contains("clear"))
            {
                switch (intDay)
                {
                    case 0: interval1Img.setImageResource(R.drawable.clear_sky); break;
                    case 1: interval2Img.setImageResource((R.drawable.clear_sky)); break;
                    case 2: interval3Img.setImageResource(R.drawable.clear_sky); break;
                    case 3: interval4Img.setImageResource((R.drawable.clear_sky)); break;
                }
                quote.setText(quotes.get(1));
            }
            else if(descriptionCheck.contains("drizzle"))
            {
                switch (intDay)
                {
                    case 0: interval1Img.setImageResource(R.drawable.drizzle); break;
                    case 1: interval2Img.setImageResource((R.drawable.drizzle)); break;
                    case 2: interval3Img.setImageResource(R.drawable.drizzle); break;
                    case 3: interval4Img.setImageResource((R.drawable.drizzle)); break;
                }
                quote.setText(quotes.get(2));
            }
            else if(descriptionCheck.contains("rain"))
            {
                switch (intDay)
                {
                    case 0: interval1Img.setImageResource(R.drawable.rain_icon); break;
                    case 1: interval2Img.setImageResource((R.drawable.rain_icon)); break;
                    case 2: interval3Img.setImageResource(R.drawable.rain_icon); break;
                    case 3: interval4Img.setImageResource((R.drawable.rain_icon)); break;
                }
                quote.setText(quotes.get(3));
            }
            else if(descriptionCheck.contains("snow"))
            {
                switch (intDay)
                {
                    case 0: interval1Img.setImageResource(R.drawable.snow); break;
                    case 1: interval2Img.setImageResource((R.drawable.snow)); break;
                    case 2: interval3Img.setImageResource(R.drawable.snow); break;
                    case 3: interval4Img.setImageResource((R.drawable.snow)); break;
                }
                quote.setText(quotes.get(4));
            }
            else if(descriptionCheck.contains("sunny"))
            {
                switch (intDay)
                {
                    case 0: interval1Img.setImageResource(R.drawable.sunny); break;
                    case 1: interval2Img.setImageResource((R.drawable.sunny)); break;
                    case 2: interval3Img.setImageResource(R.drawable.sunny); break;
                    case 3: interval4Img.setImageResource((R.drawable.sunny)); break;
                }
                quote.setText(quotes.get(5));
            }
            else if(descriptionCheck.contains("thunder"))
            {
                switch (intDay)
                {
                    case 0: interval1Img.setImageResource(R.drawable.thunderstorm); break;
                    case 1: interval2Img.setImageResource((R.drawable.thunderstorm)); break;
                    case 2: interval3Img.setImageResource(R.drawable.thunderstorm); break;
                    case 3: interval4Img.setImageResource((R.drawable.thunderstorm)); break;
                }
                quote.setText(quotes.get(6));
            }
            else if(descriptionCheck.contains("light"))
            {
                switch (intDay)
                {
                    case 0: interval1Img.setImageResource(R.drawable.lightning); break;
                    case 1: interval2Img.setImageResource((R.drawable.lightning)); break;
                    case 2: interval3Img.setImageResource(R.drawable.lightning); break;
                    case 3: interval4Img.setImageResource((R.drawable.lightning)); break;

                }
                quote.setText(quotes.get(7));
            }
            else
            {
                switch (intDay)
                {
                    case 0: interval1Img.setImageResource(R.drawable.clear_sky); break;
                    case 1: interval2Img.setImageResource((R.drawable.clear_sky)); break;
                    case 2: interval3Img.setImageResource(R.drawable.clear_sky); break;
                    case 3: interval4Img.setImageResource((R.drawable.clear_sky)); break;

                }
                int random  = (int)(Math.random()*1)+8;
                quote.setText(quotes.get(random));
            }
        }
    }


}