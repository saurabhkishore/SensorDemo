package com.skishore.sensordemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PostionSensorActivity extends BaseNavigationDrawerActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mMagneticField, mProximity;
    private boolean activityRunning = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNavigationMenuAndSetLayout(R.layout.motion_sensor_layout);
        // Get an instance of the sensor service, and use that to get an instance of
        // a particular sensor.
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((TextView)findViewById(R.id.gyroscope_id)).setVisibility(View.GONE);
        ((TextView)findViewById(R.id.linear_acc_id)).setVisibility(View.GONE);
        ((TextView)findViewById(R.id.rotation_id)).setVisibility(View.GONE);

        activityRunning = true;
        mSensorManager.registerListener(this, mMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
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
                case Sensor.TYPE_MAGNETIC_FIELD:
                    ((TextView)findViewById(R.id.accelerometer_id)).setText("Geomagnetic field strength along: \n" +
                            "x axis .: " + String.format("%.4f", event.values[0]) + " μT\n" +
                            "y axis .: " + String.format("%.4f", event.values[1]) + " μT\n" +
                            "z axis .: " + String.format("%.4f", event.values[2]) + " μT\n\n");
                    break;
                case Sensor.TYPE_PROXIMITY:
                    ((TextView)findViewById(R.id.gravity_id)).setText("Distance from object (cm): " +
                             String.format("%.2f", event.values[0]));
                    break;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
