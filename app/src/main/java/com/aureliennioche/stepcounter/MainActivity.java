package com.aureliennioche.stepcounter;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import android.Manifest;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class MainActivity extends AppCompatActivity {

    String tag = this.getClass().getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Begin by requesting the notification permission
        requestNotificationPermission();
    }

    // ------------------------------------------------------------------------------------------ //

    void requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(),
                Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            notificationPermissionHasBeenGranted();
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestNotificationPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS);
        }
    }

    // Register the permissions callback, which handles the user's response to the
    // system permissions dialog. Save the return value, an instance of
    // ActivityResultLauncher, as an instance variable.
    private final ActivityResultLauncher<String> requestNotificationPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    notificationPermissionHasBeenGranted();
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // feature requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                    Log.d(tag, "User is an ass: he refused the notifications");
                }
            });

    void notificationPermissionHasBeenGranted() {
        requestActivityPermission();
    }


    // ------------------------------------------------------------------------------------------ //

    void requestActivityPermission() {
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(),
                Manifest.permission.ACTIVITY_RECOGNITION) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            activityPermissionHasBeenGranted();
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestActivityPermissionLauncher.launch(
                    Manifest.permission.ACTIVITY_RECOGNITION);
        }
    }

    // Register the permissions callback, which handles the user's response to the
    // system permissions dialog. Save the return value, an instance of
    // ActivityResultLauncher, as an instance variable.
    private final ActivityResultLauncher<String> requestActivityPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    activityPermissionHasBeenGranted();
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // feature requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                    Log.d(tag, "User is an ass: he refused the activity monitoring");
                }});

    void activityPermissionHasBeenGranted() {
        launchService();
    }

    // ------------------------------------------------------------------------------ //

    void launchService() {
        Context context = getApplicationContext();
        if (isServiceAlive(context, StepService.class)) {
            Log.d(tag, "Service is already running");
        } else {
            Log.d(tag, "Creating the service");
            Intent intent = new Intent(context, StepService.class);
            context.startForegroundService(intent);

            Log.d(tag, "The service is supposed to be running now");
        }
    }

    @SuppressWarnings("deprecation")
    public static boolean isServiceAlive(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}