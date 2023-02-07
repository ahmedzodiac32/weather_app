package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Retrofit.ApiClient;
import com.example.myapplication.Retrofit.ApiInterface;
import com.example.myapplication.model.WeatherResponse;
import com.example.myapplication.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    ImageView search ;
    TextView tempText, descText , humidityText;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.search);
        tempText = findViewById(R.id.textTemp);
        descText = findViewById(R.id.descText);
        humidityText = findViewById(R.id.textHumidity);
        EditText searchText = findViewById(R.id.etSearch);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                String city = searchText.getText().toString();
                Log.d(TAG, "onClick: "+city);
                // here we call API
                ApiInterface apiServiceInstance = ApiClient.getApiServiceInstance();
                apiServiceInstance.fetchWeather(city, Constants.api).enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if(response.isSuccessful()){
                            WeatherResponse body = response.body();
                            viewWeatherUi(body);
                        }else{
                            Log.e(TAG, "onFailure: "+response.code() );
                            Toast.makeText(MainActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        Log.e(TAG, "onFailure: "+t.getMessage() );
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void viewWeatherUi(WeatherResponse response) {
        humidityText.setText(response.getMain().getHumidity().toString());
        tempText.setText(response.getMain().getTemp().toString());
        descText.setText(response.getWeather().get(0).getDescription());
    }
}