package com.aureliennioche.bkgstepcounterplugin;
import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.util.Log;


import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.concurrent.TimeUnit;

public class Bridge extends Application {
    String tag = this.getClass().getSimpleName();

    // our native method, which will be called from Unity3D
    public void LaunchSensorWorker(Activity unityPlayerActivity, final String message) {
        Log.i(tag, "trying to fuck ta mere:" + message);
        Intent intent = new Intent(unityPlayerActivity, StepService.class);
        unityPlayerActivity.startService(intent);
//
//        PeriodicWorkRequest workRequest =
//                new PeriodicWorkRequest.Builder(SensorWorker.class, 15, TimeUnit.MINUTES)
//                        .build();
//        WorkManager
//                .getInstance(unityPlayerActivity)
//                .enqueue(workRequest);
    }

    public void setServiceRun(boolean isRunning) {
        Log.i(tag, "ta mere is runnning"+isRunning);
    }
}