package com.cs.weather;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class MyAdapter extends ArrayAdapter<String> {

    Context context;
    private String weather_state_name;
    private String applicable_date;
    private String min_temp;
    private String max_temp;

    public MyAdapter(@NonNull Context context, int resource, Context context1, String weather_state_name, String applicable_date, String min_temp, String max_temp) {

        super(context, resource);
        this.context = context1;
        this.weather_state_name = weather_state_name;
        this.applicable_date = applicable_date;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
    }


}
