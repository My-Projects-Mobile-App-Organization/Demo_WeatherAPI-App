package com.example.appweatherapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {

    String City="";
    Button btnBack;
    ListView listView;
    TextView TP;
    ArrayList<Item_Weather> arrayList;
    Item_Adapter item_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Anhxa();
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        if (data.equals("")){
            City="Hanoi";
            get7DataNext(City);
        } else {
            City = data;
            get7DataNext(City);
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void Anhxa() {
        btnBack = (Button) findViewById(R.id.btnBack);
        listView = (ListView) findViewById(R.id.listView);
        TP = (TextView) findViewById(R.id.txtTP);
        arrayList = new ArrayList<>();
        item_adapter = new Item_Adapter(MainActivity2.this,R.layout.dong_item,arrayList);
        listView.setAdapter(item_adapter);
    }

    private void get7DataNext(String key) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + key + "&appid=30cf322341f73cb41e8c70c74c9353d2";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONObject jsonObject1 = jsonObject.getJSONObject("city");
                            String TP1 = jsonObject1.getString("name");
                            TP.setText("Ten thanh pho: " + TP1);

                            JSONArray jsonArray = jsonObject.getJSONArray("list");
                            for (int i=0;i<jsonArray.length() - 30;i++){
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                                String day1 = jsonObject2.getString("dt");
                                long l = Long.valueOf(day1);
                                Date date2 = new Date(l * 1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                                String day = simpleDateFormat.format(date2);

                                JSONObject jsonObject3 = jsonObject2.getJSONObject("main");
                                String tempMa = jsonObject3.getString("temp_max");
                                String tempMi = jsonObject3.getString("temp_min");

                                Double Max = Double.valueOf(tempMa);
                                String NhietDoMa = String.valueOf(Max.intValue());
                                Double Min = Double.valueOf(tempMi);
                                String NhietDoMi = String.valueOf(Min.intValue());

                                JSONArray jsonArray1 = jsonObject2.getJSONArray("weather");
                                JSONObject jsonObject4 = jsonArray1.getJSONObject(0);
                                String iconHinh = jsonObject4.getString("icon");
                                String mota = jsonObject4.getString("description");

                                arrayList.add(new Item_Weather(day,mota,NhietDoMa,NhietDoMi,iconHinh));
                            }
                            item_adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("AA","lỗi: " + error.toString());
            }
        });
        requestQueue.add(stringRequest);
    }
}