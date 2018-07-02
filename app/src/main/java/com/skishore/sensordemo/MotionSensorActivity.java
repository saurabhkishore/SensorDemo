package com.skishore.sensordemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MotionSensorActivity extends BaseNavigationDrawerActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer, mGravity, mGyroScope, mLinearAcc, mTypeRotationVector;
    private boolean activityRunning = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNavigationMenuAndSetLayout(R.layout.motion_sensor_layout);
        // Get an instance of the sensor service, and use that to get an instance of
        // a particular sensor.
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mGyroScope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mLinearAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mTypeRotationVector = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    @Override
    public void onResume() {
        super.onResume();
        activityRunning = true;
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGravity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mGyroScope, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mLinearAcc, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mTypeRotationVector, SensorManager.SENSOR_DELAY_NORMAL);
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
                case Sensor.TYPE_ACCELEROMETER:
                    ((TextView)findViewById(R.id.accelerometer_id)).setText("Acceleration force along(including gravity): \n" +
                            "x axis .: " + String.format("%.2f", event.values[0]) + " m/s sq\n" +
                            "y axis .: " + String.format("%.2f", event.values[1]) + " m/s sq\n" +
                            "z axis .: " + String.format("%.2f", event.values[2]) + " m/s sq\n\n");
                    break;
                case Sensor.TYPE_GRAVITY:
                    ((TextView)findViewById(R.id.gravity_id)).setText("Gravity force along: \n" +
                            "x axis .: " + String.format("%.2f", event.values[0]) + " m/s sq\n" +
                            "y axis .: " + String.format("%.2f", event.values[1]) + " m/s sq\n" +
                            "z axis .: " + String.format("%.2f", event.values[2]) + " m/s sq\n\n");
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    ((TextView)findViewById(R.id.gyroscope_id)).setText("Rate of rotation around: \n" +
                            "x axis .: " + String.format("%.2f", event.values[0]) + " rad/s\n" +
                            "y axis .: " + String.format("%.2f", event.values[1]) + " rad/s\n" +
                            "z axis .: " + String.format("%.2f", event.values[2]) + " rad/s\n\n");
                    break;
                case Sensor.TYPE_LINEAR_ACCELERATION:
                    ((TextView)findViewById(R.id.linear_acc_id)).setText("Acceleration force along the: \n" +
                            "x axis .: " + String.format("%.2f", event.values[0]) + " m/s sq\n" +
                            "y axis .: " + String.format("%.2f", event.values[1]) + " m/s sq\n" +
                            "z axis .: " + String.format("%.2f", event.values[2]) + " m/s sq\n\n");
                    break;
                case Sensor.TYPE_ROTATION_VECTOR:
                    ((TextView)findViewById(R.id.rotation_id)).setText("Rotation vector component along: \n" +
                            "x axis .: " + String.format("%.2f", event.values[0]) + " Unitless\n" +
                            "y axis .: " + String.format("%.2f", event.values[1]) + " Unitless\n" +
                            "z axis .: " + String.format("%.2f", event.values[2]) + " Unitless\n\n");
                    break;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
