package com.pspr.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("weather?appid=b2c9cbee94cf5ad81d8588846086173b&units=metric")
    Call<OpenWeather> getWeatherWithLocation(@Query("lat")double lat, @Query("lon")double lon);


    @GET("weather?appid=b2c9cbee94cf5ad81d8588846086173b&units=metric")
    Call<OpenWeather> getWeatherWithCity(@Query("q")String name);

}
