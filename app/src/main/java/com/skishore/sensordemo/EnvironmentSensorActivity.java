package com.skishore.sensordemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class EnvironmentSensorActivity extends BaseNavigationDrawerActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mPressure, mLight, mAmbientTemp, mHumidity, mTemp;
    private boolean activityRunning = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNavigationMenuAndSetLayout(R.layout.environment_sensor_layout);
        // Get an instance of the sensor service, and use that to get an instance of
        // a particular sensor.
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mAmbientTemp = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        mTemp = mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
    }

    @Override
    public void onResume() {
        super.onResume();
        activityRunning = true;
        mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mAmbientTemp, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mHumidity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mTemp, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (activityRunning) {

            switch (event.sensor.getType()){
                case Sensor.TYPE_PRESSURE:
                    ((TextView)findViewById(R.id.pressure_id)).setText("Pressure (hPa (millibar)): " + event.values[0]);
                    break;
                case Sensor.TYPE_LIGHT:
                    ((TextView)findViewById(R.id.light_id)).setText("Light (SI lux): " + event.values[0]);
                    break;
                case Sensor.TYPE_AMBIENT_TEMPERATURE:
                    ((TextView)findViewById(R.id.amb_temp_id)).setText("Amb temp (degree Celsius): " + event.values[0]);
                    break;
                case Sensor.TYPE_RELATIVE_HUMIDITY:
                    ((TextView)findViewById(R.id.humidity_id)).setText("Relative humidity (percent): " + event.values[0]);
                    break;
                case Sensor.TYPE_TEMPERATURE:
                    ((TextView)findViewById(R.id.temp_id)).setText("Temperature (degree celcius): " + event.values[0]);
                    break;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
