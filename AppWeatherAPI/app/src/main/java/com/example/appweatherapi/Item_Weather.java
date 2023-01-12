package com.example.appweatherapi;

import android.widget.Button;
import android.widget.TextView;

public class Item_Weather {

    private String Day,TT,Max,Min;
    private String icon;

    public Item_Weather(String day, String TT, String max, String min, String icon) {
        Day = day;
        this.TT = TT;
        Max = max;
        Min = min;
        this.icon = icon;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getTT() {
        return TT;
    }

    public void setTT(String TT) {
        this.TT = TT;
    }

    public String getMax() {
        return Max;
    }

    public void setMax(String max) {
        Max = max;
    }

    public String getMin() {
        return Min;
    }

    public void setMin(String min) {
        Min = min;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
