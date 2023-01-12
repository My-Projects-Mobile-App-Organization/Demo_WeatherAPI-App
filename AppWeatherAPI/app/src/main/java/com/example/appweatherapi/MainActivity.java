package com.example.appweatherapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText edtInput;
    Button btnXN,btnNext;
    ImageView imgicon;
    TextView txtTenTp,txtTenDN,txtTemp,txtStatus,txtDate,txtSpeed,txtHumidity,txtCLoud;
    String City = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Anhxa();

        getDataCurrent("Hanoi");

        btnXN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = edtInput.getText().toString().trim();
                if (city.equals("")){
                    City="Hanoi";
                    getDataCurrent(City);
                } else {
                    City = city;
                    getDataCurrent(City);
                }

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = edtInput.getText().toString().trim();

                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                intent.putExtra("data",city);
                startActivity(intent);
            }
        });

    }

    private void getDataCurrent(String key) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String link = "http://api.openweathermap.org/data/2.5/weather?q="+key+"&units=metric&appid=30cf322341f73cb41e8c70c74c9353d2";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String tenTP = jsonObject.getString("name");
                            txtTenTp.setText("Tên Thành phố: " + tenTP);

                            String date = jsonObject.getString("dt");
                            long l = Long.valueOf(date);
                            Date date1 = new Date(l * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm:ss");
                            String day = simpleDateFormat.format(date1);
                            txtDate.setText(day);

                            JSONArray jsonArray = jsonObject.getJSONArray("weather");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            String status = jsonObject1.getString("main");
                            String icon = jsonObject1.getString("icon");
                            Log.d("A","icon là: " + icon);

                            txtStatus.setText(status);
                            Picasso.with(MainActivity.this).load("http://openweathermap.org/img/wn/"+icon+".png").into(imgicon);

                            JSONObject jsonObject2 = jsonObject.getJSONObject("main");
                            String nhietDo = jsonObject2.getString("temp");
                            String doAm = jsonObject2.getString("humidity");

                            Double temp = Double.valueOf(nhietDo);
                            String NhietDo = String.valueOf(temp.intValue());

                            txtTemp.setText(NhietDo + "°C");
                            txtHumidity.setText(doAm + "%");

                            JSONObject jsonObject4 = jsonObject.getJSONObject("wind");
                            String gio = jsonObject4.getString("speed");
                            txtSpeed.setText(gio + "m/s");

                            JSONObject jsonObject5 = jsonObject.getJSONObject("clouds");
                            String may = jsonObject5.getString("all");
                            txtCLoud.setText(may + "%");

                            JSONObject jsonObject3 = jsonObject.getJSONObject("sys");
                            String datNuoc = jsonObject3.getString("country");
                            txtTenDN.setText("Tên Đất nước: " + datNuoc);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("AAA","Lỗi: " + error.toString());
            }
        });
        requestQueue.add(stringRequest);
    }

    private void Anhxa() {
        txtTenTp = (TextView) findViewById(R.id.txtTenTP);
        txtTenDN = (TextView) findViewById(R.id.txttenDN);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtHumidity = (TextView) findViewById(R.id.txtHumidity);
        txtSpeed = (TextView) findViewById(R.id.txtSpeed);
        txtCLoud = (TextView) findViewById(R.id.txtCloud);
        txtTemp = (TextView) findViewById(R.id.txtNhietDo);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        edtInput = (EditText) findViewById(R.id.edtNhapTP);
        btnXN = (Button) findViewById(R.id.btnXN);
        btnNext = (Button) findViewById(R.id.btnNext);
        imgicon = (ImageView) findViewById(R.id.imgIcon);


    }
}