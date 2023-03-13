package com.example.readsensordata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensorLight;
    private Sensor mSensorProximity;
    private Sensor mSensorTemperature;
    private Sensor mSensorPressure;
    private Sensor mSensorHumidity;

    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;
    private TextView mTextSensorTemperature;
    private TextView mTextSensorPressure;
    private TextView mTextSensorHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sensorText = new StringBuilder();
        for (Sensor currentSensor : sensorList) {
            sensorText.append(currentSensor.getName())
                    .append(System.getProperty("line.separator"));
        }
        TextView sensorTextView = findViewById(R.id.sensor_list);
        sensorTextView.setText(sensorText);

        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);
        mTextSensorTemperature = findViewById(R.id.label_temperature);
        mTextSensorPressure = findViewById(R.id.label_pressure);
        mTextSensorHumidity = findViewById(R.id.label_humidity);

        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mSensorPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mSensorHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        String sensor_error = "No sensor";

        if (mSensorLight == null) {
            mTextSensorLight.setText(sensor_error);
        }
        if (mSensorProximity == null) {
            mTextSensorProximity.setText(sensor_error);
        }
        if (mSensorTemperature == null) {
            mTextSensorTemperature.setText(sensor_error);
        }
        if (mSensorPressure == null) {
            mTextSensorPressure.setText(sensor_error);
        }
        if (mSensorHumidity == null) {
            mTextSensorHumidity.setText(sensor_error);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mSensorLight != null) {
            mSensorManager.registerListener(this, mSensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorProximity != null) {
            mSensorManager.registerListener(this, mSensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorTemperature != null) {
            mSensorManager.registerListener(this, mSensorTemperature,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorPressure != null) {
            mSensorManager.registerListener(this, mSensorPressure,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorHumidity != null) {
            mSensorManager.registerListener(this, mSensorHumidity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];

        switch (sensorType) {
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(
                        String.format("Light sensor : %1$.2f", currentValue));
                changeBackgroundColor(currentValue);
                break;
            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(
                        String.format("Proximity sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                mTextSensorTemperature.setText(
                        String.format("Ambient temperature sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_PRESSURE:
                mTextSensorPressure.setText(
                        String.format("Pressure sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                mTextSensorHumidity.setText(
                        String.format("Relative humidity sensor : %1$.2f", currentValue));
                break;
            default:
                //
        }
    }

    private void changeBackgroundColor(float currentValue) {
        ConstraintLayout layout = findViewById(R.id.layout_constraint);
        if(currentValue <= 40000 && currentValue >= 20000) layout.setBackgroundColor(Color.parseColor("#CA054D"));
        else if(currentValue < 20000 && currentValue >= 10) layout.setBackgroundColor(Color.parseColor("#5DA9E9"));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}