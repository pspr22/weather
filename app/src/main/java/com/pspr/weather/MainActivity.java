package com.pspr.weather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    TextView city,temp,humidity,minTemp,maxTemp,pressure,windSpeed,visibility,descp;
    ImageView descpImage;
    FloatingActionButton add;
    LocationManager locationManager;
    LocationListener locationListener;
    double lat,lon;
    VideoView videoView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city = findViewById(R.id.city);
        temp = findViewById(R.id.temp);
        descp = findViewById(R.id.descp);
        humidity = findViewById(R.id.Humidity);
        maxTemp = findViewById(R.id.max_temp);
        minTemp = findViewById(R.id.min_temp);
        pressure = findViewById(R.id.pressure);
        windSpeed = findViewById(R.id.wind_spped);
        visibility = findViewById(R.id.visibility);
        descpImage = findViewById(R.id.descp_image);
        add = findViewById(R.id.fab);
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

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(i);


            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                lat = location.getLatitude();
                lon = location.getLongitude();


                Log.e("lat :",String.valueOf(lat));
                Log.e("lon :",String.valueOf(lon));
                getWeatherData(lat,lon);


            }
        };

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,500,
                    50,locationListener);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1 && permissions.length>0 && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,500,50,locationListener);
        }

    }

    public void getWeatherData(double lat,double lon){

        WeatherAPI weatherAPI = RetrofitWeather.getClient().create(WeatherAPI.class);
        Call<OpenWeather> call = weatherAPI.getWeatherWithLocation(lat,lon);


        call.enqueue(new Callback<OpenWeather>() {
            @Override
            public void onResponse(Call<OpenWeather> call, Response<OpenWeather> response) {

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

            @Override
            public void onFailure(Call<OpenWeather> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.start();
    }
}