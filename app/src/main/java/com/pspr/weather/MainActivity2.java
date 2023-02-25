package com.pspr.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {

    TextView city,temp,humidity,minTemp,maxTemp,pressure,windSpeed,visibility,descp;
    ImageView descpImage;
    EditText etSearch;
    Button search;
    VideoView videoView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        etSearch = findViewById(R.id.ecity);
        search = findViewById(R.id.search);
        city = findViewById(R.id.city2);
        temp = findViewById(R.id.temp2);
        descp = findViewById(R.id.descp2);
        humidity = findViewById(R.id.Humidity2);
        maxTemp = findViewById(R.id.max_temp2);
        minTemp = findViewById(R.id.min_temp2);
        pressure  = findViewById(R.id.pressure2);
        windSpeed = findViewById(R.id.wind_spped2);
        visibility = findViewById(R.id.visibility2);
        descpImage = findViewById(R.id.descp_image2);
        videoView =  findViewById(R.id.videoView);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cityName = etSearch.getText().toString();
                getWeatherData(cityName);

                etSearch.setText("");

            }
        });

        videoView = findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.clear);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });

    }


    public void getWeatherData(String name){

        WeatherAPI weatherAPI = RetrofitWeather.getClient().create(WeatherAPI.class);
        Call<OpenWeather> call = weatherAPI.getWeatherWithCity(name);


        call.enqueue(new Callback<OpenWeather>() {
            @Override
            public void onResponse(Call<OpenWeather> call, Response<OpenWeather> response) {

                if(response.isSuccessful()){
                    city.setText(response.body().getName()+","+response.body().getSys().getCountry());
                    temp.setText(response.body().getMain().getTemp()+"°C");
                    descp.setText(response.body().getWeather().get(0).getDescription());
                    humidity.setText(response.body().getMain().getHumidity()+"%");
                    maxTemp.setText(response.body().getMain().getTempMax()+"°C");
                    minTemp.setText(response.body().getMain().getTempMin()+"°C");
                    pressure.setText(response.body().getMain().getPressure()+"");
                    windSpeed.setText(response.body().getWind().getSpeed()+"");
                    visibility.setText(response.body().getVisibility()+"");

                    String icon = response.body().getWeather().get(0).getIcon();
                    Picasso.get().load("https://openweathermap.org/img/wn/"+icon+"@2x.png")
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(descpImage);
                    String des = response.body().getWeather().get(0).getDescription();

                    switch(des){
                        case "clear sky":{
                            Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.clear);
                            videoView.setVideoURI(uri);
                            videoView.start();
                            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mediaPlayer) {
                                    mediaPlayer.setLooping(true);
                                }
                            });
                            break;
                        }

                        case "haze":{
                            Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.haze);
                            videoView.setVideoURI(uri);
                            videoView.start();
                            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mediaPlayer) {
                                    mediaPlayer.setLooping(true);
                                }
                            });
                            break;
                        }
                        case "mist":{
                            Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.haze);
                            videoView.setVideoURI(uri);
                            videoView.start();
                            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mediaPlayer) {
                                    mediaPlayer.setLooping(true);
                                }
                            });
                            break;
                        }
                        case "overcast clouds":{
                            Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.overcast);
                            videoView.setVideoURI(uri);
                            videoView.start();
                            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mediaPlayer) {
                                    mediaPlayer.setLooping(true);
                                }
                            });
                            break;
                        }
                        case "broken clouds":{
                            Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.scattered);
                            videoView.setVideoURI(uri);
                            videoView.start();
                            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mediaPlayer) {
                                    mediaPlayer.setLooping(true);
                                }
                            });
                            break;
                        }
                        case "scattered clouds":{
                            Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.scattered);
                            videoView.setVideoURI(uri);
                            videoView.start();
                            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mediaPlayer) {
                                    mediaPlayer.setLooping(true);
                                }
                            });
                            break;
                        }
                        default:{
                            Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.clear);
                            videoView.setVideoURI(uri);
                            videoView.start();
                            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mediaPlayer) {
                                    mediaPlayer.setLooping(true);
                                }
                            });
                        }
                    }





                }

                else{
                    Toast.makeText(MainActivity2.this, "City not found,Please try again", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<OpenWeather> call, Throwable t) {

            }
        });
    }
}