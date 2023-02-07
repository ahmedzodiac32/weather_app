package com.example.myapplication.Retrofit;

import com.example.myapplication.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("data/2.5/weather")
    Call<WeatherResponse> fetchWeather(@Query("q") String city, @Query("appid") String apiKey);

}
