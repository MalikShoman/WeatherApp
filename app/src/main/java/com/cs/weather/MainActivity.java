package com.cs.weather;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AsyncRequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView txt_date;
    private EditText edt_region;
    private TextView txt_country;
    private ImageButton button_get;
    private ImageView weather_logo;
    private TextView txt_temp2;
    private TextView state;
    public  String Id="";
    private TextView now_temp;
    private TextView wind;
    private TextView hum;
    private TextView air;
    private TextView w_dire;
    ListView lst;
    private TextView next_max;
    private TextView next_min;
    private TextView next_day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        txt_date= findViewById(R.id.txt_date);
        next_day=findViewById(R.id.next_day);
        weather_logo=findViewById(R.id.weather_logo);
        edt_region= findViewById(R.id.search_bar);
        button_get=findViewById(R.id.btn_getWeather);
        txt_country=findViewById(R.id.region);
//      txt_temp2= findViewById(R.id.secondDayTemp);
       // w_dire=findViewById(R.id.w_direction);
        wind =findViewById(R.id.wind);
        now_temp=findViewById(R.id.temp_now);
        hum=findViewById(R.id.hum);
        air=findViewById(R.id.air_pres);
        state= findViewById(R.id.weather_state);
        setDate(txt_date);
        next_max= findViewById(R.id.second_day_maxTemp);
        next_min= findViewById(R.id.second_day_minTemp);
        //lst=findViewById(R.id.lst_days);

       button_get.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               getID();
           }
       });


    }


    public void getID(){

        String url ="https://www.metaweather.com/api/location/search/?query=" + edt_region.getText();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONArray>()  {
                    @Override
                    public void onResponse(JSONArray response) {
                        String ID="";

                        try {
                            JSONObject ob = response.getJSONObject(0);
                            Id=(ob.getString("woeid"));
                            txt_country.setText(ob.getString("title"));
                            //weather_logo.setImageURI(Uri.parse("https://www.metaweather.com/static/img/weather/ico/lr.ico"));
                            

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        getWeather(Id);
                        Toast.makeText(MainActivity.this, "Success"+Id, Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);



    }

    public void getWeather(String id){

        String url = "https://www.metaweather.com/api/location/"+id;


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array1 = response.getJSONArray("consolidated_weather");
                            JSONObject obj1 =array1.getJSONObject(1);

                            next_max.setText(obj1.getString("max_temp").substring(0,2)+"°");
                            next_min.setText(obj1.getString("min_temp").substring(0,2)+"°");

                            JSONArray array = response.getJSONArray("consolidated_weather");
//                            days = new String[array.length()];//to store in listview
//                            for(int i = 0; i<array.length(); i++) {}
                                JSONObject obj = array.getJSONObject(0);
                                    now_temp.setText(obj.getString("the_temp").substring(0,2)+"°");
                                    hum.setText(obj.getString("humidity").substring(0,2)+ "%");
                                    wind.setText(obj.getString("wind_speed").substring(0,2)+ "Km/h");
                                    air.setText(obj.getString("air_pressure").substring(0,2) + " Pa");
                                    state.setText(obj.getString("weather_state_name"));

//                                String weatherDay = "";
//                                weatherDay = "state: " + obj.getString("weather_state_name") +
//                                        "\n, date: " + obj.getString("applicable_date") +
//                                        "\n, min: " + obj.getString("min_temp") +
//                                        ", max: " + obj.getString("max_temp");
//                                days[i] = weatherDay;
//                                Toast.makeText(MainActivity.this, days[1], Toast.LENGTH_LONG).show();
//                            }
//                            ArrayAdapter<String> itemsAdapter =
//                                    new ArrayAdapter<String>(MainActivity.this, android.R.layout.preference_category,
//                                            days);
//                            lst.setAdapter(itemsAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(MainActivity.this, "Handle Error", Toast.LENGTH_SHORT).show();

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);


    }



    public void setDate (TextView view){

        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE,  d");//formating according to my need
        String date = formatter.format(today);
        view.setText(date);
    }





}