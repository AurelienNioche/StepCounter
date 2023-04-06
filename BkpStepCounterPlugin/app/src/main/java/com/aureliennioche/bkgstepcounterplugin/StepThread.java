package com.aureliennioche.bkgstepcounterplugin;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.Date;



/**
 * Created by gojuukaze on 16/8/22.
 * Email: i@ikaze.uu.me
 */
public class StepThread extends Thread implements  SensorEventListener {

    String tag = this.getClass().getSimpleName();

    boolean isRegister;
    private SensorManager sensorManager;
    Sensor stepCounter;

    Context context;


    public StepThread(Context context) {
        this.context = context;
        initStepDetector();
    }


    @Override
    public void run() {
        if (!isRegister) {
            sensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_NORMAL);
            isRegister = true;
        }
    }

    public void mystop()
    {
        if (isRegister) {
            sensorManager.unregisterListener(this);
            isRegister = false;
        }


    }

    public void initStepDetector() {

        // today= DateTimeHelper.getToday();
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
//
//        }
        Log.i(tag, "Something is happening");
        long timestamp = event.timestamp;
        Log.i(tag, "Timestamp is"+timestamp);
        final float[] values = event.values;
        for (float item : values) {
            Log.i(tag, "Ta mere: " + item);
        }

    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }
}

