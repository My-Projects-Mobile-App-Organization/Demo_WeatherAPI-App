package com.example.appweatherapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Item_Adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Item_Weather> item_weathers;

    public Item_Adapter(Context context, int layout, List<Item_Weather> item_weathers) {
        this.context = context;
        this.layout = layout;
        this.item_weathers = item_weathers;
    }

    @Override
    public int getCount() {
        return item_weathers.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(layout,null);

        TextView  txtDay,txtTT,txtMax,txtMin;
        ImageView icon;

        txtDay = (TextView) view.findViewById(R.id.Date);
        txtTT = (TextView) view.findViewById(R.id.Status);
        txtMax = (TextView) view.findViewById(R.id.tempMax);
        txtMin = (TextView) view.findViewById(R.id.tempMin);
        icon = (ImageView) view.findViewById(R.id.Icon);


        Item_Weather item_weather = item_weathers.get(i);

        txtDay.setText(item_weather.getDay());
        txtTT.setText(item_weather.getTT());
        txtMax.setText(item_weather.getMax() + "°C");
        txtMin.setText(item_weather.getMin() + "°C");
        Picasso.with(context).load("http://openweathermap.org/img/wn/"+item_weather.getIcon()+".png").into(icon);

        return view;
    }
}
