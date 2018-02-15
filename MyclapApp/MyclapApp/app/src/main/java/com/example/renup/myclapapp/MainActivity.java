package com.example.renup.myclapapp;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.hardware.SensorEventListener;
import android.hardware.SensorEvent;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import android.media.MediaPlayer;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    //    private Sensor sensor;
    private MediaPlayer mediaPlayer;
    private final static int MAX_VOLUME = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupSensor();
    }

    private void setupSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
        mediaPlayer = MediaPlayer.create(this, R.raw.applause1); //load media app
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null){
            Toast.makeText(getApplicationContext(), "Senor ON", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Sensor OFF", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume()   {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause()    {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] == 0) {
            mediaPlayer.start();
            Toast.makeText(getApplicationContext(), "Phone is near", Toast.LENGTH_SHORT).show();
        } else {
            mediaPlayer.start();
            mediaPlayer.pause();
            Toast.makeText(getApplicationContext(), "Phone is far", Toast.LENGTH_SHORT).show();
        }
    }

    public void closeApp(View v) {
        MainActivity.this.finish();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//        System.out.println(">>>> Accuracy: " + accuracy);
//        float volume = (float)(Math.log(MAX_VOLUME - accuracy)/Math.log(MAX_VOLUME));
//        mediaPlayer.setVolume(volume, volume);
    }
}