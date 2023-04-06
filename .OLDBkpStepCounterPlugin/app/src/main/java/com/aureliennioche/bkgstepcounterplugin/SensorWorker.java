package com.aureliennioche.bkgstepcounterplugin;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SensorWorker extends Worker implements SensorEventListener {

    String tag = this.getClass().getSimpleName();

    public SensorWorker(
            @NonNull Context context,
            @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Log.i(tag, "supposed to do work but I'm lazy");

        SensorManager sensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        // float lux = event.values[0];
        // Do something with this sensor value.
        Log.i(tag, "Something is happening");
        long timestamp = event.timestamp;
        Log.i(tag, "Timestamp is"+timestamp);
        final float[] values = event.values;
        for (float item : values) {
            Log.i(tag, "Ta mere: "+item);
        }

    }
}
